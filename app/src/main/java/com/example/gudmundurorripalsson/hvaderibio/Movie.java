package com.example.gudmundurorripalsson.hvaderibio;

/**
 * Created by gudmundurorripalsson on 3/14/18.
 */

public class Movie {

    public int id;
    public String title;
    public String imdb;
    public String poster;

    public Movie(int id, String title, String imdb, String poster){
        this.id = id;
        this.title = title;
        this.imdb = imdb;
        this.poster = poster;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getImdb(){
        return imdb;
    }

    public String getPoster(){
        return poster;
    }
}
