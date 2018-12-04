/**
 * The SQLite database helper for bus activity
 * @Author: Yongpan Hu
 * @Version: 1.1
 * @Since:1.0
 */
package com.algonquincollege.final_project;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * BusDBHelper class handles SQLite database to implements CRUD functions
 */
public class BusDBHelper extends SQLiteOpenHelper {
    /**
     * The name of this class
     */
    protected static final String ACTIVITY_NAME = "BusDBHelper";
    /**
     * The SQLite database for bus stop
     */
    private SQLiteDatabase database;
    /**
     * The name of SQLite database
     */
    public static final String DATABASE_NAME = "stopListView.db";
    /**
     * The number of version
     */
    public static final int VERSION_NUM = 1;
    /**
     * The name of stations table
     */
    public static final String TABLE_NAME = "stations";
    /**
     * The number of stop
     */
    public static final String STOP_NO = "station_number";
    /**
     * The name of each stop
     */
    public static final String STOP_NAME = "station_name";
    /**
     * The name of routes name table
     */
    public static final String TABLE_NAME_ROUTES = "routes";
    /**
     * The number of each route
     */
    public static final String ROUTE_NO = "route_number";


    public BusDBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STOP_NO + " text, " + STOP_NAME +  " text);");

        db.execSQL("CREATE TABLE " + TABLE_NAME_ROUTES + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROUTE_NO +  " text);");

        Log.i(ACTIVITY_NAME, "Calling onCreate()");
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "onOpen was called");
    }

    /**
     * Open the database then get writable database
     */
    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    /**
     * Getter for query results
     * @return cursor which contains query results
     */
    public Cursor getRecords() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }

    /**
     * Close the database if it is opened and it is not null
     */
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