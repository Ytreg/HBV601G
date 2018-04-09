package com.example.gudmundurorripalsson.hvaderibio;

import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by notandi on 4.4.2018.
 */

public class Score {


    public Score(){

    }


    public ArrayList<Double> collectRatings(Map<String, Object> value) {

        ArrayList<Map<String, Object>> list = new ArrayList<>();
        ArrayList<Double> ratings = new ArrayList<>();

        for (Map.Entry<String, Object> entry : value.entrySet()){


            Map rating = (Map) entry.getValue();
            list.add(rating);

        }
        for(int i = 0; i < list.size(); i++){
            ratings.add(collectRating(list.get(i)));
        }

        return ratings;

    }

    public double collectRating(Map<String, Object> value) {
        ArrayList<Long> scores = new ArrayList<>();


        for (Map.Entry<String, Object> entry : value.entrySet()){


            Map rating = (Map) entry.getValue();
            System.out.println(rating);
            scores.add((Long) rating.get("score"));
        }

        double score = getScore(scores);
        return score;

    }
    public double getScore(ArrayList<Long> scores){
        System.out.println("size " + scores.size());
        double score = 0.0;
        for(int i = 0; i < scores.size(); i++){
            score += scores.get(i);
        }
        score = score/scores.size();
        return score;
    }

}
