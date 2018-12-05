package com.algonquincollege.final_project;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Spencer_Fragment extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spencer__fragment);

        String title = getIntent().getStringExtra("TITLE");
        String link = getIntent().getStringExtra("LINK");
        String author = getIntent().getStringExtra("AUTHOR");
        String desc = getIntent().getStringExtra("DESCRIPTION");




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

    }
}
