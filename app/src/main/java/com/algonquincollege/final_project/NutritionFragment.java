package com.algonquincollege.final_project;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
    private TextView caloriesTextView;
    private TextView fatTextView;
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
    private Button tagDelBtn;
    private TextView foodNameTextView;


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
        caloriesTextView = view.findViewById(R.id.calories_frag);
        fatTextView = view.findViewById(R.id.fat_frag);
        delBtn = view.findViewById(R.id.detail_delete_button);
        nutritionTagBtn = view.findViewById(R.id.nutritionTag);
        tagEditTxt = view.findViewById(R.id.tagEditTxt);
        foodTagTextView = view.findViewById(R.id.food_tag);
        tagDelBtn = view.findViewById(R.id.tagDelBtn);
        foodNameTextView = view.findViewById(R.id.foodName);
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

        caloriesTextView.setText(getArguments().getString("calories"));
        fatTextView.setText(getArguments().getString("fat"));
        foodNameTextView.setText(primaryFoodKey);

        //to delete the food item
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setMessage(getString(R.string.delConfMsg) + primaryFoodKey + " ?").setCancelable(false).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
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
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = mBuilder.create();
                alert.setTitle(getString(R.string.alertTitle));
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
                        toastMessage(getString(R.string.prompt_to_enter));
                    }
                } else {
                    toastMessage(getString(R.string.tag_already));
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
                        toastMessage(getString(R.string.no_tag_found));
                    }
                }
                AlertDialog calStatDialog = new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.tag_stat_title) + tag)
                        .setMessage(getString(R.string.max_cal) + Double.toString(calMax) + " g" + "\n"
                                + getString(R.string.min_cal) + Double.toString(calMin) + " g" + "\n" +
                                getString(R.string.total_cal) + calTotal + " g" + "\n" +
                                getString(R.string.ave_cal) + calAve + " g")
                        .setCancelable(true)
                        .create();
                calStatDialog.show();

            }
        });

        /**
         * to delete the food tag
         */
        tagDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodTagString = foodTagTextView.getText().toString();
                if (!(foodTagString.isEmpty())) {
                    DeleteTag(primaryFoodKey);
                } else {

                    toastMessage(getString(R.string.no_tag_found));
                }
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(NutritionFragment.this).attach(NutritionFragment.this).commit();
            }
        });

    }

    /**
     * to delete the tag
     *
     * @param id primary key
     */
    public void DeleteTag(String id) {
        // foodDatabaseHelper = new NutritionDatabaseHelper(getActivity());
        boolean deleteTag = foodDatabaseHelper.deleteTag(id);
        if (deleteTag) {
            toastMessage(getString(R.string.tag_delete));
        } else {
            toastMessage(getString(R.string.error));
        }
    }

    // to update the database if there's any food tag added.
    public void UpdateData(String food, String id) {
        foodDatabaseHelper = new NutritionDatabaseHelper(getActivity());
        boolean updateData = foodDatabaseHelper.updateName(food, id);
        if (updateData) {
            toastMessage(getString(R.string.tag_update));
        } else {
            toastMessage(getString(R.string.error));
        }
    }

    //toast message
    private void toastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
