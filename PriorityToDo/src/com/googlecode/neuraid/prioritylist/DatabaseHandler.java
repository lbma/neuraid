package com.googlecode.neuraid.prioritylist;

import java.util.ArrayList;
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
	public static final String DATABASE_NAME = "ToDoListManager";

	// Course table name
	public static final String TABLE_TODOLIST = "ToDoList";

	// Course Table Columns names
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_PRIORITY = "priority";


	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TODOLIST_TABLE = "CREATE TABLE " + TABLE_TODOLIST + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
				+ KEY_DESCRIPTION + " TEXT," + KEY_PRIORITY + " INTEGER" +  ")";
		db.execSQL(CREATE_TODOLIST_TABLE);
		Log.d("Create", "Database is created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOLIST);
		Log.d("Upgrade", "Database is upgraded");

		// Create tables again
		onCreate(db);
		Log.d("Update", "Database is created again");

	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new course
	public void addActivity(String title, String description, int priority) {
		Log.d("AddValues", "Beginning to add courses to the database");

		SQLiteDatabase db = this.getWritableDatabase();
		Log.d("AddValues", "Opened writable database");

		ContentValues values = new ContentValues();
		Log.d("AddValues", "implementing values");

		values.put(KEY_TITLE, title); // Course Name
		Log.d("AddValues", "Getting value name");

		values.put(KEY_DESCRIPTION, description); // Course credit
		Log.d("AddValues", "Getting value credit");

		values.put(KEY_PRIORITY, priority); // Course grade
		Log.d("AddValues", "Getting value course");



		// Inserting Row
		db.insert(TABLE_TODOLIST, null, values);
		Log.d("AddValues", "Inserting value into the table");

		db.close(); // Closing database connection
		Log.d("AddValues", "Just closed the database");

	}

	// Getting single courses
	YourActivity getActivity(int id) {
		Log.d("Get Item", "Starting to get items from database");

		SQLiteDatabase db = this.getReadableDatabase();
		Log.d("Get Item", "Opened a readable database");


		Cursor cursor = db.query(TABLE_TODOLIST, new String[] { KEY_ID,
				KEY_TITLE, KEY_DESCRIPTION, KEY_PRIORITY }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		Log.d("Get Course", "querying the database with a cursor");

		if (cursor != null)
			cursor.moveToFirst();
		Log.d("Get Course", "Cursor moved to first spot in database");


		YourActivity titleofactivity = new YourActivity(cursor.getString(0), cursor.getString(1),Integer.parseInt(cursor.getString(2)));
		// return the title of the activity
		return titleofactivity;
	}
	
	// Getting All Courses
	public ArrayList<YourActivity> getAllActivities() {
		ArrayList<YourActivity> items = new ArrayList<YourActivity>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TODOLIST;
		Log.d("Database Query","selecting all from the query");

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			Log.d("Database Query","moved to first");
			do {
				YourActivity item = new YourActivity();
				item.setId(Integer.parseInt(cursor.getString(0)));
				item.setTitleofactivity(cursor.getString(1));
				item.setDescription(cursor.getString(2));
				item.setPriority(cursor.getInt(3));
				

				// Adding contact to list
				items.add(item);
				Log.d("Database Query","adding the contact to the list");
			} while (cursor.moveToNext());
		}
		cursor.close();
		// return contact list
		Log.d("Database Query","Retured course list");
		return items;
	}

	// Updating single course
	public int updateActivity(int id ,String titleofactivity, String description, int priority){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, titleofactivity);
		values.put(KEY_DESCRIPTION, description);
		values.put(KEY_PRIORITY, priority);


		// updating row
		return db.update(TABLE_TODOLIST, values, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	// Deleting single contact
	public void deleteActivity(YourActivity item) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TODOLIST, KEY_ID + " = ?",
				new String[] { String.valueOf(item.getId()) });
		db.close();
		Log.d("Delete", "Course deleted from db");
	}


	// Getting contacts Count
	public int getActivityCount() {
		String countQuery = "SELECT  * FROM " + TABLE_TODOLIST;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	

}
