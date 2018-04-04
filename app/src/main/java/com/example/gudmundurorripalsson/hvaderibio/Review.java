package com.example.gudmundurorripalsson.hvaderibio;

/**
 * Created by Ómar on 4.4.2018.
 */

public class Review {


    private int score;
    private String comment;

    public Review(){

    }

    public Review(int score, String comment){
        this.score = score;
        this.comment = comment;
    }


    public int getScore() {
        return score;
    }

    public String getComment() {
        return comment;
    }
}
