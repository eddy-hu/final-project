package com.algonquincollege.final_project;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.algonquincollege.final_project.R.*;
import static java.lang.System.in;

public class NHL_ScheduleFragment extends Fragment {

    private static final String TAG = "NHL_ScheduleFragment";

    ImageView favImgBtn;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public NHL_ScheduleViewAdapter sadapter;
    ProgressBar pd;
    ArrayList<NHL_ScheduleItem> scheduleItems;
    NHL_ScheduleItem current= null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    //DATABSE
    NHL_DataBaseHelper Ndb;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        new gameQuery().execute();
        return inflater.inflate(layout.nhl_schedule_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pd = (ProgressBar)view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(id.schedule_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        scheduleItems = new ArrayList<>();
        sadapter = new NHL_ScheduleViewAdapter(getActivity(),scheduleItems );
        recyclerView.setAdapter(sadapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        favImgBtn = (ImageView)view.findViewById(id.fav_button);
        //to add the food to the favourite list.
       /* favImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (sadapter != null) {
                        String homeCity = current.homeCityD;
                        String awayCity = current.awayCityD;
                        String homeTeam = current.homeTeamD;
                        String awayTeam = current.awayTeamD;
                        String playedStatus = current.playedStatusD;
                        String homeScore = current.homeScoreD;
                        String awayScore = current.awayScoreD;
                        String dateTime = String.valueOf(current.dateTimeD);
                        int homeLogo = current.homeLogoD;
                        int awayLogo = current.awayLogoD;


                        AddData(homeCity,awayCity,homeTeam, awayTeam, playedStatus, homeScore, awayScore, dateTime, homeLogo, awayLogo);
                    } else {
                        Toast.makeText(getActivity(),"Item not found",Toast.LENGTH_SHORT).show();

                }

            }
        });
*/
    }

    public void AddData(String homeCity, String awayCity, String homeTeam, String awayTeam, String playedStatus, String homeScore,
                        String awayScore, String dateTime, int homeLogo, int awayLogo) {
        boolean insertData = Ndb.addData(homeCity,awayCity,homeTeam, awayTeam, playedStatus, homeScore, awayScore,
                dateTime, homeLogo, awayLogo);
        if (insertData) {
            Toast.makeText(getActivity(),"Data inserted succefully",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"Data not Inserted!!",Toast.LENGTH_SHORT).show();
        }
    }
    private class gameQuery extends AsyncTask<String, Integer,ArrayList<NHL_ScheduleItem>>{

        private String encoding;
        URL url;
        InputStream in = null;
        String userName = "853f95f4-2838-4e01-b451-c3f66e";
        String password = "MYSPORTSFEEDS";
        String base = userName + ":" + password;
        @Override
        protected ArrayList<NHL_ScheduleItem> doInBackground(String... strings) {

            try {
                url = new URL("https://api.mysportsfeeds.com/v2.0/pull/nhl/2018-2019-regular/games.xml");
                encoding = Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);

                HttpURLConnection conn;
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization","Basic "+encoding);
                conn.setDoInput(true);
                conn.connect();
                if (conn.getInputStream() != null) {
                    Log.d("Connected?", "Yes!" );
                } else {
                    Log.d("Connected?", "No");
                }
                in = conn.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("connected for free", "at education connected");
            XmlPullParser parser = Xml.newPullParser();
            try {
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
                parser.setInput(in,null);
                parser.nextTag();
                parser.require(XmlPullParser.START_TAG,null,"gam:iceHockeyGames");
                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }
                    String name = parser.getName();
                    if (name.equalsIgnoreCase("games")) {
                        Log.i("new game", "Create new schedule");
                        current = new NHL_ScheduleItem() ;
                    } else if (current!= null) {
                        if(name.equalsIgnoreCase("startTime")){
                            //current.newdateD = parser.nextText();
                            current.dateTimeD = parser.nextText();
                            // simpleDateFormat.parse(current.newdateD);
                            Log.i(TAG, "doInBackground: startTime Added");
                       } else if(name.equals("abbreviation") && (current.awayTeamD == null)){
                           current.awayTeamD = parser.nextText();
                            Log.i(TAG, "doInBackground: awayTeam Added");
                       }else if(name.equals("abbreviation") && (current.awayTeamD != null)){
                           current.homeTeamD = parser.nextText();
                            Log.i(TAG, "doInBackground: homeTeam Added");
                       }else if(name.equals("playedStatus")){
                           current.playedStatusD = parser.nextText();
                            Log.i(TAG, "doInBackground: status Added");
                       }else if(name.equals("homeScoreTotal")){
                           current.homeScoreD= parser.nextText();
                            Log.i(TAG, "doInBackground: homeScore Added");
                       }else if(name.equals("awayScoreTotal")){
                           current.awayScoreD = parser.nextText();
                            Log.i(TAG, "doInBackground: awayScore Added");
                            scheduleItems.add(current);
                       }
                    }
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null ;
        }

        @Override
        protected void onPostExecute(ArrayList<NHL_ScheduleItem> scheduleItems) {
            super.onPostExecute(scheduleItems);
            Toast.makeText(getActivity(),"Schedule Loaded",Toast.LENGTH_LONG).show();
            recyclerView.setAdapter(sadapter);
            sadapter.notifyDataSetChanged();
            pd.setVisibility(View.GONE);
            Log.i(TAG, "onPostExecute: Data Passed");

            }
        }
    }


