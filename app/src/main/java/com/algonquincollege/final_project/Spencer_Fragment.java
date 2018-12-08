package com.algonquincollege.final_project;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.algonquincollege.final_project.DatabaseHelper.TABLE_NAME;

public class Spencer_Fragment extends AppCompatActivity{
    ContentValues content;
    Button save;
    private DatabaseHelper dhHelper;
    String title;
    String link;
    String author;
    String desc;
    String guid;
    String pub;
    String category;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spencer__fragment);

        title = getIntent().getStringExtra("TITLE");
        link = getIntent().getStringExtra("LINK");
        author = getIntent().getStringExtra("AUTHOR");
        desc = getIntent().getStringExtra("DESCRIPTION");
        guid = getIntent().getStringExtra("GUID");
        pub = getIntent().getStringExtra("PUB");
        category = getIntent().getStringExtra("CATEGORY");


        Button save = (Button) findViewById(R.id.s_save);
        TextView S_title = (TextView) findViewById(R.id.S_textTitle);
        TextView S_link = (TextView) findViewById(R.id.S_textLink);
        TextView S_author = (TextView) findViewById(R.id.S_textAuthor);
        TextView S_desc = (TextView) findViewById(R.id.S_textBox);

        S_title.setText(title);
        S_link.setText(link);
        S_author.setText(author);
        S_desc.setText(desc);

        S_link.setTextColor(getResources().getColor(R.color.blue));

        S_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = new ContentValues();
                dhHelper = new DatabaseHelper(getApplicationContext());
                db = dhHelper.getWritableDatabase();

                content.put("TITLE", title);
                content.put("LINK", link);
                content.put("AUTHOR", author);
                content.put("GUID", guid);
                content.put("DESCRIPTION", desc);
                content.put("PUBDATE", pub);
                content.put("CATEGORY", category);
                db.insert("Save", null, content);
            }
        });




    }


}
