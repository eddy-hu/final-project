/**
 * File name: NutritionDetail.java
 * NutritionAuthor: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To show the calories and fat
 */
package com.algonquincollege.final_project;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Feng Cheng
 */
public class NutritionDetail extends AppCompatActivity {
    private TextView textViewF;
    private TextView textViewC;
    private Button delBtn;
    private NutritionDatabaseHelper foodDatabaseHelper = new NutritionDatabaseHelper(this);
    private Button yesBtn;
    private Button noBtn;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_nutrition_detail);

        delBtn = (Button) findViewById(R.id.delFood);
        textViewF = (TextView) findViewById(R.id.fatDetail);
        textViewC = (TextView) findViewById(R.id.calDetail);

        final String foodId = getIntent().getStringExtra("id");
        textViewF.setText("Fat: " + getIntent().getStringExtra("fat")); // to get the fat data
        textViewC.setText("Calories: " + getIntent().getStringExtra("calories")); // to get the calories data

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Food deleted !", Snackbar.LENGTH_LONG).setAction(
                        "Action", null
                ).show();
//                sqLiteDatabase = foodDatabaseHelper.getWritableDatabase();
//                foodDatabaseHelper.delFood(foodId, sqLiteDatabase);
//                textViewC.setText("");
//                textViewF.setText("");

                openDialog();
            }
        });


    }

    /**
     * to open the customized dialog
     */
    public void openDialog() {
        NutritionDialog dialog = new NutritionDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

}
