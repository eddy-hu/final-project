package com.algonquincollege.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class NHL_DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "favourite_table";
    public static final String KEY_ID = "id";
    public static final String TEAM_NAME = "name";
    public static final String TEAM_CITY = "city";
    public static final String TAG = "NHL_DataBaseHelper";
    public static final String DATABASE_NAME = "NHL.db";
    public static final int VERSION_NUM = 1;
    public static final String DATABASE_CREATE = "create table " + TABLE_NAME + "( " + KEY_ID + " integer primary key autoincrement,"+ TEAM_CITY+ " text not null, "+ TEAM_NAME+ " text not null);";


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

    public boolean addData(String name, String city) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(KEY_ID, id);
        contentValues.put(TEAM_NAME, name);
        contentValues.put(TEAM_CITY, city);


        Log.d(TAG, "addData: Adding " + name + city + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * to retrieve data from the database
     *
     * @return Cursor the data
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getContact(String id, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {KEY_ID, TEAM_NAME, TEAM_CITY};
        String selections = KEY_ID + " LIKE ?";
        String[] selection_args = {id};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projections, selections, selection_args, null, null, null);
        return cursor;
    }

    /**
     * to delete a specific row from the database
     *
     * @param id             primary key
     * @param sqLiteDatabase SQLiteDatabase
     */
    public void delTEAM(String id, SQLiteDatabase sqLiteDatabase) {
        SQLiteDatabase database = this.getWritableDatabase();
        String selection = KEY_ID + " LIKE ?";
        String[] selection_args = {id};
        sqLiteDatabase.delete(TABLE_NAME, selection, selection_args);
    }
}

