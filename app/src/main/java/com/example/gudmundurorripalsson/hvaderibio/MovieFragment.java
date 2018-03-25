package com.example.gudmundurorripalsson.hvaderibio;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Helgi on 24/03/2018.
 */

public class MovieFragment extends Fragment {

    private Movie movie;
    private View mView;
    private String descr;
    private String cert;

    public static final String TAG = MovieFragment.class.getSimpleName();

    public MovieFragment() {
        // Required empty public constructor
    }

    private static final String MOVIE_NUMBER = "argMovieNumber";

    /**
     * Create a new DetailsFragment
     * @param kittenNumber The number (between 1 and 6) of the kitten to display
     */
    public static MovieFragment newInstance(int movieNumber) {
        Bundle args = new Bundle();
        args.putInt(MOVIE_NUMBER, movieNumber);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_movie, container, false);
        try {
            String arg = getArguments().getString("movie");
            JSONObject json = new JSONObject(arg);
            movie = new Movie(
                    json.getInt("id"),
                    json.getString("title"),
                    json.getJSONObject("ratings").getString("imdb"),
                    json.getString("poster"),
                    json.getString("certificateImg"),
                    json.getString("plot")
            );
        } catch (JSONException e) {
            Log.e(TAG, "Exception caught: ", e);
        }

        updateView();

        return mView;
    }

    private void updateView() {
        ImageView imageView = (ImageView) mView.findViewById(R.id.movieImage);
        TextView titleView = (TextView) mView.findViewById(R.id.movieTitle);
        TextView descrView = (TextView) mView.findViewById(R.id.movieDescr);
        TextView ratingView = (TextView) mView.findViewById(R.id.movieRating);
        ImageView certView = (ImageView) mView.findViewById(R.id.movieCertificate);

        Picasso.with(getContext()).load(movie.getPoster()).into(imageView);
        titleView.setText(movie.getTitle());
        descrView.setText(movie.getDescr());
        ratingView.setText(movie.getImdb());
        Picasso.with(getContext()).load(movie.getCert()).into(certView);
    }
}
