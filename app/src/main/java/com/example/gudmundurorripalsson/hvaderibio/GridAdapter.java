package com.example.gudmundurorripalsson.hvaderibio;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Helgi on 23/03/2018.
 */

public class GridAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;
    private Integer[] ids;
    private String[] titles;
    private String[] images;
    private String[] imdbRatings;
    private ArrayList<MovieScore> bioRatings;
    private ArrayList<Integer> ratedMovies;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, Integer[] ids, String[] images, String[] titles, String[] imdbRatings, ArrayList<MovieScore> bioRatings, ArrayList<Integer> ratedMovies) {
        this.ids = ids;
        this.context = context;
        this.titles = titles;
        this.images = images;
        this.imdbRatings = imdbRatings;
        this.bioRatings = bioRatings;
        this.ratedMovies = ratedMovies;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_item, parent, false);
        }
        view = new View(context);
        view = inflater.inflate(R.layout.single_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.movieImage);
        imageView.setTransitionName(String.valueOf(position) + "_image");
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setTransitionName(String.valueOf(position) + "_title");
        TextView ratingView = (TextView) view.findViewById(R.id.rating);
        ratingView.setTransitionName(String.valueOf(position) + "_rating");
        TextView bioRatingView = (TextView) view.findViewById(R.id.bioRating);
        ImageView starView = (ImageView) view.findViewById(R.id.star);
        starView.setTransitionName(String.valueOf(position) + "_star");
        Picasso.with(context).load(images[position]).into(imageView);
        titleView.setText(titles[position]);
        ratingView.setText(imdbRatings[position]);
        DecimalFormat df = new DecimalFormat("#.#");
        for(int i = 0; i < bioRatings.size(); i++){
            if(ids[position] == bioRatings.get(i).getId()){
                bioRatingView.setText(df.format(bioRatings.get(i).getScore()));
                break;
            }
        }
        for(int i = 0; i < ratedMovies.size(); i++){
            if(ids[position].equals(ratedMovies.get(i))){
                //Checkmark animation
                ImageView checkmark = (ImageView) view.findViewById(R.id.checkmark);
                checkmark.setVisibility(View.VISIBLE);
                ((Animatable) checkmark.getDrawable()).start();
                break;
            }
        }

        return view;
    }
}
