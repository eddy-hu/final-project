package com.algonquincollege.final_project;

import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NutritionFragment extends Fragment {

    private View view;
    private TextView calories;
    private TextView fat;
    private Button delBtn;
    private NutritionDatabaseHelper foodDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public NutritionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_nutrition_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calories = view.findViewById(R.id.calories_frag);
        fat = view.findViewById(R.id.fat_frag);
        delBtn = view.findViewById(R.id.detail_delete_button);

        calories.setText(getArguments().getString("calories"));
        fat.setText(getArguments().getString("fat"));

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments().getBoolean("isTablet")) {
                    foodDatabaseHelper = new NutritionDatabaseHelper((getActivity()));
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
        });
    }
}
