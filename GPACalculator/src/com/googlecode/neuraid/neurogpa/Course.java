package com.googlecode.neuraid.neurogpa;

// Course object that contains the name, credits, and the grades 
public class Course {

	// Data members
	private int id;
	private String name;
	private int credit;
	private String grade;
	
	//Empty constructor
	public Course(){
		
	}

	// constructors

	public Course(int id, String name, int credit, String grade) {
		this.id = id;
		this.name = name;
		this.credit = credit;
		this.grade = grade;
		}
	
	public Course(String name, int credit,  String grade){
		this.name = name;
		this.credit = credit;
		this.grade = grade;
	}

	

	// other methods
	public int getID(){
		return this.id;
	}
	
	public void setID(int id){
		this.id = id;
	}
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
