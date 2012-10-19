package com.t3hh4xx0r.simpleweatherplugin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final SharedPreferences prefs = this.getSharedPreferences(getPrefsKey(this), Context.MODE_WORLD_WRITEABLE);
		if (!prefs.getBoolean("shouldShow", false)) {
			prefs.edit().putBoolean("shouldShow", true).apply();
		}
	}
	
	public static String getPrefsKey(Context c) {
		return c.getPackageName()+"_plugin_preferences";
	}
}
