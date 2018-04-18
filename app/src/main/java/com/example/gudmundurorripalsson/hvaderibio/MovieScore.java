package com.example.gudmundurorripalsson.hvaderibio;

/**
 * Created by Ómar on 10.4.2018.
 */

/**
 * Model Object klasi sem heldur utan um einkunn(score) sem notandi hefur gefið mynd með
 * auðkennisnúmerið "id"
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
