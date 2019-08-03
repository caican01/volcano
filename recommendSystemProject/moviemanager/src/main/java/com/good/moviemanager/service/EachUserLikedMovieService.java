package com.good.moviemanager.service;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.EachUserLikedMovie;
import com.good.moviemanager.entity.QueryCondition;

/**
 * 用户收藏 逻辑接口
 */
public interface EachUserLikedMovieService {

    /**
     * 获取所有用户收藏的电影
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return
     */
    PageInfo<EachUserLikedMovie> selectUsersLikedMovies(int pageNum,int pageSize,QueryCondition condition);

}
