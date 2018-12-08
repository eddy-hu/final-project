/**
 * The fragment for list view
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
 * BusRouteFragment class to handles list view
 */
public class BusRouteFragment extends Fragment {
    /**
     * ACTIVITY_NAME the name of class
     */
    protected static final String ACTIVITY_NAME = "BusRouteFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bus_fragment, container, false);
    }


}