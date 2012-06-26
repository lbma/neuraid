package com.googlecode.neuraid.neurogpa;


import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class EditCourseEntryDialog extends DialogFragment {
	
	// Declare the Button and Drop list in the Course Entry Dialog View
	private EditText mEditText;
	private EditText cEditText;
	private Spinner gradeData;
	private Course c;

	public EditCourseEntryDialog() {
		// Empty constructor required for DialogFragment
	}

	public EditCourseEntryDialog(Course c) {
		this.c = c;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("A", 0);
		map.put("B", 1);
		map.put("C", 2);
		map.put("D", 3);
		map.put("F", 4);
		
		// Prepares the layout for use and renames it view
		View view = inflater.inflate(R.layout.courseentry, container);
		// Initialization *Connects the view with the data member
		mEditText = (EditText) view.findViewById(R.id.nameofcourse);
		cEditText = (EditText) view.findViewById(R.id.credit);
		gradeData = (Spinner) view.findViewById(R.id.gradeList);
		// The Title of the Dialog that adds courses
		getDialog().setTitle("Edit Courses");
		mEditText.setText(c.getName());
		cEditText.setText("" + c.getCredit());
		gradeData.setSelection(map.get(c.getGrade()));

		// Initialize the Button with this
		Button no = (Button) view.findViewById (R.id.negative);
		Button yes = (Button) view.findViewById (R.id.positive);
		
		// Set up reaction for the Button to be pressed
		no.setOnClickListener(new OnClickListener() {
      
		// When the button is clicked it cancels the add course dialog box and it doesn't add the new courses
			public void onClick(View arg0) {
				EditCourseEntryDialog.this.dismiss();
			}

		});
		
		// Set up reaction for the Button to be pressed
		yes.setOnClickListener(new OnClickListener() {
			// When Yes is Clicked it adds the new info the the Array List
			public void onClick(View arg0) {
				NeuroGPAActivity activity = (NeuroGPAActivity) getActivity();
				activity.editCourse(c.getID(),mEditText.getText().toString(),Integer.parseInt(cEditText.getText().toString()),gradeData.getSelectedItem().toString());
				
				//(mEditText.getText().toString(),Integer.parseInt(cEditText.getText().toString()),gradeData.getSelectedItem().toString());
				Log.d("onClick Dialog", "sendng data to activity file");

				// Makes the add course Dialog Box disappear
				EditCourseEntryDialog.this.dismiss();
				Log.d("onClick Dialog", "Course Entry Dialog dismissed");

			}

		});

		return view;
	}
}