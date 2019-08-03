package com.movie.moviesite.entity;

import java.io.Serializable;

/**
 * 电影类别
 */
public class Category implements Serializable{

    private int categoryId;//类别Id
    private String categoryName;//类别名称

    public Category(){}

    public Category(int categoryId,String categoryName){
        initCategory(categoryId,categoryName);
    }

    public void initCategory(int categoryId,String categoryName){
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return this.categoryId+":"+this.categoryName;
    }
}
