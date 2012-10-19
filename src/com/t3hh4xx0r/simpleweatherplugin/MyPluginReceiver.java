package com.t3hh4xx0r.simpleweatherplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class MyPluginReceiver extends BroadcastReceiver {
	private static final String PANEL_REQUEST = "com.t3hh4xx0r.haxlauncher.PANEL_REQUEST";
	private static final String PANEL_REGISTER = "com.t3hh4xx0r.haxlauncher.PANEL_REGISTER";
		
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(PANEL_REQUEST)) {
//			When panels are requested, send back an intent with a bundle containing the info below.
			PackageInfo pInfo = null;
			try {
				pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent i = new Intent();
			i.setAction(PANEL_REGISTER);
			Bundle b = new Bundle();
			String author_name_value = "r2DoesInc";			
			String version_value = pInfo.versionName;
			String plugin_name_value = "Simple Weather Plugin";
			String description_value = "Simple weather panel.";
			b.putString("author_name", author_name_value);
			b.putString("version", version_value);
			b.putString("description", description_value);
			b.putString("plugin_name", plugin_name_value);
			b.putString("package_name", context.getPackageName());
			i.putExtras(b);
			context.sendBroadcast(i); 
		}
	}

}
