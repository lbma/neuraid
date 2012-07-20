package com.googlecode.neuraid.testingeeg;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.androidplot.Plot;
import com.androidplot.xy.*;
 
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
 
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
	//private ImageView iv;
	
	   // redraws a plot whenever an update is received:
	   private class MyPlotUpdater implements Observer {
	       Plot plot;
	       public MyPlotUpdater(Plot plot) {
	           this.plot = plot;
	       }
	       public void update(Observable o, Object arg) {
	           try {
	        	   Log.d("EEG","trying to post a redraw");
	               plot.postRedraw();
	        	   Log.d("EEG","redraw posted");
	           } catch (InterruptedException e) {
	               e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	           }
	       }
	   }
	 
	   private XYPlot dynamicPlot;
	   private MyPlotUpdater plotUpdater;
	   private SampleDynamicSeries sine1Series;



	private Button b;


	private TGDevice tgDevice;
	final boolean rawEnabled = true;
	
	private SampleDynamicXYDatasource data;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	       // get handles to our View defined in layout.xml:
	       dynamicPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
	 
	       plotUpdater = new MyPlotUpdater(dynamicPlot);
	       
	       // only display whole numbers in domain labels
	       dynamicPlot.getGraphWidget().setDomainValueFormat(new DecimalFormat("0"));
	 
	       // getInstance and position datasets:
	       data = new SampleDynamicXYDatasource();
	       SampleDynamicSeries sine1Series = new SampleDynamicSeries(data, 0, "Meditation");
	       //SampleDynamicSeries sine2Series = new SampleDynamicSeries(data, 1, "Sine 2");
	 
	       dynamicPlot.addSeries(sine1Series, new LineAndPointFormatter(Color.rgb(0, 0, 0), null, null));
	 
	       // create a series using a formatter with some transparency applied:
	       //LineAndPointFormatter f1 = new LineAndPointFormatter(Color.rgb(0, 0, 200), null, Color.rgb(0, 0, 80));
	       //f1.getFillPaint().setAlpha(220);
	       //dynamicPlot.addSeries(sine2Series, f1);
	       //dynamicPlot.setGridPadding(5, 0, 5, 0);
	 
	       //dynamicPlot.addSeries(sine1Series, new BarFormatter(Color.argb(100, 0, 200, 0), Color.rgb(0, 80, 0)));
	       //dynamicPlot.addSeries(sine2Series, new BarFormatter(Color.argb(100, 0, 0, 200), Color.rgb(0, 0, 80)));
	 
	       // hook up the plotUpdater to the data model:
	       data.addObserver(plotUpdater);
	 
	       dynamicPlot.setDomainStepMode(XYStepMode.SUBDIVIDE);
	       dynamicPlot.setDomainStepValue(sine1Series.size());
	       // thin out domain/range tick labels so they dont overlap each other:
	       dynamicPlot.setTicksPerDomainLabel(5);
	       dynamicPlot.setTicksPerRangeLabel(3);
	       //dynamicPlot.getGraphWidget().setTicksPerRangeLabel(3);
	       dynamicPlot.disableAllMarkup();
	 
	       // uncomment this line to freeze the range boundaries:
	       dynamicPlot.setRangeBoundaries(-1000, 1000, BoundaryMode.FIXED);
	 
	       // comment this line to get rid of "panning" or modify
	       // the x/y values to move the view left or right.
	       dynamicPlot.setDomainBoundaries(0,2000, BoundaryMode.FIXED);
	 
	       // kick off the data generating thread:
	       new Thread(data).start();
		
		tvp = (TextView)findViewById(R.id.poortextView1);
		tvr = (TextView)findViewById(R.id.rawtextView2);
		
		tva = (TextView)findViewById(R.id.attentiontextView4);
		tvm = (TextView)findViewById(R.id.meditationtextView5);
		tvb = (TextView)findViewById(R.id.blinktextView6);
		

		tv1p = (TextView)findViewById(R.id.poorView1);
		tv1r = (TextView)findViewById(R.id.rawView2);
		
		tv1a = (TextView)findViewById(R.id.attentionView4);
		tv1m = (TextView)findViewById(R.id.meditationView5);
		tv1b = (TextView)findViewById(R.id.blinkView6);
		

		b = (Button)findViewById(R.id.connect);
		b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

		//iv = (ImageView)findViewById(R.id.background);
		//iv.setAlpha(100);


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
			Log.d("EEG", "received message " + msg.what);
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
				tv1p.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				//tv.append("PoorSignal: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_RAW_DATA:	  
				//raw1 = msg.arg1;
				tv1r.setText(" "+msg.arg1+ " ");
				tv1r.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				data.setY(msg.arg1);
				//tv.append("Got raw: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_HEART_RATE:
				//tv1h.setText(" "+msg.arg1+ " ");
				//tv1h.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				//tv.append("Heart rate: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_ATTENTION:
				tv1a.setText(" "+msg.arg1+ " ");
				tv1a.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				//att = msg.arg1;
				//tv.append("Attention: " + msg.arg1 + "\n");
				//Log.v("HelloA", "Attention: " + att + "\n");
				break;
			case TGDevice.MSG_MEDITATION:
				tv1m.setText(" "+msg.arg1+ " ");
				tv1m.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				//data.setY(msg.arg1);
				//tv.append("Meditation:" + msg.arg1 + "\n"); 
				break;
			case TGDevice.MSG_BLINK:
				tv1b.setText(" "+msg.arg1+ " ");
				tv1b.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
				//tv.append("Blink: " + msg.arg1 + "\n");
				break;
			case TGDevice.MSG_RAW_COUNT:
				//tv1rc.setText(" " +msg.arg1+ " ");
				//tv1rc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
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
