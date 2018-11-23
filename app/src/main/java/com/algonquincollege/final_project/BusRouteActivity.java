package com.algonquincollege.final_project;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    public static final String ACTIVITY_NAME = "BusRouteActivity";

    private BusRouteBean route;
    private TextView routeDestination;
    private TextView direction;
    private TextView startTime;
    private TextView adjustedTime;
    private TextView coordinates;
    private TextView speed;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route);
        //bid the node from layout
        routeDestination = (TextView) findViewById(R.id.routenoDestinationView);
        direction = (TextView) findViewById(R.id.directionView);
        startTime = (TextView) findViewById(R.id.startTimeView);
        adjustedTime = (TextView) findViewById(R.id.adjustedTimeView);
        coordinates = (TextView) findViewById(R.id.coordinatesView);
        speed = (TextView) findViewById(R.id.speedView);
        backButton = (Button) findViewById(R.id.busBackButton);

        Bundle bundle = getIntent().getExtras();
        //instantiate a new Route java bean
        route = new BusRouteBean (bundle.getString("routeno"), bundle.getString("destination"),
                bundle.getString("direction"), bundle.getString("stationNum")
        );
        //add the back previous activity button
        backButton.setOnClickListener((e) ->{
            finish();
        });

        new Query().executeOnExecutor( ((r) -> {r.run();}),"");


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
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.bus_help:
                AlertDialog alertDialog = new AlertDialog.Builder(BusRouteActivity.this).create();
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

    private void setDisplay() {
        routeDestination.setText(getString(R.string.bus_route) + route.getRouteNum() + " " + route.getDestination());
        direction.setText(getString(R.string.bus_direction) + route.getDirection());
        startTime.setText(getString(R.string.bus_starttime) + route.getStartTime());
        adjustedTime.setText(getString(R.string.bus_adjustedtime) + route.getAdjustedTime());
        coordinates.setText(getString(R.string.bus_latlong) + route.getCoordinates());
        speed.setText(getString(R.string.bus_gpsspeed) + route.getSpeed());
    }

    /**
     * AsyncTask query inner class to connect the api and parse the information
     */
    public class Query extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            route.updateData();
            if (route.getStartTime() == null || route.getSpeed() == null || route.getCoordinates() == null || route.getAdjustedTime() == null) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Log.i(ACTIVITY_NAME, e.toString());
                }
                route.updateData();
            }
            setDisplay();
            return null;
        }
    }

}