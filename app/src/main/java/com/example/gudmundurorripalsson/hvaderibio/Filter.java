package com.example.gudmundurorripalsson.hvaderibio;

import java.util.ArrayList;

/**
 * Created by Ómar on 16.4.2018.
 */

/**
 * Model Object klasi sem geymir síunar stillingar notandans
 */

public class Filter {

    private  int imdbRating;
    private  Boolean[] cinemas;

    public Filter(){
        imdbRating = 1;
        cinemas = new Boolean[10];
        for(int i = 0; i < cinemas.length; i++)
            cinemas[i] = true;
    }

    public Filter(int imdbRating, Boolean[] cinemas){
        this.imdbRating = imdbRating;
        this.cinemas = cinemas;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public Boolean[] getCinemas() {
        return cinemas;
    }

    public void setImdbRating(int imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setCinemas(Boolean[] cinemas) {
        this.cinemas = cinemas;
    }
}

