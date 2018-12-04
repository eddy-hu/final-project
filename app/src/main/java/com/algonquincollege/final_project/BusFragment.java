/**
 * The fragment for list view of bus
 * @Author: Yongpan Hu
 * @Version: 1.1
 * @Since:1.0
 */
package com.algonquincollege.final_project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * BusFragment class to handles listView of bus stops
 */
public class BusFragment extends Fragment {
    /**
     * ACTIVITY_NAME
     */
    protected static final String ACTIVITY_NAME = "BusFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bus_fragment, container, false);
    }


}