package com.googlecode.neuraid.neurogpa;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DeleteAllCoursesDialog extends DialogFragment {

	DeleteAllCoursesDialog(){
		
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.deleteallcourses, container);

		Button No = (Button)view.findViewById(R.id.No);
		Button Yes = (Button)view.findViewById(R.id.Yes);
		getDialog().setTitle("Really Delete All?");

		
		// Set up reaction for the Button to be pressed
		No.setOnClickListener(new OnClickListener() {
      
		// When the button is clicked it cancels the add course dialog box and it doesn't add the new courses
			public void onClick(View arg0) {
				DeleteAllCoursesDialog.this.dismiss();
			}

		});
		
		// Set up reaction for the Button to be pressed
		Yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				NeuroGPAActivity activity = (NeuroGPAActivity) getActivity();
				activity.deleteAll();
				
				
				DeleteAllCoursesDialog.this.dismiss();
			}
	});
		return view;
	}
}
