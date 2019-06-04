package com.ssdi.pricesplash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(History.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + History.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insert(String url, String website, String name){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(History.COLUMN_URL, url);
        values.put(History.COLUMN_NAME, name);
        values.put(History.COLUMN_WEBSITE,website);

        // insert row
        long id = db.insert(History.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public List<History> getHistory() {
        List<History> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + History.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History note = new History();
                note.setId(cursor.getInt(cursor.getColumnIndex(History.COLUMN_ID)));
                note.setName(cursor.getString(cursor.getColumnIndex(History.COLUMN_NAME)));
                note.setWebsite(cursor.getString(cursor.getColumnIndex(History.COLUMN_WEBSITE)));
                note.setUrl(cursor.getString(cursor.getColumnIndex(History.COLUMN_URL)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public void deleteNote(History note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(History.TABLE_NAME, History.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}
