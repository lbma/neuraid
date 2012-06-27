package com.googlecode.neuraid.prioritylist;

public class YourActivity implements Comparable<YourActivity> {
	// Data Member
	private int id;
	private String titleofactivity;
	private String description;
	private int priority;
	
	
	// Constructors
	public YourActivity(){
		
	}
	
	public YourActivity(int ID, String titleofactivity, String description, int priority){
		this.id = ID;
		this.description = description;
		this.priority = priority;
		this.titleofactivity = titleofactivity;
	}
	
	public YourActivity(String titleofactivity, String description, int priority){
		this.description = description;
		this.priority = priority;
		this.titleofactivity = titleofactivity;
	}
	
	// Other Methods
	
	public String getTitleofactivity() {
		return titleofactivity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitleofactivity(String titleofactivity) {
		this.titleofactivity = titleofactivity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int compareTo(YourActivity arg0) {
		// TODO Auto-generated method stub
		if(this.priority< arg0.priority){
			return -1;
			} 
		else if(this.priority > arg0.priority){
			return +1;
		}
		else{
			return 0;
		}
		
		}
	}

