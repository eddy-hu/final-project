package com.algonquincollege.final_project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movies_login_activity);
        button = findViewById(R.id.buttonlog);
        button.setOnClickListener(e-> {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            //Chain together various setter methods to set the dialog characteristics
            builder.setMessage("Do you want to log in?") //Add a dialog message to strings.xml
                    .setTitle("Log in")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response", "Here is my response");
                            setResult(Activity.RESULT_OK, resultIntent);
                            Intent intent = new Intent(MainActivity.this, Movies.class);
                            startActivity(intent);
                            finish(); // User clicked OK button
                        }

                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }

                    })
                    .show();
        });
    }
}
