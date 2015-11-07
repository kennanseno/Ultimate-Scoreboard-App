package com.kennanseno.ultimate_scoreboard_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    SQLiteDatabase myDb;
    DbHelper dbHelper;

    public DBManager(Context context){

        dbHelper = new DbHelper(context);
    }

    public class DbHelper extends SQLiteOpenHelper {

        final static String DB_NAME = "UltimateData.db";

        public DbHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Table.User.CREATE_USER_TABLE);
            db.execSQL(Table.Club.CREATE_CLUB_TABLE);
            db.execSQL(Table.Score.CREATE_SCORE_TABLE);
            db.execSQL(Table.Event.CREATE_EVENT_TABLE);
            db.execSQL(Table.Matches.CREATE_MATCHES_TABLE);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(Table.Matches.DELETE_MATCHES_TABLE);
            db.execSQL(Table.Event.DELETE_EVENT_TABLE);
            db.execSQL(Table.Score.DELETE_SCORE_TABLE);
            db.execSQL(Table.Club.DELETE_CLUB_TABLE);
            db.execSQL(Table.User.DELETE_USER_TABLE);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

    }

    public void insertNewUser(String id, String name){
        open();
        if(isNewUser(id)){
            ContentValues values = new ContentValues();
            values.put(Table.User.ID, id);
            values.put(Table.User.NAME, name);

            myDb.insert(Table.User.TABLE_NAME, null, values);
            Log.d("Test", "New user Added!");
        }else{
            Log.d("Test", "Old user!");
        }
        close();
    }


    public ArrayList<Event> getAllEvents(){
        open();
        //Get data from db then add it to an arraylist
        Cursor mCursor = myDb.rawQuery("SELECT * FROM " + Table.Event.TABLE_NAME, null);
        ArrayList<Event> eventArrayList = new ArrayList<Event>();

        if (mCursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(mCursor.getInt(mCursor.getColumnIndex(Table.Event.ID)));
                event.setName(mCursor.getString(mCursor.getColumnIndex(Table.Event.NAME)));
                event.setVenue(mCursor.getString(mCursor.getColumnIndex(Table.Event.VENUE)));
                event.setStartDate(mCursor.getString(mCursor.getColumnIndex(Table.Event.START_DATE)));
                event.setEndDate(mCursor.getString(mCursor.getColumnIndex(Table.Event.END_DATE)));
                event.setEventOrganizer(mCursor.getString(mCursor.getColumnIndex(Table.Event.USER_ID)));

                Log.d("Test", "Event ID: " + event.getId() + " Name: " + event.getName() + " Venue: " + event.getVenue() + " Start Date: " + event.getStartDate() + " End Date: " + event.getEndDate() + " Organizer Id: " + event.getEventOrganizer());
                eventArrayList.add(event);

            } while (mCursor.moveToNext());
        }

        close();
        return eventArrayList;
    }


    private boolean isNewUser(String id){
        Cursor cursor = myDb.rawQuery("SELECT * FROM " + Table.User.TABLE_NAME + " WHERE " + Table.User.ID + " like " + id, null);

        if(cursor.getCount() == 0){
            return true;
        }
        return false;
    }

    public void open(){
        myDb = dbHelper.getWritableDatabase();
        myDb.isOpen();
    }

    public void close(){
        dbHelper.close();
    }
}
