package com.googlecode.neuraid.neuraidservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class NeuraidServiceActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);

		//Start the Service
		Intent start = new Intent("com.googlecode.neuraid.neuraidservice.NeuraidService");
		NeuraidServiceActivity.this.startService(start);
	}


	@Override
	protected void onDestroy() {
		//Stop the Service
		Intent stop = new Intent("com.googlecode.neuraid.neuraidservice.NeuraidService");
		NeuraidServiceActivity.this.stopService(stop);

		super.onDestroy();


	}
}

