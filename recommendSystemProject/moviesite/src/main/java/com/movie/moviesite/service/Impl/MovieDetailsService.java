package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.Category;
import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.mapper.CategoryMapper;
import com.movie.moviesite.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieDetailsService implements com.movie.moviesite.service.MovieDetailsService{

    @Autowired
    private MovieMapper movieMapper;
//    @Autowired
//    private CategoryMapper categoryMapper;

    @Override
    public Movie getMovieDetailsByMovieId(Integer movieId) {
        Movie movie = movieMapper.selectByPrimaryKey(movieId);
//        //这种方式表示电影类型存储的是电影类型id，要切分字段值，再根据每个类型id获取相对应的类型
//        String[] split = movie.getTypeList().split("/");
//        StringBuffer buffer = new StringBuffer();
//        for (String str:split){
//            Category category = categoryMapper.selectByPrimaryKey(Integer.parseInt(str.trim()));
//            buffer.append(category.getCategoryName().trim()+"/");
//        }
//        String string = buffer.toString();
//        movie.setTypeList(string.substring(0,string.length()-1));
//        //这种方式表示电影类型存储的是电影类型的文本，不需要再做转换了，所以直接返回即可
        return movie;
    }
}
