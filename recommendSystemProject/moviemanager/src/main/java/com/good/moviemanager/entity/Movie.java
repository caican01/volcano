package com.good.moviemanager.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 电影字段
 */
public class Movie implements Serializable {

    @Excel(name = "ID",orderNum = "1")
    private Integer movieId;//电影Id
    @Excel(name = "片名",orderNum = "2")
    private String movieName;//电影名称
    @Excel(name = "上映日期",orderNum = "3")
    private String movieDate;//电影发行日期
    @Excel(name = "海报链接",orderNum = "4")
    private String moviePicture;//电影海报链接
    @Excel(name = "类型",orderNum = "5")
    private String typeList;//电影类型列表
    @Excel(name = "热度",orderNum = "6")
    private int historyHeat;//电影历史热度
    @Excel(name = "评分",orderNum = "7")
    private double averageRating;//电影平均评分

    public Movie(){}

    public Movie(Integer movieId,String movieName,String movieDate,String moviePicture,String typeList,int historyHeat,double averageRating){
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieDate = movieDate;
        this.moviePicture = moviePicture;
        this.typeList = typeList;
        this.historyHeat = historyHeat;
        this.averageRating = averageRating;
    }

    public Integer getMovieId() {
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

    @Override
    public String toString() {
        return getMovieId()+"_"+getMovieName()+"_"+getMovieDate()+"_"+getMoviePicture()+"_"+getTypeList()+"_"+getHistoryHeat()+"_"+getAverageRating();
    }
}
