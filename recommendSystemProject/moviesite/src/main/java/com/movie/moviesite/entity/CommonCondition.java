package com.movie.moviesite.entity;

//通用条件封装类
public class CommonCondition {

    private int categoryId;
    private int limitCount;

    public CommonCondition(){}

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }
}
