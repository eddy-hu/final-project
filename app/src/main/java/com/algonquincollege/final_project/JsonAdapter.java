package com.algonquincollege.final_project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class JsonAdapter extends BaseAdapter {

    List<NewBean> data ;
    LayoutInflater inflater;
    public double calData;
    public double fatData;

    public JsonAdapter(Context context, List<NewBean> data) {
        super();
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_listview_item, null);
            viewHolder.fat = (TextView) convertView
                    .findViewById(R.id.fat);
            viewHolder.calories = (TextView) convertView
                    .findViewById(R.id.calories);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        calData = data.get(position).calories;
        fatData = data.get(position).fat;
        String cal = Double.toString(calData);
        String fatS = Double.toString(fatData);

//        calData = Double.toString(data.get(position).calories);
//        fatData = Double.toString(data.get(position).fat);
//        viewHolder.fat.setText("Fat: " + Double.toString(data.get(position).fat));
//        viewHolder.calories.setText("Calories: " + Double.toString(data.get(position).calories));
        viewHolder.fat.setText("Fat: " + fatS);
        viewHolder.calories.setText("Calories: " + cal);



        return convertView;
    }

    class ViewHolder {
        public TextView fat, calories;

    }
}

