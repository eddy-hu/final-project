package com.algonquincollege.final_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class NHL_ScheduleViewAdapter extends RecyclerView.Adapter<NHL_ScheduleViewAdapter.ViewHolder> {

    private static final String TAG = "NHL_ScheduleViewAdapter";

    private  FragmentActivity activity;
    private ArrayList<NHL_ScheduleItem> scheduleItems;
    private Context context;
    LayoutInflater inflater;


    public NHL_ScheduleViewAdapter(ArrayList<NHL_ScheduleItem> scheduleItems, Context context, LayoutInflater inflater) {
        this.scheduleItems = scheduleItems;
        this.context = context;
        this.inflater = inflater;
    }

    public NHL_ScheduleViewAdapter(FragmentActivity activity, ArrayList<NHL_ScheduleItem> data) {
        this.activity = this.activity;
        this.scheduleItems = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhl_schedule_fragment_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        NHL_ScheduleItem current = scheduleItems.get(position);
        holder.homeCityText.setText(current.homeCityD);
        holder.awayCityText.setText(current.awayCityD);
        holder.homeTeamText.setText(current.homeTeamD);
        holder.awayTeamText.setText(current.awayTeamD);
        holder.playedStatusText.setText(current.playedStatusD);
        holder.homeScoreText.setText(current.homeScoreD);
        holder.awayScoreText.setText(current.awayScoreD);
        holder.dateTimeText.setText(String.valueOf(current.dateTimeD));
        holder.favButtonL.setImageResource(R.drawable.ic_add_circle_black_24dp);
        //holder.homeLogoImgL.setImageResource(current.homeLogo);
       // holder.awayLogoImgL.setImageResource(current.awayLogo);

        switch (current.playedStatusD){
            case "COMPLETED":
                holder.playedStatusText.setText("FINAL");
                break;
            case "UNPLAYED":
                holder.playedStatusText.setText("");
                break;

        }

        switch (current.awayTeamD){
            case "TBL":
                holder.awayLogoImgL.setImageResource(R.drawable.id_1_tampa_bay_lightning);
                break;
            case "WPJ":
                holder.awayLogoImgL.setImageResource(R.drawable.id_2_winnipeg_jets);
                break;
            case "CAR":
                holder.awayLogoImgL.setImageResource(R.drawable.id_3_carolina_hurricanes);
                break;
            case "FLO":
                holder.awayLogoImgL.setImageResource(R.drawable.id_4_florida_panthers);
                break;
            case "WSH":
                holder.awayLogoImgL.setImageResource(R.drawable.id_5_washington_capitals);
                break;
            case "PHI":
                holder.awayLogoImgL.setImageResource(R.drawable.id_6_philadelphia_flyers);
                break;
            case "NJD":
                holder.awayLogoImgL.setImageResource(R.drawable.id_7_newjersey_devils);
                break;
            case "NYI":
                holder.awayLogoImgL.setImageResource(R.drawable.id_8_new_york_islanders);
                break;
            case "NYR":
                holder.awayLogoImgL.setImageResource(R.drawable.id_9_new_york_rangers);
                break;
            case "PIT":
                holder.awayLogoImgL.setImageResource(R.drawable.id_10_pittsburgh_penguins);
                break;
            case "BOS":
                holder.awayLogoImgL.setImageResource(R.drawable.id_11_boston_bruins);
                break;
            case "TOR":
                holder.awayLogoImgL.setImageResource(R.drawable.id_12_toronto_mapleleafs);
                break;
            case "OTT":
                holder.awayLogoImgL.setImageResource(R.drawable.id_13_ottawa_senators);
                break;
            case "MTL":
                holder.awayLogoImgL.setImageResource(R.drawable.id_14_montreal_canadiens);
                break;
            case "BUF":
                holder.awayLogoImgL.setImageResource(R.drawable.id_15_buffalo_sabres);
                break;
            case "DET":
                holder.awayLogoImgL.setImageResource(R.drawable.id_16_detroit_redwings);
                break;
            case "STL":
                holder.awayLogoImgL.setImageResource(R.drawable.id_17_stlouis_blues);
                break;
            case "NSH":
                holder.awayLogoImgL.setImageResource(R.drawable.id_18_nashville_predators);
                break;
            case "CBJ":
                holder.awayLogoImgL.setImageResource(R.drawable.id_19_columbus_bluejackets);
                break;
            case "CHI":
                holder.awayLogoImgL.setImageResource(R.drawable.id_20_chicago_blackhawks);
                break;
            case "COL":
                holder.awayLogoImgL.setImageResource(R.drawable.id_22_colorado__avalanche);
                break;
            case "VAN":
                holder.homeLogoImgL.setImageResource(R.drawable.id_21_vancouver_canucks);
            case "CGY":
                holder.awayLogoImgL.setImageResource(R.drawable.id_23_clagary_flames);
                break;
            case "EDM":
                holder.awayLogoImgL.setImageResource(R.drawable.id_24_edmontn_oilers);
                break;
            case "MIN":
                holder.awayLogoImgL.setImageResource(R.drawable.id_25_minnesota_wild);
                break;
            case "SJS":
                holder.awayLogoImgL.setImageResource(R.drawable.id_26_sanjose_sharks);
                break;
            case "DAL":
                holder.awayLogoImgL.setImageResource(R.drawable.id_27_dallas_stars);
                break;
            case "LAK":
                holder.awayLogoImgL.setImageResource(R.drawable.id_28_la_kings);
                break;
            case "ANA":
                holder.awayLogoImgL.setImageResource(R.drawable.id_29_anaheim_ducks);
                break;
            case "ARI":
                holder.awayLogoImgL.setImageResource(R.drawable.id_30_arizona_coyotes);
                break;
            case "VGK":
                holder.awayLogoImgL.setImageResource(R.drawable.id_142_vegas_goldenknights);
                break;
        }

        switch (current.homeTeamD){
            case "TBL":
                holder.homeLogoImgL.setImageResource(R.drawable.id_1_tampa_bay_lightning);
                break;
            case "WPJ":
                holder.homeLogoImgL.setImageResource(R.drawable.id_2_winnipeg_jets);
                break;
            case "CAR":
                holder.homeLogoImgL.setImageResource(R.drawable.id_3_carolina_hurricanes);
                break;
            case "FLO":
                holder.homeLogoImgL.setImageResource(R.drawable.id_4_florida_panthers);
                break;
            case "WSH":
                holder.homeLogoImgL.setImageResource(R.drawable.id_5_washington_capitals);
                break;
            case "PHI":
                holder.homeLogoImgL.setImageResource(R.drawable.id_6_philadelphia_flyers);
                break;
            case "NJD":
                holder.homeLogoImgL.setImageResource(R.drawable.id_7_newjersey_devils);
                break;
            case "NYI":
                holder.homeLogoImgL.setImageResource(R.drawable.id_8_new_york_islanders);
                break;
            case "NYR":
                holder.homeLogoImgL.setImageResource(R.drawable.id_9_new_york_rangers);
                break;
            case "PIT":
                holder.homeLogoImgL.setImageResource(R.drawable.id_10_pittsburgh_penguins);
                break;
            case "BOS":
                holder.homeLogoImgL.setImageResource(R.drawable.id_11_boston_bruins);
                break;
            case "TOR":
                holder.homeLogoImgL.setImageResource(R.drawable.id_12_toronto_mapleleafs);
                break;
            case "OTT":
                holder.homeLogoImgL.setImageResource(R.drawable.id_13_ottawa_senators);
                break;
            case "MTL":
                holder.homeLogoImgL.setImageResource(R.drawable.id_14_montreal_canadiens);
                break;
            case "BUF":
                holder.homeLogoImgL.setImageResource(R.drawable.id_15_buffalo_sabres);
                break;
            case "DET":
                holder.homeLogoImgL.setImageResource(R.drawable.id_16_detroit_redwings);
                break;
            case "STL":
                holder.homeLogoImgL.setImageResource(R.drawable.id_17_stlouis_blues);
                break;
            case "NSH":
                holder.homeLogoImgL.setImageResource(R.drawable.id_18_nashville_predators);
                break;
            case "CBJ":
                holder.homeLogoImgL.setImageResource(R.drawable.id_19_columbus_bluejackets);
                break;
            case "CHI":
                holder.homeLogoImgL.setImageResource(R.drawable.id_20_chicago_blackhawks);
                break;
            case "VAN":
                holder.homeLogoImgL.setImageResource(R.drawable.id_21_vancouver_canucks);
                break;
            case "COL":
                holder.homeLogoImgL.setImageResource(R.drawable.id_22_colorado__avalanche);
                break;
            case "CGY":
                holder.homeLogoImgL.setImageResource(R.drawable.id_23_clagary_flames);
                break;
            case "EDM":
                holder.homeLogoImgL.setImageResource(R.drawable.id_24_edmontn_oilers);
                break;
            case "MIN":
                holder.homeLogoImgL.setImageResource(R.drawable.id_25_minnesota_wild);
                break;
            case "SJS":
                holder.homeLogoImgL.setImageResource(R.drawable.id_26_sanjose_sharks);
                break;
            case "DAL":
                holder.homeLogoImgL.setImageResource(R.drawable.id_27_dallas_stars);
                break;
            case "LAK":
                holder.homeLogoImgL.setImageResource(R.drawable.id_28_la_kings);
                break;
            case "ANA":
                holder.homeLogoImgL.setImageResource(R.drawable.id_29_anaheim_ducks);
                break;
            case "ARI":
                holder.homeLogoImgL.setImageResource(R.drawable.id_30_arizona_coyotes);
                break;
            case "VGK":
                holder.homeLogoImgL.setImageResource(R.drawable.id_142_vegas_goldenknights);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return scheduleItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView homeCityText;
        private TextView awayCityText;
        private TextView homeTeamText;
        private TextView awayTeamText;
        private TextView playedStatusText;
        private TextView homeScoreText;
        private TextView awayScoreText;
        private TextView dateTimeText;
        private ImageView homeLogoImgL;
        private ImageView awayLogoImgL;
        private ImageView favButtonL;//MAybe?

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homeCityText= itemView.findViewById(R.id.home_city);
            awayCityText= itemView.findViewById(R.id.away_city);
            homeTeamText = itemView.findViewById(R.id.home_team);
            awayTeamText = itemView.findViewById(R.id.away_team);
            playedStatusText = itemView.findViewById(R.id.status);
            homeScoreText = itemView.findViewById(R.id.home_score);
            awayScoreText = itemView.findViewById(R.id.away_score);
            dateTimeText = itemView.findViewById(R.id.date);
            homeLogoImgL = itemView.findViewById(R.id.home_logo_img);
            awayLogoImgL = itemView.findViewById(R.id.away_logo_img);
            favButtonL = itemView.findViewById(R.id.fav_button);//MAybe?
        }
    }
}
