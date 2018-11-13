package com.algonquincollege.final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

public class BusActivity extends Activity {

    protected static final String ACTIVITY_NAME = "BusActivity";
    private ArrayList<String> list;
    private BusAdapter busAdapter;
    private BusListDBHelper dbHelper;
    private ProgressBar progressBar;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        dbHelper = new BusListDBHelper(this);

        list = new ArrayList<>();
        busAdapter = new BusAdapter(this);
        final ListView listView = (ListView) findViewById(R.id.busListView);
        listView.setAdapter(busAdapter);
        final EditText editText = (EditText) findViewById(R.id.busEditText);
        final Button addButton = (Button) findViewById(R.id.busAddButton);
        editText.setHint("Enter bus number");
        final Button deleteButton = (Button) findViewById(R.id.busDeleteButton);
        final Button busHelp  = (Button) findViewById(R.id.busHelpButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        Query query = new Query();
        query.execute();

        busHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(BusActivity.this).create();
                alertDialog.setTitle("dialog notification");
                alertDialog.setMessage("Welcome to OCTranspo");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
                                   });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                list.add(text);
                busAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView(
                dbHelper.insertEntry(editText.getText().toString());
                editText.setText("");
                Toast toast = Toast.makeText(getApplicationContext(), text+" has been added", Toast.LENGTH_SHORT);
                toast.show();

            }
        });
        busAdapter = new BusAdapter(this);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                editText.setText("");
                Snackbar.make(v, "History records have been deleted.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.openDatabase();
        query();
        Log.i(ACTIVITY_NAME, "In onStart()");

    }


    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        dbHelper.closeDatabase();
        super.onDestroy();
    }

    private class BusAdapter extends ArrayAdapter<String> {

        public BusAdapter(Context ctx) {
            super(ctx, 0);
        }

        public String getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public int getCount() {
            return list.size();
        }

        public View getView(int position, View oldView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View result = inflater.inflate(R.layout.bus_history_list_layout, null);

            TextView busSearchList = (TextView) result.findViewById(R.id.bus_serach_list);

            busSearchList.setText(getItem(position)); // get the string at position
            return result;

        }
    }
    public void query() {
        Cursor cursor = dbHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(BusListDBHelper.KEY_MESSAGE)));

                list.add(cursor.getString(cursor.getColumnIndex(BusListDBHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());

        }

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "The " + i + " row is " + cursor.getColumnName(i));
        }
        }
        public void delete(){
            list = new ArrayList<>();
            dbHelper.delete();
        }

    public class Query extends AsyncTask<String, Integer, String> {
        private String currentTemperature;
        private String minTemperature;
        private String maxTemperature;
        private String windSpeed;
        private String icon;
        private Bitmap bitmap;

        @Override
        protected String doInBackground(String ...args) {
            InputStream stream;
            String iconUrl = "http://openweathermap.org/img/w/";
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                stream = conn.getInputStream();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(stream,null);

                while(parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    if(parser.getName().equals("temperature")) {//get temperature
                        currentTemperature = parser.getAttributeValue(null, "value");
                        publishProgress(10);
                        android.os.SystemClock.sleep(200);
                        minTemperature = parser.getAttributeValue(null, "min");
                        publishProgress(20);
                        android.os.SystemClock.sleep(200);
                        maxTemperature = parser.getAttributeValue(null, "max");
                        publishProgress(30);
                        android.os.SystemClock.sleep(200);
                    }
                    if (parser.getName().equals("speed")){//get wind speed
                        windSpeed = parser.getAttributeValue(null, "value");
                        publishProgress(40);
                        android.os.SystemClock.sleep(200);
                    }

                    if(parser.getName().equals("weather")) {
                        icon = parser.getAttributeValue(null, "icon");
                    }
                }
                conn.disconnect();

                if(getBaseContext().getFileStreamPath(icon + ".png").exists()){ //check the local folder
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(getBaseContext().getFileStreamPath(icon + ".png"));
                        Log.d(ACTIVITY_NAME, "File is found");
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap = BitmapFactory.decodeStream(fis);
                }
                else { // if it doesn't exits, connect to the api
                    Log.d(ACTIVITY_NAME, "File is not found");
                    URL imageUrl =  new URL(iconUrl + icon + ".png");
                    conn = (HttpURLConnection) imageUrl.openConnection();
                    conn.connect();
                    stream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(stream);//decode an input stream into a bitmap.
                    FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
                publishProgress(100);
            }
            catch (FileNotFoundException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            catch (XmlPullParserException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            catch (IOException e) {
                Log.e(ACTIVITY_NAME, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer ... value){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
            Log.i(ACTIVITY_NAME, "In onProgressUpdate");
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);

        }

    }


    }




