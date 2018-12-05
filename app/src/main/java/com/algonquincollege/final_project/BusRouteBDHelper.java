/**
 * The SQLite database helper for adjusted time for each route
 * @Author: Yongpan Hu
 * @Version: 1.1
 * @Since:1.0
 */
package com.algonquincollege.final_project;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * BusRoute SQLite Database Helper to stores the adjusted time of each route
 */
public class BusRouteBDHelper extends SQLiteOpenHelper {
    /**
     * Name of this Class
     */
    protected static final String ACTIVITY_NAME = "BusRouteBDHelper";
    /**
     * Name of this database
     */
    public static final String DATABASE_NAME = "bus_route_avg_adjusted_time.db";
    /**
     * The table name
     */
    public static final String TABLE_NAME = "TIME";
    /**
     * Version number of database
     */
    public static int VERSION_NUM = 1;
    /**
     * The key ID of each row
     */
    public static final String KEY_ID = "id";
    /**
     * The route number of each entry
     */
    public static final String ROUTE_NO = "routeno";
    /**
     * The adjusted time for each route
     */
    public static final String ADJUSTED_TIME = "adjustedtime";
    /**
     * The SQLite database
     */
    private SQLiteDatabase database;
    /**
     * The query string of create table
     */
    private static final String CREATE_QUERRY= "CREATE TABLE " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +ROUTE_NO+ " TEXT,"+
            ADJUSTED_TIME + " TEXT" +
            ")";



    public BusRouteBDHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME); //delete current table
        onCreate(db);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion= " +oldVersion +"newVersion=" +newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERRY);
        Log.i(ACTIVITY_NAME, "Calling onCreate");

    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(CREATE_QUERRY);
        Log.i(ACTIVITY_NAME,"Calling onDowngrade, oldVersion=" + oldVer + "newVersion=" +newVer);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.i(ACTIVITY_NAME, "onOpen was called");
    }

    /**
     * Open the database then get the writable database
     */
    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    /**
     * Insert the route number and adjusted time for each route
     * @param route route number
     * @param adjustedtime adjusted time
     */
    public void insertEntry(String route,String adjustedtime) {
        ContentValues values = new ContentValues();
        values.put(ROUTE_NO, route);
        values.put(ADJUSTED_TIME, adjustedtime);
        Log.i(ACTIVITY_NAME, "insert entry!!!!!!!!!!"+route);

        Log.i(ACTIVITY_NAME, "insert entry!!!!!!!!!!"+adjustedtime);


        database.insert(TABLE_NAME, null, values);

    }

    /**
     * Getter for average adjusted time cursor
     * @param routeNo number of route
     * @return average adjusted time cursor
     */
    public Cursor getAverageAdjustedTime(String routeNo){
        return database.query(TABLE_NAME, new String[]{ADJUSTED_TIME}, ROUTE_NO+" like ?", new String[]{routeNo}, null, null, null);

    }

    /**
     * Getter for records cursor
     * @return query result cursor
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

}