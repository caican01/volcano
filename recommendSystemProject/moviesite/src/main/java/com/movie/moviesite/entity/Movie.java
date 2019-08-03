package com.movie.moviesite.entity;

import java.io.Serializable;

/**
 * 电影信息实体
 */
public class Movie implements Serializable{

    private int movieId;//电影Id
    private String movieName;//电影名称
    private String movieDate;//电影发行日期
    private String moviePicture;//电影海报链接
    private String typeList;//电影类型列表
    private int historyHeat;//电影历史热度
    private double averageRating;//电影平均评分
    private String download = "";//下载链接
    private String mc = "";//资源格式
    private String playId;//播放id
    private long timestamp;//主要是为了存储电影评分/评论时间，数据库表中并没有也不需要这个字段

    public Movie(){}

    public Movie(int movieId,String movieName,String movieDate,String moviePicture,String typeList,int historyHeat,double averageRating,String download,String mc,String playId){
        initMovie(movieId,movieName,movieDate,moviePicture,typeList,historyHeat,averageRating,download,mc,playId);
    }

    /**
     * 初始化电影信息
     * @param movieId
     * @param movieName
     * @param movieDate
     * @param moviePicture
     * @param typeList
     * @param historyHeat
     * @param averageRating
     */
    public void initMovie(int movieId,String movieName,String movieDate,String moviePicture,String typeList,int historyHeat,double averageRating,String download,String mc,String playId){
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieDate = movieDate;
        this.moviePicture = moviePicture;
        this.typeList = typeList;
        this.historyHeat = historyHeat;
        this.averageRating = averageRating;
        this.download = download;
        this.mc = mc;
        this.playId = playId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    public String getMoviePicture() {
        return moviePicture;
    }

    public void setMoviePicture(String moviePicture) {
        this.moviePicture = moviePicture;
    }

    public String getTypeList() {
        return typeList;
    }

    public void setTypeList(String typeList) {
        this.typeList = typeList;
    }

    public int getHistoryHeat() {
        return historyHeat;
    }

    public void setHistoryHeat(int historyHeat) {
        this.historyHeat = historyHeat;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    @Override
    public String toString() {
        return this.movieId+":"+this.movieName+":"+this.movieDate+":"
                +this.moviePicture+":"+this.getTypeList()+":"+this.historyHeat+":"+this.averageRating;
    }
}