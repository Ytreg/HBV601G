package com.example.gudmundurorripalsson.hvaderibio;

/**
 * Created by gudmundurorripalsson on 3/14/18.
 */

public class Movie {

    private int id;
    private String title;
    private String imdb;
    private String poster;
    private String cert;
    private String descr;

    public Movie(int id, String title, String imdb, String poster, String cert, String descr){
        this.id = id;
        this.title = title;
        this.imdb = imdb;
        this.poster = poster;
        this.cert = cert;
        this.descr = descr;
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

    public String getCert() {
        return cert;
    }

    public String getDescr() {
        return descr;
    }

}
