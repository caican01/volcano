package com.movie.moviesite.entity;

import java.io.Serializable;

/**
 * 给每个用户推荐的电影
 */
public class MoviesRecommendForAllUsers implements Serializable{

    private int userId;//用户Id
    private String movieIds;//推荐给该用户的电影的Id列表

    public MoviesRecommendForAllUsers(){}

    public MoviesRecommendForAllUsers(int userId,String movieIds){
        initMoviesRecommendForAllUsers(userId,movieIds);
    }

    public void initMoviesRecommendForAllUsers(int userId,String movieIds){
        this.userId = userId;
        this.movieIds = movieIds;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(String movieIds) {
        this.movieIds = movieIds;
    }

    @Override
    public String toString() {
        return this.userId+":"+this.movieIds;
    }
}
