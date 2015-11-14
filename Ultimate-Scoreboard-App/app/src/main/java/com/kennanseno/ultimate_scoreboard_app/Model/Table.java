package com.kennanseno.ultimate_scoreboard_app.Model;


public class Table {

    public Table() {
    }

    public static abstract class User {

        public static final String TABLE_NAME = "User";
        public static final String ID = "user_id";
        public static final String NAME = "user_name";
        public static final String EMAIL = "user_email";

        public static final String CREATE_USER_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID + " TEXT PRIMARY KEY," +
                        NAME + " TEXT," +
                        EMAIL + " TEXT )";

        public static final String DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


    public static abstract class Club {

        public static String TABLE_NAME = "Club";
        public static String ID = "club_id";
        public static String NAME = "club_name";

        public static final String CREATE_CLUB_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID + " TEXT PRIMARY KEY," +
                        NAME + " TEXT )";

        public static final String DELETE_CLUB_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Score {

        public static String TABLE_NAME = "Score";
        public static String ID = "score_id";
        public static String SCORE = "score";
        public static String SPIRIT_RULE1 = "rules_knowledge_use";
        public static String SPIRIT_RULE2 = "foul_body_contact";
        public static String SPIRIT_RULE3 = "fair_mindedness";
        public static String SPIRIT_RULE4 = "positive_attitude";
        public static String SPIRIT_RULE5 = "communication";
        public static String SPIRIT_TOTAL = "spirit_score";

        public static final String CREATE_SCORE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID + " INTEGER PRIMARY KEY," +
                        SCORE + " INTEGER," +
                        SPIRIT_RULE1 + " INTEGER," +
                        SPIRIT_RULE2 + " INTEGER," +
                        SPIRIT_RULE3 + " INTEGER," +
                        SPIRIT_RULE4 + " INTEGER," +
                        SPIRIT_RULE5 + " INTEGER," +
                        SPIRIT_TOTAL + " INTEGER )";

        public static final String DELETE_SCORE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Event {

        public static String TABLE_NAME = "Event";
        public static String ID = "event_id";
        public static String NAME = "event_name";
        public static String VENUE = "venue";
        public static String START_DATE = "start_date";
        public static String END_DATE = "end_date";
        public static String USER_ID = "user_id";

        public static final String CREATE_EVENT_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        NAME + " TEXT," +
                        VENUE + " TEXT," +
                        START_DATE + " DATE," +
                        END_DATE + " DATE," +
                        USER_ID + " INTEGER," +
                        "FOREIGN KEY (" + USER_ID + ") REFERENCES " + User.TABLE_NAME + "(" + User.ID + "))";

        public static final String DELETE_EVENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Matches {

        public static String TABLE_NAME = "Matches";
        public static String ID = "match_id";
        public static String CLUB1_ID = "club1_id";
        public static String CLUB1_SCORE_ID = "club1_score_id";
        public static String CLUB2_ID = "club2_id";
        public static String CLUB2_SCORE_ID = "club2_score_id";
        public static String START_TIME = "start_time";
        public static String END_TIME = "end_time";
        public static String DAY = "day";
        public static String EVENT_ID = "event_id";

        public static final String CREATE_MATCHES_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CLUB1_ID + " TEXT," +
                        CLUB1_SCORE_ID + " INTEGER," +
                        CLUB2_ID + " TEXT," +
                        CLUB2_SCORE_ID + " INTEGER," +
                        START_TIME + " TEXT," +
                        END_TIME + " TEXT," +
                        DAY + " INTEGER," +
                        EVENT_ID + " INTEGER," +
                        "FOREIGN KEY (" + CLUB1_ID + ") REFERENCES " + Club.TABLE_NAME + "(" + Club.ID + ")," +
                        "FOREIGN KEY (" + CLUB1_SCORE_ID + ") REFERENCES " + Score.TABLE_NAME + "(" + Score.ID + ")," +
                        "FOREIGN KEY (" + CLUB2_ID + ") REFERENCES " + Club.TABLE_NAME + "(" + Club.ID + ")," +
                        "FOREIGN KEY (" + CLUB2_SCORE_ID + ") REFERENCES " + Score.TABLE_NAME + "(" + Score.ID + ")," +
                        "FOREIGN KEY (" + EVENT_ID + ") REFERENCES " + Event.TABLE_NAME + "(" + Event.ID + "))";

        public static final String DELETE_MATCHES_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}





























