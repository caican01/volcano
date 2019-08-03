package com.movie.moviesite.service;

import com.movie.moviesite.entity.Movie;
import java.util.List;

public interface MoviesRecommendForAllUsersService {

    /**
     * 根据用户Id获取推荐给他的电影Id们
     * @param userId
     * @return
     */
    List<Movie> selectByUserId(Integer userId);

}
