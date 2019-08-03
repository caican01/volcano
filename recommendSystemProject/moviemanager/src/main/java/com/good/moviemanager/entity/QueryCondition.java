package com.good.moviemanager.entity;

import java.io.Serializable;

/**
 * 用于封装查询条件，可用于多个情景下的封装，
 * 虽然定义的属性名好像是只限于Movie类使用，
 * 其实其他任意适用的情景下都可以使用，
 * 不必过于纠结属性名的含义
 */
public class QueryCondition implements Serializable{

    private String movieName;//电影名称
    private String movieDate;//电影发行日期
    private String type;//电影类型列表
    private String orderColumn; //排序字段

    public QueryCondition(){ }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }
}
