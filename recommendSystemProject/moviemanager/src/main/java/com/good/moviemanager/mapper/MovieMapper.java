package com.good.moviemanager.mapper;

import com.good.moviemanager.entity.Movie;
import com.good.moviemanager.entity.QueryCondition;

import java.util.List;

public interface MovieMapper {

    /**
     * 根据条件获取所有满足条件的电影信息
     * @param movie
     * @return
     */
    List<Movie> selectAllMovies(QueryCondition movie);

    /**
     * 获取电影热度榜
     * @return
     */
    List<Movie> selectTopKHistoryHeatMovies();

    /**
     * 根据电影Id查询电影
     * @param movieId
     * @return
     */
    Movie selectByMovieId(Integer movieId);

    /**
     * 前端插入一条电影信息
     * @param movie
     * @return 插入的行数
     */
    int insertOneMovie(Movie movie);

    /**
     * 导入电影信息
     * @param movie
     * @return
     */
    int importOneMovie(Movie movie);

    /**
     * 更新电影信息
     * @param movie
     * @return 修改影响的记录数
     */
    int updateOneMovieSelective(Movie movie);

    /**
     * 删除单条电影记录/批量删除
     * @param movieId
     * @return 删除的记录数
     */
    int deleteOneMovie(Integer movieId);
}
