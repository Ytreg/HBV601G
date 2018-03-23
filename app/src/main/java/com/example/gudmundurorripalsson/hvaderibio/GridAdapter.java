package com.example.gudmundurorripalsson.hvaderibio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Helgi on 23/03/2018.
 */

public class GridAdapter extends BaseAdapter {

    Context context;
    private String[] titles;
    private  int[] images;
    private  String[] ratings;
    View view;
    LayoutInflater layoutInflater;

    public GridAdapter(Context context, int[] images, String[] titles, String[] ratings) {
        this.context = context;
        this.titles = titles;
        this.images = images;
        this.ratings = ratings;
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
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = new View(context);
            view = layoutInflater.inflate(R.layout.single_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            TextView titleView = (TextView) view.findViewById(R.id.title);
            TextView ratingView = (TextView) view.findViewById(R.id.rating);
            imageView.setImageResource(images[position]);
            titleView.setText(titles[position]);
            ratingView.setText(ratings[position]);
        }
        return view;
    }
}