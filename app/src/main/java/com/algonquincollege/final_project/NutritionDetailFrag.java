package com.algonquincollege.final_project;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This class is the fragment for nutrition details.
 */
public class NutritionDetailFrag extends AppCompatActivity {
    /**
     * to create the nutrition activity.
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrition_activity_detail_frag);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
        }

        Bundle bundle = new Bundle();
        bundle.putString("calories", getIntent().getStringExtra("calories"));
        bundle.putString("fat", getIntent().getStringExtra("fat"));

        NutritionFragment fragment = new NutritionFragment();
        fragment.setArguments(bundle);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.detail_frameLayout, fragment);
        ft.commit();
    }
}
