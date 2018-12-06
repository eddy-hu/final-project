/**
 * The start activity for bus application
 * @Author: Yongpan Hu
 * @Version: 1.1
 * @Since:1.0
 */
package com.algonquincollege.final_project;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    /**
     * The name of this class
     */
    protected static final String ACTIVITY_NAME = "BusActivity";
    /**
     * The list of stops
     */
    private ArrayList<String> stopList = new ArrayList<String>();
    /**
     * The list of stop numbers
     */
    private ArrayList<String> stopNumbers = new ArrayList<String>();
    /**
     * The list view of stops
     */
    private ListView stopListView;
    /**
     * The edit text for stop input
     */
    private EditText stopInputText;
    /**
     * The button of add bus stop
     */
    private Button addStopButton;
    /**
     * Current context
     */
    private Context ctx;
    /**
     * The SQLite database for bus stop
     */
    private SQLiteDatabase database;
    /**
     * The SQLite cursor for database queryStop results
     */
    private Cursor cursor;
    /**
     * The index of current stop
     */
    private int currentStopIndex = 0;
    /**
     * The adapter for bus list view
     */
    private BusAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        BusDBHelper dbHelper = new BusDBHelper(ctx);
        database = dbHelper.getWritableDatabase();

        setContentView(R.layout.activity_bus);

        stopListView = (ListView) findViewById(R.id.stopView);
        stopInputText = (EditText) findViewById(R.id.stationNoInput);
        addStopButton = (Button) findViewById(R.id.addStationNoButton);
        adapter = new BusAdapter(this);
        stopListView.setAdapter(adapter);
        Button busHelp  = (Button) findViewById(R.id.busHelpButton);
        cursor = queryStop(database);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "Current cursor position: " + cursor.getPosition());
            stopList.add("Stop number".concat(cursor.getString(1)));
            stopNumbers.add(cursor.getString(1));
            cursor.moveToNext();
        }
        /**
         * Add click listener action to help button
         * when user clicks it, it will display the author information
         */
        busHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(BusActivity.this).create();
                alertDialog.setTitle("Help dialog notification");
                alertDialog.setMessage("Welcome to OCTranspo \nAuthor: Yongpan Hu \n Verssion:1.0 \n 1.Add stop number" +
                        "\n 2.Click route number\n 3.See the detail of route");
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
         * Add click listener action to add button
         * when user typed the stop number and clicks add button, it will add
         * the stop information to list and database;
         */
        addStopButton.setOnClickListener((e) -> {
            String stopInput = stopInputText.getText().toString();
            if (stopInput.matches("-?\\d+")) { //check the input if is an integer;
                ContentValues cv = new ContentValues();
                cv.put(BusDBHelper.STOP_NAME, "NAME_NOT_FOUND");
                cv.put(BusDBHelper.STOP_NO, stopInput);
                database.insert(BusDBHelper.TABLE_NAME, BusDBHelper.STOP_NAME, cv);
                stopList.add("Stop number ".concat(stopInput));
                stopNumbers.add(stopInput);
                stopInputText.setText("");
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), stopInput+" has been added", Toast.LENGTH_SHORT).show();

            } else {
                Snackbar invalidInputBar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.bus_badinput), Snackbar.LENGTH_SHORT);
                invalidInputBar.show();
                stopInputText.setText("");
            }

        });

        /**
         * Add click listener action to stop listView
         * when user clicks the item, it will jump to the BusStopActivity;
         */
        stopListView.setOnItemClickListener((parent, view, position, id) -> {
            String s = stopList.get(position);
            Log.i(ACTIVITY_NAME, "Stop: " + s);
            String stationNumber = stopNumbers.get(position);
            Intent i = new Intent(BusActivity.this, BusStopActivity.class);
            i.putExtra("busStopNumber", stationNumber);
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
                alertDialog.setMessage("Welcome to OCTranspo \nAuthor: Yongpan Hu \n Verssion:1.0 \n 1.Add stop number" +
                        "\n 2.Click route number\n 3.See the detail of route");
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

    /**
     * Query the stop from database
     * @param database
     * @return cursor
     */
    private Cursor queryStop(SQLiteDatabase database){

        Cursor cursor= database.rawQuery("SELECT " +
                BusDBHelper.STOP_NAME + ", " +
                BusDBHelper.STOP_NO + " FROM " +
                BusDBHelper.TABLE_NAME, null, null);
        return cursor;
    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onResume()");

        if (BusStopActivity.getDeleteStop()) {
            Log.i(ACTIVITY_NAME, "Deleting stop " + currentStopIndex);
            String[] params = new String[1];
            params[0] = stopNumbers.get(currentStopIndex);
            database.delete(BusDBHelper.TABLE_NAME, BusDBHelper.STOP_NO + "=?", params);
            adapter = new BusAdapter(this);
            stopListView.setAdapter(adapter);
            stopList.remove(currentStopIndex);
            stopNumbers.remove(currentStopIndex);
            adapter.notifyDataSetChanged();
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                     BusStopActivity.getDeletedStopNo() + " has been deleted", Snackbar.LENGTH_SHORT);
            snackbar.show();
            BusStopActivity.resetDeleteStop();
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
