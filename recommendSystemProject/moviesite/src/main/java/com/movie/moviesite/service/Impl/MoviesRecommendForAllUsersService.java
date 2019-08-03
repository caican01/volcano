package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.MoviesRecommendForAllUsers;
import com.movie.moviesite.mapper.MovieMapper;
import com.movie.moviesite.mapper.MoviesRecommendForAllUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoviesRecommendForAllUsersService implements com.movie.moviesite.service.MoviesRecommendForAllUsersService{

    @Autowired
    private MoviesRecommendForAllUsersMapper moviesRecommendForAllUsersMapper;
    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<Movie> selectByUserId(Integer userId) {
        MoviesRecommendForAllUsers moviesRecommendForAllUsers = moviesRecommendForAllUsersMapper.selectByUserId(userId);
        String[] split = moviesRecommendForAllUsers.getMovieIds().split(",");
        List<Movie> list = new ArrayList<>();
        for (String str:split) {
            //数据组成movieId:rating,movieId:rating,...
            String[] idAndRating = str.split(":");
            list.add(movieMapper.selectByPrimaryKey(Integer.parseInt(idAndRating[0].trim())));
        }
        return list;
    }
}
