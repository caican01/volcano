package com.movie.moviesite.service;

import com.movie.moviesite.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有的电影类别
     * 并按类别Id升序排序
     * @return
     */
    List<Category> selectAllCategory();

    /**
     * 根据主键查询电影类别
     * @param categoryId
     * @return
     */
    Category selectByPrimaryKey(Integer categoryId);
}
