package com.algonquincollege.final_project;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.algonquincollege.final_project.DatabaseHelper.S_UPGRADE;
import static com.algonquincollege.final_project.DatabaseHelper.TABLE_NAME;
import static com.algonquincollege.final_project.DatabaseHelper.VERSION_NUM;
import static com.algonquincollege.final_project.DatabaseHelper.author;
import static com.algonquincollege.final_project.DatabaseHelper.category;
import static com.algonquincollege.final_project.DatabaseHelper.description;
import static com.algonquincollege.final_project.DatabaseHelper.guid;
import static com.algonquincollege.final_project.DatabaseHelper.link;
import static com.algonquincollege.final_project.DatabaseHelper.pubDate;
import static com.algonquincollege.final_project.DatabaseHelper.title;


public class Spencer_MainActivity extends AppCompatActivity {

    /**
     * toolbar menu items
     *
     * @param menu Menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.s_menu, menu);
        return true;
    }

    /**
     * multiple menu items for switching
     *
     * @param item Menuitem
     * @return booolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        Fragment selected = null;
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.bus_help:
                selected = new Spencer_frag();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selected).commit();
                //intent = new Intent(this, Spencer_frag.class);
                //this.startActivity(intent);
//            case R.id.bus_nutrition_app:
//                intent = new Intent(this, NutritionSearchActivity.class);
//                this.startActivity(intent);
//                return true;
//            case R.id.bus_movie_app:
//                intent = new Intent(this, MovieStartActivity.class);
//                this.startActivity(intent);
//                return true;
//            case R.id.bus_news_app:
//                intent = new Intent(this, Spencer_MainActivity.class);
//                this.startActivity(intent);
//                return true;
//            case R.id.bus_bus_icon:
//                intent = new Intent(this, BusActivity.class);
//                this.startActivity(intent);
//                return true;
//            case R.id.bus_hockey_app:
//                intent = new Intent(this, Mordechai_mainActivity.class);
//                this.startActivity(intent);
//                return true;
//            case R.id.home_page_icon:
//                intent = new Intent(this, StartActivity.class);
//                this.startActivity(intent);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



private static final String ACTIVITY_NAME = "SPENCER'S MAIN.";
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

private ProgressBar s_progress;

private DatabaseHelper dhHelper;

protected SQLiteDatabase db;

private ArrayList<String> list;

private ContentValues content;

private DbAdapter DBAdapter;

private String urlString = "https://www.cbc.ca/cmlink/rss-world";

private TextView message;

private ListView listView;

private Toolbar s_toolbar;

private String tag = "";
private String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spencer_activity_main);

       // s_toolbar = (Toolbar)findViewById(R.id.tool);
       // setSupportActionBar(s_toolbar);

        s_search = (Button)findViewById(R.id.s_search);
        s_open =  (Button)findViewById(R.id.s_open);
        s_button2 = (Button)findViewById(R.id.s_button2);

        s_progress = (ProgressBar)findViewById(R.id.s_progress);
        listView = (ListView)findViewById(R.id.spencer_list);

        DBAdapter = new DbAdapter(this);
        dhHelper = new DatabaseHelper(this);
        db = dhHelper.getWritableDatabase();


        content = new ContentValues();

        CBCQuery query = new CBCQuery();
        query.execute(urlString);

        s_progress = (ProgressBar) findViewById(R.id.s_progress);


        /**
         *  onClickListener for searching button, currently just makes toast.
         */
        s_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "This will search for keywords.";
                int duration = Toast.LENGTH_SHORT;
                s_progress.setVisibility(View.VISIBLE);
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
                CharSequence text = "Opening Saved Articles.";
                int duration = Snackbar.LENGTH_SHORT;
                Snackbar snackbar = Snackbar.make(s_button2, text, duration);
                snackbar.show();

                Intent i = new Intent(Spencer_MainActivity.this, Spencer_Favourites.class);
                startActivity(i);

            }
        });


        /**
         * <p> onClickListener for refreshing articles.</p>
         */
        // https://developer.android.com/guide/topics/ui/dialogs#java
        s_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Spencer_MainActivity.this);
                builder.setMessage("Refresh Articles?")
                        .setTitle("Refresh")
                        .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dhHelper.onUpgrade(db, VERSION_NUM, VERSION_NUM+1);
                                CBCQuery query = new CBCQuery();
                                query.execute(urlString);
                            }
                        })

                        .show();
            }
        });





    }
    private class CBCQuery extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            Log.i(ACTIVITY_NAME, "In doInbackground");
            try {
                URL url = new URL(strings[0]);
                Log.d("doInBackground", "Connect??");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();
                Log.i(ACTIVITY_NAME, "Connected DoInBackground");
                InputStream stream = conn.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser parser = factory.newPullParser();
                parser.setInput(stream, null);


                Log.i(ACTIVITY_NAME, "Parser started.");
                if (S_UPGRADE != false) {
                    while (parser.next() != XmlPullParser.END_DOCUMENT) {
                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                            continue;
                        }
                        if (parser.getName().equals("item")) {
                            parser.nextTag();
                            // if (parser.getName().equals("title")) {
                            content.put(title, parser.nextText());
                            DBAdapter.notifyDataSetChanged();
                            publishProgress(15);

                            parser.nextTag();
                            //} else if (parser.getName().equals("link")) {
                            content.put(link, parser.nextText());
                            publishProgress(30);

                            parser.nextTag();
                            //} else if (parser.getName().equals("guid")) {
                            content.put(guid, parser.nextText());
                            publishProgress(45);

                            parser.nextTag();
                            //} else if (parser.getName().equals("pubdate")) {
                            content.put(pubDate, parser.nextText());
                            publishProgress(60);

                            parser.nextTag();
                            // } else if (parser.getName().equals("author")) {
                            content.put(author, parser.nextText());
                            publishProgress(75);

                            parser.nextTag();
                            // } else if (parser.getName().equals("category")) {
                            content.put(category, parser.nextText());
                            publishProgress(90);

                            parser.nextTag();
                            String str = parser.nextText();
                            // String image = str.substring(str.indexOf("='") + 2, str.indexOf("'") );
                            String result = str.substring(str.indexOf("<p>") + 3, str.indexOf("</p>"));
                            // } else if (parser.getName().equals("description")) {
                            content.put(description, result);
                            publishProgress(100);
                            db.insert(TABLE_NAME, null, content);


                        } else {
                            parser.next();
                        }
                        // parser.next();
                    }
                } else {
                    publishProgress(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Integer... value) {
            s_progress.setVisibility(View.VISIBLE);
            s_progress.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("TAG", "Post Execute, before cursor.");
            Cursor cursor = dhHelper.getData();
            list = new ArrayList<>();
            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                list.add(cursor.getString(1));
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(
                        cursor.getColumnIndex(title)));
                cursor.moveToNext();
            }
            DbAdapter adapter = new DbAdapter(getApplicationContext());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                        Intent intent = new Intent(Spencer_MainActivity.this, Spencer_Fragment.class);
                        intent.putExtra("TITLE", title);
                        intent.putExtra("LINK", link);
                        intent.putExtra("GUID", guid);
                        intent.putExtra("PUB", pub);
                        intent.putExtra("AUTHOR", author);
                        intent.putExtra("CATEGORY", cate);
                        intent.putExtra("DESCRIPTION", desc);
                        startActivity(intent);

                        //Spencer_Fragment fragment = new Spencer_Fragment();
                        //FragmentTransaction ft = getFragmentManager().beginTransaction();
                        //ft.replace(R.id.S_fragment, fragment);
                        //ft.commit();


                }
            });

            Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
            for (int i = 0; i < cursor.getColumnCount(); i++){
                Log.i(ACTIVITY_NAME, "Column name: " +cursor.getColumnName(i));
            }
            Log.i(ACTIVITY_NAME, "In onPostExecute.");
        }
    }


    private class DbAdapter extends ArrayAdapter<String> {
        public DbAdapter(Context ctx){
            super(ctx, 0);
        }

        @Override
        public int getCount(){
            return list.size();
        }

        @Override
        public String getItem(int position){
            return (list.get(position));
        }

        @Override
        public View getView(int position, View contentView, ViewGroup parent){
            LayoutInflater inflater = Spencer_MainActivity.this.getLayoutInflater();
            View result = null;
            result = inflater.inflate(R.layout.spencer_article, null);
            message = (TextView)result.findViewById(R.id.articleTitle);
            message.setText(getItem(position));
            Log.i(ACTIVITY_NAME, "set up for ListView (GetView)");
            return result;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }
}
