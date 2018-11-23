package com.algonquincollege.final_project;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Bus start activity class which handles user landing and add stop number functions
 */
public class BusActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "BusActivity";
    ArrayList<String> stopList = new ArrayList<String>();
    ArrayList<String> stopNumbers = new ArrayList<String>();
    ListView stopListView;
    EditText stopInputText;
    Button addStopButton;
    private Context ctx;
    private SQLiteDatabase database;
    private Cursor cursor;

    private int currentStopIndex = 0;

    BusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        //database helper
        BusDBHelper dbHelper = new BusDBHelper(ctx);
        database = dbHelper.getWritableDatabase();

        setContentView(R.layout.activity_bus);

        stopListView = (ListView) findViewById(R.id.stopView);
        stopInputText = (EditText) findViewById(R.id.stationNoInput);
        addStopButton = (Button) findViewById(R.id.addStationNoButton);
        adapter = new BusAdapter(this);
        stopListView.setAdapter(adapter);
        Button busHelp  = (Button) findViewById(R.id.busHelpButton);

        Log.i(ACTIVITY_NAME, "Attempted query:    SELECT " +
                BusDBHelper.STATION_NAME + ", " +
                BusDBHelper.STATION_NO + " FROM " +
                BusDBHelper.TABLE_NAME);

        cursor = database.rawQuery("SELECT " +
                BusDBHelper.STATION_NAME + ", " +
                BusDBHelper.STATION_NO + " FROM " +
                BusDBHelper.TABLE_NAME, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "Current cursor position: " + cursor.getPosition());
            String newStation = "Stop number ";
            newStation = newStation.concat(cursor.getString(1));

            stopList.add(newStation);
            stopNumbers.add(cursor.getString(1));


            cursor.moveToNext();
        }
        /**
         * add click listener action to help button
         * when user clicks it, it will display the author information
         */
        busHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(BusActivity.this).create();
                alertDialog.setTitle("Help dialog notification");
                alertDialog.setMessage("Welcome to OCTranspo \nAuthor: Yongpan Hu");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        /**
         * add click listener action to add button
         * when user typed the stop number and clicks add button, it will add
         * the stop information to list and database;
         */
        addStopButton.setOnClickListener((e) -> {
            String stopInput = stopInputText.getText().toString();
            if (stopInput.matches("-?\\d+")) { //check the input if is an integer;
                ContentValues newData = new ContentValues();

                newData.put(BusDBHelper.STATION_NAME, "NAME_NOT_FOUND");
                newData.put(BusDBHelper.STATION_NO, stopInput);

                database.insert(BusDBHelper.TABLE_NAME, BusDBHelper.STATION_NAME, newData);

                String newStop = "Stop number ";
                newStop = newStop.concat(stopInput);
                stopList.add(newStop);
                stopNumbers.add(stopInput);
                stopInputText.setText("");
                adapter.notifyDataSetChanged();
                Toast toast = Toast.makeText(getApplicationContext(), stopInput+" has been added", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Snackbar badinput = Snackbar.make(findViewById(android.R.id.content), getString(R.string.bus_badinput), Snackbar.LENGTH_SHORT);
                badinput.show();
                stopInputText.setText("");
            }

        });

        /**
         * add click listener action to stop listView
         * when user clicks the item, it will jump to the BusStopActivity;
         */
        stopListView.setOnItemClickListener((parent, view, position, id) -> {
            String s = stopList.get(position);
            Log.i(ACTIVITY_NAME, "Stop: " + s);
            String stationNumber = stopNumbers.get(position);
            Intent i = new Intent(BusActivity.this, BusStopActivity.class);
            i.putExtra("busStopNumber", stationNumber);
            currentStopIndex = position;
            currentStopIndex = position;
            startActivity(i);
        });


    }

    /**
     * add the options menu to this activity
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bus_options_menu, menu);
        return true;
    }

    /**
     * add the options menu to this activity
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.bus_help:
                AlertDialog alertDialog = new AlertDialog.Builder(BusActivity.this).create();
                alertDialog.setTitle("Help dialog notification");
                alertDialog.setMessage("Welcome to OCTranspo \nAuthor: Yongpan Hu");
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
            case R.id.bus_bus_icon:
                intent = new Intent(this, BusActivity.class);
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

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");

        if (BusStopActivity.getDeleteStation() == true) {
            Log.i(ACTIVITY_NAME, "Deleting stop " + currentStopIndex);
            String[] params = new String[1];
            params[0] = stopNumbers.get(currentStopIndex);
            database.delete(BusDBHelper.TABLE_NAME, BusDBHelper.STATION_NO + "=?", params);

            adapter = new BusAdapter(this);
            stopListView.setAdapter(adapter);

            stopList.remove(currentStopIndex);
            stopNumbers.remove(currentStopIndex);
            adapter.notifyDataSetChanged();


            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                     BusStopActivity.getDeletedStationNo() + " has been deleted", Snackbar.LENGTH_SHORT);
            snackbar.show();

            BusStopActivity.resetDeleteStation();
        }

        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.i(ACTIVITY_NAME, "In onStart()");
        super.onStart();
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
        database.close();
        super.onDestroy();
    }

    /**
     * bus list adapter to handles bus stops list array list view;
     */
    public class BusAdapter extends ArrayAdapter<String> {
        public BusAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return (stopList.size());
        }

        @Override
        public String getItem(int position) {
            return stopList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = BusActivity.this.getLayoutInflater();

            View result = inflater.inflate(R.layout.bus_stop, null);

            TextView stationText = (TextView) result.findViewById(R.id.station_text);
            stationText.setText(getItem(position));

            return result;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }

}
