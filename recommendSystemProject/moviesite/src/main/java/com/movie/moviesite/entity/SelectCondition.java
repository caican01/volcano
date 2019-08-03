package com.movie.moviesite.entity;

/**
 * 用于封装查询条件
 */
public class SelectCondition {

    private String category;
    private String orderColumn;
    private int limitCount;

    public SelectCondition(){}

    public SelectCondition(String category,String orderColumn,int limitCount){
        initSelectCondition(category,orderColumn,limitCount);
    }

    public void initSelectCondition(String category,String orderColumn,int limitCount){
        this.category = category;
        this.orderColumn = orderColumn;
        this.limitCount = limitCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    @Override
    public String toString() {
        return this.category+":"+this.orderColumn+":"+this.limitCount;
    }
}
