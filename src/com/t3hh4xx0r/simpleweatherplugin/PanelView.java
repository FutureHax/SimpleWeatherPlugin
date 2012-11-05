package com.t3hh4xx0r.simpleweatherplugin;

import java.io.IOException;
import java.util.Date;

import org.w3c.dom.Document;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PanelView extends RelativeLayout implements OnClickListener {

    private TextView mWeatherCity, mWeatherCondition, mWeatherLowHigh, mWeatherTemp, mUpdateTime;
    private ImageView mWeatherImage;
    private View mWeatherPanel;
	private static final String PANEL_UPDATE = "com.t3hh4xx0r.haxlauncher.PANEL_UPDATE";

	public PanelView(Context context) {
		super(context);
		init(context);
	}		
	
	public void init(Context context) {		    
		   LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		   mWeatherPanel = layoutInflater.inflate(R.layout.weather_lp, this);
		   mWeatherPanel.setOnClickListener(this);
	       mWeatherPanel = (RelativeLayout) findViewById(R.id.weather_panel);
	       mWeatherCity = (TextView) findViewById(R.id.weather_city);
	       mWeatherCondition = (TextView) findViewById(R.id.weather_condition);
	       mWeatherImage = (ImageView) findViewById(R.id.weather_image);
	       mWeatherTemp = (TextView) findViewById(R.id.weather_temp);
	       mWeatherLowHigh = (TextView) findViewById(R.id.weather_low_high);
	       mUpdateTime = (TextView) findViewById(R.id.update_time);
	       refreshWeather(false);
	}
    
    /*
     * CyanogenMod Lock screen Weather related functionality
     */
    private static final String URL_YAHOO_API_WEATHER = "http://weather.yahooapis.com/forecastrss?w=%s&u=";
    private static WeatherInfo mWeatherInfo = new WeatherInfo();
    public final static int QUERY_WEATHER = 0;
    
    public void updateWeather(String woeid) {
 	   if (woeid != null) {
           WeatherInfo w = null;
           try {
               w = parseXml(getDocument(woeid));
           } catch (Exception e) {
           	e.printStackTrace();
           }
           if (w == null) {
               setNoWeatherData();
           } else {
               setWeatherData(w);
               mWeatherInfo = w;
           }
       } else {
           if (mWeatherInfo.temp.equals(WeatherInfo.NODATA)) {
               setNoWeatherData();
           } else {
               setWeatherData(mWeatherInfo);
           }
       }
    }
    
    /**
     * Reload the weather forecast
     */
    public void refreshWeather(boolean force) {    
    	final long interval = getWeatherInterval(getContext()); // Default to hourly
        if ((((System.currentTimeMillis() - mWeatherInfo.last_sync) / 60000) >= interval) || (force)) {
        	updateWeather(getWoeid());        	
        } else {
            setWeatherData(mWeatherInfo);
        }
    }

    private String getWoeid() {
    	LocationManager locationManager = (LocationManager) getLauncherContext().
                getSystemService(Context.LOCATION_SERVICE);
        //change me
        boolean useCustomLoc = false;
        String customLoc = null;
        String woeid = null;

        // custom location
        if (customLoc != null && useCustomLoc) {
            try {
                woeid = YahooPlaceFinder.GeoCode(getContext().getApplicationContext(), customLoc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        // network location
        } else {
            Criteria crit = new Criteria();
            crit.setAccuracy(Criteria.ACCURACY_COARSE);
            String bestProvider = locationManager.getBestProvider(crit, true);
            Location loc = null;
            if (bestProvider != null) {
                loc = locationManager.getLastKnownLocation(bestProvider);
            } else {
                loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
            try {
                woeid = YahooPlaceFinder.reverseGeoCode(getContext(), loc.getLatitude(),
                        loc.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }		
        return woeid;
	}

	/**
     * Display the weather information
     * @param w
     */
    private void setWeatherData(WeatherInfo w) {
        final Resources res = getContext().getResources();
        if (mWeatherPanel != null) {
            if (mWeatherCity != null) {
                mWeatherCity.setText(w.city);
                mWeatherCity.setVisibility(View.VISIBLE);
            }
            if (mWeatherCondition != null) {
                mWeatherCondition.setText(w.condition);
            }
            if (mUpdateTime != null) {
                Date lastTime = new Date(mWeatherInfo.last_sync);
                String date = DateFormat.getDateFormat(getContext()).format(lastTime);
                String time = DateFormat.getTimeFormat(getContext()).format(lastTime);
                mUpdateTime.setText(date + " " + time);
                mUpdateTime.setVisibility(View.GONE);
            }
            if (mWeatherTemp != null) {
                mWeatherTemp.setText(w.temp);
            }
            if (mWeatherLowHigh != null) {
                mWeatherLowHigh.setText(w.low + " | " + w.high);
            }

            if (mWeatherImage != null) {
                String conditionCode = w.condition_code;
                String condition_filename = "weather_" + conditionCode;
                int resID = res.getIdentifier(condition_filename, "drawable",
                        getContext().getPackageName());


                if (resID != 0) {
                    mWeatherImage.setImageDrawable(res.getDrawable(resID));
                } else {
                    mWeatherImage.setImageResource(R.drawable.weather_na);
                }
            }

            // Show the Weather panel view
            mWeatherPanel.setVisibility(View.VISIBLE);
        }
    }

    /**
     * There is no data to display, display 'empty' fields and the
     * 'Tap to reload' message
     */
    private void setNoWeatherData() {
        boolean useMetric = getUseMetric(getContext());

        if (mWeatherPanel != null) {
            if (mWeatherCity != null) {
                mWeatherCity.setText("CM Weather");  //Hard coding this on purpose
                mWeatherCity.setVisibility(View.VISIBLE);
            }
            if (mWeatherCondition != null) {
                mWeatherCondition.setText("Tap to refresh.");
            }
            if (mUpdateTime != null) {
                mUpdateTime.setVisibility(View.GONE);
            }
            if (mWeatherTemp != null) {
                mWeatherTemp.setText(useMetric ? "0°c" : "0°f");
            }
            if (mWeatherLowHigh != null) {
                mWeatherLowHigh.setText("0° | 0°");
            }
            if (mWeatherImage != null) {
                mWeatherImage.setImageResource(R.drawable.weather_na);
            }

            // Show the Weather panel view
            mWeatherPanel.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Get the weather forecast XML document for a specific location
     * @param woeid
     * @return
     */
    private Document getDocument(String woeid) {
        try {
            boolean celcius = getUseMetric(getContext());
            String urlWithDegreeUnit;

            if (celcius) {
                urlWithDegreeUnit = URL_YAHOO_API_WEATHER + "c";
            } else {
                urlWithDegreeUnit = URL_YAHOO_API_WEATHER + "f";
            }

            return new HttpRetriever().getDocumentFromURL(String.format(urlWithDegreeUnit, woeid));
        } catch (IOException e) {
        	e.printStackTrace();
        }

        return null;
    }

    /**
     * Parse the weather XML document
     * @param wDoc
     * @return
     */
    private WeatherInfo parseXml(Document wDoc) {
        try {
            return new WeatherXmlParser(getContext()).parseWeatherResponse(wDoc);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
	public static int getWeatherInterval(Context context) {
		final SharedPreferences preferences = context.getSharedPreferences(MainActivity.getPrefsKey(context), Context.MODE_WORLD_WRITEABLE);
		return preferences.getInt("com.t3hh4xx0r.haxlauncher.ui_live_weather_interval", 60);
	}        	
	public static boolean getUseMetric(Context context) {
		final SharedPreferences preferences = context.getSharedPreferences(MainActivity.getPrefsKey(context), Context.MODE_WORLD_WRITEABLE);
		return preferences.getBoolean("com.t3hh4xx0r.haxlauncher.ui_live_weather_metric", false);
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent();
    	Bundle b = new Bundle();
    	b.putString("name", v.getContext().getPackageName());
    	i.putExtras(b);
    	i.setAction(PANEL_UPDATE);
    	this.getContext().sendBroadcast(i);
    } 

	private Context getLauncherContext() {
		Context forgeinContext = null;
        try {
			forgeinContext = this.getContext().createPackageContext("com.t3hh4xx0r.haxlauncher", Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}  

		return forgeinContext;
	}
}
