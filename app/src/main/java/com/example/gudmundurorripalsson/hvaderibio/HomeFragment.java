package com.example.gudmundurorripalsson.hvaderibio;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Build;
import android.support.annotation.Nullable;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit.*;

/**
 * Created by Helgi on 24/03/2018.
 */

public class HomeFragment extends Fragment {

    private TextView mTextMessage;
    private GridView gridView;
    private View mView;
    private JSONArray moviesArray;
    private Score score;
    private Movie[] movies;
    private ArrayList<MovieScore> bioRating = new ArrayList<>();
    private ArrayList<Integer> ratedMovies = new ArrayList<>();
    private FirebaseUser user;
    private DecimalFormat df = new DecimalFormat(".#");
    private String username;
    JSONArray jsonArray;
    Double[] bioRatings;
    AnimationDrawable animation;

    public static final String TAG = HomeFragment.class.getSimpleName();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        score = new Score();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
            username = user.getDisplayName();
        try {
            String json = getArguments().getString("json");
            try {
                jsonArray = new JSONArray(json);
                updateMovieList(jsonArray);
            } catch (JSONException e) {
                Log.e(TAG, "Exception caught: ", e);
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "Exception caught: ", e);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference().child("Movies");
        mRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        if(dataSnapshot.getValue() != null) {
                            ArrayList<MovieScore> ratings = score.collectRatings((Map<String, Object>) dataSnapshot.getValue());
                            for (int i = 0; i < ratings.size(); i++) {
                                bioRating.add(ratings.get(i));
                            }
                            if(gridView != null) {
                                bioRatings = new Double[gridView.getChildCount()];
                                for (int i = 0; i < gridView.getChildCount(); i++) {
                                    View cell = gridView.getChildAt(i);
                                    int id = movies[i].getId();
                                    for (int j = 0; j < bioRating.size(); j++) {
                                        if (id == (bioRating.get(j).getId())) {
                                            bioRatings[i] = bioRating.get(j).getScore();
                                            TextView bioRatingView = (TextView) cell.findViewById(R.id.bioRating);
                                            bioRatingView.setText(df.format(bioRating.get(j).getScore()));
                                            break;
                                        }
                                    }
                                    System.out.println("yupp " + bioRatings.toString());
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
        if(user != null) {
            DatabaseReference mUserRef = database.getReference().child("Users").child(username);

                mUserRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Get map of users in datasnapshot
                                if(dataSnapshot.getValue() != null)
                                    ratedMovies = score.collectMovieIds((Map<String, Object>) dataSnapshot.getValue());
                                if(gridView != null) {
                                    for (int i = 0; i < gridView.getChildCount(); i++) {
                                        View cell = gridView.getChildAt(i);
                                        int id = movies[i].getId();
                                        for (int j = 0; j < ratedMovies.size(); j++) {
                                            if (id == ratedMovies.get(j)) {
                                                ImageView checkmark = (ImageView) cell.findViewById(R.id.checkmark);
                                                checkmark.setVisibility(View.VISIBLE);
                                                ((Animatable) checkmark.getDrawable()).start();
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }
                        });

        }
        ImageView loading = (ImageView) mView.findViewById(R.id.loading);
        animation = (AnimationDrawable) loading.getDrawable();
        animation.start();
        return mView;

    }



    public void updateMovieList(JSONArray json) {
        animation.stop();

        ImageView loading = (ImageView) mView.findViewById(R.id.loading);
        loading.setVisibility(View.GONE);

        moviesArray = json;

        movies = new Movie[json.length()];
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
        Integer[] ids = new Integer[movies.length];
        String[] posters = new String[movies.length];
        String[] titles = new String[movies.length];
        String[] ratings = new String[movies.length];
        for (int i = 0; i < movies.length; i++){

            ids[i] = movies[i].getId();
            posters[i] = movies[i].getPoster();
            titles[i] = movies[i].getTitle();
            ratings[i] = df.format(Double.parseDouble(movies[i].getImdb()));
            System.out.println(titles[i] + " " + ids[i]);
        }

        gridView = (GridView) mView.findViewById(R.id.simpleGridView);
        System.out.println("context " + getContext());
        GridAdapter gridAdapter = new GridAdapter(getContext(), ids, posters, titles, ratings, bioRatings);
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
                    //movieFragment.setExitTransition(exitFade);

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
                sharedElement2.setTransitionName(position + "_title");
                sharedElement3.setTransitionName(position + "_rating");
                sharedElement4.setTransitionName(position + "_star");
                fragmentTransaction.addSharedElement(sharedElement1, "movieImage");
                fragmentTransaction.addSharedElement(sharedElement2, "movieTitle");
                fragmentTransaction.addSharedElement(sharedElement3, "movieRating");
                fragmentTransaction.addSharedElement(sharedElement4, "star");
                fragmentTransaction.replace(R.id.main_frame, movieFragment);
                fragmentTransaction.addToBackStack(TAG);
                fragmentTransaction.commit();
            }

        });
    }

}
