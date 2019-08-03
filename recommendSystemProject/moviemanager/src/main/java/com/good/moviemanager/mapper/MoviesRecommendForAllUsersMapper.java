package com.good.moviemanager.mapper;

import com.good.moviemanager.entity.MoviesRecommendForAllUsers;

import java.util.List;

public interface MoviesRecommendForAllUsersMapper {

    /**
     * 查询为每个用户推荐的电影
     * @param userName
     * @return
     */
    List<MoviesRecommendForAllUsers> selectAllMoviesRecommendForUsers(String userName);

}
