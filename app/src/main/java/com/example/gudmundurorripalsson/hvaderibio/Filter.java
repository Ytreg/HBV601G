package com.example.gudmundurorripalsson.hvaderibio;

import java.util.ArrayList;

/**
 * Created by Ómar on 16.4.2018.
 */

public class Filter {


    private final int imdbRating;
    private final int ageLimit;
    private final ArrayList<Boolean> cinemas;

    public Filter(int imdbRating, int ageLimit, ArrayList<Boolean> cinemas){

        this.imdbRating = imdbRating;
        this.ageLimit = ageLimit;
        this.cinemas = cinemas;
    }
}
