/**
 * File name: NutritionSearchActivity.java
 * NutritionAuthor: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To set up main activity of the application
 */
package com.algonquincollege.final_project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * This is the activity for searching food.
 */
public class NutritionSearchActivity extends AppCompatActivity {
    private ProgressDialog loading = null;
    private NutritionJsonAdapter adapter;
    private EditText searchTxt;
    private Button btnAdd;
    private Button btnFavourite;
    private Button btnSearch;
    private ListView listView;
    protected static final String ACTIVITY_NAME = "NutritionSearchActivity";
    private Context ctx = null;
    private String app_id = "40cb1f76", app_key = "9dd571cf4d9e83a7796c460130be79dd";
    private List<NutritionNewBean> newBeanList = new ArrayList<>();
    public String food;
    private String jsonUrl = " https://api.edamam.com/api/food-database/parser?ingr=" + food + "&app_id=" + app_id + "&app_key=" + app_key;
    private NutritionDatabaseHelper foodDatabaseHelper = new NutritionDatabaseHelper(this);
    private NutritionNewBean newBean;

    /**
     * to create the search activity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_search);

        listView = (ListView) findViewById(R.id.searchResult);
        searchTxt = (EditText) findViewById(R.id.searchEditTxt);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnFavourite = (Button) findViewById(R.id.btn_favourite);

        Toolbar nutritionToolbar = (Toolbar) findViewById(R.id.nutrition_toolbar);
        setSupportActionBar(nutritionToolbar);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = searchTxt.getText().toString();
                if (food != null && !food.isEmpty()) {
                    new MyAsyncTask().execute(jsonUrl);
                } else {
                    toastMessage("ToastMessage: Please enter something.");
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = searchTxt.getText().toString();

                if (food != null && !(food.isEmpty())) {
                    double fat = adapter.fatData;
                    double cal = adapter.calData;
                    AddData(food, cal, fat);
                } else {
                    Snackbar.make(v, "Snackbar: Please enter the name of the food !", Snackbar.LENGTH_LONG).setAction(
                            "Action", null
                    ).show();
                }

            }
        });

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutritionSearchActivity.this, NutritionFavouriteList.class);
                startActivity(intent);
            }
        });

    }

    /**
     * to add the data
     *
     * @param food primary key in the database
     * @param cal  the detail that needs to be inserted in the calory column
     * @param fat  the detail that needs to be insterted in the fat column
     */
    public void AddData(String food, double cal, double fat) {
        boolean insertData = foodDatabaseHelper.addData(food, cal, fat);
        if (insertData) {
            toastMessage("ToastMessage: Data successfully inserted");
        } else {
            toastMessage("ToastMessage: Something went wrong");
        }
    }

    /**
     * inner class
     */
    class MyAsyncTask extends AsyncTask<String, Void, List<NutritionNewBean>> {
        private String jsonUrl = " https://api.edamam.com/api/food-database/parser?ingr=" + food + "&app_id=" + app_id + "&app_key=" + app_key;
        NutritionJsonData jsonData = new NutritionJsonData();

        /**
         * the get the data from the Json Object
         *
         * @param params String
         * @return the data of the Json Object
         */
        @Override
        protected List<NutritionNewBean> doInBackground(String... params) {
            return newBeanList = jsonData.getJsonData(jsonUrl);
        }

        /**
         * the show the progress bar
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(NutritionSearchActivity.this);
            loading.setMessage("Please wait");
            loading.setCancelable(false);
            loading.show();
            ;
        }

        /**
         * to set up the listview of the searched result
         *
         * @param result the data from the Json Object
         */
        @Override
        protected void onPostExecute(List<NutritionNewBean> result) {
            super.onPostExecute(result);
            adapter = new NutritionJsonAdapter(NutritionSearchActivity.this, newBeanList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (loading.isShowing()) {
                loading.dismiss();
            }
        }

        /**
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bus_options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.bus_help:
                AlertDialog alertDialog = new AlertDialog.Builder(NutritionSearchActivity.this).create();
                alertDialog.setTitle("Help dialog notification");
                alertDialog.setMessage("Welcome to Final Project \nAuthor: Feng Cheng");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            case R.id.bus_nutrition_app:

                intent = new Intent(this, NutritionSearchActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_movie_app:
                intent = new Intent(this, MovieStartActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_news_app:
                intent = new Intent(this, Spencer_MainActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_hockey_app:
                intent = new Intent(this, Mordechai_mainActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.home_page_icon:
                intent = new Intent(this, StartActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}