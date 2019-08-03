package com.good.moviemanager.mapper;

import com.good.moviemanager.entity.RecentMovieHeatRankedList;

import java.util.List;

/**
 * 近期热度榜
 */
public interface RecentMovieHeatRankedListMapper {

    /**
     * 取出历史热度榜前10
     * @return
     */
    List<RecentMovieHeatRankedList> selectAll();

}
