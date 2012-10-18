package com.t3hh4xx0r.testplugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

//Class must be named Configuration.java. May extend any type of view.
public class Configuration extends LinearLayout {
	Context ctx;
	EditText et;
	String defaultText = "WOOT, This is a third party application providing custom views via dynamic class loading.";

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
//		this can be as complicated of a view as you would like, preference screens, anything. 
//		This must work as a standalone view for it to render in the settings corrently.
		
		ctx = c;
		final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        LayoutInflater layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.config, this);
		
		et = (EditText) findViewById(R.id.text);
		et.setText(prefs.getString("panel_text", defaultText));
		Button done = (Button) findViewById(R.id.set);
		done.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				prefs.edit().putString("panel_text", et.getText().toString()).apply();
			}
		});
	}

}
