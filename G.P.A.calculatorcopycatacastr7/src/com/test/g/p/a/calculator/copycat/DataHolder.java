package com.test.g.p.a.calculator.copycat;

import android.content.Context;
import android.text.Editable;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class DataHolder {

	private String courseName;
	private int selectedCredit;
	private int selectedGrade;

	private ArrayAdapter<CharSequence> creditAdapter;
	private ArrayAdapter<CharSequence> gradeAdapter;

	public DataHolder(){
		
	}

	public DataHolder(Context parent) {
		creditAdapter = ArrayAdapter.createFromResource(parent, R.array.course_credits, android.R.layout.simple_spinner_item);
		creditAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		gradeAdapter = ArrayAdapter.createFromResource(parent, R.array.course_grade, android.R.layout.simple_spinner_item);
		gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	}

	
	public String getCourseName() {
		return courseName;
	}


	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}


	public String getCreditText() {
        return (String) creditAdapter.getItem(selectedCredit);
    }
	
	public ArrayAdapter<CharSequence> getCreditAdapter() {
		return creditAdapter;
	}

	public int getSelectedCredit() {
		return selectedCredit;
	}

	public void setSelectedCredit(int selectedCredit) {
		this.selectedCredit = selectedCredit;
	}

	public ArrayAdapter<CharSequence> getGradeAdapter() {
		return gradeAdapter;
	}
	
	public String getGradeText() {
        return (String) gradeAdapter.getItem(selectedGrade);
    }

	public int getSelectedGrade() {
		return selectedGrade;
	}

	public void setSelectedGrade(int selectedGrade) {
		this.selectedGrade = selectedGrade;
	}

}
