package com.algonquincollege.final_project;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;


public class NHL_TeamFragment extends Fragment  {

    private static final String TAG = "NHL_TeamFragment";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private NHL_RecyclerViewAdapter adapter;
    MenuItem searchTeam;
    SearchView searchView;
    String userInput;
    ArrayList<NHL_team> team;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {
        Log.d(TAG, "onCreateView: Started");
        return inflater.inflate(R.layout.nhl_team_fragment_list, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.team_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NHL_RecyclerViewAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("fuck", "fuck");

            }
        });

    }

    public ArrayList<NHL_team> getData(){
        team = new ArrayList<>();

        int [] logos = {R.drawable.id_1_tampa_bay_lightning,R.drawable.id_2_winnipeg_jets,R.drawable.id_3_carolina_hurricanes,
                R.drawable.id_4_florida_panthers,R.drawable.id_5_washington_capitals, R.drawable.id_6_philadelphia_flyers, R.drawable.id_7_newjersey_devils,
                R.drawable.id_8_new_york_islanders,R.drawable.id_9_new_york_rangers,R.drawable.id_10_pittsburgh_penguins,R.drawable.id_11_boston_bruins,R.drawable.id_12_toronto_mapleleafs,
                R.drawable.id_13_ottawa_senators,R.drawable.id_14_montreal_canadiens,R.drawable.id_15_buffalo_sabres,R.drawable.id_16_detroit_redwings,R.drawable.id_17_stlouis_blues,
                R.drawable.id_18_nashville_predators,R.drawable.id_19_columbus_bluejackets,R.drawable.id_20_chicago_blackhawks,R.drawable.id_21_vancouver_canucks,R.drawable.id_22_colorado__avalanche,
                R.drawable.id_23_clagary_flames,R.drawable.id_25_minnesota_wild,R.drawable.id_26_sanjose_sharks,R.drawable.id_27_dallas_stars,R.drawable.id_28_la_kings,
                R.drawable.id_29_anaheim_ducks,R.drawable.id_30_arizona_coyotes,R.drawable.id_142_vegas_goldenknights};

        String []teamNames = {"lightning","Jets","Hurricanes","Panthers","Capitals","Flyers","Devils","Islanders","Rangers",
                "Penguins","Bruins","Maple Leafs","Senators","Canadiens","Sabres","Redwings","Blues","Predators","Blue Jackets",
                "Blackhawks","Canucks","Avalanche","Flames","Wild","Sharks","Stars","Kings","Ducks","Coyotes","Golden Knights"};

        String[]cities = {"Tampa Bay", "Winnipeg", "Carolina", "Florida", "Washington", "Philadelphia", "New Jersey", "New York", "New York",
                "Pittsburgh", "Boston", "Toronto", "Ottawa", "Montreal", "Buffalo", "Detroit", "St.louis", "Nashville", "Columbus", "Chicago",
                "Vancouver", "Colorado", "Clagary", "Minnesota", "San Jose", "Dallas", "Los-Angeles", "Anaheim", "Arizona", "Las-Vegas"};

        for (int i=0;i<cities.length && i<logos.length &&i<teamNames.length;i++ ){
            NHL_team current = new NHL_team();
            current.logoId=logos[i];
            current.city = cities[i];
            current.teamName = teamNames[i];
            team.add(current);
        }
        return team;
    }
}
