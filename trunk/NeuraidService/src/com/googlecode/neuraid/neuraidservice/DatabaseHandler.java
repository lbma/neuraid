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

//	// Getting single contact
//	Contact getContact(int id) {
//		SQLiteDatabase db = this.getReadableDatabase();
//
//		Cursor cursor = db.query(TABLE, new String[] { KEY_ID,
//				KEY_TYPE, KEY_VALUE, KEY_TIME }, KEY_ID + "=?",
//				new String[] { String.valueOf(id) }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//
//		Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//				cursor.getString(1), cursor.getString(2));
//		// return contact
//		return contact;
//	}
//	
//	// Getting All Contacts
//	public List<Contact> getAllContacts() {
//		List<Contact> contactList = new ArrayList<Contact>();
//		// Select All Query
//		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
//
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor cursor = db.rawQuery(selectQuery, null);
//
//		// looping through all rows and adding to list
//		if (cursor.moveToFirst()) {
//			do {
//				Contact contact = new Contact();
//				contact.setID(Integer.parseInt(cursor.getString(0)));
//				contact.setName(cursor.getString(1));
//				contact.setPhoneNumber(cursor.getString(2));
//				// Adding contact to list
//				contactList.add(contact);
//			} while (cursor.moveToNext());
//		}
//
//		// return contact list
//		return contactList;
//	}
//
//	// Updating single contact
//	public int updateContact(Contact contact) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_NAME, contact.getName());
//		values.put(KEY_PH_NO, contact.getPhoneNumber());
//
//		// updating row
//		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//				new String[] { String.valueOf(contact.getID()) });
//	}
//
//	// Deleting single contact
//	public void deleteContact(Contact contact) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//				new String[] { String.valueOf(contact.getID()) });
//		db.close();
//	}


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
