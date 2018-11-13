package com.algonquincollege.final_project;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BusStationDBHelper extends SQLiteOpenHelper {
    protected static final String ACTIVITY_NAME = "BusStationDBHelper";
    public static final String DATABASE_NAME = "bus_station.db";
    public static final String TABLE_NAME = "INFO";
    public static int VERSION_NUM = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "bus_number";
    public static final String STATION_NO = "station_number";
    public static final String STATION_NAME = "station_name";
    public static final String TABLE_ROUTES = "routes";
    public static final String ROUTE_NO = "route_number";

    private SQLiteDatabase database;

    private static final String CREATE_QUERRY= "CREATE TABLE " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STATION_NO + " text, " + STATION_NAME +  " TEXT" +
            ")";
    private static final String CREATE_ROUTES_QUERRY= "CREATE TABLE " + TABLE_ROUTES + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ROUTE_NO +  " TEXT" +
            ")";


    public BusStationDBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME); //delete current table
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ROUTES); //delete current table
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion= " +oldVersion +"newVersion=" +newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERRY);
        db.execSQL(CREATE_ROUTES_QUERRY);
        Log.i(ACTIVITY_NAME, "Calling onCreate");

    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME); //delete current table
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ROUTES); //delete current table
        onCreate(db);
        Log.i(ACTIVITY_NAME,"Calling onDowngrade, oldVersion=" + oldVer + "newVersion=" +newVer);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "onOpen was called");
    }

    public void openDatabase() {
        database = this.getWritableDatabase();
    }
    public void insertEntry(String content) {
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, content);
        database.insert(TABLE_NAME, null, values);

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

}