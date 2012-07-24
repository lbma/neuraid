package com.test.g.p.a.calculator.copycat;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class numberofcoursesdialog extends DialogFragment{

	private double numberofcourses;
	private EditText numberofcoursesE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
		View view = inflater.inflate(R.layout.numberofcourses, container);

		numberofcoursesE = (EditText) view.findViewById(R.id.numberofcourses);
		getDialog().setTitle("Enter Number of Enrolled Courses?");

		// removes the title bar from the dialog
		//getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


		// Initialize the Button with this
		Button submit = (Button) view.findViewById (R.id.Submit);

		// Set up reaction for the Button to be pressed
		submit.setOnClickListener(new OnClickListener() {
			// When Yes is Clicked it adds the new info the the Array List
			public void onClick(View arg0) {
				MainActivity ma = (MainActivity) getActivity();
				// Change the EditText number of courses to a double
				if(numberofcoursesE.getText().toString().equals("")){
					Toast.makeText(ma,"Please Enter Number Of Courses",Toast.LENGTH_LONG);
				}else{

					numberofcourses = Integer.parseInt(numberofcoursesE.getText().toString());
					Log.d("Number of courses in frag", "" + numberofcourses);

					if (numberofcourses < 0){

						Toast.makeText(ma, "Please Enter A Number",Toast.LENGTH_LONG).show();
					}else{


						// adding the number of the courses to the for loop 
						ma.addCourseToList(numberofcourses);
						

						//main.numberofcoursestaken.setText(numberofcourses.getText());
						Log.d("onClick Dialog", "sendng data to activity file");



						// Makes the add course Dialog Box disappear
						numberofcoursesdialog.this.dismiss();
						Log.d("onClick Dialog", "Course Entry Dialog dismissed");
					}

				}
			}
		});


		return view;
	}

}
