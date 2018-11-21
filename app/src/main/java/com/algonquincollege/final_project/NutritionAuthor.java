/**
 * File name: NutritionAuthor.java
 * NutritionAuthor: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To show the author
 */
package com.algonquincollege.final_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity is the memu item that shows the author's name.
 */
public class NutritionAuthor extends AppCompatActivity {
    /**
     * to create the activity
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_author);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
