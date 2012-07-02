package com.googlecode.neuraid.prioritylist;


import java.util.HashMap;

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


public class EditActivityEntryDialog extends DialogFragment {
	
	// Declare the Button and Drop list in the Course Entry Dialog View
	private EditText title;
	private EditText describe;
	private EditText prioritize;
	private YourActivity c;

	public EditActivityEntryDialog() {
		// Empty constructor required for DialogFragment
	}

	public EditActivityEntryDialog(YourActivity c2) {
		this.c = c2;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.activityentry, container);

		// Initialization *Connects the view with the data member
		title = (EditText) view.findViewById(R.id.title);
		describe = (EditText) view.findViewById(R.id.description);
		prioritize = (EditText) view.findViewById(R.id.priority);
		// The Title of the Dialog that adds courses
				getDialog().setTitle("Edit Activites");

		// Initialize the Button with this
		Button no = (Button) view.findViewById (R.id.negative);
		Button yes = (Button) view.findViewById (R.id.positive);
		
		// Set up reaction for the Button to be pressed
		no.setOnClickListener(new OnClickListener() {
      
		// When the button is clicked it cancels the add course dialog box and it doesn't add the new courses
			public void onClick(View arg0) {
				EditActivityEntryDialog.this.dismiss();
			}

		});
		
		// Set up reaction for the Button to be pressed
		yes.setOnClickListener(new OnClickListener() {
			// When Yes is Clicked it adds the new info the the Array List
			public void onClick(View arg0) {
				PriorityToDoListActivity activity = (PriorityToDoListActivity) getActivity();
				// crash because can't pare int
				activity.addActivity(title.getText().toString(),describe.getText().toString(),Integer.parseInt(prioritize.getText().toString()));
				
				//(mEditText.getText().toString(),Integer.parseInt(cEditText.getText().toString()),gradeData.getSelectedItem().toString());
				Log.d("onClick Dialog", "sendng data to activity file");

				// Makes the add course Dialog Box disappear
				EditActivityEntryDialog.this.dismiss();
				Log.d("onClick Dialog", "Course Entry Dialog dismissed");

			}

		});

		return view;
	}
}