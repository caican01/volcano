package com.good.moviemanager.service;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.RecentMovieHeatRankedList;

/**
 * 近期热度榜
 */
public interface RecentMovieHeatRankedListService {

    /**
     * 获取近期热度前10的电影记录
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<RecentMovieHeatRankedList> selectAll(int pageNum, int pageSize);

}
