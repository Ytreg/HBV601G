package com.example.gudmundurorripalsson.hvaderibio;

import java.util.ArrayList;

/**
 * Created by Ómar on 16.4.2018.
 */

public class Filter {




    private  int imdbRating;
    private  int ageLimit;
    private  Boolean[] cinemas;

    public Filter(){
        imdbRating = 1;
        ageLimit = 0;
        cinemas = new Boolean[10];
        for(int i = 0; i < cinemas.length; i++)
            cinemas[i] = true;
    }

    public Filter(int imdbRating, int ageLimit, Boolean[] cinemas){

        this.imdbRating = imdbRating;
        this.ageLimit = ageLimit;
        this.cinemas = cinemas;
    }

    public int getImdbRating() {
        return imdbRating;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public Boolean[] getCinemas() {
        return cinemas;
    }

    public void setImdbRating(int imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public void setCinemas(Boolean[] cinemas) {
        this.cinemas = cinemas;
    }
}

