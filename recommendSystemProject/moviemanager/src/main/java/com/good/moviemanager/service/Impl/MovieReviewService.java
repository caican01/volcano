package com.good.moviemanager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.MovieReview;
import com.good.moviemanager.entity.QueryCondition;
import com.good.moviemanager.mapper.MovieReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieReviewService implements com.good.moviemanager.service.MovieReviewService{

    @Autowired
    private MovieReviewMapper movieReviewMapper;

    /**
     * 查询电影评论
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return
     */
    @Override
    public PageInfo<MovieReview> selectAllMovieReviews(int pageNum, int pageSize, QueryCondition condition) {
        PageHelper.startPage(pageNum,pageSize);
        List<MovieReview> movieReviews = movieReviewMapper.selectAllMovieReview(condition);
        PageInfo<MovieReview> info = new PageInfo<>(movieReviews);
        return info;
    }
}
