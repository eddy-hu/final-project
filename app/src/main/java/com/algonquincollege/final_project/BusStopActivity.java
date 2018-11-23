package com.algonquincollege.final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Bus top detail activity to displays bus stop details information
 */
public class BusStopActivity extends AppCompatActivity {

    public static final String API_URL = "https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";
    protected static final String ACTIVITY_NAME = "BusStopActivity";
    private static boolean deleteStation = false;
    private static String lastStation = "";
    private int busStopNumber;
    private String stopName = "";
    private Context ctx = this;
    private ArrayList<BusRouteBean> allRoutes = new ArrayList<>();
    private ArrayList<String> routesInfo = new ArrayList<>();
    private ListView routeListView;
    private ProgressBar progressBar;
    private int progress;
    private TextView stopNameView;
    private Button backButton;
    private Button deleteStopButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        routeListView = (ListView) findViewById(R.id.routesView);
        backButton = (Button) findViewById(R.id.busStopBackButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progress = 0;
        stopNameView = (TextView) findViewById(R.id.stationName);
        deleteStopButton = (Button) findViewById(R.id.deleteStationButton);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.i(ACTIVITY_NAME, "Error: no stop number could be found");
        } else {
            busStopNumber = Integer.parseInt(extras.getString("busStopNumber"));
            stopNameView.setText("Stop: " + stopName);
        }

        new Query().execute("");


        StopAdapter adapter = new StopAdapter(this);
        routeListView.setAdapter(adapter);

        //add the back previous activity button
        backButton.setOnClickListener((e) ->{
            finish();
        });

        //add the delete stop button action
        deleteStopButton.setOnClickListener((e) -> {
            Log.i(ACTIVITY_NAME, "Delete button clicked!");
            deleteStation = true;
            lastStation = Integer.toString(busStopNumber);
            finish();
        });

        //displays route list, when user clicks the item on the list will jump to the route detail activity
        routeListView.setOnItemClickListener((parent, view, position, id) -> {
            String s = routesInfo.get(position);
            Log.i(ACTIVITY_NAME, "Message: " + s);
            Intent i = new Intent(BusStopActivity.this, BusRouteActivity.class);
            i.putExtra("routeno", allRoutes.get(position).getRouteNum());
            i.putExtra("destination", allRoutes.get(position).getDestination());
            i.putExtra("stationNum", allRoutes.get(position).getStopNum());
            i.putExtra("direction", allRoutes.get(position).getDirection());
            startActivity(i);
        });


    }

    @Override
    protected void onResume() {
        Log.i(ACTIVITY_NAME, "In onCreate()");
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
        super.onDestroy();
    }

    private void updateProgressBar(int u, int max) {
        progress += u;
        if (progress > max)
            progress = max;
        progressBar.setProgress(progress);
    }

    private void stationNotFoundProcedure() {
        AlertDialog alertDialog = new AlertDialog.Builder(BusStopActivity.this).create();
        alertDialog.setTitle("Stop not found");
        alertDialog.setMessage("Stop not found, please try another one");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public class StopAdapter extends ArrayAdapter<String> {
        public StopAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return (routesInfo.size());
        }

        @Override
        public String getItem(int position) {
            return routesInfo.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = BusStopActivity.this.getLayoutInflater();

            View result = inflater.inflate(R.layout.bus_route, null);

            TextView routeText = (TextView)result.findViewById(R.id.route_text);

            routeText.setText (getItem(position) );
            return result;
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

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
                AlertDialog alertDialog = new AlertDialog.Builder(BusStopActivity.this).create();
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

    /**
     * AsyncTask query inner class to handles connect to the api and parse the information
     */
    public class Query extends AsyncTask<String, Integer, String> {
        public String connStationNumber;
        public ArrayList<BusRouteBean> routesList = new ArrayList<BusRouteBean>();
        private String currentRouteno;
        private String currentRouteDirection;
        private String currentRouteDestination;

        @Override
        protected String doInBackground(String... array) {
            Log.i(ACTIVITY_NAME, "Do in background");
            String lastTag = "";
            try {
                URL url = new URL(API_URL.concat(Integer.toString(busStopNumber)));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                updateProgressBar(10, 10);

                Log.i(ACTIVITY_NAME, "Attempting parse.");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(conn.getInputStream(), "UTF-8");


                int eventType = xpp.getEventType();
                updateProgressBar(10,20);

                Log.i(ACTIVITY_NAME, "Attempting parse: ");
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            Log.i(ACTIVITY_NAME, "Tag is found.");
                            lastTag = xpp.getName();
                            updateProgressBar(3,80);
                            Log.i(ACTIVITY_NAME, "Tag is " + lastTag);
                            break;
                        case XmlPullParser.TEXT:
                            if (lastTag.equals("StopDescription")) {
                                Log.i(ACTIVITY_NAME, "Stop name found: ");
                                stopName = xpp.getText();
                                updateProgressBar(12,80);
                            } else if (lastTag.equals("RouteNo")) {
                                currentRouteno = xpp.getText();
                                updateProgressBar(12,80);
                            } else if (lastTag.equals("Direction")) {
                                currentRouteDirection = xpp.getText();
                                updateProgressBar(10,80);
                            } else if (lastTag.equals("RouteHeading")) {
                                currentRouteDestination = xpp.getText();
                                updateProgressBar(10,80);
                                routesList.add(new BusRouteBean(currentRouteno, currentRouteDestination, currentRouteDirection, Integer.toString(busStopNumber)));
                            }
                            break;
                        default:
                            break;
                    }
                    xpp.next();
                    eventType = xpp.getEventType();
                }
                conn.getInputStream().close();
                Log.i(ACTIVITY_NAME, "closed input stream");
                updateProgressBar(100,90);
                Log.i(ACTIVITY_NAME, "parse complete");
            } catch (Exception e) {

                Log.i(ACTIVITY_NAME, "Error: " + e.toString());
                return ("Error: " + e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            StopAdapter adapter = new StopAdapter(ctx);
            ListView routesView = (ListView)findViewById(R.id.routesView);
            routesView.setAdapter(adapter);

            stopNameView.setText("Stop: " + stopName);

            for (BusRouteBean r : routesList) {
                String newRoute = "";
                newRoute = newRoute.concat(r.getRouteNum());
                newRoute = newRoute.concat(" ");
                newRoute = newRoute.concat(r.getDestination());
                routesInfo.add(newRoute);
                adapter.notifyDataSetChanged();
                allRoutes.add(r);
                updateProgressBar(2,100);
            }
            updateProgressBar(100,100);

            if (stopName.equals(""))
                stationNotFoundProcedure();
        }
    }
    public static void resetDeleteStation()
    {
        deleteStation = false;
    }

    public static boolean getDeleteStation() {

        return deleteStation;
    }

    public static String getDeletedStationNo() {
        return lastStation; }
}