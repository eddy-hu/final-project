/**
 * File name: Instruction.java
 * Author: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To show the instructions of how to use this application
 */
package com.algonquincollege.final_project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * @author Feng Cheng
 */
public class Instruction extends AppCompatActivity {
    /**
     * to create the activity
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_instruction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebView view = (WebView) findViewById(R.id.instruction);

        String text;
        text = "<html><body style = \"font-size: 30px\"><p align=\"justify\">";
        text += "Instructions: You can search for the calories and fat content of a food. You can add it to your favourite\n" +
                "    list. On your list, you can retrieve the nutrition information of a selected food. You can also delete an item on your favourite list from the details page. You can\n" +
                "    add a tag to a food and can get statistics on each tag counting the total calories, average calories, max and min calories.";
        text += "</p></body></html>";
        view.loadData(text, "text/html", "utf-8");

    }
}
