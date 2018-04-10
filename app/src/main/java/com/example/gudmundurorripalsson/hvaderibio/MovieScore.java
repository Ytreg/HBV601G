package com.example.gudmundurorripalsson.hvaderibio;

/**
 * Created by notandi on 10.4.2018.
 */

public class MovieScore {
    int id;
    double score;

    public MovieScore(int id, double score){
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public double getScore() {
        return score;
    }
}
