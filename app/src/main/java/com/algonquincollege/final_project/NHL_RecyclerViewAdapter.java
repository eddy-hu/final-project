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



public class NHL_RecyclerViewAdapter extends RecyclerView.Adapter<NHL_RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "NHL_RecyclerViewAdapter";

    private  FragmentActivity activity;
    private ArrayList<NHL_team> team=new ArrayList<NHL_team>() ;
    private Context context;
    LayoutInflater inflater;


    public NHL_RecyclerViewAdapter(ArrayList<NHL_team> team, Context context, LayoutInflater inflater) {
        this.team = team;
        this.context = context;
        this.inflater = inflater;
    }

    public NHL_RecyclerViewAdapter(FragmentActivity activity, ArrayList<NHL_team> data) {
        this.activity = this.activity;
        this.team = data;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nhl_team_fragment_item, parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        NHL_team current = team.get(position);
        holder.teamNametext.setText(current.teamName);
        holder.citytext.setText(current.city);
        holder.logoImg.setImageResource(current.logoId);
    }


    @Override
    public int getItemCount() {
        return team.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView logoImg;
        TextView citytext;
        TextView teamNametext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            logoImg = itemView.findViewById(R.id.logo_img);
            citytext = itemView.findViewById(R.id.city);
            teamNametext = itemView.findViewById(R.id.team_name);
        }
    }
}
