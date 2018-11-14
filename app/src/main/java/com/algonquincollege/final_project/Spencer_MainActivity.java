package com.algonquincollege.final_project;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Spencer_MainActivity extends Activity {

    /**
        <p> button used for searching the database for certain articles </p>
     */
private Button s_search;
    /**
        <p> button used for opening saved articles </p>
     */
private Button s_open;
    /**
        <p> button used for deleting saved articles from saved database </p>
     */
private Button s_button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spencer_activity_main);

        s_search = (Button)findViewById(R.id.s_search);
        s_open =  (Button)findViewById(R.id.s_open);
        s_button2 = (Button)findViewById(R.id.s_button2);

        /**
         *  onClickListener for searching button, currently just makes toast.
         */
        s_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "This will search for keywords.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        });
        /**
         *  <p> onClickListener for opening saved methods, currently only creates snackBar.</p>
         */
        s_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "This will open up saved articles.";
                int duration = Snackbar.LENGTH_SHORT;
                Snackbar snackbar = Snackbar.make(s_button2, text, duration);
                snackbar.show();
            }
        });


        /**
         * <p> onClickListener for deleting saved articles, not implemented yet, currently just opens an alertDialog.</p>
         */
        // https://developer.android.com/guide/topics/ui/dialogs#java
        s_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Spencer_MainActivity.this);
                builder.setMessage("This will eventually delete items from the saved list")
                        .setTitle("Deleter")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });



    }
}
