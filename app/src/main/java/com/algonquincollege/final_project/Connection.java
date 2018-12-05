package com.algonquincollege.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


class Connection extends AsyncTask<Void, Void, String> {

    /**
     * Declaration and Initialization of variables
     * */
    private final static String URL = "http://www.omdbapi.com/?";
    private final static String API = "6a7a44e4";
    //Access to the Movies and Movies_Info activities
    public static Activity myActivity;
    public static String test = "";


    private ProgressBar progressBar = Connection.myActivity.findViewById(R.id.progressBar);
    private EditText editText = Connection.myActivity.findViewById(R.id.find);
    private String movieName = editText.getText().toString();
    public static String someVar = "";


    public Connection(String test){
        this.test= test;
    }

    public Connection(){}

    protected void onPreExecute(){
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
     // Toast.makeText(Movies.this, "Almost there...", Toast.LENGTH_LONG).show();
        editText.setText("");
    }
    @Override
    protected String doInBackground(Void... voids) {
        try {
            //Establishing the connection
            java.net.URL url;
            if(test == "")
                url = new URL(URL + "apiKey=" + API + "&s=" + movieName);
            else {
                url = new URL(URL + "apiKey=" + API + "&i=" + test);
            }
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            progressBar.setVisibility(View.GONE);
            return reader(urlConnection);
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }

    }
    @Override
    protected void onPostExecute(String response){
        super.onPostExecute(response);
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        try {
            Log.i("RESPOM", response);
            //Working with ArrayList
            JSONObject json = new JSONObject(response);

            if(test == "") {
                JSONArray jsonArray = json.getJSONArray("Search");
                JSONObject movie;
                for (int i = 0; i < jsonArray.length(); i++) {
                    movie = jsonArray.getJSONObject(i);
                    Movies.getBunchOfMovies().add(movie.getString("Title") + "\nYear: " + movie.getString("Year") + "\nType: " + movie.getString("Type"));
                    Movies.getMovieID().add(movie.getString("imdbID"));
                }
            }else {
                Movies.testString = response;
                someVar = response;
                Movies_Info.allMovieInformation = response;
                test="";

            }
            Movies.getArrayAdapter().notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }

    public String reader(HttpURLConnection urlConnection){
        try {
            //Building the StringBuilder from JSON
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            urlConnection.disconnect();
        }
        return null;
    }
}
