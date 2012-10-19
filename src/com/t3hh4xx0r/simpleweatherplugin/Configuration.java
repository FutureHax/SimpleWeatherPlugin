package com.t3hh4xx0r.simpleweatherplugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Configuration extends LinearLayout {
	Context ctx;
    public static final String PREFERENCES_KEY = "plugin_preferences";

	public Configuration(Context context) {
		super(context);
		init(context);
	}

	public Configuration(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Configuration(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context c) {		
		ctx = c;
		final SharedPreferences prefs = ctx.getSharedPreferences(PREFERENCES_KEY, Context.MODE_WORLD_WRITEABLE);

        LayoutInflater layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.config, this);

		ToggleButton b = (ToggleButton) findViewById(R.id.metric_toggle);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean status = ((ToggleButton) v).getText().toString().equals("Using metric measurments");
				prefs.edit().putBoolean("com.t3hh4xx0r.haxlauncher.ui_live_weather_metric", status).apply();
				((ToggleButton) v).setText(prefs.getBoolean("com.t3hh4xx0r.haxlauncher.ui_live_weather_metric", false) ? 
						"Using metric measurments" : "Using imperial measurments");
			}	
		});
		b.setText(prefs.getBoolean("com.t3hh4xx0r.haxlauncher.ui_live_weather_metric", false) ? 
				"Using metric measurments" : "Using imperial measurments");
		b.setChecked(prefs.getBoolean("com.t3hh4xx0r.haxlauncher.ui_live_weather_metric", false));
	}
}
