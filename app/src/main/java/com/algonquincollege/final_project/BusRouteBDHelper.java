package com.algonquincollege.final_project;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BusRouteBDHelper extends SQLiteOpenHelper {
    protected static final String ACTIVITY_NAME = "BusRouteBDHelper";
    public static final String DATABASE_NAME = "bus_route_avg_adjusted_time.db";
    public static final String TABLE_NAME = "TIME";
    public static int VERSION_NUM = 1;
    public static final String KEY_ID = "id";
    public static final String ROUTE_NO = "routeno";
    public static final String ADJUSTED_TIME = "adjustedtime";

    private SQLiteDatabase database;

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

    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    public void insertEntry(String route,String adjustedtime) {
        ContentValues values = new ContentValues();
        values.put(ROUTE_NO, route);
        values.put(ADJUSTED_TIME, adjustedtime);
        Log.i(ACTIVITY_NAME, "insert entry!!!!!!!!!!"+route);

        Log.i(ACTIVITY_NAME, "insert entry!!!!!!!!!!"+adjustedtime);


        database.insert(TABLE_NAME, null, values);

    }

    public Cursor getAverageAdjustedTime(String routeNo){
        return database.query(TABLE_NAME, new String[]{ADJUSTED_TIME}, ROUTE_NO+" like ?", new String[]{routeNo}, null, null, null);

    }

    public Cursor getRecords() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void closeDatabase() {
        if(database != null && database.isOpen()){
            database.close();
        }
    }

}