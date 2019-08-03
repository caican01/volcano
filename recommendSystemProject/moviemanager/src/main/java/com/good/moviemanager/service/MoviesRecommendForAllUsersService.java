package com.good.moviemanager.service;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.MoviesRecommendForAllUsers;

public interface MoviesRecommendForAllUsersService {

    /**
     * 获取为每个用户推荐的电影
     * @param pageNum
     * @param pageSize
     * @param userName
     * @return
     */
    PageInfo<MoviesRecommendForAllUsers> selectAllMoviesRecommendForUsers(int pageNum, int pageSize, String userName);

}
