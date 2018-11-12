package com.algonquincollege.final_project;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog loading = null;
    private JsonAdapter adapter;
    private EditText searchTxt;
    private Button btnAdd;
    private Button btnFavourite;
    private Button btnSearch;
    private ListView listView;
    protected static final String ACTIVITY_NAME = "MainActivity";
    private Context ctx = null;
    private String app_id = "40cb1f76", app_key = "9dd571cf4d9e83a7796c460130be79dd";
    private List<NewBean> newBeanList = new ArrayList<>();
    ;
    public String food;
    private String jsonUrl = " https://api.edamam.com/api/food-database/parser?ingr=" + food + "&app_id=" + app_id + "&app_key=" + app_key;
    private FoodDatabaseHelper foodDatabaseHelper = new FoodDatabaseHelper(this);
    private NewBean newBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.nutritionCtnt);
        searchTxt = (EditText) findViewById(R.id.searchTxt);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnAdd = (Button) findViewById(R.id.btn_add);
        btnFavourite = (Button) findViewById(R.id.btn_favourite);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = searchTxt.getText().toString();
                if (food != null && !food.isEmpty()) {
                    new MyAsyncTask().execute(jsonUrl);
                    //searchTxt.setText("");
                } else {
                    toastMessage("Please enter something.");
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food = searchTxt.getText().toString();
                double fat = adapter.fatData;
                double cal = adapter.calData;
                AddData(food, cal, fat);

            }
        });

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouriteList.class);
                startActivity(intent);
            }
        });

    }

    public void AddData(String food, double cal, double fat) {
        boolean insertData = foodDatabaseHelper.addData(food, cal, fat);
        if (insertData) {
            toastMessage("Data successfully inserted");
        } else {
            toastMessage("Something went wrong");
        }
    }

    class MyAsyncTask extends AsyncTask<String, Void, List<NewBean>> {
        private String jsonUrl = " https://api.edamam.com/api/food-database/parser?ingr=" + food + "&app_id=" + app_id + "&app_key=" + app_key;
        JsonData jsonData = new JsonData();

        @Override
        protected List<NewBean> doInBackground(String... params) {
            return newBeanList = jsonData.getJsonData(jsonUrl);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(MainActivity.this);
            loading.setMessage("Please wait");
            loading.setCancelable(false);
            loading.show();
            ;
        }


        @Override
        protected void onPostExecute(List<NewBean> result) {
            super.onPostExecute(result);
            adapter = new JsonAdapter(MainActivity.this, newBeanList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (loading.isShowing()) {
                loading.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.author:
                Intent intent1 = new Intent(MainActivity.this, Author.class);
                this.startActivity(intent1);
                return true;
            case R.id.version:
                Intent intent2 = new Intent(MainActivity.this, Version.class);
                this.startActivity(intent2);
                return true;
            case R.id.instruction:
                Intent intent3 = new Intent(MainActivity.this, Instruction.class);
                this.startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
