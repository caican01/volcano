package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.SelectCondition;
import com.movie.moviesite.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements com.movie.moviesite.service.MovieService{

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public Movie selectByPrimaryKey(Integer movieId) {
        return movieMapper.selectByPrimaryKey(movieId);
    }

    @Override
    public List<Movie> selectTopKMovies(Integer k) {
        return movieMapper.selectTopKMovies(k);
    }

    @Override
    public List<Movie> selectByMovieName(String movieName) {
        return movieMapper.selectByMovieName(movieName);
    }

    @Override
    public List<Movie> selectLikeMovieName(String movieName) {
        return movieMapper.selectLikeMovieName(movieName);
    }

    @Override
    public List<Movie> selectByCondition(SelectCondition condition) {
        return movieMapper.selectByCondition(condition);
    }
}
