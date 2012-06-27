package com.googlecode.neuraid.prioritylist;

import java.util.ArrayList;
import java.util.Collections;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class PriorityToDoListActivity extends FragmentActivity implements OnClickListener {
	// Data Members
	private ListView activityList;
	private Button add;
	private ArrayList<YourActivity> activities;

	private ActivityAdapter aa;
	private static DatabaseHandler  db;

	// connects a view to a model
	private class ActivityAdapter extends ArrayAdapter<YourActivity> {
		// Data Members
		// Creating the Array List variable
		private ArrayList<YourActivity> items;
		// Constructing the use of other methods from Java
		public ActivityAdapter(Context context, int textViewResourceId, ArrayList<YourActivity> objects) {
			super(context, textViewResourceId, objects);
			//  Initializing items
			this.items = objects;


		}
		// Other Methods

		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			// Create view if not there already
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}

			// find item based on position in list
			YourActivity a = items.get(position);
			if (a != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView mt = (TextView) v.findViewById(R.id.middletext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				// set the view to display information from the Course object
				if(tt != null) {
					tt.setText("Title: " + a.getTitleofactivity());                            
				}
				if(mt != null){
					mt.setText("Description: " + a.getDescription());
				}
				if(bt != null){
					bt.setText("Priority: " + a.getPriority());
				}
			}
			return v;
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Constructorish
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		db = new DatabaseHandler(this);
		Log.d("Create", "implemented databasehandler");

		activityList = (ListView)findViewById(R.id.activityList);
		add = (Button)findViewById(R.id.addButton);
		add.setText("Add Activity");
		add.setOnClickListener(this);

		// Initialize the array containing the activity into a variable
		activities = new ArrayList<YourActivity>();
		/*activities.add(new YourActivity("English101","A",4));
        	activities.add(new YourActivity("Math101","C",4));
        	activities.add(new YourActivity("Science101","B",4));
		 */

		aa = new ActivityAdapter(this,R.layout.row,activities);
		activityList.setAdapter(aa);
		updateView();

	}

	public void onClick(View arg0) {
		// When button is clicked, show entry dialog
		FragmentManager fm = getSupportFragmentManager();
		ActivityEntryDialog aeDialog = new ActivityEntryDialog();
		aeDialog.show(fm, "fragment_edit_name");	
	}

	public void addActivity(String titleofactivity, String description, int priority) {
		// Creates a ne object called Course and adds it to the array
		db.addActivity(titleofactivity, description, priority);
		Log.d("ADDCourseActivity", "Activity is adding the courses from Dialog to the database");
		
		// Refreshes the ListView containing the courses in the view via the adapter
		aa.notifyDataSetChanged();
		updateView();
	}
	public void editActiviey(int id ,String titleofactivity, String description, int priority){
		db.updateActivity(id, titleofactivity, description, priority);
		Log.d("ADDCourseActivity", "Activity is adding the courses from Dialog to the database");
		updateView();	
	}

	public void deleteActivity(YourActivity d){
		db.deleteActivity(d);
		updateView();
	}

	public void updateView(){	
		activities = db.getAllActivities();
		Log.d("Update", "returned all courses");
		// Initialize the course adapter into a variable and setting up connection between ArrayList and ListView
		aa = new ActivityAdapter(this,R.layout.row,activities);
		Log.d("Update", "Just used the adapter to make the row view for the courses");

		activityList.setAdapter(aa);
		Log.d("Update", "set adapter");
		
		Collections.sort(activities);
		
		aa.notifyDataSetChanged();
		Log.d("Load", "refreash array");
				

	}


	}


