package com.example.gudmundurorripalsson.hvaderibio;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Helgi on 24/03/2018.
 */

public class HomeFragment extends Fragment {

    private TextView mTextMessage;
    private GridView gridView;
    private View mView;

    public static final String TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        String json = getArguments().getString("json");
        try {
            JSONArray jsonArray = new JSONArray(json);
            updateMovieList(jsonArray);
        } catch (JSONException e) {
            Log.e(TAG, "Exception caught: ", e);
        }
        return mView;
    }

    private void updateMovieList(JSONArray json) {

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

        String[] posters = new String[movies.length];
        String[] titles = new String[movies.length];
        String[] ratings = new String[movies.length];
        for (int i = 0; i < movies.length; i++){
            posters[i] = movies[i].getPoster();
            titles[i] = movies[i].getTitle();
            ratings[i] = movies[i].getImdb();
        }

        gridView = (GridView) mView.findViewById(R.id.simpleGridView);
        GridAdapter gridAdapter = new GridAdapter(getContext(), posters, titles, ratings);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) parent.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getContext(),
                        "Position : " + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }

        });
    }
}
