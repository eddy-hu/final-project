package com.algonquincollege.final_project;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class BusActivity extends Activity {

    protected static final String ACTIVITY_NAME = "BusActivity";
    private ArrayList<String> list;
    private BusAdapter busAdapter;
    private BusListDBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        dbHelper = new BusListDBHelper(this);

        list = new ArrayList<>();
        busAdapter = new BusAdapter(this);
        final ListView listView = (ListView) findViewById(R.id.busListView);
        listView.setAdapter(busAdapter);
        final EditText editText = (EditText) findViewById(R.id.busEditText);
        final Button searchButton = (Button) findViewById(R.id.busSearchButton);
        editText.setHint("Enter the bus number");
        final Button deleteButton = (Button) findViewById(R.id.busDeleteButton);
        //from http://wiki.jikexueyuan.com/project/twenty-four-Scriptures/progress-bar.html
        final ProgressBar mProgress = (ProgressBar) findViewById(R.id.progress_bar);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<5;i++){
                    mProgress.setProgress(i);
                }
                //Snackbar.make(v, "The First SnackBar Button was clicked.", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();

                String text = editText.getText().toString();
                list.add(text);
                busAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView(
                dbHelper.insertEntry(editText.getText().toString());
                editText.setText("");

            }
        });
        busAdapter = new BusAdapter(this);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                editText.setText("");

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.openDatabase();
        query();
        Log.i(ACTIVITY_NAME, "In onStart()");

    }


    @Override
    protected void onPause() {
        Log.i(ACTIVITY_NAME, "In onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(ACTIVITY_NAME, "In onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        dbHelper.closeDatabase();
        super.onDestroy();
    }

    private class BusAdapter extends ArrayAdapter<String> {

        public BusAdapter(Context ctx) {
            super(ctx, 0);
        }

        public String getItem(int position) {
            return list.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public int getCount() {
            return list.size();
        }

        public View getView(int position, View oldView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();

            View result = inflater.inflate(R.layout.bus_history_list_layout, null);

            TextView busSearchList = (TextView) result.findViewById(R.id.bus_serach_list);

            busSearchList.setText(getItem(position)); // get the string at position
            return result;

        }
    }
    public void query() {
        Cursor cursor = dbHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(BusListDBHelper.KEY_MESSAGE)));

                list.add(cursor.getString(cursor.getColumnIndex(BusListDBHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());

        }

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "The " + i + " row is " + cursor.getColumnName(i));
        }
        }
        public void delete(){
            list = new ArrayList<>();
            dbHelper.delete();
        }
    }




