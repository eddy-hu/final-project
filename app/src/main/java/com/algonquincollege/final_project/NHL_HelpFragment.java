package com.algonquincollege.final_project;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class NHL_HelpFragment extends Fragment {

    ImageView infoBtn;
    Snackbar snackbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nhl_help_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        infoBtn = view.findViewById(R.id.about_pack);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Author info", Snackbar.LENGTH_LONG).show();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                //Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Author:Mordechai Edery\nVersion: 1.0") //Add a dialog message to strings.xml
                        .setTitle("App Info")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                            }

                        })
                        .show();
            }
        });

    }



    /**
     * add the options menu to this activity
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Toast.makeText(getActivity(), "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.bus_help:
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Help dialog notification");
                alertDialog.setMessage("Welcome to OCTranspo \nAuthor: Yongpan Hu");
                alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return true;
            case R.id.bus_nutrition_app:

                intent = new Intent(getActivity(), NutritionSearchActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_movie_app:
                intent = new Intent(getActivity(), MoviesActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_news_app:
                intent = new Intent(getActivity(), Spencer_MainActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_hockey_app:
                intent = new Intent(getActivity(), NHL_main.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.bus_bus_icon:
                intent = new Intent(getActivity(), BusActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            case R.id.home_page_icon:
                intent = new Intent(getActivity(), StartActivity.class);
                this.startActivity(intent);
                // do your code
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
