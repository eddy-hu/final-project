package com.algonquincollege.final_project;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.help:
                AlertDialog alertDialog = new AlertDialog.Builder(MoviesActivity.this).create();
                alertDialog.setTitle(getString(R.string.title));
                alertDialog.setMessage(getString(R.string.author2) + "\n" +
                        getString(R.string.version));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
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
                return true;
            case R.id.bus_news_app:
                intent = new Intent(this, Spencer_MainActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.bus_bus_icon:
                intent = new Intent(this, BusActivity.class);
                this.startActivity(intent);
                return true;
            case R.id.bus_hockey_app:
                intent = new Intent(this, NHL_main.class);
                this.startActivity(intent);
                return true;
            case R.id.home_page_icon:
                intent = new Intent(this, StartActivity.class);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Variables declaration
     */
    private ProgressBar progressBar;
    private EditText editText;
    private Button button;
    private final static String URL = "http://www.omdbapi.com/?";
    private final static String API = "6a7a44e4";
    private String movieName;
    private static ArrayAdapter<String> arrayAdapter;
    private static ArrayList<String> bunchOfMovies;
    private LayoutInflater inflater;
    private static ArrayList<String> movieID;
    private ListView lv;
    public static String testString = "";
    public ArrayList<String> movieList;
    public static String jsonMovie;


    public static ArrayAdapter<String> getArrayAdapter() {
        return arrayAdapter;
    }

    public static ArrayList<String> getBunchOfMovies() {
        return bunchOfMovies;
    }

    public static ArrayList<String> getMovieID() {
        return movieID;
    }


    /**
    Initializes the variables
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_main_activity);

        //Variables Initialization
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.find);
        button = findViewById(R.id.button);
        inflater = LayoutInflater.from(this);
        bunchOfMovies = new ArrayList<>();
        movieID = new ArrayList<>();
        jsonMovie = "";

        Connection.myActivity = this;


        //Fragment Example
        FrameLayout frameLayout = findViewById(R.id.fragmentForSomeText);
        View fragment = inflater.inflate(R.layout.list, frameLayout, false);
        TextView exampleFragmentText = findViewById(R.id.fragmentText_example);
        exampleFragmentText.setText("Some text");
        frameLayout.addView(fragment);

        button.setOnClickListener(e -> {
            bunchOfMovies.clear();
            movieID.clear();
            new Connection().execute();
        });
        /**
         *  onClickListener for searching button, currently just makes snackbar.
         */


        //Working with ListView
        lv = findViewById(R.id.movieList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, bunchOfMovies) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.WHITE);

                // Generate ListView Item using TextView
                return view;
            }
        };
        lv.setAdapter(arrayAdapter);

        //Handling event when clicking the item from the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MoviesActivity.this, Movies_Info.class);
                intent.putExtra("ID", movieID.get(position));
                new Connection(movieID.get(position)).execute();
                startActivity(intent);

                /**
                 * Shows toast message and progress bar
                 * */


            }});

    }

}



