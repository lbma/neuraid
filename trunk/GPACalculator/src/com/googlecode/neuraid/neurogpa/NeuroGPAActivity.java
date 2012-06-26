package com.googlecode.neuraid.neurogpa;


import java.math.BigDecimal;
import java.util.ArrayList;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class NeuroGPAActivity extends FragmentActivity implements OnClickListener{

	// Data Members
	private Button B1;
	private ListView courseList;
	private TextView gradeNumber;

	// Declares the array that will contain on the courses
	private static ArrayList<Course> courses;
	// Declares the Adapter
	private CourseAdapter ca;
	// Declares the Database
	private static DatabaseHandler  db;
	// Places the information from the Array into the main view
	private class CourseAdapter extends ArrayAdapter<Course>{
		// Creating the Array List variable
		private ArrayList<Course> items;

		// initializes the items (courses) from the array list for the view
		public CourseAdapter(Context context, int textViewResourceId, ArrayList<Course> courses)  {
			super(context, textViewResourceId, courses);
			this.items = courses;
		}

		// The List Entry that contains Name, Course, and Credit 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			// Create view if not there already
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}

			// find item based on position in list
			Course o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.topText);
				TextView mt = (TextView) v.findViewById(R.id.middleText);
				TextView bt = (TextView) v.findViewById(R.id.bottomText);
				//editButton = (Button) v.findViewById(R.id.editcoursebutton);
				//editButton.setOnClickListener(this);


				// set the view to display information from the Course object
				if(tt != null) {
					tt.setText("Name: " + o.getName());
				}
				if(mt != null){
					mt.setText("Credit: " + o.getCredit());
				}
				if(bt != null){
					bt.setText("Grade: " + o.getGrade());
				}
						
			}
			return v;
		}

	}


	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		// Constructorish
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d("Create", "Just opened view");

		db = new DatabaseHandler(this);
		Log.d("Create", "implemented databasehandler");

		// Initialize a Button 
		B1 = (Button) findViewById(R.id.B1);
		// Set up reaction when the Button to be pressed
		B1.setOnClickListener(this);



		// Initialize the ListView containing the courses and the Text containing the GPA in the main view
		courseList = (ListView) findViewById(R.id.coursesArray);
		gradeNumber = (TextView) findViewById(R.id.gpanumber);
		
		//courseDB = new CourseDB(this);

		// Initialize the array containing the courses into a variable

		courses = new ArrayList<Course>();
		Log.d("Create", "Just made and array with courses");

		/* courses.add(new Course("English101",4,"A"));
	        courses.add(new Course("Math101",3,"C"));
	        courses.add(new Course("Science101",4,"B"));
		 */

		// Initialize the course adapter into a variable and setting up connection between ArrayList and ListView
		ca = new CourseAdapter(this,R.layout.row,courses);
		Log.d("Create", "Just used the adapter to make the row view for the courses");

		courseList.setAdapter(ca);
		courseList.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				FragmentManager fm2 = getSupportFragmentManager();
				Course c = courses.get(position);
				EditCourseEntryDialog eceDialog = new EditCourseEntryDialog(c);
				
				eceDialog.show(fm2, "fragment_edit_name");	
				Log.d("Click View", "Just opened dialog to edit course entries");
				
				
				Log.d("Click", "Selected " + position);
				Toast toast = Toast.makeText(NeuroGPAActivity.this, "Selected " + position, Toast.LENGTH_SHORT);
				toast.show();
			}
			
		}
		
		);
		Log.d("Create", "set adapter");
		
//		courseList.setOnItemLongClickListener(new OnItemLongClickListener(){
//			public void onItemLongClick(AdapterView<?> arg0, View arg1, int position,
//					long arg3) {
//				FragmentManager fm3 = getSupportFragmentManager();
//				Course d = courses.get(position);
//				EditCourseEntryDialog eceDialog = new EditCourseEntryDialog(c);
//			}
//		});

		updateView();
		recalculateGPA();
		Log.d("Create", "recalculate GPA");

	}


	public void onResume() {
		super.onResume();
		Log.d("onResume","Just Resumed");
	}


	public void onClick(View arg0) {
		// When button is clicked, show entry dialog

		// do something
		FragmentManager fm = getSupportFragmentManager();
		CourseEntryDialog ceDialog = new CourseEntryDialog();
		ceDialog.show(fm, "fragment_edit_name");	
		Log.d("Click View", "Just opened dialog for course entry");
	}




	// Other Methods

	// Method that calculates the GPA
	public void recalculateGPA(){
		double gpa = 0;
		double totalCredits = 0;
		/* Goes through the Array list and adds each credit in the array to the totalcredit(variable), while it is going through the
		it examines the Grade and if it is equal to a certain letter it multiples it by it's corresponding number. After it multiples the 
		the credit by the letter's number and adds it to what ever the GPA is currently. 
		 */
		for (int i = 0; i < courses.size(); i++){
			Course c = courses.get(i);
			totalCredits += c.getCredit();

			if(c.getGrade().equals("A")){
				gpa += c.getCredit() * 4;
			}
			if(c.getGrade().equals("B")){
				gpa += c.getCredit() * 3;
			}
			if(c.getGrade().equals("C")){
				gpa += c.getCredit() * 2;
			}
			if(c.getGrade().equals("D")){
				gpa += c.getCredit() * 1;
			}
		}

		// If the total credits is not 0, then the GPA is divided by the total number of credits.
		if (totalCredits != 0) {
			gpa /= totalCredits; }

		// Rounds the raw GPA to its hundredth place.
		BigDecimal totalround = new BigDecimal(gpa).setScale(2, BigDecimal.ROUND_HALF_UP);

		// Sets the text in the main view to show the rounded GPA
		gradeNumber.setText("" + totalround);
		Log.d("GPA", "GPA calculated");


	}



	// Creates a method that need the input of the name, credit, and grade.  
	public void addCourse(String name, int credit, String grade) {
		db.addCourses(name, credit, grade);
		Log.d("ADDCourseActivity", "Activity is adding the courses from Dialog to the database");
		updateView();
	}
	
	
	public void editCourse(int id ,String name, int credit, String grade){
		db.updateCourse(id, name, credit, grade);
		Log.d("ADDCourseActivity", "Activity is adding the courses from Dialog to the database");
		updateView();	
	}

	public void removeCourse(){
		
	}

	public void updateView(){	
		courses = db.getAllCourses();
		Log.d("Update", "returned all courses");
		// Initialize the course adapter into a variable and setting up connection between ArrayList and ListView
		ca = new CourseAdapter(this,R.layout.row,courses);
		Log.d("Update", "Just used the adapter to make the row view for the courses");

		courseList.setAdapter(ca);
		Log.d("Update", "set adapter");

		recalculateGPA();
		Log.d("Update", "recalculate GPA");
		ca.notifyDataSetChanged();
		Log.d("Load", "refreash array");
		recalculateGPA();		

	}
}

