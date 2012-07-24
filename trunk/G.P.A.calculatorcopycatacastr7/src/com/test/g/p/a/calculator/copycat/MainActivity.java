package com.test.g.p.a.calculator.copycat;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.support.v4.app.FragmentManager;

public class MainActivity extends SherlockFragmentActivity {

	private double numberofcoursestaken;
	private ListView courseList;
	private Button calculate;
	//private DatabaseHandler db;
	private DataHolder data;
	int numberofcoursesinitems;

	// Declares the array that will contain on the courses


	private DataAdapter d;
	// Declares the Adapter
	//private CourseAdapter ca;

	private ArrayList<DataHolder> items;


	// Declares the Database
	//private class CourseAdapter extends ArrayAdapter<DataHolder>{
	// Creating the Array List variable



	// initializes the items (courses) from the array list for the view
	//		public CourseAdapter(Context context, int textViewResourceId, ArrayList<DataHolder> courses)  {
	//			super(context, textViewResourceId, courses);
	//			this.items = courses;
	//		}
	/*@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Changing the grade letter to spinner positions
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("A", 0);
			map.put("B", 1);
			map.put("C", 2);
			map.put("D", 3);
			map.put("F", 4);
			View v = convertView;
			// Create view if not there already
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}

			Course o = items.get(position);
			if (o != null) {
				TextView ft = (TextView) v.findViewById(R.id.rowclassname);
				Spinner sd = (Spinner) v.findViewById(R.id.rowspinner);
				TextView td = (TextView) v.findViewById(R.id.rowgrade);
				Spinner frt = (Spinner) v.findViewById(R.id.rowspinnersecond);

//				// set the view to display information from the Course object
//				if(ft != null) {
//					ft.setText("" + o.getName());
//				}
//				if(sd != null){
//					sd.setSelection(o.getCredit());
//				}
//				if(td != null){
//					td.setText("grade");
//				}
//				if(frt != null){
//					//frt.setSelection(map.get(o.getGrade()));
//				}
			}



			return v;
		}*/

	//}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("Create", "Just opened view");
		items = new ArrayList<DataHolder>();
		Log.d("Create", "Items made");
		numberofcoursesinitems = 0;
		Log.d("Create","Courses in Items ewuals 0");
		d = new DataAdapter(this,R.layout.row,items);
		
		Log.d("Create", "Just used the adapter to make the row view for the courses");
		courseList = (ListView) findViewById(R.id.courseList);
		Log.d("Create", " CourseList declared");
		
		
		courseList.setAdapter(d);
		Log.d("Create", "set adapter");
		courseList.setOnItemLongClickListener(new OnItemLongClickListener(){
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Log.d("coursList","CLicking");
				FragmentManager fm3 = getSupportFragmentManager();
				DataHolder course = items.get(position);
				Log.d("LongClickListener",""+position);
				DeleteCourseDialog dcDialog = new DeleteCourseDialog(course);
				dcDialog.show(fm3, "fragment_edit_name");
				
				return true;
			}
		});
		
		
		

		calculate = (Button) findViewById(R.id.calculategpa);
		Log.d("Calculate", "Will calculate soon");
		calculate.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				calculateGPA();
			}


		});

		Log.d("Updating View", "View is updating");
		updateView();
	}

	public void setNumberOfCourses(){
		FragmentManager fm2 = getSupportFragmentManager();
		numberofcoursesdialog ncDialog = new numberofcoursesdialog();
		ncDialog.show(fm2, "fragment_edit_name");	
		Log.d("Click View", "Just opened dialog for course entry");
	}

	public void addCourseToList(double numberofcourses){
		this.numberofcoursestaken = numberofcourses;
		Log.d("tranfer noc", "Transfer number of course to main activity");

		// Adding the DataHolders to the listView

		while(numberofcoursesinitems < numberofcoursestaken){

			items.add(new DataHolder(this));
			items.get(numberofcoursesinitems).setCourseName("Course " + (numberofcoursesinitems+1));
			Log.d("Adding all items", "Added " + (numberofcoursesinitems+1) + " of " + numberofcoursestaken + " to items");
			numberofcoursesinitems++;
		}
		while(numberofcoursestaken < numberofcoursesinitems){
			items.remove(numberofcoursesinitems-1);
			Log.d("Removing items", "Remove " + numberofcoursestaken+1 + " of " + numberofcoursesinitems + " to items");
			numberofcoursesinitems--;
		}
		//updateView();
	}

	//courses.add(Course(name.getText().toString(), Float.parseFloat(rowspinnercreidts.getSelectedItem().toString()), rowspinnercreidts.getSelectedItem().toString()));



	public void calculateGPA(){
		// Method that calculates the GPA
		float totalCredits = (float) 0.0;
		float qualitypoints = (float) 0.0;


		/* Goes through the Array list and adds each credit in the array to the totalcredit(variable), while it is going through the
			it examines the Grade and if it is equal to a certain letter it multiples it by it's corresponding number. After it multiples the 
			the credit by the letter's number and adds it to what ever the GPA is currently. 
		 */
		for (int i = 0; i < items.size(); i++){
			DataHolder c = items.get(i);
			totalCredits += Float.parseFloat(c.getCreditText());
			Log.d("Calculate", "Credit " + Float.parseFloat(c.getCreditText()) + " added to total of credits " + totalCredits);
		
			if(c.getGradeText().equals("A")){
				qualitypoints += Float.parseFloat(c.getCreditText()) * 4;
			}
			if(c.getGradeText().equals("B")){
				qualitypoints += Float.parseFloat(c.getCreditText()) * 3;
			}
			if(c.getGradeText().equals("C")){
				qualitypoints += Float.parseFloat(c.getCreditText()) * 2;
			}
			if(c.getGradeText().equals("D")){
				qualitypoints += Float.parseFloat(c.getCreditText()) * 1;
			}

			Log.d("calculate", " Grade = " + c.getGradeText() + " Credit = "+ Float.parseFloat(c.getCreditText()) + 
					" * Total Credits = " + totalCredits +" TQuality Points = " + qualitypoints);

		}


		Float gpa = (float) (qualitypoints / totalCredits);
		Log.d("CalculateE", "Total Quality Points = "+qualitypoints + "/ Total Credits = " + totalCredits + " = the GPA = " + gpa);
		// If the total credits is not 0, then the GPA is divided by the total number of credits.
		if (totalCredits != 0) {
			qualitypoints /= totalCredits; 
		}

		// Rounds the raw GPA to its hundredth place.
		BigDecimal totalround = new BigDecimal(qualitypoints).setScale(2, BigDecimal.ROUND_HALF_UP);

		// Sets the text in the main view to show the rounded GPA
		Toast.makeText(this, ""+totalround, Toast.LENGTH_LONG).show();

		Log.d("GPA", "GPA calculated");

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		inflater.inflate(R.menu.removecourses, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			setNumberOfCourses();
			return true;
		case R.id.removecourses:
			FragmentManager fm = getSupportFragmentManager();
			RemoveAllCoursesDialog racDialog = new RemoveAllCoursesDialog();
			racDialog.show(fm, "fragment_edit_name");	
			Log.d("Click View", "Just opened dialog to remove all course entries");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void updateView(){
		Log.d("Updating View", "New Data Adapter");

		d = new DataAdapter(this, R.layout.row, items);
		Log.d("Updating View", "Adapter set");

		courseList.setAdapter(d);
		Log.d("Updating View", " setting up the adapter");

		d.notifyDataSetChanged();
		Log.d("Update", "Just used the adapter to make the row view for the courses");



	}

	public void deletecourse(DataHolder d){
		items.remove(d);
		numberofcoursesinitems--;
		updateView();
	}
	public void deleteAll() {
		items.removeAll(items);
		Log.d("Removing items", "Remove " + numberofcoursestaken+1 + " of " + numberofcoursesinitems + " to items");
		numberofcoursesinitems=0;
		updateView();
	}
}

