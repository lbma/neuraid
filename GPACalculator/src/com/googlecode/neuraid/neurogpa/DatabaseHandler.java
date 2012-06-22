package com.googlecode.neuraid.neurogpa;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	public static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "CourseManager";

	// Course table name
	public static final String TABLE_COURSES = "Courses";

	// Course Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_CREDIT = "credit";
	public static final String KEY_GRADE = "grade";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_COURSE_TABLE = "CREATE TABLE " + TABLE_COURSES + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_CREDIT + " INTEGER," + KEY_GRADE + " TEXT" +  ")";
		db.execSQL(CREATE_COURSE_TABLE);
		Log.d("Create", "Database is created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
		Log.d("Upgrade", "Database is upgraded");

		// Create tables again
		onCreate(db);
		Log.d("Update", "Database is created again");

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new course
	public void addCourses(String name, int credit, String grade) {
		Log.d("AddValues", "Beginning to add courses to the database");

		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("AddValues", "Opened writable database");

		ContentValues values = new ContentValues();
		Log.d("AddValues", "implementing values");

		values.put(KEY_NAME, name); // Course Name
		Log.d("AddValues", "Getting value name");

		values.put(KEY_CREDIT, credit); // Course credit
		Log.d("AddValues", "Getting value credit");

		values.put(KEY_GRADE, grade); // Course grade
		Log.d("AddValues", "Getting value course");



		// Inserting Row
		db.insert(TABLE_COURSES, null, values);
		Log.d("AddValues", "Inserting value into the table");

		db.close(); // Closing database connection
		Log.d("AddValues", "Just closed the database");

	}

	// Getting single contact
	Course getCourse(int id) {
		Log.d("Get Course", "Starting to get courses from database");

		SQLiteDatabase db = this.getReadableDatabase();
		Log.d("Get Course", "Opened a readable database");


		Cursor cursor = db.query(TABLE_COURSES, new String[] { KEY_ID,
				KEY_NAME, KEY_CREDIT, KEY_GRADE }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		Log.d("Get Course", "querying the database with a cursor");

		if (cursor != null)
			cursor.moveToFirst();
		Log.d("Get Course", "Cursor moved to first spot in database");


		Course courses = new Course(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getInt(2), cursor.getString(3));
		// return contact
		return courses;
	}
	
	// Getting All Courses
	public ArrayList<Course> getAllCourses() {
		ArrayList<Course> courseList = new ArrayList<Course>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_COURSES;
		Log.d("Database Query","selecting all from the query");

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			Log.d("Database Query","moved to first");
			do {
				Course course = new Course();
				course.setID(Integer.parseInt(cursor.getString(0)));
				course.setName(cursor.getString(1));
				course.setCredit(cursor.getInt(2));
				course.setGrade(cursor.getString(3));

				// Adding contact to list
				courseList.add(course);
				Log.d("Database Query","adding the contact to the list");
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		Log.d("Database Query","Retured course list");
		return courseList;
	}

	// Updating single contact
	public int updateCourse(Course course) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, course.getName());
		values.put(KEY_CREDIT, course.getGrade());
		values.put(KEY_GRADE, course.getGrade());

		// updating row
		return db.update(TABLE_COURSES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(course.getID()) });
	}

	// Deleting single contact
	public void deleteCourse(Course course) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_COURSES, KEY_ID + " = ?",
				new String[] { String.valueOf(course.getID()) });
		db.close();
	}


	// Getting contacts Count
	public int getCourseCount() {
		String countQuery = "SELECT  * FROM " + TABLE_COURSES;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
