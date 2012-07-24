package com.googlecode.neuraid.neuraidservice;

public class Userdata {
		private int id;
		private String type;
		private int value;
		private long time;
		
		//Empty constructor
		public Userdata(){
			
		}
		public Userdata(int id, String type, int value, long time) {
			this.id = id;
			this.type = type;
			this.value = value;
			this.time = time;
			}
		
		public Userdata(String type, int value,  long time){
			this.type = type;
			this.value = value;
			this.time = time;
		}

		

		// other methods

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public long getTime() {
			return time;
		}

		public void setTime(long time) {
			this.time = time;
		}

}
