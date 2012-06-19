package com.googlecode.neuraid.testingeeg;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.thinkgear.*;

public class TestingEEGActivity extends Activity {
	private BluetoothAdapter bluetoothAdapter;

	private TextView tvp;
	private TextView tv1p;
	private TextView tvr;
	private TextView tv1r;
	private TextView tvh;
	private TextView tv1h;
	private TextView tva;
	private TextView tv1a;
	private TextView tvm;
	private TextView tv1m;
	private TextView tvb;
	private TextView tv1b;
	private TextView tvrc;
	private TextView tv1rc;
	//private TextView tvcs;
	


	private Button b;
	

	private TGDevice tgDevice;
	final boolean rawEnabled = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tvp = (TextView)findViewById(R.id.poortextView1);
		tvp.setText(" Noise: ");
		tvp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tvr = (TextView)findViewById(R.id.rawtextView2);
		tvr.setText(" Raw: ");
		tvr.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tvh = (TextView)findViewById(R.id.hearttextView3);
		tvh.setText(" Heart Rate: ");
		tvh.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tva = (TextView)findViewById(R.id.attentiontextView4);
		tva.setText(" Attention: ");
		tva.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tvm = (TextView)findViewById(R.id.meditationtextView5);
		tvm.setText(" Meditation: ");
		tvm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tvb = (TextView)findViewById(R.id.blinktextView6);
		tvb.setText(" Blink: ");
		tvb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		tvrc = (TextView)findViewById(R.id.rawCounttextView7);
		tvrc.setText(" Raw Count: ");
		tvrc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		

		tv1p = (TextView)findViewById(R.id.poorView1);

		tv1r = (TextView)findViewById(R.id.rawView2);

		tv1h = (TextView)findViewById(R.id.heartView3);

		tv1a = (TextView)findViewById(R.id.attentionView4);

		tv1m = (TextView)findViewById(R.id.meditationView5);

		tv1b = (TextView)findViewById(R.id.blinkView6);

		tv1rc = (TextView)findViewById(R.id.rawCountView7);

		//tvcs = (TextView)findViewById(R.id.tvcs);
		
		b = (Button)findViewById(R.id.button1);
		b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		
		
		
		//tvp.setLayoutParams( new TableRow.LayoutParams( android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 , 1 ) );
		
		//tv.append("Android version: " + Integer.valueOf(android.os.Build.VERSION.SDK) + "\n" );
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(bluetoothAdapter == null) {
			// Alert user that Bluetooth is not available
			Toast.makeText(this, "Bluetooth not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}else {
			/* create the TGDevice */
			tgDevice = new TGDevice(bluetoothAdapter, handler);
		}  
	}

	@Override
	public void onDestroy() {
		tgDevice.close();
		super.onDestroy();
	}
	/**
	 * Handles messages from TGDevice
	 */
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TGDevice.MSG_STATE_CHANGE:

				switch (msg.arg1) {
				case TGDevice.STATE_IDLE:
					break;
				case TGDevice.STATE_CONNECTING:		                	
					b.setText("Connecting...");
					//tvcs.setText("Connecting...");
					//tv.append("Connecting...\n");
					break;		                    
				case TGDevice.STATE_CONNECTED:
					//tv.append("Connected.\n");
					b.setText("Connected");
					tgDevice.start();
					break;
				case TGDevice.STATE_NOT_FOUND:
					b.setText("Cant't find");
					//tv.append("Can't find\n");
					break;
				case TGDevice.STATE_NOT_PAIRED:
					b.setText("not paired");
					//tv.append("not paired\n");
					break;
				case TGDevice.STATE_DISCONNECTED:
					b.setText("Disconnected");
					//tv.append("Disconnected mang\n");
				}

				break;
			case TGDevice.MSG_POOR_SIGNAL:
				//signal = msg.arg1;
				tv1p.setText(" "+ msg.arg1 + " ");
				tv1p.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				//tv.append("PoorSignal: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_RAW_DATA:	  
				//raw1 = msg.arg1;
				tv1r.setText(" "+msg.arg1+ " ");
				tv1r.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				//tv.append("Got raw: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_HEART_RATE:
				tv1h.setText(" "+msg.arg1+ " ");
				tv1h.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				//tv.append("Heart rate: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_ATTENTION:
				tv1a.setText(" "+msg.arg1+ " ");
				tv1a.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				//att = msg.arg1;
				//tv.append("Attention: " + msg.arg1 + "\n");
				//Log.v("HelloA", "Attention: " + att + "\n");
				break;
			case TGDevice.MSG_MEDITATION:
				tv1m.setText(" "+msg.arg1+ " ");
				tv1m.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				//tv.append("Meditation:" + msg.arg1 + "\n"); 
				break;
			case TGDevice.MSG_BLINK:
				tv1b.setText(" "+msg.arg1+ " ");
				tv1b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
				//tv.append("Blink: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_RAW_COUNT:
				tv1rc.setText(" " +msg.arg1+ " ");
				tv1rc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
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

	public void doStuff(View view) {
		if(tgDevice.getState() != TGDevice.STATE_CONNECTING && tgDevice.getState() != TGDevice.STATE_CONNECTED)
			tgDevice.connect(rawEnabled);   
		//tgDevice.ena
	}
}
