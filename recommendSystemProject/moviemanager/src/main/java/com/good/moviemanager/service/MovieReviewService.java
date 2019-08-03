package com.good.moviemanager.service;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.MovieReview;
import com.good.moviemanager.entity.QueryCondition;

/**
 * 用户评价 逻辑接口
 */
public interface MovieReviewService {

    /**
     * 查询电影评论
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return
     */
    PageInfo<MovieReview> selectAllMovieReviews(int pageNum, int pageSize, QueryCondition condition);

}
