package com.algonquincollege.final_project;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;

public class OCRouteInformation extends Activity {
    public static final String ACTIVITY_NAME = "OCRouteInformation";

    OCRouteBean route;

    TextView routenoDestination;
    TextView direction;
    TextView startTime;
    TextView adjustedTime;
    TextView coordinates;
    TextView speed;
    Button refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocroute_information);

        routenoDestination = (TextView) findViewById(R.id.routenoDestinationView);
        direction = (TextView) findViewById(R.id.directionView);
        startTime = (TextView) findViewById(R.id.startTimeView);
        adjustedTime = (TextView) findViewById(R.id.adjustedTimeView);
        coordinates = (TextView) findViewById(R.id.coordinatesView);
        speed = (TextView) findViewById(R.id.speedView);
        refresh = (Button) findViewById(R.id.refreshRouteButton);


        Bundle extras = getIntent().getExtras();

        route = new OCRouteBean(extras.getString("routeno"), extras.getString("destination"),
                extras.getString("direction"), extras.getString("stationNum")
        );

        new Update().executeOnExecutor( ((r) -> {r.run();}),"");


        refresh.setOnClickListener((e) -> {
            Toast toast = Toast.makeText(this, getString(R.string.oc_refresh), Toast.LENGTH_SHORT);
            toast.show();
            route.updateData();
            setDisplay();
        });
    }

    private void setDisplay() {
        routenoDestination.setText(getString(R.string.oc_route) + route.getRouteno() + " " + route.getDestination());
        direction.setText(getString(R.string.oc_direction) + route.getDirection());
        startTime.setText(getString(R.string.oc_starttime) + route.getStartTime());
        adjustedTime.setText(getString(R.string.oc_adjustedtime) + route.getAdjustedTime());
        coordinates.setText(getString(R.string.oc_latlong) + route.getCoordinates());
        speed.setText(getString(R.string.oc_gpsspeed) + route.getSpeed());
    }

    public class Update extends AsyncTask<String, Integer, String> {
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
    class OCRouteBean {

        private String stationNum;
        private String routeno;
        private String destination;
        private String coordinates;
        private String speed;
        private String startTime;
        private String adjustedTime;
        private String direction;

        private boolean ready = false;

        public final String getRouteInfo = "https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";
        public final String getRouteInfoTrailer = "&routeNo=";

        public OCRouteBean (String stationNum, String routeno, String destination, String coordinates, String speed, String startTime, String adjustedTime, String direction) {
            this.stationNum = ((stationNum != null) ? stationNum : "Information unavailable");
            this.routeno = ((routeno != null) ? routeno : "Information unavailable");
            this.destination = ((destination != null) ? destination : "Information unavailable");
            this.coordinates = ((coordinates != null) ? coordinates : "Information unavailable");
            this.speed = ((speed != null) ? speed : "Information unavailable");
            this.startTime = ((startTime != null) ? startTime : "Information unavailable");
            this.adjustedTime = ((adjustedTime != null) ? adjustedTime : "Information unavailable");
            this.direction = ((direction != null) ? direction : "Information unavailable");

            ready = true;
        }

        public OCRouteBean(String routeno, String destination, String direction, String stationNum) {
            this.routeno = routeno;
            this.destination = destination;
            this.direction = direction;
            this.stationNum = stationNum;
        }

        public void updateData() {
            new OCRouteQuery().execute("");
        }


        public String getRouteno() { return routeno; }
        public String getDestination() {
            return destination;
        }
        public String getStationNum() { return stationNum; }
        public String getCoordinates() { return coordinates; }
        public String getSpeed() { return speed; }
        public String getStartTime() { return startTime; }
        public String getAdjustedTime() { return adjustedTime; }
        public String getDirection() { return direction; }

        public boolean isReady () {
            return ready;
        }




        public class OCRouteQuery extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... array) {
                Log.i("OCRoute constructor", "background activity begun..");


                try {
                    String urlstring = getRouteInfo.concat(stationNum);
                    urlstring = urlstring.concat(getRouteInfoTrailer);
                    urlstring = urlstring.concat(routeno);
                    URL url = new URL(urlstring);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    Log.i("OCRoute constructor", "attempting parse..");
                    parse(conn.getInputStream());
                    Log.i("OCRoute constructor", "parse complete");
                } catch (Exception e) {
                    Log.i("OCRoute constructor", "Error: " + e.toString());
                    return ("Error: " + e.toString());
                }
                return null;
            }

            protected void parse(InputStream in) throws XmlPullParserException, IOException {
                String lastTag = "";
                boolean cont = true;
                boolean foundDirection = false;

                String fullCoordinates = "";
                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);

                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(in, "UTF-8");


                    int eventType = xpp.getEventType();

                    while ((eventType != XmlPullParser.END_DOCUMENT) && cont) {

                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                lastTag = xpp.getName();
                                break;
                            case XmlPullParser.TEXT:
                                if (lastTag.equals("Direction") && xpp.getText().equals(direction)) {
                                    foundDirection = true;
                                } else if (foundDirection) {
                                    Log.i("TagValue", xpp.getText());
                                    if (lastTag.equals("TripDestination"))
                                        destination = xpp.getText();
                                    else if (lastTag.equals("TripStartTime"))
                                        startTime = xpp.getText();
                                    else if (lastTag.equals("AdjustedScheduleTime"))
                                        adjustedTime = xpp.getText();
                                    else if (lastTag.equals("Latitude"))
                                        fullCoordinates = (xpp.getText().concat("/"));
                                    else if (lastTag.equals("Longitude"))
                                        coordinates = fullCoordinates.concat(xpp.getText());
                                    else if (lastTag.equals("GPSSpeed")) {
                                        speed = xpp.getText();
                                    }
                                }
                                break;
                            case XmlPullParser.END_TAG:
                                if (xpp.getName().equals("Trip") && foundDirection) {
                                    cont = false;
                                    Log.i("Route", "breaking from parse");
                                }
                                break;
                            default:
                                break;
                        }
                        xpp.next();
                        eventType = xpp.getEventType();
                    }
                    Log.i("FinalValues", destination +" "+
                            startTime +" "+
                            adjustedTime +" "+
                            coordinates +" "+
                            speed);
                } finally {
                    in.close();
                    Log.i("OCRoute constructor","closed input stream");
                }
            }

            @Override
            protected void onPostExecute(String result) {
                stationNum = ((stationNum != null) ? stationNum : "Information unavailable");
                routeno = ((routeno != null) ? routeno : "Information unavailable");
                destination = ((destination != null) ? destination : "Information unavailable");
                coordinates = ((coordinates != null) ? coordinates : "Information unavailable");
                speed = ((speed != null) ? speed : "Information unavailable");
                startTime = ((startTime != null) ? startTime : "Information unavailable");
                adjustedTime = ((adjustedTime != null) ? adjustedTime : "Information unavailable");
                direction = ((direction != null) ? direction : "Information unavailable");

                ready = true;
            }
        }
    }
}