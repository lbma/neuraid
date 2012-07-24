package com.test.g.p.a.calculator.copycat;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.TextView;


public class DataAdapter extends ArrayAdapter<DataHolder> {

	private Activity myContext;
	/* private int credit;
    private int grade;
    private String name;
	 */


	public DataAdapter(Activity context, int textViewResourceId, ArrayList<DataHolder> courses) {
		super(context, textViewResourceId, courses);
		myContext = context;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;

		// Check to see if this row has already been painted once.
		//if (convertView == null) {
			Log.d("Adapter",  "Check to see if this row has already been painted once");


			// If it hasn't, set up everything:
			LayoutInflater inflator = myContext.getLayoutInflater();
			view = inflator.inflate(R.layout.row, null);
			Log.d("Adapter",  "Inflating the row layout");

		//} else {
			//view = convertView;
		//}


		Log.d("Adapter", "Position" + position);

		DataHolder data = getItem(position);
		Log.d("Adapter",  "New DataHolder");

		TextView name = (TextView) view.findViewById(R.id.rowclassname);
		//EditText name = (EditText) view.findViewById(R.id.rowclassname);
		name.setText(data.getCourseName());
		
		Log.d("Adapter", "" + data.getCourseName());
		Spinner spin = (Spinner) view.findViewById(R.id.rowspinner);
		spin.setAdapter(data.getCreditAdapter());
		Log.d("Adapter", "spin" );

		Spinner spin1 = (Spinner) view.findViewById(R.id.rowspinnersecond);
		spin1.setAdapter(data.getGradeAdapter());
		Log.d("Adapter", "spiner1");
		spin.setSelection(data.getSelectedCredit());
		spin1.setSelection(data.getSelectedGrade());


		// Used to handle events when the user changes the Spinner selection:
		
		
		spin.setOnItemSelectedListener(new MyItemSelectedListener(data));

		spin1.setOnItemSelectedListener(new MyGradeItemSelectedListener(data));
		// Update the TextView to reflect what's in the Spinner
		//viewHolder.text.setText(viewHolder.data.getText());


		//This is what gets called every time the ListView refreshes
		Log.d("Adapter", "New Data Holder Created");





		return view;
	}

	private class MyItemSelectedListener implements OnItemSelectedListener {

		private DataHolder d;

		public MyItemSelectedListener(DataHolder d) {
			this.d = d;
		}

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Log.d("Adapter", "set credit");

			d.setSelectedCredit(arg2);
			//viewHolder.text.setText(viewHolder.data.getText());
			// credit = viewHolder.data.getSelectedCredit();

			Log.d("Spinner Credit","" + d.getCreditText());
			// viewHolder.text.setText(viewHolder.data.getText());
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}

	}
	private class MyGradeItemSelectedListener implements OnItemSelectedListener {

		private DataHolder d;

		public MyGradeItemSelectedListener(DataHolder d) {
			this.d = d;
		}

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Log.d("Adapter", "set grade");

			d.setSelectedGrade(arg2);
			//viewHolder.text.setText(viewHolder.data.getText());
			// credit = viewHolder.data.getSelectedCredit();

			Log.d("Spinner Grade","" + d.getGradeText());
			// viewHolder.text.setText(viewHolder.data.getText());
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}

	}
}
