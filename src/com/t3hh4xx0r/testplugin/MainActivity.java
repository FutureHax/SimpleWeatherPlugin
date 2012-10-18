package com.t3hh4xx0r.testplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//Just needs some activity so that the broadcat intents can be registered.
		//If you want to put a little "about" page with your info here, you can go ahead and do that.
	}
}
