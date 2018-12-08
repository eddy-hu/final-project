package com.algonquincollege.final_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NewsDBHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "News.db";
    public static boolean S_UPGRADE = false;
    public static int VERSION_NUM = 3;
    public static String TABLE_NAME = "news";
    public static String id = "ID";
    public static String title = "TITLE";
    public static String link = "LINK";
    public static String guid = "GUID";
    public static String pubDate = "PUBDATE";
    public static String author = "AUTHOR";
    public static String category = "CATEGORY";
    public static String description = "DESCRIPTION";
    public static String DATABASE_CREATOR = "Create Table " + TABLE_NAME + " ( " + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            title + " text not null, " +
            link + " text not null, " + guid + " DOUBLE not null, "+ pubDate + " text not null, "+ author +" text not null, " +
            category + " text not null, "+ description + " text not null );";
    public static String DATABASE_CREATE = "Create Table IF NOT EXISTS SAVE ( " + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            title + " text not null, " +
            link + " text not null, " + guid + " DOUBLE not null, "+ pubDate + " text not null, "+ author +" text not null, " +
            category + " text not null, "+ description + " text not null );";
    public NewsDBHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATOR);
        db.execSQL(DATABASE_CREATE);
        Log.i("DatabaseHelper", "Calling onCreate");


    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NEWS");
        S_UPGRADE = true;
        onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NEWS");
        S_UPGRADE = true;
        onCreate(db);
        Log.i("DatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String data = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(data, null);
        return cursor;
    }

    public Cursor getData(String string, SQLiteDatabase db) {

        String data = "SELECT * FROM " + TABLE_NAME + " WHERE DESCRIPTION LIKE " + "\"" + string + "\"";
        Cursor cursor = db.rawQuery(data, null);
        return cursor;
    }

    public Cursor getData(String string) {
        SQLiteDatabase db = this.getWritableDatabase();
        String data = "SELECT * FROM " + string;
        Cursor cursor = db.rawQuery(data, null);
        return cursor;
    }

}