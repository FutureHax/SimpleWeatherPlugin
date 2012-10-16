package com.t3hh4xx0r.testplugin;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//The class must be named PanelView.
//The class may extend any other type of view.
public class PanelView extends TextView {
	Context c;
	
	public PanelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	public PanelView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public PanelView(Context context) {
		super(context);
		init(context);
	}

	//This is where your view is built. you may set your callbacks and all of that here.
	public void init(Context context) {
		c = context;
		this.setText("WOOT, This is a third party application providing custom views via dynamic class loading.");
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "You clicked the TestPlugin live panel!", Toast.LENGTH_LONG).show();				
			}
		});
	}
}