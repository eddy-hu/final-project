package com.algonquincollege.final_project;

import android.app.ActionBar;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.i(ACTIVITY_NAME, "In onCreate()");
        setContentView(R.layout.activity_start);


        /**
        Button buttonStart = (Button) findViewById(R.id.octranspo_button);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, OCTranspoActivity.class);
                startActivityForResult(intent, 5);
            }
        }); **/
        Button newsBtn = (Button) findViewById(R.id.news_button);
        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked cbc news ");
                Intent intent = new Intent(StartActivity.this, Spencer_MainActivity.class);
                startActivity(intent);

            }
        });
        Button hockyBtn = (Button) findViewById(R.id.hockey_button);
        hockyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked hockey ");
                Intent intent = new Intent(StartActivity.this, NHL_main.class);
                startActivity(intent);

            }
        });

        Button nutritionBtn = (Button) findViewById(R.id.nutrition_button);
        nutritionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Nutrition ");
                Intent intent = new Intent(StartActivity.this, NutritionSearchActivity.class);
                startActivity(intent);

            }
        });

        Button movieBtn = (Button) findViewById(R.id.movie_button);
        movieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Nutrition Chat");
                Intent intent = new Intent(StartActivity.this, MoviesActivity.class);
                startActivity(intent);

            }
        });

        Button startBusBtn = (Button) findViewById(R.id.bus_button);
        startBusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(StartActivity.this, BusActivity.class);
                startActivity(intent);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        if(requestCode == 5) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }

        if(responseCode == Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            Toast.makeText(getApplicationContext(), "ListItemsActivity passed: "+messagePassed, Toast.LENGTH_LONG).show();
        }
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
                AlertDialog alertDialog = new AlertDialog.Builder(StartActivity.this).create();
                alertDialog.setTitle("Help dialog notification");
                alertDialog.setMessage("Welcome to Final Project \nAuthor: \nFeng Cheng \nMordechai Edery \nKmilla Sam \nSpencer Schening\nYongpan Hu");
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
                intent = new Intent(this, MoviesActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_news_app:
                intent = new Intent(this, Spencer_MainActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_hockey_app:
                intent = new Intent(this, NHL_main.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.home_page_icon:
                intent = new Intent(this, StartActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_bus_icon:
                intent = new Intent(this, BusActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");

    }
}

