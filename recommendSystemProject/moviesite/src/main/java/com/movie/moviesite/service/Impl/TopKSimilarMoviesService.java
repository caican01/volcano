package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.TopKSimilarMovies;
import com.movie.moviesite.mapper.CategoryMapper;
import com.movie.moviesite.mapper.MovieMapper;
import com.movie.moviesite.mapper.TopKSimilarMoviesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopKSimilarMoviesService implements com.movie.moviesite.service.TopKSimilarMoviesService{

    @Autowired
    private TopKSimilarMoviesMapper topKSimilarMoviesMapper;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Movie> selectSimilarMovieByMovieId(Integer movieId) {
        TopKSimilarMovies topKSimilarMovies = topKSimilarMoviesMapper.selectSimilarMovieByMovieId(movieId);
        String[] split = topKSimilarMovies.getSimsMovieIds().split(",");
        List<Movie> list = new ArrayList<>();
        int K = split.length<=5?split.length:5;
        for (int i=0;i<K;i++){
            Movie movie = movieMapper.selectByPrimaryKey(Integer.parseInt(split[i].substring(0, split[i].indexOf(":"))));
//            String[] typeSplit = movie.getTypeList().split("/");
//            StringBuffer buffer = new StringBuffer();
//            for (String str:typeSplit) {
//                String categoryName = categoryMapper.selectByPrimaryKey(Integer.parseInt(str.trim())).getCategoryName();
//                buffer.append(categoryName+"/");
//            }
//            String s = buffer.toString();
//            movie.setTypeList(s.substring(0,s.length()-1));
            list.add(movie);
        }
        return list;
    }
}
