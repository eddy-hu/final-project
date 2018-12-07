package com.algonquincollege.final_project;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import static com.algonquincollege.final_project.NutritionFavouriteList.selectedName;

public class NHL_FavoriteFragment extends Fragment {

    private static final String TAG = "NHL_FavoriteFragment";

    RecyclerView fRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    public NHL_ScheduleViewAdapter favadapter;
    NHL_DataBaseHelper nhlDataBaseHelper;
    ArrayList<NHL_ScheduleItem> scheduleItems;
    NHL_ScheduleItem favoriteList = null;
    //DATABASE
    private SQLiteDatabase Ndb;
    Cursor cursor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.nhl_favourite_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fRecyclerView = view.findViewById(R.id.fa_recyclerview);
        fRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        fRecyclerView.addItemDecoration(new DividerItemDecoration(fRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }



    public void populateListView() {

        Log.d(TAG, "populateListView: Displaying data in the ListView ");
        nhlDataBaseHelper = new NHL_DataBaseHelper(getActivity());
        Cursor data = nhlDataBaseHelper.getGameData();
        favoriteList = new NHL_ScheduleItem();
        while (data.moveToNext()) {
            scheduleItems.add((NHL_ScheduleItem) data); //add the items that store in database to the array adapter
        }
        // show the items on the list view
        fRecyclerView.setAdapter(favadapter);

        //click on an food item of fav list, shows the details fragment.

    }
}

























        /*favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedName = adapterView.getItemAtPosition(position).toString(); //to retrieve the name of the food that has been entered.
                foodDatabaseHelper = new NutritionDatabaseHelper(getApplicationContext());
                sqLiteDatabase = foodDatabaseHelper.getReadableDatabase();
                cursor = foodDatabaseHelper.getSpecificFood(selectedName, sqLiteDatabase);//to get the data from the database
                double cal = 0;
                double fat = 0;

                if (cursor.moveToFirst()) {
                    cal = cursor.getDouble(1); // to get the calories content based on the primary key which is the food that is shown on the fav list.
                    fat = cursor.getDouble(2); // to get the fat content based on the primary key which is the food that is shown on the fav list.
                    Log.d(TAG, "FAT " + fat + "Cal " + cal);
                }
                String calData = Double.toString(cal);
                String fatData = Double.toString(fat);

                Log.d(TAG, "onItemClick: You clicked on " + selectedName);
            }
        });//end of click listener
    }//end of populate view*/

 /*public List<NHL_ScheduleItem> getAllFavorite(ArrayList<NHL_ScheduleItem> favoritelist){
        NHL_ScheduleViewAdapter fAdapter;
        NHL_DataBaseHelper dbHelper = new NHL_DataBaseHelper(getActivity());
        String[] columns = {
                KEY_ID,
                HOME_CITY,
                AWAY_CITY,
                HOME_TEAM,
                AWAY_TEAM,
                STATUS,
                HOME_SCORE,
                AWAY_SCORE,
                HOME_LOGO,
                AWAY_LOGO,
                DATETIME

        };
        String sortOrder =
                KEY_ID + " ASC";
        List<NHL_ScheduleItem> favoriteList = new ArrayList<>();

        SQLiteDatabase Ndb = dbHelper.getReadableDatabase();

        Cursor cursor = Ndb.query(TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                NHL_ScheduleItem scheduleItem = new NHL_ScheduleItem();
                scheduleItem.setHomeCity(cursor.getString(cursor.getColumnIndex(HOME_CITY)));
                scheduleItem.setHomeCity(cursor.getString(cursor.getColumnIndex(AWAY_CITY)));
                scheduleItem.setHomeCity(cursor.getString(cursor.getColumnIndex(HOME_TEAM)));
                scheduleItem.setHomeCity(cursor.getString(cursor.getColumnIndex(AWAY_TEAM)));
                scheduleItem.setHomeCity(cursor.getString(cursor.getColumnIndex(STATUS)));
                scheduleItem.setHomeCity(cursor.getString(cursor.getColumnIndex(HOME_SCORE)));
                scheduleItem.setHomeCity(cursor.getString(cursor.getColumnIndex(AWAY_SCORE)));
                scheduleItem.setHomeCity(cursor.getString(cursor.getColumnIndex(DATETIME)));
                scheduleItem.setHomeLogo(Integer.parseInt(cursor.getString(cursor.getColumnIndex(HOME_LOGO))));
                scheduleItem.setAwayLogo(Integer.parseInt(cursor.getString(cursor.getColumnIndex(AWAY_LOGO))));

                favoriteList.add(scheduleItem);

            }while(cursor.moveToNext());
        }
        cursor.close();
        Ndb.close();

        return favoriteList;
    }*/
