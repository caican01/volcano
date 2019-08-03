package com.movie.moviesite.entity;

import java.io.Serializable;

/**
 * 电影评论，与User实体关联，
 * 获取到评论该电影的用户昵称、评论内容、评论时间、赞同数
 *
 * 影评的显示按赞同数降序排序
 */
public class MovieReview implements Serializable{

    private int userId;//用户Id，数据库表中需要这一字段，但在实体中因要做关联，所以不需要，直接用User代替就行。
    private int movieId;//电影Id
    private String content;//评论内容
    private long timestamp;//评论的时间戳
    private int count;//觉得该影评有用的其他用户数量,建表时设置该字段值默认为0。

    /**
     * 经严密考虑后，不能直接在这里设立这个字段，按照传统的方式的话，这个字段应为每个点赞过该条评论的用户id的拼接结果；
     * 如果某用户点赞过该条评论，则该条评论的status字段中包含该用户的Id，否则不包含。
     */
    private String status;


    private User user;//用户

    public MovieReview(){ }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.getUser().getUserName()+":"+this.userId+":"+this.movieId+":"+this.content+":"+this.count+":"+this.timestamp;
    }
}
