package com.example.gudmundurorripalsson.hvaderibio;

import android.system.ErrnoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Helgi on 11/04/2018.
 */

/**
 * Hjálparklasi sem síar myndalistann "movies" eftir ákveðnum Filter
 */

public class SortMovies {
    public static String[] cinemaNames = new String[10];

    public SortMovies(){
        cinemaNames[0] = "Álfabakki";
        cinemaNames[1] = "Borgarbíó";
        cinemaNames[2] = "Bíó Paradís";
        cinemaNames[3] = "Sambíóin Egilshöll";
        cinemaNames[4] = "Háskólabíó";
        cinemaNames[5] = "Kringlubíó";
        cinemaNames[6] = "Laugarásbíó";
        cinemaNames[7] = "Sambíóin, Akureyri";
        cinemaNames[8] = "Sambíóin, Keflavík";
        cinemaNames[9] = "Smárabíó";

    }

    public static Movie[] sortTheater(Movie[] movies, Filter filter) {
        Boolean[] cinemas = filter.getCinemas();
        List<Integer> validMovies = new ArrayList<>();

        for (int i = 0; i < movies.length; i++) {
            double imdb;
            if(movies[i].getImdb().equals("null"))
                imdb = 10;
            else {
                imdb = Double.parseDouble(movies[i].getImdb());
            }
            if(imdb >= filter.getImdbRating()) {
                for (int j = 0; j < cinemaNames.length; j++) {
                    if(cinemas[j]) {
                        if (movies[i].getTheaters().contains(cinemaNames[j])) {
                            validMovies.add(i);
                            break;
                        }
                    }
                }
            }
        }


        Movie[] newMovies = new Movie[validMovies.size()];
        for (int i = 0; i < newMovies.length; i++) {
            newMovies[i] = movies[validMovies.get(i)];
        }
        return newMovies;
    }

}
