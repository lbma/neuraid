package com.googlecode.neuraid.neuraidservice;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DeleteCourseDialog extends DialogFragment {
	

	DeleteCourseDialog(){
		
	}

	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.deletecourse, container);

		Button No = (Button)view.findViewById(R.id.nobutton);
		Button Yes = (Button)view.findViewById(R.id.yesbutton);
		getDialog().setTitle("Really Delete?");
		
		// Set up reaction for the Button to be pressed
		No.setOnClickListener(new OnClickListener() {
      
		// When the button is clicked it cancels the add course dialog box and it doesn't add the new courses
			public void onClick(View arg0) {
				DeleteCourseDialog.this.dismiss();
			}

		});
		
		// Set up reaction for the Button to be pressed
		Yes.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				NeuraidServiceActivity activity = (NeuraidServiceActivity) getActivity();
				
				activity.deleteAll();
				Toast.makeText(activity, "All Data Wiped", Toast.LENGTH_LONG).show();

				DeleteCourseDialog.this.dismiss();
			}
	});
		return view;
	}
}

