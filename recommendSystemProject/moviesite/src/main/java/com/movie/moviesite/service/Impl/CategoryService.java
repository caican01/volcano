package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.Category;
import com.movie.moviesite.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService implements com.movie.moviesite.service.CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> selectAllCategory() {
        return categoryMapper.selectAllCategory();
    }

    @Override
    public Category selectByPrimaryKey(Integer categoryId) {
        return categoryMapper.selectByPrimaryKey(categoryId);
    }
}
