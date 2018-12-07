package com.algonquincollege.final_project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class NHL_main extends AppCompatActivity{

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhl_activity_main);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListenr);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListenr
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected = null;

            switch (item.getItemId()) {
                case R.id.navigation_teams:
                    selected = new NHL_TeamFragment();
                    break;
                case R.id.navigation_schedule:
                    selected = new NHL_ScheduleFragment();
                    break;
                case R.id.navigation_favorite:
                    selected = new NHL_FavoriteFragment();
                    break;
                case R.id.navigation_help:
                    selected = new NHL_HelpFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selected).commit();
            return true;
        }
    };

}
