/**
 * The Java bean object class for bus route
 * @Author: Yongpan Hu
 * @Version: 1.1
 * @Since:1.0
 */
package com.algonquincollege.final_project;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Bus route java bean to store the route object which contains the route information
 */
public class BusRouteBean {
    /**
     * The number of stop
     */
    private String stopNum;
    /**
     * The number of route
     */
    private String routeNum;
    /**
     * The destination of route
     */
    private String destination;
    /**
     * The coordinates of route
     */
    private String coordinates;
    /**
     * The speed of route
     */
    private String speed;
    /**
     * The start time of route
     */
    private String startTime;
    /**
     * The adjusted time of route
     */
    private String adjustedTime;
    /**
     * The direction of route
     */
    private String direction;
    /**
     * The URL of get route info API
     */
    public final String getRouteInfo = "https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo=";
    /**
     * The trailer of of get route info API
     */
    public final String getRouteInfoTrailer = "&routeNo=";

    /**
     * default constructor
     * initialize the variables with default values
     */
    public BusRouteBean(){
        this.stopNum = "unkown";
        this.routeNum = "unkown";
        this.destination = "unkown";
        this.coordinates = "unkown";
        this.speed = "unkown";
        this.startTime = "unkown";
        this.adjustedTime = "unkown";
        this.direction = "unkown";
        //this.ready = false;
    }

    /**
     * Initialize constructor
     * initialize the variables with specified values
     * @param stopNum
     * @param routeNum
     * @param destination
     * @param coordinates
     * @param speed
     * @param startTime
     * @param adjustedTime
     * @param direction
     */
    public BusRouteBean (String stopNum, String routeNum, String destination, String coordinates, String speed, String startTime, String adjustedTime, String direction) {
        this.stopNum = stopNum;
        this.routeNum = routeNum;
        this.destination = destination;
        this.coordinates = coordinates;
        this.speed = speed;
        this.startTime = startTime;
        this.adjustedTime = adjustedTime;
        this.direction = direction;
    }

    /**
     * Initialize constructor
     * initialize the variables with specified values
     * @param routeno
     * @param destination
     * @param direction
     * @param stopNum
     */
    public BusRouteBean(String routeno, String destination, String direction, String stopNum) {
        this.routeNum = routeno;
        this.destination = destination;
        this.direction = direction;
        this.stopNum = stopNum;

    }

    public void updateData() {
        Query query= new Query();
        query.execute("");
    }

    /**
     * AsyncTask Query inner class to connect to Api and parse the information from xml
     */
    public class Query extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... array) {
            String lastTag = "";
            boolean cont = true;
            boolean foundDirection = false;
            String fullCoordinates = "";
            int eventType;

            Log.i("Bean query", "background begun.");


            try {
                String urlstring = getRouteInfo.concat(stopNum);
                urlstring = urlstring.concat(getRouteInfoTrailer);
                urlstring = urlstring.concat(routeNum);
                URL url = new URL(urlstring);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                Log.i("Bean query", "attempting parse");

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(conn.getInputStream(), "UTF-8");
                eventType = xpp.getEventType();
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
                                Log.i("Route", "end parse");
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
                conn.getInputStream().close();
                Log.i("Bean query","closed input stream");
                Log.i("Bean query", "parse complete");
            } catch (Exception e) {
                Log.i("Bean query", "Exception: " + e.toString());
                return ("Exception: " + e.toString());
            }
            return null;
        }

    }

    /**
     * Getter for number of stop
     * @return stopNum
     */
    public String getStopNum() {
        return stopNum;
    }

    /**
     * Getter for number of route
     * @return routeNum
     */
    public String getRouteNum() {
        return routeNum;
    }

    /**
     * Getter for destination of route
     * @return destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Getter for coordinates of route
     * @return
     */
    public String getCoordinates() {
        return coordinates;
    }

    /**
     * Getter for speed of route
     * @return speed
     */
    public String getSpeed() {
        return speed;
    }

    /**
     * Getter for  startTime of route
     * @return startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Getter for adjusted time of route
     * @return  adjustedTime
     */
    public String getAdjustedTime() {
        return adjustedTime;
    }

    /**
     * Getter for direction of route
     * @return direction
     */
    public String getDirection() {
        return direction;
    }
}