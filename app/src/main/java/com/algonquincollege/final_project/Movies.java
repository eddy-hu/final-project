package com.algonquincollege.final_project;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Movies extends AppCompatActivity {
    /**
     * Declaration of variables
     * */
    private ProgressBar progressBar;
    private EditText editText;
    private ListView listView;
    private Button button;
    private final static String URL = "http://www.omdbapi.com/?";
    private final static String API = "6a7a44e4";
    private String movieName;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Button getButton() {
        return button;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_main_activity);
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.find);
        listView = findViewById(R.id.list);
        button = findViewById(R.id.button);

        onClick();
    }
    private void onClick(){
        button.setOnClickListener(e->{
            movieName = editText.getText().toString();
            new Connection().execute();
        });

    }

    class Connection extends AsyncTask<Void, Void, String>{


        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(Movies.this, "Almost there...", Toast.LENGTH_LONG).show();
            editText.setText("");
        }
        @Override
        protected String doInBackground(Void... voids) {

            try {
                java.net.URL url = new URL(URL + "apiKey=" + API + "&s=" + movieName);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
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
                JSONObject json = new JSONObject(response);
                Log.i("Movies",json + "");
                JSONArray jsonArray = json.getJSONArray("Search");
                JSONObject movie;
                String[] movieArr = new String[jsonArray.length()];
                for(int i = 0; i < jsonArray.length();i ++){
                     movie = jsonArray.getJSONObject(i);
                     movieArr[i] = "Title: " + movie.getString("Title") + "\n Year: " + movie.getString("Year");
                }
                progressBar.setVisibility(View.GONE);
                //TODO:PUT MY LISTVIEW HERE:
                Log.i("INFO", response);
            } catch (JSONException e) {
                e.printStackTrace();

            }

        }
        }
    }


