package com.example.gudmundurorripalsson.hvaderibio;

import android.system.ErrnoException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Helgi on 11/04/2018.
 */

public class SortMovies {

    public static final int SORT_EGILS = 0;
    public static final int SORT_AKUR = 1;
    public static final int SORT_KEFLA = 2;
    public static final int SORT_KRINGLU = 3;
    public static final int SORT_ALFA = 4;
    public static final int SORT_BORGAR = 5;
    public static final int SORT_SMARA = 6;
    public static final int SORT_HASKOL = 7;
    public static final int SORT_LAUGAR = 8;

    public static Movie[] sortTheater(Movie[] movies, int theater) {
        List<Integer> validMovies = new ArrayList<>();
        switch (theater) {
            case SORT_EGILS:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Sambíóin Egilshöll")) {
                        validMovies.add(i);
                    }
                }
                break;
            case SORT_AKUR:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Sambíóin, Akureyri")) {
                        validMovies.add(i);
                    }
                }
                break;
            case SORT_KEFLA:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Sambíóin, Keflavík")) {
                        validMovies.add(i);
                    }
                }
                break;
            case SORT_KRINGLU:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Kringlubíó")) {
                        validMovies.add(i);
                    }
                }
                break;
            case SORT_ALFA:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Álfabakki")) {
                        validMovies.add(i);
                    }
                }
                break;
            case SORT_BORGAR:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Borgarbíó")) {
                        validMovies.add(i);
                    }
                }
                break;
            case SORT_SMARA:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Smárabíó")) {
                        validMovies.add(i);
                    }
                }
                break;
            case SORT_HASKOL:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Háskólabíó")) {
                        validMovies.add(i);
                    }
                }
                break;
            case SORT_LAUGAR:
                for (int i = 0; i < movies.length; i++) {
                    if (movies[i].getTheaters().contains("Laugarásbíó")) {
                        validMovies.add(i);
                    }
                }
                break;
            default:
                return movies;
        }
        Movie[] newMovies = new Movie[validMovies.size()];
        for (int i = 0; i < newMovies.length; i++) {
            newMovies[i] = movies[validMovies.get(i)];
        }
        return newMovies;
    }

}
