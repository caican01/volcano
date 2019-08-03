package com.movie.moviesite.entity;

import java.io.Serializable;

public class MovieRating implements Serializable{

    private int userId;//用户Id
    private int movieId;//电影Id
    private double rating;//用户对电影的评分
    private long timestamp;//评分时的时间戳


    public MovieRating(){}

    public MovieRating(int userId,int movieId,double rating,long timestamp){
        initMovieRating(userId,movieId,rating,timestamp);
    }

    public void initMovieRating(int userId,int movieId,double rating,long timestamp){
        this.userId = userId;
        this.movieId = movieId;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        return this.userId+":"+this.movieId+":"+this.rating+":"+this.timestamp;
    }
}
