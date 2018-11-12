package com.algonquincollege.final_project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FavouriteList extends Activity {
    private static final String TAG = "FavouriteList";
    FoodDatabaseHelper foodDatabaseHelper;
    private ListView fListView;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        fListView = (ListView) findViewById(R.id.favListView);

        populateListView();


    }

    public void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView ");
        foodDatabaseHelper = new FoodDatabaseHelper(this);
        Cursor data = foodDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
            //listData.add(data.getString(1));
            // listData.add(data.getString(2));

        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        fListView.setAdapter(adapter);
        fListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedName = adapterView.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: You clicked on " + selectedName);
                foodDatabaseHelper = new FoodDatabaseHelper(getApplicationContext());
                sqLiteDatabase = foodDatabaseHelper.getReadableDatabase();
                Cursor cursor = foodDatabaseHelper.getContact(selectedName, sqLiteDatabase);
                double cal = 0;
                double fat= 0;
                if (cursor.moveToFirst()) {
                    cal = cursor.getDouble(1);
                    fat = cursor.getDouble(2);
                    Log.d(TAG, "FAT " + fat + "Cal " + cal);
                }
                String calData = Double.toString(cal);
                String fatData = Double.toString(fat);
                Intent intent = new Intent(FavouriteList.this, NutritionDetail.class);
                intent.putExtra("id", selectedName);
                intent.putExtra("calories", calData);
                intent.putExtra("fat", fatData);
                startActivity(intent);

            }
        });//end of click listener
    }//end of populate view

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}




