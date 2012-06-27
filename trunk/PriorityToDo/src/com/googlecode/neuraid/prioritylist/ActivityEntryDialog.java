package com.googlecode.neuraid.prioritylist;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class ActivityEntryDialog extends DialogFragment{

	private EditText title;
	private EditText describe;
	private EditText prioritize;
	private Button no;
	private Button yes;
	
	public ActivityEntryDialog(){
		// Empty constructor required for DialogFragment
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		// Prepares the layout for use and renames it view
		View view = inflater.inflate(R.layout.activityentry, container);

		// Initialization *Connects the view with the data member
		title = (EditText) view.findViewById(R.id.title);
		describe = (EditText) view.findViewById(R.id.description);
		prioritize = (EditText) view.findViewById(R.id.priority);
		// The Title of the Dialog that adds courses
				getDialog().setTitle("Add Activites");

				// Initialize the Button with this
				no = (Button) view.findViewById (R.id.negative);
				no.setText("Cancel");
				yes = (Button) view.findViewById (R.id.positive);
				yes.setText("Submit");
				// Set up reaction for the Button to be pressed
				no.setOnClickListener(new OnClickListener() {
		      
				// When the button is clicked it cancels the add course dialog box and it doesn't add the new courses
					public void onClick(View arg0) {
						ActivityEntryDialog.this.dismiss();
					}

				});
				
				// Set up reaction for the Button to be pressed
				yes.setOnClickListener(new OnClickListener() {
					
					// When Yes is Clicked it adds the new info the the Array List
					public void onClick(View arg0) {
						PriorityToDoListActivity activity = (PriorityToDoListActivity) getActivity();
						activity.addActivity(title.getText().toString(),describe.getText().toString(),Integer.parseInt(prioritize.getText().toString()));
						// Makes the add course Dialog Box disappear
						ActivityEntryDialog.this.dismiss();
					}	
					
				});
					
				return view;	
				}
	}


