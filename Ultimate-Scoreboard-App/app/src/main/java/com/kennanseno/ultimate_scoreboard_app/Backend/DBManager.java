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

    public void insertNewSchedule(String team1, String team2, String startTime, String endTime, int day, int eventId ){
        Log.d("Test", "DBManager:insertNewSchedule()");
        String team1Code = getClubCode(team1);
        Log.d("Test", "Team 1 Code:" + team1Code);
        String team2Code = getClubCode(team2);
        Log.d("Test", "Team 2 Code:" + team2Code);
        int team1ScoreId = createScoreData();
        Log.d("Test", "Score 1 ID:" + team1ScoreId);
        int team2ScoreId = createScoreData();
        Log.d("Test", "Score 2 ID:" + team2ScoreId);

        open();
        ContentValues values = new ContentValues();
        values.put(Table.Matches.CLUB1_ID, team1Code);
        values.put(Table.Matches.CLUB1_SCORE_ID, team1ScoreId);
        values.put(Table.Matches.CLUB2_SCORE_ID, team2ScoreId);
        values.put(Table.Matches.CLUB2_ID, team2Code);
        values.put(Table.Matches.START_TIME, startTime);
        values.put(Table.Matches.END_TIME, endTime);
        values.put(Table.Matches.DAY, day);
        values.put(Table.Matches.EVENT_ID, eventId);
        myDb.insert(Table.Matches.TABLE_NAME, null, values);

        close();
    }

    //create a score row in Score table and returns the id of it
    public int createScoreData(){
        int id = 0;

        open();
        //create new row
        ContentValues values = new ContentValues();
        values.put(Table.Score.SCORE, 00);
        values.put(Table.Score.SPIRIT_RULE1, 00);
        values.put(Table.Score.SPIRIT_RULE2, 00);
        values.put(Table.Score.SPIRIT_RULE3, 00);
        values.put(Table.Score.SPIRIT_RULE4, 00);
        values.put(Table.Score.SPIRIT_RULE5, 00);
        values.put(Table.Score.SPIRIT_TOTAL, 00);
        myDb.insert(Table.Score.TABLE_NAME, null, values);

        //get id od the new row
        Cursor mCursor = myDb.rawQuery("SELECT * FROM " + Table.Score.TABLE_NAME, null);
        if(mCursor.moveToLast()){
            id = mCursor.getInt(mCursor.getColumnIndex(Table.Score.ID));
        }
        close();

        return id;
    }

    private String getClubCode(String clubName){
        open();
        Cursor mCursor = myDb.rawQuery("SELECT " + Table.Club.ID + " FROM " + Table.Club.TABLE_NAME + " WHERE " + Table.Club.NAME + " like '" + clubName + "'", null);
        String name = null;

        if(mCursor.moveToFirst()) {
             name = mCursor.getString(mCursor.getColumnIndex(Table.Club.ID));
        }
        close();

        return name;
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

                Log.d("Test", "DBManager:getAllEvents()");
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
                "SELECT m.match_id, m.club1_id, " +
                        "s1.score_id AS club1_score_id, s1.score AS club1_score, s1.spirit_score AS club1_spirit_score, " +
                        "m.club2_id, s2.score_id AS club2_score_id, s2.score AS club2_score, s2.spirit_score AS club2_spirit_score, " +
                        "m.start_time, m.end_time, m.day, m.event_id " +
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
                schedule.setClub1ScoreId(mCursor.getInt(mCursor.getColumnIndex("club1_score_id")));
                schedule.setClub1SpiritScore(mCursor.getInt(mCursor.getColumnIndex("club1_spirit_score")));
                schedule.setClub2Id(mCursor.getString(mCursor.getColumnIndex(Table.Matches.CLUB2_ID)));
                schedule.setClub2ScoreId(mCursor.getInt(mCursor.getColumnIndex("club2_score_id")));
                schedule.setClub2Score(mCursor.getInt(mCursor.getColumnIndex("club2_score")));
                schedule.setClub2SpiritScore(mCursor.getInt(mCursor.getColumnIndex("club2_spirit_score")));
                schedule.setStartTime(mCursor.getString(mCursor.getColumnIndex(Table.Matches.START_TIME)));
                schedule.setEndTime(mCursor.getString(mCursor.getColumnIndex(Table.Matches.END_TIME)));
                schedule.setDay(mCursor.getInt(mCursor.getColumnIndex(Table.Matches.DAY)));
                schedule.setEventId(mCursor.getInt(mCursor.getColumnIndex(Table.Matches.EVENT_ID)));

                Log.d("Test", "DBManager:getSchedules()");
                Log.d("Test", "Club1 Score ID: " + schedule.getClub1ScoreId() + " Club2 Score ID: " + schedule.getClub2ScoreId());
                Log.d("Test", "ID:" + schedule.getMatchId() + " Club1 Code:" + schedule.getClub1Id() + " Club1 Score:" + schedule.getClub1Score() + " Club1 SpiritScore:" + schedule.getClub1SpiritScore());
                Log.d("Test", "Club2 Code:" + schedule.getClub2Id() + " Club2 Score:" + schedule.getClub2Score() + " Club2 SpiritScore:" + schedule.getClub2SpiritScore());
                Log.d("Test", "Start Time:" + schedule.getStartTime() + " End Time:" + schedule.getEndTime() + " Day:" + schedule.getDay() + " Event ID:" + schedule.getEventId());


                scheduleArrayList.add(schedule);

            } while (mCursor.moveToNext());
        }

        close();
        return scheduleArrayList;
    }

    public ArrayList<String> getClubNames(){

        open();
        Cursor mCursor = myDb.rawQuery("SELECT " + Table.Club.NAME + " FROM " + Table.Club.TABLE_NAME, null);
        ArrayList<String> clubName = new ArrayList<>();

        if(mCursor.moveToFirst()){
            do {
                clubName.add(mCursor.getString(mCursor.getColumnIndex(Table.Club.NAME)));
            } while (mCursor.moveToNext());

        }
        close();

        return clubName;
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
