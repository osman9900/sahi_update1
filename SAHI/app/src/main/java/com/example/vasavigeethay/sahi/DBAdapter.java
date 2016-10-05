package com.example.vasavigeethay.sahi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	private static final String TAG = "DBAdapter"; //used for logging database version changes

	// Field Names:
	public static final String Doctor_Name = "DoctorName";
	public static final String Doctor_Hosp = "DoctorHosp";

	public static final String[] ALL_KEYS = new String[] {Doctor_Name, Doctor_Hosp};

	// DataBase info:
	public static final String DATABASE_NAME = "DoctorPage";
	public static final String DATABASE_TABLE = "DoctorDetails";
	public static final int DATABASE_VERSION = 2;  // The version number must be incremented each time a change to DB structure occurs.
		
	//SQL statement to create database
	private static final String DATABASE_CREATE_SQL = "CREATE TABLE " + DATABASE_TABLE + " (" + Doctor_Name + " VARCHAR(20) NOT NULL, " + Doctor_Hosp + " VARCHAR(30));";
	
	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}
	
	// Open the database connection.
	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}
	
	// Close the database connection.
	public void close() {
		myDBHelper.close();
	}
	
	// Add a new set of values to be inserted into the database.
	public long insertRow(String name, String hosp) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(Doctor_Name, name);
		initialValues.put(Doctor_Hosp, hosp);
				
		// Insert the data into the database.
		return db.insert(DATABASE_TABLE, null, initialValues);
	}

	// Get a specific row (by rowId)
	public Cursor getRow() {

		Cursor c = 	db.rawQuery("SELECT * FROM "+DATABASE_TABLE, null);
		if (c != null)
		{
			c.moveToFirst();
		}
		return c;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			
			// Recreate new database:
			onCreate(_db);
		}
	}
}

