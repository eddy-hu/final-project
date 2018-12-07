package com.algonquincollege.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class NHL_DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "favourite_table";
    public static final String KEY_ID = "id";
    public static final String HOME_CITY = "homeCity";
    public static final String AWAY_CITY = "awayCity";
    public static final String HOME_TEAM = "homeTeam";
    public static final String AWAY_TEAM = "awayTeam";
    public static final String STATUS = "playedStatus";
    public static final String HOME_SCORE = "homeScore";
    public static final String AWAY_SCORE = "awayScore";
    public static final String DATETIME = "dateTime";
    public static final String HOME_LOGO = "homeLogo";
    public static final String AWAY_LOGO = "awayLogo";
    public static final String TAG = "NHL_DataBaseHelper";
    public static final String DATABASE_NAME = "NHL.db";
    public static final int VERSION_NUM = 1;
    public static final String DATABASE_CREATE = "create table " + TABLE_NAME + "( " + KEY_ID + " integer primary key autoincrement,"
            + HOME_CITY + " text not null, "
            + AWAY_CITY + " text not null, "
            + HOME_TEAM + " text not null,"
            + AWAY_TEAM + " text not null, "
            + STATUS + " text not null, "
            + HOME_SCORE + " text not null, "
            + AWAY_SCORE + " text not null, "
            + HOME_LOGO + " integer not null, "
            + AWAY_LOGO + " integer not null, "
            + DATETIME + " text not null);";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase Ndb;


    public NHL_DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    //ONCREATE
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.i(TAG, "Calling onCreate");
    }

    //ONUPGRADE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(TAG, "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }

    public boolean addData(String homeCity, String awayCity, String homeTeam, String awayTeam, String playedStatus, String homeScore, String awayScore, String dateTime, int homeLogo, int awayLogo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(HOME_CITY, homeCity);
        Values.put(AWAY_CITY, awayCity);
        Values.put(HOME_TEAM, homeTeam);
        Values.put(AWAY_TEAM, awayTeam);
        Values.put(STATUS, playedStatus);
        Values.put(HOME_SCORE, homeScore);
        Values.put(AWAY_SCORE, awayScore);
        Values.put(DATETIME, dateTime);
        Values.put(HOME_LOGO, homeLogo);
        Values.put(AWAY_LOGO, awayLogo);


        Log.d(TAG, "addData: Adding " + homeCity +
                awayCity +
                awayCity +
                homeTeam +
                awayTeam +
                playedStatus +
                homeScore +
                awayScore +
                dateTime +
                homeLogo +
                awayLogo +
                " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, Values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor getGameData() {
        Ndb = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = Ndb.rawQuery(query, null);
        return data;
    }

    public void deleteFavorite(String id, SQLiteDatabase sqLiteDatabase) {
        Ndb = this.getWritableDatabase();
        String selection = KEY_ID + " LIKE ?";
        String[] selection_args = {id};
        sqLiteDatabase.delete(TABLE_NAME, selection, selection_args);
    }

    public Cursor getSpecificGame(String id, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {KEY_ID, HOME_CITY, AWAY_CITY, HOME_TEAM, AWAY_TEAM, STATUS, HOME_SCORE, AWAY_SCORE, DATETIME, HOME_LOGO, AWAY_LOGO};
        String selections = KEY_ID + " LIKE ?";
        String[] selection_args = {id};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projections, selections, selection_args, null, null, null);
        return cursor;
    }

    public void openDatabase() {

        Ndb = this.getWritableDatabase();
    }

    public void closeDatabase() {
        if (Ndb != null && Ndb.isOpen()) {
            Ndb.close();
        }
    }

}


