package com.algonquincollege.final_project;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";
    private ArrayList<String> list;
    private ChatAdapter messageAdapter;
    private ChatDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        dbHelper = new ChatDatabaseHelper(this);

        list = new ArrayList<>();
        messageAdapter = new ChatAdapter(this);

        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(messageAdapter);

        final EditText editText = (EditText) findViewById(R.id.editTextChat);
        final Button sendBtn = (Button) findViewById(R.id.sendBtn);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                list.add(text);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView(
                dbHelper.insertEntry(editText.getText().toString());
                editText.setText("");
            }
        });
        messageAdapter = new ChatAdapter(this);
        Log.i(ACTIVITY_NAME, "In onCreate()");


    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
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

            View result = null;
            if (position % 2 == 0) {

                result = inflater.inflate(R.layout.chat_row_incoming, null);

            } else {

                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            final TextView message = (TextView) result.findViewById(R.id.messageText);

            message.setText(getItem(position)); // get the string at position
            return result;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");

    }

    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.openDatabase();
        query();
        Log.i(ACTIVITY_NAME, "In onStart()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeDatabase();
        Log.i(ACTIVITY_NAME, "In onDestroy()");

    }
    public void query() {
        Cursor cursor = dbHelper.getRecords();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                list.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
        }

        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "The " + i + " row is " + cursor.getColumnName(i));
        }
    }
}
