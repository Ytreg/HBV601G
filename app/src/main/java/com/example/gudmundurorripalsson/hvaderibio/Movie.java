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
        id = this.id;
        title = this.title;
        imdb = this.imdb;
        poster = this.poster;
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
