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

import java.util.ArrayList;


public class NHL_ScheduleViewAdapter extends RecyclerView.Adapter<NHL_ScheduleViewAdapter.ViewHolder> {

    private static final String TAG = "NHL_ScheduleViewAdapter";

    private  FragmentActivity activity;
    private ArrayList<NHL_ScheduleItem> scheduleItems=new ArrayList<NHL_ScheduleItem>() ;
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
        holder.homeTeamText.setText(current.homeTeam);
        holder.awayTeamText.setText(current.awayTeam);
        holder.playedStatusText.setText(current.playedStatus);
        holder.homeScoreText.setText(current.homeScore);
        holder.awayScoreText.setText(current.awayScore);
        holder.dateTimeText.setText(current.dateTime);
        holder.homeLogoImg.setImageResource(current.LogoId);
        holder.awayLogoImg.setImageResource(current.LogoId);
    }


    @Override
    public int getItemCount() {
        return scheduleItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView homeTeamText;
        private TextView awayTeamText;
        private TextView playedStatusText;
        private TextView homeScoreText;
        private TextView awayScoreText;
        private TextView dateTimeText;
        private ImageView homeLogoImg;
        private ImageView awayLogoImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            homeTeamText = itemView.findViewById(R.id.home_city);
            awayTeamText = itemView.findViewById(R.id.away_city);
            playedStatusText = itemView.findViewById(R.id.status);
            homeScoreText = itemView.findViewById(R.id.home_score);
            awayScoreText = itemView.findViewById(R.id.away_score);
            dateTimeText = itemView.findViewById(R.id.date);
            homeLogoImg = itemView.findViewById(R.id.home_logo_img);
            awayLogoImg = itemView.findViewById(R.id.away_logo_img);
        }
    }
}
