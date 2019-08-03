package com.good.moviemanager.entity;

import java.io.Serializable;

/**
 * 用户喜欢（收藏）的电影，也作注册时选择喜欢的电影用
 *
 * 按收藏时间降序显示（即最新收藏的显示在最前）
 */
public class EachUserLikedMovie implements Serializable{

    /**
     * userId与movieId是外键，一起作为主键
     */
    private int userId;//用户Id
    private int movieId;//电影Id
    private long timestamp;//时间戳
    private User user;//用户
    private Movie movie;//电影

    public EachUserLikedMovie(){}

    public EachUserLikedMovie(int userId, int movieId, long timestamp){
        initEachUserLikedMovie(userId,movieId,timestamp);
    }

    public void initEachUserLikedMovie(int userId, int movieId, long timestamp){
        this.userId = userId;
        this.movieId = movieId;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return this.user.toString()+":"+this.movie.toString()+":"+this.timestamp;
    }
}
