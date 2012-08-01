/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.googlecode.neuraid.neuraidservice;

import com.neurosky.thinkgear.TGDevice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 
 * @author openmobster@gmail.com
 */
public class NeuraidService extends Service {
	private boolean foundbluetooth = false;
	private TGDevice tgDevice;
	final boolean rawEnabled = true;
	private BluetoothAdapter bluetoothAdapter;
	private DatabaseHandler db;
	
	


	@Override
	public IBinder onBind(Intent intent) 
	{
		//Not implemented...this sample is only for starting and stopping services.
		//Service binding will be covered in another tutorial
		return null;
	}


	@Override
	public void onCreate() 
	{
		super.onCreate();
		Toast.makeText(this, "Creating the Neuraid Service", Toast.LENGTH_SHORT).show();

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if(bluetoothAdapter == null) {
			// Alert user that Bluetooth is not available
			Toast.makeText(this, "Bluetooth not available", Toast.LENGTH_LONG).show();
		}else {
			/* create the TGDevice */
			foundbluetooth = true;
			tgDevice = new TGDevice(bluetoothAdapter, handler);
		}  
		
		db = new DatabaseHandler(this);
		


			
		 /**
        * CRUD Operations
        * */
       // Inserting Headset Data
		Log.d("Insert: ", "Inserting ..");
		
		
		
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		super.onStartCommand(intent, flags, startId);

		//Announcement about starting
		Toast.makeText(this, "Starting the Neuraid Service", Toast.LENGTH_SHORT).show();

		if(foundbluetooth){

			if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
				tgDevice.connect(rawEnabled);   
		}

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public void onDestroy() 
	{
		super.onDestroy();

		// Disconnect the Bluetooth headset
		if(foundbluetooth){
			tgDevice.close();
		}

		//Announcement about stopping
		Toast.makeText(this, "Stopping the Neuraid Service", Toast.LENGTH_SHORT).show();
	}

	/**
	 * Handles messages from TGDevice
	 */
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//Log.d("EEG", "received message " + msg.what);
			switch (msg.what) {
			case TGDevice.MSG_STATE_CHANGE:

				switch (msg.arg1) {
				case TGDevice.STATE_IDLE:
					break;
				case TGDevice.STATE_CONNECTING:		                	

					//NeuraidServiceActivity.connect.setText("Connecting...");
					//tv.append("Connecting...\n");
					break;		                    
				case TGDevice.STATE_CONNECTED:
					//tv.append("Connected.\n");

					tgDevice.start();
					break;
				case TGDevice.STATE_NOT_FOUND:

					//tv.append("Can't find\n");
					break;
				case TGDevice.STATE_NOT_PAIRED:

					//tv.append("not paired\n");
					break;
				case TGDevice.STATE_DISCONNECTED:

					//tv.append("Disconnected mang\n");
				}

				break;
			case TGDevice.MSG_POOR_SIGNAL:
				//signal = msg.arg1;
				Log.d("TGDevice", "Signal = " + msg.arg1);
//				if (msg.arg1 != 0){
//					Toast.makeText(getApplicationContext(), "Signal = " + msg.arg1 , Toast.LENGTH_SHORT).show();
//				}
				
				//tv.append("PoorSignal: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_RAW_DATA:	  
				//raw1 = msg.arg1;
				//tv.append("Got raw: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_HEART_RATE:

				//tv.append("Heart rate: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_ATTENTION:
				db.addData("Attention", msg.arg1, System.currentTimeMillis());
				//att = msg.arg1;
				//tv.append("Attention: " + msg.arg1 + "\n");
				Log.d("TGDevice", "Attention = " + msg.arg1);
				break;
			case TGDevice.MSG_MEDITATION:
				db.addData("Meditation", msg.arg1, System.currentTimeMillis());
				Log.d("TGDevice", "Meditaion = "+  msg.arg1);
				//data.setY(msg.arg1);
				//tv.append("Meditation:" + msg.arg1 + "\n"); 
				break;
			case TGDevice.MSG_BLINK:
				//tv.append("Blink: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_RAW_COUNT:

				//tv.append("Raw Count: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_LOW_BATTERY:
				Toast.makeText(getApplicationContext(), "Low battery!", Toast.LENGTH_SHORT).show();
				break;
			case TGDevice.MSG_RAW_MULTI:
				//TGRawMulti rawM = (TGRawMulti)msg.obj;
				//tv.append("Raw1: " + rawM.ch1 + "\nRaw2: " + rawM.ch2);
			default:
				break;
			}
		}
	};



}
