package com.googlecode.neuraid.neurogpa;

// Course object that contains the name, credits, and the grades 
public class Course {

	// Data members
	 private String name;
	 private int credit;
	 private String grade;
	
	// constructor
	 
	 public Course(String name, int credit,  String grade){
		 this.name = name;
		 this.credit = credit;
		 this.grade = grade;
	}
	 
	 // other methods
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public int getCredit() {
			return credit;
		}
	
		public void setCredit(int credit) {
			this.credit = credit;
		}
	
		public String getGrade() {
			return grade;
		}
	
		public void setGrade(String grade) {
			this.grade = grade;
		}
	 
	
	 
}
