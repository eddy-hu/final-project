package com.algonquincollege.final_project;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class BusRouteActivity extends Activity {
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

        routeDestination = (TextView) findViewById(R.id.routenoDestinationView);
        direction = (TextView) findViewById(R.id.directionView);
        startTime = (TextView) findViewById(R.id.startTimeView);
        adjustedTime = (TextView) findViewById(R.id.adjustedTimeView);
        coordinates = (TextView) findViewById(R.id.coordinatesView);
        speed = (TextView) findViewById(R.id.speedView);
        backButton = (Button) findViewById(R.id.busBackButton);

        Bundle bundle = getIntent().getExtras();

        route = new BusRouteBean(bundle.getString("routeno"), bundle.getString("destination"),
                bundle.getString("direction"), bundle.getString("stationNum")
        );

        backButton.setOnClickListener((e) ->{
            finish();
        });

        new Query().executeOnExecutor( ((r) -> {r.run();}),"");


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