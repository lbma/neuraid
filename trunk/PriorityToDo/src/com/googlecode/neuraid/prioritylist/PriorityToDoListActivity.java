package com.googlecode.neuraid.prioritylist;

import java.util.ArrayList;
import java.util.Collections;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;


public class PriorityToDoListActivity extends SherlockFragmentActivity {
	// Data Members
	private ListView activityList;
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
		
		// Initialize the array containing the activity into a variable
		activities = new ArrayList<YourActivity>();
		/*activities.add(new YourActivity("English101","A",4));
        	activities.add(new YourActivity("Math101","C",4));
        	activities.add(new YourActivity("Science101","B",4));
		 */

		aa = new ActivityAdapter(this,R.layout.row,activities);
		activityList.setAdapter(aa);
		activityList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				FragmentManager fm2 = getSupportFragmentManager();
				YourActivity c = activities.get(position);
				EditActivityEntryDialog eceDialog = new EditActivityEntryDialog(c);

				eceDialog.show(fm2, "fragment_edit_name");	
				Log.d("Click View", "Just opened dialog to edit course entries");


				Log.d("Click", "Selected " + position);
			}

		}

				);
		Log.d("Create", "set adapter");

		activityList.setOnItemLongClickListener(new OnItemLongClickListener(){
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				FragmentManager fm3 = getSupportFragmentManager();
				YourActivity d = activities.get(position);
				DeleteActivityDialog dcDialog = new DeleteActivityDialog(d);
				dcDialog.show(fm3, "fragment_edit_name");
				return true;
			}
		});

		updateView();

	}
	public void onResume() {
		super.onResume();
		Log.d("onResume","Just Resumed");
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
	public void deleteAll(){
		deleteDatabase(db.getDatabaseName());
		Log.d("DeleteAll", "All courses have been selected");

	}

	// Action Bar
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.main_activity, menu);

		//		menu.add("Add Activity")
		//		.setIcon(R.drawable.contentnew)
		//		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


		return true;
	}

	// Action Bar Items
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_activity:
			// app icon in action bar clicked; go home
			FragmentManager fm = getSupportFragmentManager();
			ActivityEntryDialog ceDialog = new ActivityEntryDialog();
			ceDialog.show(fm, "fragment_edit_name");	
			Log.d("Click View", "Just opened dialog for course entry");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}


