/**
 * File name: NutritionDatabaseHelper.java
 * NutritionAuthor: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To set up the database
 */
package com.algonquincollege.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Feng Cheng
 */
public class NutritionDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FoodNutrition.db";
    public static final int VERSION_NUM = 6;
    public static final String KEY_ID = "food";
    public static final String COL2 = "Calory";
    public static final String COL3 = "Fat";
    public static final String COL4 = "Tag";

    public static final String TABLE_NAME = "Nutrition_Table";
    public static final String TAG = "NutritionDatabaseHelper";
    private SQLiteDatabase database;
    public static String[] NUTRITION_FIELDS = new String[]{
            KEY_ID, COL2, COL3
    };

    /**
     * the constructor for instantiation
     *
     * @param cxt Context
     */
    public NutritionDatabaseHelper(Context cxt) {
        super(cxt, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * to create the database
     *
     * @param db SOLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " TEXT PRIMARY KEY, " + COL2 + " REAL," + COL3 + " REAL," +
                COL4 + " TEXT);");


        Log.i(TAG, "Calling onCreate()");
    }

    /**
     * to upgrade the database
     *
     * @param db         SQLiteDatabase
     * @param oldVersion the old database
     * @param newVersion the new database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //deleteStopButton current table
        onCreate(db);
        Log.i(TAG, "Calling onUpdate(), oldVersion=" + oldVersion + ", newVersion=" + newVersion);
    }

    /**
     * to add data
     *
     * @param food primary id
     * @param cal  the attribute
     * @param fat  the attribute
     * @return the data inserted successfully or not
     */
    public boolean addData(String food, double cal, double fat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, food);
        contentValues.put(COL2, cal);
        contentValues.put(COL3, fat);


        Log.d(TAG, "addData: Adding " + food + cal + fat + " to " + TABLE_NAME);

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
        database = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = database.rawQuery(query, null);
        return data;
    }

//    public Cursor getItemDetail(String id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "SELECT " + COL2  + " FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = '" + id + " '" ;
//        Cursor data = db.rawQuery(query, null);
//        return data;
//    }

    /**
     * to retrieve a specific row from the database
     *
     * @param id             primary key
     * @param sqLiteDatabase SQLiteDatabase
     * @return Cursor the specific the row
     */
    public Cursor getSpecificFood(String id, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {KEY_ID, COL2, COL3, COL4};
        String selections = KEY_ID + " LIKE ?";
        String[] selection_args = {id};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projections, selections, selection_args, null, null, null);
        return cursor;
    }

    public Cursor getTag(String tag, SQLiteDatabase sqLiteDatabase) {
        String[] projections = {KEY_ID, COL2, COL3, COL4};
        String selections = COL4 + " LIKE ?";
        String[] selection_args = {tag};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, projections, selections, selection_args, null, null, null);
        return cursor;
    }

    public boolean updateName(String tagName, String id){
        SQLiteDatabase db = this.getWritableDatabase();
       // String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +  " = " + tagName + "' WHERE " + KEY_ID +  " = '" + id ;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, tagName);
       long result =  db.update(TABLE_NAME, contentValues, "food = ?", new String[]{id});
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public void deleteName(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = '" + id + " ';";
        Log.d(TAG, "deleteName: query: " + query);
        db.execSQL(query);
    }

    /**
     * to deleteStopButton a specific row from the database
     *
     * @param id             primary key
     * @param sqLiteDatabase SQLiteDatabase
     */
    public void delFood(String id, SQLiteDatabase sqLiteDatabase) {
        database = this.getWritableDatabase();
        String selection = KEY_ID + " LIKE ?";
        String[] selection_args = {id};
        sqLiteDatabase.delete(TABLE_NAME, selection, selection_args);
    }

    public Cursor getRecords() {
        return database.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }

    public Cursor getNutritionContent(){
        return database.query(DATABASE_NAME, NUTRITION_FIELDS, null, null, null, null, null);
    }

}

