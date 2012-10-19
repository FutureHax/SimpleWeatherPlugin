package com.t3hh4xx0r.simpleweatherplugin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    public static final String PREFERENCES_KEY = "plugin_preferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final SharedPreferences prefs = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_WORLD_WRITEABLE);
		if (!prefs.getBoolean("shouldShow", false)) {
			prefs.edit().putBoolean("shouldShow", true).apply();
		}
	}
}
