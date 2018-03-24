package com.example.gudmundurorripalsson.hvaderibio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.io.IOException;

import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView listView;
    private BottomNavigationView navigation;
    private GridView gridView;

    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getMovies();

        mTextMessage = findViewById(R.id.message);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        gridView = (GridView) findViewById(R.id.simpleGridView);
        int[] images = {R.drawable.boi, R.drawable.boi, R.drawable.boi};
        String[] ratings = {"boi", "afdafda", "there"};
        String[] titles = {"bbb", "smash", "whoa"};
        GridAdapter gridAdapter = new GridAdapter(this, images, titles, ratings);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) parent.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position : " + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_account:
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                    //overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };




    private void getMovies() {
        String url = "http://bio-serverinn.herokuapp.com/";

        if (isNetworkAvailable()) {
            //toggleRefresh();
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Log.v(TAG, request.toString());
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //toggleRefresh();
                        }
                    });
                    //alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //toggleRefresh();
                        }
                    });
                    try {
                        final JSONArray jsonData = new JSONArray(response.body().string());
                        if (response.isSuccessful()) {
                            //We are not on main thread
                            //Need to call this method and pass a new Runnable thread
                            //to be able to update the view.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateMovieList(jsonData);
                                    //Call the method to update the view.
                                }
                            });
                        } else {
                            //alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            // Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }



    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) isAvailable = true;
        return isAvailable;
    }

    private void updateMovieList(JSONArray json) {
        /*
        gridView = (GridView) findViewById(R.id.simpleGridView);
        int[] images = {R.drawable.boi, R.drawable.boi};
        String[] ratings = {"boi", "iob"};
        String[] titles = {"bbb", "aaa"};
        GridAdapter gridAdapter = new GridAdapter(this, images, titles, ratings);
        gridView.setAdapter(gridAdapter);
        */

        Movie[] movies = new Movie[json.length()];
        for (int i = 0; i < json.length(); i++) {
            try {
                JSONObject j = json.getJSONObject(i);
                Movie movie = new Movie(
                        j.getInt("id"),
                        j.getString("title"),
                        j.getJSONObject("ratings").getString("imdb"),
                        j.getString("poster")
                );
                movies[i] = movie;
            } catch (JSONException e) {
                Log.e(TAG, "Exception caught: ", e);
            }
        }

        String[] titles = new String[movies.length];
        for (int i = 0; i < movies.length; i++) {
            titles[i] = movies[i].getTitle();
            if (movies[i].getImdb() != "null") {
                titles[i] += " " + movies[i].getImdb();
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, titles);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) gridView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position : " + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });
    }
}