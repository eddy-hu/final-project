package com.algonquincollege.final_project;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Movies_Info extends AppCompatActivity {
    /**
     * Variables declaration
     */
    private ImageView poster;
    private RatingBar stars;
    public  TextView titleMovie;
    private TextView yearMovie;
    private TextView runtime;
    private TextView genre;
    private TextView director;
    private TextView actors;
    private TextView plot;
    private Button addToFavorites;
    public static String allMovieInformation = "";
    public DatabaseHelper myDB;
    public Button dataButton;
    String str;
    private Button fragmentTest;


    /**
     * allMovieInformation getter and setter
     * Connection sets json-like string into the variable via setter
     */

    /**
     * movieID getter for Connection
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies__info);

        //Variables initialization
        dataButton = findViewById(R.id.databaseButton);
        myDB = new DatabaseHelper(this);
        poster = findViewById(R.id.moviePoster);
        stars = findViewById(R.id.ratingStars);
        titleMovie = findViewById(R.id.movieName);
        yearMovie = findViewById(R.id.movieYear);
        runtime = findViewById(R.id.runtime);
        genre = findViewById(R.id.genre);
        director = findViewById(R.id.director);
        actors = findViewById(R.id.actors);
        plot = findViewById(R.id.plot);
        addToFavorites = findViewById(R.id.addToFavorites);
        str = "";
        new Connection().execute();
        textViewDetails();
        viewAll();
}

    /**
     * Extracting information from json file
     * */
    private void textViewDetails(){
        try {
            Log.i("lala", str);

                    JSONObject json = new JSONObject(allMovieInformation);
                    String url = json.getString("Poster");
                    Picasso.with(getApplicationContext()).load(url).into(poster);
                    Picasso.with(getApplicationContext()).setLoggingEnabled(true);
                    fragmentTest = findViewById(R.id.fragmentTester);
                    titleMovie.setText(json.getString("Title").toString());
                    yearMovie.setText("Year: " + json.getString("Year").toString());
                    runtime.setText("Runtime: " + json.getString("Runtime").toString());
                    genre.setText("Genre: " + json.getString("Genre").toString());
                    director.setText("Director: " + json.getString("Director").toString());
                    actors.setText("Actors: " + json.getString("Actors").toString());
                    plot.setText("Plot: " + json.getString("Plot").toString());
                    float rating = Float.parseFloat(json.getString("imdbRating")) / 2.0f;
                    stars.setRating(Float.parseFloat(String.format("%.1f", rating).replace(",", ".")));
                    addToFavorites.setOnClickListener(e -> {
                        boolean isInserted;
                        try {

                                String runtime = json.getString("Runtime").replaceAll("\"", "");
                                isInserted = myDB.insertData(json.getString("imdbID"), json.getString("Title").toString(), json.getString("Year").toString(), json.getString("Plot").toString(),
                                        Integer.parseInt(runtime.replace(" min", "")));
                            if (isInserted == true) {
                                Toast.makeText(Movies_Info.this, "Data inserted", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    });
                    allMovieInformation = "";
                    fragmentTest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Movie_Fragment mf = new Movie_Fragment();
                            FragmentManager manage = getSupportFragmentManager();
                            FragmentTransaction ft = manage.beginTransaction();
                            ft.add(R.id.fragmentWithText, mf, "TRANSACTION").commit();
                        }
                    });

        } catch (JSONException e) {
        e.printStackTrace(); }
    }
    public void viewAll(){
        dataButton.setOnClickListener(e-> {
            Intent intentDetails = new Intent(Movies_Info.this, MovieDetails.class);
            startActivity(intentDetails);
        });
    }

}