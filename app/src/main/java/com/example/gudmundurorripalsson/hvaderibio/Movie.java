package com.example.gudmundurorripalsson.hvaderibio;

import java.util.List;

/**
 * Created by gudmundurorripalsson on 3/14/18.
 *
 * Geymir upplýsingar um einstaka mynd
 */

public class Movie {

    private int id;
    private String title;
    private String imdb;
    private String poster;
    private String cert;
    private String descr;
    private List<String> directors;

    public Movie(int id, String title, String imdb, String poster, String cert, String descr, List<String> directors){
        this.id = id;
        this.title = title;
        this.imdb = imdb;
        this.poster = poster;
        this.cert = cert;
        this.descr = descr.replace("\n", " ");
        this.directors = directors;
    }

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

    public String getCert() {
        return cert;
    }

    public String getDescr() {
        return descr;
    }

    public List<String> getDirectors() {
        return directors;
    }

}
