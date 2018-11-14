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

/**
 * NHL mian activity class
 */
public class Mordechai_mainActivity extends Activity  {
    protected static final String ACTIVITY_NAME = "Mordechai_mainActivity";
    private ArrayList<String> list;
    private Button sBtn;
    private Button aBtn;
    private Button dBtn;
    private ProgressBar progressBar;
    private EditText editText;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mordechai_mainactivity);

        list = new ArrayList<>();
        adapter = new Adapter(this);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.input);
        sBtn = (Button)findViewById(R.id.search_btn);
        aBtn = (Button)findViewById(R.id.add_btn);
        dBtn = (Button)findViewById(R.id.del_btn);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        NHLQuery query = new NHLQuery();
        query.execute();

        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                 list.add(text);
                adapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView(
                editText.setText("");
                Toast toast = Toast.makeText(getApplicationContext(), text+" searching toast", Toast.LENGTH_SHORT);
                toast.show();

            }
        });
        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "You have clicked on Add", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });

        dBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(Mordechai_mainActivity.this).create();
                alertDialog.setMessage("Delete button");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

        });

    }

    /**
     * example query from lab6
     */
    public class NHLQuery extends AsyncTask<String, Integer, String> {
        private String currentTemperature;
        private String minTemperature;
        private String maxTemperature;
        private String windSpeed;
        private String icon;
        private Bitmap bitmap;

        /**
         *  do it in backgraound
         * @param args
         * @return String
         */
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

    /**
     * list view adapter
     */
    public class Adapter extends ArrayAdapter<String> {

        public Adapter(Context ctx) {
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

            View result = inflater.inflate(R.layout.nhl_list_layout, null);

            TextView nhlList = (TextView) result.findViewById(R.id.nhl_serach_list);

            nhlList.setText(getItem(position)); // get the string at position
            return result;

        }
    }
}
