package com.example.vasavigeethay.sahi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vasavi Geetha .Y on 04-Oct-16.
 */
public class DateDBAdapter {
    private static final String TAG = "DateDBAdapter"; //used for logging database version changes

    // Field Names:
    public static final String Id = "Id";
    public static final String Score = "Score";

    public static final String[] ALL_KEYS = new String[] {Id, Score};

    // DataBase info:
    public static final String DATABASE_NAME = "DiagResults";
    public static final String DATABASE_TABLE = "Results";
    public static final int DATABASE_VERSION = 2;  // The version number must be incremented each time a change to DB structure occurs.

    //SQL statement to create database
    private static final String DATABASE_CREATE_SQL = "CREATE TABLE " + DATABASE_TABLE + " (" + Id + " VARCHAR(10) NOT NULL , " + Score + " VARCHAR(30));";


    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DateDBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DateDBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to be inserted into the database.
    public long insertRow(String id, String score) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Id, id);
        initialValues.put(Score, score);

        // Insert the data into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow(String score) {
        ContentValues newValues = new ContentValues();
        newValues.put(Score, score);
        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, null, null) != 0;
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
