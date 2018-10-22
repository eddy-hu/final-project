package com.algonquincollege.final_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 17146 on 3/6/2018.
 */

public class RouteDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MessagesDb.db"; //database name
    public static final int VERSION_NUM = 12; //database version
    public static final String KEY_ID = "ID"; //column name, primary key
    public static final String KEY_MESSAGE = "MessageCol"; //column name
    public static final String TB_NAME = "MessageTb"; //table name

    public RouteDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    //create the table
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        String CREATE_TABLE = "CREATE TABLE " + TB_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, old Version=" +
                oldVersion + " newVersion="+newVersion );
        db.execSQL("DROP TABLE IF EXISTS " + TB_NAME); //drop old table
        onCreate(db);
    }
}