package com.movie.moviesite.service;

import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.SelectCondition;

import java.util.List;

public interface MovieService {
    /**
     * 根据主键查询电影
     * @param movieId
     * @return
     */
    Movie selectByPrimaryKey(Integer movieId);

    /**
     * 按照历史热度降序排序，获取前K条记录
     * @param k
     * @return
     */
    List<Movie> selectTopKMovies(Integer k);

    /**
     * 根据电影名称搜索电影（用于搜索电影）
     * @param movieName
     * @return
     */
    List<Movie> selectByMovieName(String movieName);

    /**
     * 根据电影名称进行模糊查询（根据输入的名称进行模糊查询，返回前6条查询结果，用于搜索的智能提示框显示）
     * @param movieName
     * @return
     */
    List<Movie> selectLikeMovieName(String movieName);

    /**
     * 按照指定条件查询电影信息
     * @param condition
     * @return
     */
    List<Movie> selectByCondition(SelectCondition condition);

}
