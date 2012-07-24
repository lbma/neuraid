package com.googlecode.neuraid.neuraidservice;

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
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "NeuroManager";

	// Table name
	private static final String TABLE = "UserData";

	// Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TYPE = "type";
	private static final String KEY_VALUE = "value";
	private static final String KEY_TIME = "time_stamp";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TYPE + " TEXT,"
				+ KEY_VALUE + " INTEGER," + KEY_TIME + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
		Log.d("OnClickDH", "table created");
		
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new data
	void addData(String type, int value, long time) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, type); // Type of data 
		values.put(KEY_VALUE, value); // Value of data
		values.put(KEY_TIME, time); // Time data was taken
		Log.d("db", "adding to database "+ type);

		// Inserting Row
		db.insert(TABLE, null, values);
		db.close(); // Closing database connection
	}


	// Getting All UserData 
	public String getAllData() {
		//ArrayList<Userdata> userdataList = new ArrayList<Userdata>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE;
		Log.d("Database Query","selecting all from the query");


		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		String userdates = "";
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				userdates += Integer.parseInt(cursor.getString(0)) + "," + cursor.getString(1) + "," + cursor.getInt(2) + "," + cursor.getLong(3) + "\n";
//				Userdata userdata = new Userdata();
//				userdata.setId(Integer.parseInt(cursor.getString(0)));
//				userdata.setType(cursor.getString(1));
//				userdata.setValue(cursor.getInt(2));
//				userdata.setTime(cursor.getLong(3));

				// Adding contact to list
				//userdataList.add(userdata);
			} while (cursor.moveToNext());
		}
		
//		for (int i = 1; i < this.getCount(); i++){
//			 userdatas += userdataList;
//			Log.d("forloop", userdatas);
//		}
		// return data list
		return userdates;
	}
	
	

	// Getting contacts Count
	public int getCount() {
		String countQuery = "SELECT  * FROM " + TABLE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
