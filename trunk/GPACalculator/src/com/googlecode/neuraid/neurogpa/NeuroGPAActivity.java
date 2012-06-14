package com.googlecode.neuraid.neurogpa;


import java.math.BigDecimal;
import java.util.ArrayList;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class NeuroGPAActivity extends FragmentActivity implements OnClickListener {

	// Data Members
	private Button B1;
	private ListView courseList;
	private TextView gradeNumber;
	private ArrayList<Course> courses;
	
	private CourseAdapter ca;

	// Places the information from the Array into the main view
	private class CourseAdapter extends ArrayAdapter<Course> {

		// Creating the Array List variable
		private ArrayList<Course> items;
		
		// connects the items (courses) from the array list with the view
		public CourseAdapter(Context context, int textViewResourceId, ArrayList<Course> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		// The Dialog Box that contains Name, Course, and Credit 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			Course o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView mt = (TextView) v.findViewById(R.id.middletext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
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
		// Constructor
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Makes a Button 
		B1 = (Button) findViewById(R.id.B1);
		// Make it wait for the Button to be pressed
		B1.setOnClickListener(this);
		
		// Locate the Array containing the courses and the Text containing the GPA in the main view
		courseList = (ListView) findViewById(R.id.coursesArray);
		gradeNumber = (TextView) findViewById(R.id.gpanumber);

		// Makes the array containing the courses into a variable
		courses = new ArrayList<Course>();
		/* courses.add(new Course("English101",4,"A"));
	        courses.add(new Course("Math101",3,"C"));
	        courses.add(new Course("Science101",4,"B"));
		 */
		
		// Makes the course adapter into a variable 
		ca = new CourseAdapter(this,R.layout.row,courses);
		courseList.setAdapter(ca);
		recalculateGPA();
	}



	public void onClick(View arg0) {
		// Makes the app able to support other models
		FragmentManager fm = getSupportFragmentManager();
		CourseEntryDialog ceDialog = new CourseEntryDialog();
		ceDialog.show(fm, "fragment_edit_name");	
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

	}

	// Creates a method that need the input of the name, credit, and grade.  
	public void addCourse(String name, int credit, String grade) {
		// Creates a new object called Course and adds it to another course
		courses.add(new Course(name, credit, grade));
		// Refreshes the array containing the courses in the view
		ca.notifyDataSetChanged();
		// Recalculates the GPA 
		recalculateGPA();
	}




}

