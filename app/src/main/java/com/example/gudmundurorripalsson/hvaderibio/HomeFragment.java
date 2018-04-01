package com.example.gudmundurorripalsson.hvaderibio;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Helgi on 24/03/2018.
 */

public class HomeFragment extends Fragment {

    private TextView mTextMessage;
    private GridView gridView;
    private View mView;
    private JSONArray moviesArray;

    public static final String TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            String json = getArguments().getString("json");
            try {
                JSONArray jsonArray = new JSONArray(json);
                updateMovieList(jsonArray);
            } catch (JSONException e) {
                Log.e(TAG, "Exception caught: ", e);
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "Exception caught: ", e);
        }
        return mView;
    }

    public void updateMovieList(JSONArray json) {

        moviesArray = json;

        final Movie[] movies = new Movie[json.length()];
        for (int i = 0; i < json.length(); i++) {
            try {
                JSONObject j = json.getJSONObject(i);
                Movie movie = new Movie(
                        j.getInt("id"),
                        j.getString("title"),
                        j.getJSONObject("ratings").getString("imdb"),
                        j.getString("poster"),
                        j.getString("certificateImg"),
                        j.getString("plot")
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

                MovieFragment movieFragment = new MovieFragment();

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) parent.getItemAtPosition(position);
                // Show Alert


                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("movie", moviesArray.getJSONObject(position).toString());
                    movieFragment.setArguments(bundle);
                } catch (JSONException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }

                int FADE_DEFAULT_TIME = 300;
                int MOVE_DEFAULT_TIME = 300;

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();


                // Virkar bara fyrir API sem eru 21+
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    // 1. Exit for Previous Fragment
                    Fade exitFade = new Fade();
                    exitFade.setDuration(FADE_DEFAULT_TIME + MOVE_DEFAULT_TIME);
                    movieFragment.setExitTransition(exitFade);

                    // 2. Shared Elements Transition
                    TransitionSet enterTransitionSet = new TransitionSet();
                    enterTransitionSet.addTransition(TransitionInflater.from(getActivity()).inflateTransition(android.R.transition.move));
                    enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
                    enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
                    movieFragment.setSharedElementEnterTransition(enterTransitionSet);

                    // 3. Enter Transition for New Fragment
                    Fade enterFade = new Fade();
                    enterFade.setStartDelay(FADE_DEFAULT_TIME + MOVE_DEFAULT_TIME);
                    enterFade.setDuration(FADE_DEFAULT_TIME);
                    movieFragment.setEnterTransition(enterFade);

                }

                View sharedElement1 = view.findViewById(R.id.movieImage);
                View sharedElement2 = view.findViewById(R.id.title);
                View sharedElement3 = view.findViewById(R.id.rating);
                View sharedElement4 = view.findViewById(R.id.star);
                // Do not change pls
                sharedElement1.setTransitionName(position + "_image");
                fragmentTransaction.addSharedElement(sharedElement1, "movieImage");
                fragmentTransaction.addSharedElement(sharedElement2, "movieTitle");
                fragmentTransaction.addSharedElement(sharedElement3, "movieRating");
                fragmentTransaction.addSharedElement(sharedElement4, "star");
                fragmentTransaction.replace(R.id.main_frame, movieFragment);
                fragmentTransaction.addToBackStack(getClass().getName());
                fragmentTransaction.commit();
            }

        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}
