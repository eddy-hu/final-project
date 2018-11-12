package com.algonquincollege.final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NutritionDetail extends AppCompatActivity {
    private TextView textViewF;
    private TextView textViewC;
    private Button delBtn;
    private FoodDatabaseHelper foodDatabaseHelper = new FoodDatabaseHelper(this);
    private Button yesBtn;
    private Button noBtn;
    ;
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_detail);

        delBtn = (Button) findViewById(R.id.delFood);
        textViewF = (TextView) findViewById(R.id.fatDetail);
        textViewC = (TextView) findViewById(R.id.calDetail);

        final String foodId = getIntent().getStringExtra("id");
        textViewF.setText("Fat: " + getIntent().getStringExtra("fat"));
        textViewC.setText("Calories: " + getIntent().getStringExtra("calories"));

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v,"Food deleted !", Snackbar.LENGTH_LONG).setAction(
//                        "Action", null
//                ).show();
//                sqLiteDatabase = foodDatabaseHelper.getWritableDatabase();
//                foodDatabaseHelper.delFood(foodId, sqLiteDatabase);
//                textViewC.setText("");
//                textViewF.setText("");

                openDialog();
            }
        });


    }
    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"example dialog" );
    }

}
