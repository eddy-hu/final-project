package com.algonquincollege.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.round;
import static java.util.Collections.max;

/**
 * to create the nutrition detail activity
 */
public class NutritionFragment extends Fragment {

    private View view;
    private TextView calories;
    private TextView fat;
    private Button delBtn;
    private NutritionDatabaseHelper foodDatabaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Button nutritionTagBtn;
    private String foodTag;
    private EditText tagEditTxt;
    private String primaryFoodKey;
    private TextView foodTagTextView;
    private Cursor cursor;
    private String tag;
    private Button statBtn;
    private ArrayList<Double> calStat = new ArrayList<>();
    private double calTotal;
    private double calAve;
    private double calMax;
    private double calMin;
    private static final String TAG = "NutritionFragment";


    public NutritionFragment() {
    }

    /**
     * to create fragment view of nutrition details
     *
     * @param inflater           Inflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.nutrition_activity_fragment, container, false);
        return view;
    }

    /**
     * to create the activity of detail fragment
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calories = view.findViewById(R.id.calories_frag);
        fat = view.findViewById(R.id.fat_frag);
        delBtn = view.findViewById(R.id.detail_delete_button);
        nutritionTagBtn = view.findViewById(R.id.nutritionTag);
        tagEditTxt = view.findViewById(R.id.tagEditTxt);
        foodTagTextView = view.findViewById(R.id.food_tag);
        statBtn = view.findViewById(R.id.statTag);
        primaryFoodKey = NutritionFavouriteList.selectedName;
        foodDatabaseHelper = new NutritionDatabaseHelper(getActivity());
        sqLiteDatabase = foodDatabaseHelper.getReadableDatabase();
        cursor = foodDatabaseHelper.getSpecificFood(primaryFoodKey, sqLiteDatabase);//to get the data from the database
        if (cursor.moveToFirst()) {
            tag = cursor.getString(3);
            Log.d(TAG, " Tag " + tag);
            foodTagTextView.setText(tag);
        }
        calories.setText(getArguments().getString("calories"));
        fat.setText(getArguments().getString("fat"));

        //to delete the food item
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setMessage("Do you really want to delete it?").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getArguments().getBoolean("isTablet")) {
                            sqLiteDatabase = foodDatabaseHelper.getWritableDatabase();
                            foodDatabaseHelper.delFood(getArguments().getString("id"), sqLiteDatabase);
                            ((NutritionFavouriteList) getActivity()).notifyChange();
                            ((NutritionFavouriteList) getActivity()).query();
                            getFragmentManager().beginTransaction().remove(NutritionFragment.this).commit();
                        } else {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("id", getArguments().getString("id"));
                            getActivity().setResult(1, resultIntent);
                            getActivity().finish();

                        }

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = mBuilder.create();
                alert.setTitle("Alert!!");
                alert.show();

            }
        });
        //to add a tag to a food
        nutritionTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodTag = tagEditTxt.getText().toString();
                if (tag == null) {
                    if (foodTag != null && !(foodTag.isEmpty())) {
                        UpdateData(foodTag, primaryFoodKey);
                        foodTagTextView.setText(foodTag);
                        tagEditTxt.setText("");
                    } else {
                        toastMessage("Please enter something!");
                    }
                } else {
                    toastMessage("It has a tag already.");
                }

            }


        });

        //to get the statistics of total, average, min and max on a specific tag
        statBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cursor = foodDatabaseHelper.getSpecificFood(primaryFoodKey, sqLiteDatabase);//to get the data from the database
                if (cursor.moveToFirst()) {
                    tag = cursor.getString(3);
                    if (tag != null) {
                        cursor = foodDatabaseHelper.getTag(tag, sqLiteDatabase);
                        if (cursor.moveToFirst()) {
                            do {
                                calStat.add(cursor.getDouble(1));
                            } while (cursor.moveToNext());
                        }
                        calMax = Collections.max(calStat);
                        calMin = Collections.min(calStat);
                        for (int i = 0; i < calStat.size(); i++) {
                            calTotal += calStat.get(i);
                        }
                        calAve = round(calTotal / calStat.size());

                    } else {
                        toastMessage("No tag found! ");
                    }
                }
                AlertDialog calStatDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Tag: " + tag)
                        .setMessage("Max Calories: " + Double.toString(calMax) + "\n"
                                + "Min Calories: " + Double.toString(calMin) + "\n" +
                                "Total Calories: " + calTotal + "\n" +
                                "Average Calories: " + calAve)
                        .setCancelable(true)
                        .create();
                calStatDialog.show();

            }
        });

    }

    // to update the database if there's any food tag added.
    public void UpdateData(String food, String id) {
        foodDatabaseHelper = new NutritionDatabaseHelper(getActivity());
        boolean updateData = foodDatabaseHelper.updateName(food, id);
        if (updateData) {
            toastMessage("ToastMessage: Data successfully updated");
        } else {
            toastMessage("ToastMessage: Something went wrong");
        }
    }

    //toast message
    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
