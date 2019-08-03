package com.movie.moviesite.mapper;

import com.movie.moviesite.entity.MoviesRecommendForAllUsers;

/**
 * 推荐给用户的电影
 */
public interface MoviesRecommendForAllUsersMapper {

    /**
     * 根据用户Id获取推荐给他的电影Id们
     * @param userId
     * @return
     */
    MoviesRecommendForAllUsers selectByUserId(Integer userId);

}
