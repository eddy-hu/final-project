package com.algonquincollege.final_project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BusDBHelper extends SQLiteOpenHelper {

    protected static final String ACTIVITY_NAME = "BusDBHelper";
    private SQLiteDatabase database;

    public static final String DATABASE_NAME = "stopListView.db";
    public static final int VERSION_NUM = 1;

    public static final String TABLE_NAME = "stations";
    public static final String STATION_NO = "station_number";
    public static final String STATION_NAME = "station_name";

    public static final String TABLE_NAME_ROUTES = "routes";
    public static final String ROUTE_NO = "route_number";



    public BusDBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STATION_NO + " text, " + STATION_NAME +  " text);");

        db.execSQL("CREATE TABLE " + TABLE_NAME_ROUTES + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROUTE_NO +  " text);");

        Log.i(ACTIVITY_NAME, "Calling onCreate()");
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "onOpen was called");
    }

    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    public Cursor getRecords() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void closeDatabase() {
        if(database != null && database.isOpen()){
            database.close();
        }
    }
    public void delete(){
        database.execSQL("delete from "+ TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ROUTES);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade(), oldVersion="
                + oldVer + ". newVersion=" + newVer + ".");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ROUTES);
        Log.i(ACTIVITY_NAME, "Calling onDowngrade(), oldVersion="
                + oldVer + ". newVersion=" + newVer + ".");
        onCreate(db);
    }


}