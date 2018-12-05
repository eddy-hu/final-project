package com.algonquincollege.final_project;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class NHL_FavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private NHL_RecyclerViewAdapter favadapter;
    ArrayList<NHL_team> team;
    NHL_TeamFragment favteam ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.nhl_favourite_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favteam = new NHL_TeamFragment();
        recyclerView = view.findViewById(R.id.team_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //favadapter = new NHL_RecyclerViewAdapter(getActivity(),);
        recyclerView.setAdapter(favadapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

    }
}

