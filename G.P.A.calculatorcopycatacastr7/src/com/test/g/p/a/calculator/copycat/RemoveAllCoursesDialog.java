package com.test.g.p.a.calculator.copycat;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class RemoveAllCoursesDialog extends DialogFragment{
	
		private DataHolder d;

		RemoveAllCoursesDialog(){
			
		}

		RemoveAllCoursesDialog(DataHolder d){
			this.d = d;
		}
		
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.removeallcourses, container);

			Button No = (Button)view.findViewById(R.id.nobutton);
			Button Yes = (Button)view.findViewById(R.id.yesbutton);
			getDialog().setTitle("Really Delete?");

			
			// Set up reaction for the Button to be pressed
			No.setOnClickListener(new OnClickListener() {
	      
			// When the button is clicked it cancels the add DataHolder dialog box and it doesn't add the new DataHolders
				public void onClick(View arg0) {
					RemoveAllCoursesDialog.this.dismiss();
				}

			});
			
			// Set up reaction for the Button to be pressed
			Yes.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					MainActivity activity = (MainActivity) getActivity();
					activity.deleteAll();
					RemoveAllCoursesDialog.this.dismiss();
				}
		});
			return view;
		}
	}



