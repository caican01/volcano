package com.good.moviemanager.entity;

import java.io.Serializable;

public class RecentMovieHeatRankedList implements Serializable{

    private int movieId;//电影ID
    private int recentHeat;//近期热度
    private Movie movie;//电影记录

    public RecentMovieHeatRankedList(){}

    public RecentMovieHeatRankedList(int movieId,int recentHeat,Movie movie){
        this.movieId = movieId;
        this.recentHeat = recentHeat;
        this.movie = movie;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRecentHeat() {
        return recentHeat;
    }

    public void setRecentHeat(int recentHeat) {
        this.recentHeat = recentHeat;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return movie.toString()+"_"+recentHeat;
    }
}
