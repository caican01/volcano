package com.good.moviemanager.mapper;

import com.good.moviemanager.entity.MovieReview;
import com.good.moviemanager.entity.QueryCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MovieReviewMapper {

    /**
     * 查询所有的电影评论
     * @param condition
     * @return
     */
    List<MovieReview> selectAllMovieReview(QueryCondition condition);

}
