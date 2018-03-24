package com.example.gudmundurorripalsson.hvaderibio;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Helgi on 23/03/2018.
 */

public class GridAdapter extends BaseAdapter {

    Context context;
    private LayoutInflater inflater;
    private String[] titles;
    private String[] images;
    private String[] ratings;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, String[] images, String[] titles, String[] ratings) {
        this.context = context;
        this.titles = titles;
        this.images = images;
        this.ratings = ratings;
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
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        TextView ratingView = (TextView) view.findViewById(R.id.rating);
        Picasso.with(context).load(images[position]).into(imageView);
        titleView.setText(titles[position]);
        ratingView.setText(ratings[position]);
        return view;
    }
}