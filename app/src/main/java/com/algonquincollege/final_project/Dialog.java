/**
 * File name: Dialog.java
 * Author: Feng Cheng, ID#:040719618
 * Course: CST2335 - Mobile Graphical Interface Prog.
 * Final project
 * Date: 2018-11-12
 * Professor: Eric
 * Purpose: To show the customized dialog box
 */

package com.algonquincollege.final_project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * @author Feng Cheng
 */
public class Dialog extends AppCompatDialogFragment {
    private TextView dialogTextV;
    private TextView textViewF;
    private TextView textViewC;


    /**
     * to create the activity
     *
     * @param savedInstanceState Bundle
     * @return the dialog box
     */
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.nutrition_activity_dialog_box, null);

        builder.setView(view).setTitle("").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogTextV = view.findViewById(R.id.delConf);
        return builder.create();
    }
}
