package com.movie.moviesite.service;

import com.movie.moviesite.entity.MovieRating;

import java.util.List;

public interface MovieRatingService {

    /**
     * 获取用户对某部电影的评分数据
     * @param userId
     * @param movieId
     * @return
     */
    MovieRating selectByUserIdAndMovieId(Integer userId, Integer movieId);

    /**
     * 获取用户的所有评分数据
     * @param userId
     * @return
     */
    List<MovieRating> selectByUserId(Integer userId);
}
