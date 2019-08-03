package com.movie.moviesite.entity;

import java.io.Serializable;

/**
 * 近期电影热度表
 */
public class RecentMovieHeatRankedList implements Serializable{

    private int movieId;//电影Id
    private int recentHeat;//电影近期热度
    private Movie movie;//电影

    public RecentMovieHeatRankedList(){}

    public RecentMovieHeatRankedList(int movieId,int recentHeat){
        initRecentMovieHeatRankedList(movieId,recentHeat);
    }

    public void initRecentMovieHeatRankedList(int movieId,int recentHeat){
        this.movieId = movieId;
        this.recentHeat = recentHeat;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }


    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getRecentHeat() {
        return recentHeat;
    }

    public void setRecentHeat(int recentHeat) {
        this.recentHeat = recentHeat;
    }

    @Override
    public String toString() {
        return this.getMovie().toString()+":"+this.recentHeat;
    }
}
