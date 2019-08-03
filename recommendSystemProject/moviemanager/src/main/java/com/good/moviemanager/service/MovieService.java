package com.good.moviemanager.service;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.Movie;
import com.good.moviemanager.entity.QueryCondition;

import java.util.List;

public interface MovieService {

    /**
     * 不添加条件获取所有的电影信息,用于导出操作
     * @return
     */
    List<Movie> selectAllMoviesNoCondition();

    /**
     * 获取所有的电影记录,分页
     * @param pageNum 页数
     * @param pageSize 每页显示的记录数
     * @return
     */
    PageInfo<Movie> selectAllMovies(int pageNum, int pageSize, QueryCondition movie);

    /**
     * 获取电影历史热度榜
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Movie> selectTopKHistoryHeatMovies(int pageNum, int pageSize);

    /**
     * 根据电影Id查询电影
     * @param movieId
     * @return
     */
    Movie selectByMovieId(Integer movieId);

    /**
     * 插入一条电影记录
     * @param movie
     * @return
     */
    boolean insert(Movie movie);

    /**
     * 更新电影信息
     * @param movie
     * @return
     */
    boolean updateOneMovieSelective(Movie movie);

    /**
     * 删除指定的电影记录/批量删除
     * @param movieId
     * @return
     */
    boolean deleteOneMovie(Integer movieId);

    /**
     * 批量插入电影数据
     * @param movies
     * @return
     */
    boolean batchInsertMovies(List<Movie> movies);

}
