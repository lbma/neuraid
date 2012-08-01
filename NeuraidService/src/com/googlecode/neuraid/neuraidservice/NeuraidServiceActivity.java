package com.googlecode.neuraid.neuraidservice;


import java.util.ArrayList;
import android.support.v4.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class NeuraidServiceActivity extends FragmentActivity {
	/** Called when the activity is first created. */
	private Button sendemailb;
	private DatabaseHandler  db;
	private static String userdata;
	private EditText address;
	private EditText subject;
	private TextView emailtext;
	private Button wipealldatabutton;
	private Button connect;
	private Button disconnect;
	private boolean connected;

	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.main);
		Log.d("Create", "set content view to main.xml");
		
		connected = false;

		db = new DatabaseHandler(this);
		sendemailb = (Button)findViewById(R.id.emailsendbutton);
		sendemailb.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userdata = db.getAllData();
//				Log.d("OnClick", "got all data");

				//emailtext.setText("" + userdata.toString());
				emailtext.setText(userdata);
				//Log.d("OnClick", "email text says bye service");



				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

				emailIntent.setType("text/plain");

				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ address.getText().toString()});

				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());

				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());

				NeuraidServiceActivity.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

			}
		});
		wipealldatabutton = (Button)findViewById(R.id.wipealldatabutton);
		wipealldatabutton.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				FragmentManager fm3 = getSupportFragmentManager();
				DeleteCourseDialog dcDialog = new DeleteCourseDialog();
				dcDialog.show(fm3, "fragment_edit_name");
			}
		});
		emailtext = (TextView) findViewById(R.id.emailtext);
		address = (EditText) findViewById(R.id.emailaddress);
		address.setText("dadley2@my.centenary.edu");
		subject = (EditText) findViewById(R.id.emailsubject);
		Log.d("Create", "Button and textview creadted");

		connect = (Button)findViewById(R.id.connect);
		connect.setOnClickListener(new OnClickListener(){
			
			public void onClick(View v){
		
		//Start the Service
		Intent start = new Intent("com.googlecode.neuraid.neuraidservice.NeuraidService");
		Log.d("Create", "New intent started");

		NeuraidServiceActivity.this.startService(start);
		Log.d("Create", "Service started");
		
		connected = true;
		

			}});
		
		disconnect = (Button) findViewById(R.id.disconnect);
		disconnect.setOnClickListener(new OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (connected == false){
					Toast.makeText(getApplicationContext(),"Service Not Started", Toast.LENGTH_LONG).show();
				} else {
				onDestroy();
				connected = false;
				Toast.makeText(getApplicationContext(),"Service Stopped",Toast.LENGTH_LONG).show();
				}
			}
			
		});
		
	}



	@Override
	protected void onDestroy() {
		//Stop the Service
		Intent stop = new Intent("com.googlecode.neuraid.neuraidservice.NeuraidService");
		NeuraidServiceActivity.this.stopService(stop);

		super.onDestroy();


	}

	public void deleteAll(){
		deleteDatabase("NeuroManager");
		//deleteDatabase(db.getDatabaseName());
		Log.d("DeleteAll", "All courses have been selected");

	}

	

}




