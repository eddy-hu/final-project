/**
 * File name: NutritionJsonAdapter.java
 * NutritionAuthor: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To set up the view of searched result
 */
package com.algonquincollege.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @author Feng Cheng
 */
public class NutritionJsonAdapter extends BaseAdapter {

    private List<NutritionNewBean> data;
    LayoutInflater inflater;
    public double calData;
    public double fatData;

    /**
     * constructor for instantiation
     *
     * @param context Context
     * @param data    List<NutritionNewBean>
     */
    public NutritionJsonAdapter(Context context, List<NutritionNewBean> data) {
        super();
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    /**
     * to get the size of the List
     *
     * @return size of the List
     */
    @Override
    public int getCount() {
        return data.size();
    }

    /**
     * to get the item of a specific position
     *
     * @param position int
     * @return the specific item
     */
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    /**
     * to get the ID of a specific item
     *
     * @param position int
     * @return the position
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * to set up the view
     *
     * @param position    int
     * @param convertView View
     * @param parent      ViewGroup
     * @return the convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.nutrition_activity_listview_item, null);
            viewHolder.fat = (TextView) convertView
                    .findViewById(R.id.fat);
            viewHolder.calories = (TextView) convertView
                    .findViewById(R.id.author);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        calData = data.get(position).calories;
        fatData = data.get(position).fat;
        String cal = Double.toString(calData);
        String fatS = Double.toString(fatData);
        viewHolder.fat.setText("Fat: " + fatS);
        viewHolder.calories.setText("Calories: " + cal);


        return convertView;
    }

    /**
     * inner class to hold the view of detailed searched result
     */
    class ViewHolder {
        public TextView fat, calories;

    }
}

