/**
 * The activity for bus route, when use clicked a bus route number from
 * BusStopActivity will invoke this activity
 * @Author: Yongpan Hu
 * @Version: 1.1
 * @Since:1.0
 */
package com.algonquincollege.final_project;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Bus route activity to displays the route detail information
 */
public class BusRouteActivity extends AppCompatActivity {
    /**
     * The name of this class
     */
    public static final String ACTIVITY_NAME = "BusRouteActivity";

    /**
     * BusRouteBean object
     */
    private BusRouteBean route;
    /**
     * Destination of route
     */
    private TextView routeDestination;
    /**
     * Direction of route
     */
    private TextView direction;
    /**
     * Start time of route
     */
    private TextView startTime;
    /**
     * Adjusted time of route
     */
    private TextView adjustedTime;
    /**
     * Coordinates of route
     */
    private TextView coordinates;
    /**
     * Speed of route
     */
    private TextView speed;
    /**
     * Average adjusted time of route
     */
    private TextView averageAdjustedTime;
    /**
     * The button of back to previous activity
     */
    private Button backButton;
    /**
     * The SQLite database helper of route
     */
    private BusRouteBDHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route);
        dbHelper = new BusRouteBDHelper(this);
        //bind the node from layout
        averageAdjustedTime = (TextView) findViewById(R.id.averageAdjustedTime);
        routeDestination = (TextView) findViewById(R.id.routenoDestinationView);
        direction = (TextView) findViewById(R.id.directionView);
        startTime = (TextView) findViewById(R.id.startTimeView);
        adjustedTime = (TextView) findViewById(R.id.adjustedTimeView);
        coordinates = (TextView) findViewById(R.id.coordinatesView);
        speed = (TextView) findViewById(R.id.speedView);
        backButton = (Button) findViewById(R.id.busBackButton);
        Bundle bundle = getIntent().getExtras();
        //instantiate a new Route java bean
        route = new BusRouteBean(bundle.getString("routeno"), bundle.getString("destination"),
                bundle.getString("direction"), bundle.getString("stationNum")
        );
        //add the back previous activity button
        backButton.setOnClickListener((e) -> {
            finish();
        });

        Query query = new Query();
        query.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        Log.i(ACTIVITY_NAME, "onCreate complete");


    }

    /**
     * add the options menu to this activity
     *
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
     *
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.bus_help:
                AlertDialog alertDialog = new AlertDialog.Builder(BusRouteActivity.this).create();
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
                intent = new Intent(this, MoviesActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_bus_icon:
                intent = new Intent(this, BusActivity.class);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        super.onDestroy();
    }

    private void insertEntry() {
        if (route == null) {
            Log.i(ACTIVITY_NAME, "route object empty");
        }
        String routeNo = route.getRouteNum();
        String adjustedTime = route.getAdjustedTime();
        if (adjustedTime == null) {
            adjustedTime = "0";

        }
        dbHelper.openDatabase();
        dbHelper.insertEntry(routeNo, adjustedTime);
        dbHelper.closeDatabase();
    }

    /**
     * get the adjusted time from database then calculate the average
     * @return average adjusted time
     */

    private String getAverageAdjustedTime() {
        dbHelper.openDatabase();
        Log.i(ACTIVITY_NAME, "start getAverageAdjustedTime  ");
        /**
         * Counts how many rows of database
         */
        int count=1;
        /**
         * The total of adjusted time
         */
        int total = 0;
        /**
         * The result of calculated average of adjusted time
         */
        int avgAdjustedTime=0;
        Cursor cursor = dbHelper.getAverageAdjustedTime(route.getRouteNum());
        int colIndex = cursor.getColumnIndex(BusRouteBDHelper.ADJUSTED_TIME);
        cursor.moveToFirst();
        Log.i(ACTIVITY_NAME, "after move to first");
        while (!cursor.isAfterLast()) {
               Log.i(ACTIVITY_NAME, "after cursor= " + cursor.getString(colIndex));

                total += Integer.parseInt(cursor.getString(colIndex));
                Log.i(ACTIVITY_NAME, "total = " + total);

                cursor.moveToNext();

            }
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());


        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "The " + i + " row is " + cursor.getColumnName(i));
        }
        count = cursor.getCount();
        dbHelper.closeDatabase();
     if(total==0){
         return"null";
     }else{
         avgAdjustedTime=total/count;
     }
      return avgAdjustedTime+"";
    }
    /**
     * Refresh the content of the layout
     */
    private void display() {
        routeDestination.setText(getString(R.string.bus_route) + route.getRouteNum() + " " + route.getDestination());
        direction.setText(getString(R.string.bus_direction) + route.getDirection());
        startTime.setText(getString(R.string.bus_starttime) + route.getStartTime());
        coordinates.setText(getString(R.string.bus_latlong) + route.getCoordinates());
        speed.setText(getString(R.string.bus_gpsspeed) + route.getSpeed());
        adjustedTime.setText(getString(R.string.bus_adjustedtime) + route.getAdjustedTime());
        averageAdjustedTime.setText(getString(R.string.bus_avgadjustedtime) + getAverageAdjustedTime());

    }

    /**
     * AsyncTask query inner class to connect the api and parse the information
     */
    public class Query extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            route.updateData();
            if (route.isNull()) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    Log.i(ACTIVITY_NAME, "occurs "+e.toString());
                }
                route.updateData();
            }
            insertEntry();// call insertEntry() to insert the adjusted time to database;
            display();
            return null;
        }
    }

}