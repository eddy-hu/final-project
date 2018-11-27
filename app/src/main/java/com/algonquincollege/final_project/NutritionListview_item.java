/**
 * File name: NutritionListview_item.java
 * NutritionAuthor: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To create the view of detailed searched item
 */
package com.algonquincollege.final_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * listview for searching result
 */
public class NutritionListview_item extends AppCompatActivity {
    /**
     * to create the activity for searching result
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_listview_item);
    }
}
