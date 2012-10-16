package com.t3hh4xx0r.testplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyPluginReceiver extends BroadcastReceiver {
	private static final String PANEL_REQUEST = "com.t3hh4xx0r.haxlauncher.PANEL_REQUEST";
	private static final String PANEL_REGISTER = "com.t3hh4xx0r.haxlauncher.PANEL_REGISTER";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		Log.d("ON RECEIVER", action);
		if (action.equals(PANEL_REQUEST)) {
//			When panels are requested, send back an intent with a bundle containing your name and the plugins name
			Intent i = new Intent();
			i.setAction(PANEL_REGISTER);
			Bundle b = new Bundle();
			String author_name_value = "r2DoesInc";
			String plugin_name_value = "MyExample Plugin";
			b.putString("author_name", author_name_value);
			b.putString("plugin_name", plugin_name_value);
			i.putExtras(b);
			context.sendBroadcast(i); 
		}
	}

}
