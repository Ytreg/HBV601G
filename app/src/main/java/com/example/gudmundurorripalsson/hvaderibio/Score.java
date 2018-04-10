package com.example.gudmundurorripalsson.hvaderibio;

import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ómar on 4.4.2018.
 */

public class Score {


    public Score(){

    }


    public ArrayList<Integer> collectMovieIds(Map<String, Object> value){
        ArrayList<Integer> keys = new ArrayList<>();

        for (Map.Entry<String, Object> entry : value.entrySet()){
            keys.add(Integer.parseInt(entry.getKey()));
        }

        return keys;
    }

    public ArrayList<MovieScore> collectRatings(Map<String, Object> value) {

        ArrayList<Map<String, Object>> list = new ArrayList<>();
        ArrayList<MovieScore> ratings = new ArrayList<>();
        ArrayList<Integer> keys = new ArrayList<>();

        for (Map.Entry<String, Object> entry : value.entrySet()){


            Map rating = (Map) entry.getValue();
            keys.add(Integer.parseInt(entry.getKey()));
            list.add(rating);

        }
        for(int i = 0; i < list.size(); i++){
            double rating = collectRating(list.get(i));
            MovieScore mScore = new MovieScore(keys.get(i), rating);
            ratings.add(mScore);
        }

        return ratings;

    }

    public double collectRating(Map<String, Object> value) {
        ArrayList<Long> scores = new ArrayList<>();


        for (Map.Entry<String, Object> entry : value.entrySet()){


            Map rating = (Map) entry.getValue();
            scores.add((Long) rating.get("score"));
        }

        double score = getScore(scores);
        return score;

    }
    public double getScore(ArrayList<Long> scores){
        double score = 0.0;
        for(int i = 0; i < scores.size(); i++){
            score += scores.get(i);
        }
        score = score/scores.size();
        return score;
    }

}
