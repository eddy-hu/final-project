package com.algonquincollege.final_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.algonquincollege.final_project.DatabaseHelper.VERSION_NUM;
import static com.algonquincollege.final_project.DatabaseHelper.title;

public class Spencer_Favourites extends Activity {
    private ArrayList<String>s_Arraylist;

    private DatabaseHelper dhHelper;

    private SQLiteDatabase db;

    private static final String ACTIVITY_NAME = "SPENCER'S FAVOURITES";

    private Button s_delete;

    private ListView s_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spencer__favourites);

        s_delete = (Button)findViewById(R.id.s_delete);
        s_list = (ListView)findViewById(R.id.s_list);

        dhHelper = new DatabaseHelper(getApplicationContext());
        db = dhHelper.getReadableDatabase();
        Cursor cursor = dhHelper.getData("SAVE");
        s_Arraylist = new ArrayList<>();
        cursor.moveToFirst();
        while(cursor.moveToNext()) {
            s_Arraylist.add(cursor.getString(1));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(
                    cursor.getColumnIndex(title)));
            cursor.moveToNext();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, s_Arraylist);
        s_list.setAdapter(adapter);

        s_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Spencer_Favourites.this);
                builder.setMessage("Delete Article?")
                        .setTitle("Delete")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.execSQL("DELETE FROM SAVE WHERE ID = " + (position+1));
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return false;
            }
        });
        s_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                dhHelper = new DatabaseHelper(getApplicationContext());
                db = dhHelper.getReadableDatabase();
                Cursor cursor = dhHelper.getData();
                String title = "";
                String link = "";
                String guid = "";
                String pub = "";
                String author = "";
                String cate = "";
                String desc = "";

                try {
                    if (cursor.moveToNext()) {
                        cursor.moveToPosition(position);
                        title = cursor.getString(1);
                        link = cursor.getString(2);
                        guid = cursor.getString(3);
                        pub = cursor.getString(4);
                        author = cursor.getString(5);
                        cate = cursor.getString(6);
                        desc = cursor.getString(7);

                        Log.i("TAG", title);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Spencer_Favourites.this, Spencer_Fragment.class);
                intent.putExtra("TITLE", title);
                intent.putExtra("LINK", link);
                intent.putExtra("GUID", guid);
                intent.putExtra("PUB", pub);
                intent.putExtra("AUTHOR", author);
                intent.putExtra("CATEGORY", cate);
                intent.putExtra("DESCRIPTION", desc);
                startActivity(intent);




            }
        });

        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Column name: " +cursor.getColumnName(i));
        }
        Log.i(ACTIVITY_NAME, "After onClick");
    }




}
