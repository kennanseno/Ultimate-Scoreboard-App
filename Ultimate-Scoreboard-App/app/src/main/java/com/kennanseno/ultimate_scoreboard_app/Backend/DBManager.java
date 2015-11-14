package com.kennanseno.ultimate_scoreboard_app.Backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kennanseno.ultimate_scoreboard_app.Model.Event;
import com.kennanseno.ultimate_scoreboard_app.Model.Schedule;
import com.kennanseno.ultimate_scoreboard_app.Model.Table;

import java.util.ArrayList;

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

    public void insertNewEvent(String name, String venue, String startDate, String endDate, int userId){

        open();
        ContentValues values = new ContentValues();
        values.put(Table.Event.NAME, name);
        values.put(Table.Event.VENUE, venue);
        values.put(Table.Event.START_DATE, startDate);
        values.put(Table.Event.END_DATE, endDate);
        values.put(Table.Event.USER_ID, userId);
        myDb.insert(Table.Event.TABLE_NAME, null, values);

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

    public ArrayList<Schedule> getSchedules(int eventId){
        open();
        //Get data from db then add it to an arraylist
        Cursor mCursor = myDb.rawQuery(
                "SELECT m.match_id, m.club1_id, s1.score AS club1_score, s1.spirit_score AS club1_spirit_score, m.club2_id, s2.score AS club2_score, s2.spirit_score AS club2_spirit_score, m.start_time, m.end_time, m.day, m.event_id " +
                        "FROM " + Table.Matches.TABLE_NAME + " m " +
                        "JOIN " + Table.Event.TABLE_NAME + " e ON m.event_id=e.event_id " +
                        "JOIN " + Table.Score.TABLE_NAME + " s1 ON m.club1_score_id=s1.score_id " +
                        "JOIN " + Table.Score.TABLE_NAME + " s2 ON m.club2_score_id=s2.score_id " +
                        "WHERE m.event_id=" + eventId  +
                        " ORDER BY start_time", null);

        ArrayList<Schedule> scheduleArrayList = new ArrayList<>();

        if (mCursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setMatchId(mCursor.getInt(mCursor.getColumnIndex(Table.Matches.ID)));
                schedule.setClub1Id(mCursor.getString(mCursor.getColumnIndex(Table.Matches.CLUB1_ID)));
                schedule.setClub1Score(mCursor.getInt(mCursor.getColumnIndex("club1_score")));
                schedule.setClub1SpiritScore(mCursor.getInt(mCursor.getColumnIndex("club1_spirit_score")));
                schedule.setClub2Id(mCursor.getString(mCursor.getColumnIndex(Table.Matches.CLUB2_ID)));
                schedule.setClub2Score(mCursor.getInt(mCursor.getColumnIndex("club2_score")));
                schedule.setClub2SpiritScore(mCursor.getInt(mCursor.getColumnIndex("club2_spirit_score")));
                schedule.setStartTime(mCursor.getString(mCursor.getColumnIndex(Table.Matches.START_TIME)));
                schedule.setEndTime(mCursor.getString(mCursor.getColumnIndex(Table.Matches.END_TIME)));
                schedule.setDay(mCursor.getInt(mCursor.getColumnIndex(Table.Matches.DAY)));
                schedule.setEventId(mCursor.getInt(mCursor.getColumnIndex(Table.Matches.EVENT_ID)));

                Log.d("Test", "ID:" + schedule.getMatchId() + " Club1 Code:" + schedule.getClub1Id() + " Club1 Score:" + schedule.getClub1Score() + " Club1 SpiritScore:" + schedule.getClub1SpiritScore());
                Log.d("Test", "Club2 Code:" + schedule.getClub2Id() + " Club2 Score:" + schedule.getClub2Score() + " Club2 SpiritScore:" + schedule.getClub2SpiritScore());
                Log.d("Test", "Start Time:" + schedule.getStartTime() + " End Time:" + schedule.getEndTime() + " Day:" + schedule.getDay() + " Event ID:" + schedule.getEventId());


                scheduleArrayList.add(schedule);

            } while (mCursor.moveToNext());
        }

        close();
        return scheduleArrayList;
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
