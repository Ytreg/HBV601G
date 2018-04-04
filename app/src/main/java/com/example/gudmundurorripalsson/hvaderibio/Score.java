package com.example.gudmundurorripalsson.hvaderibio;

/**
 * Created by notandi on 4.4.2018.
 */

public class Score {
    public int reviews = 1, totalScore = 0;
    double score = 0;

    public Score(){

    }

    public Score(int reviews, int totalScore, double score){
        this.reviews = reviews;
        this.totalScore = totalScore;
        this.score = score;
    }
}
