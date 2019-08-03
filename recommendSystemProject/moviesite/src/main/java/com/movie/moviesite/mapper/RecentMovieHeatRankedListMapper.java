package com.movie.moviesite.mapper;

import com.movie.moviesite.entity.RecentMovieHeatRankedList;
import java.util.List;

/**
 * 近期热度电影
 */
public interface RecentMovieHeatRankedListMapper {

    /**
     * 将movie表与RecentMovieHeatRankedList表进行内连接，
     * 再对得到的新表按近期热度字段进行降序排序，
     * 获取近期热度前k的电影
     * @param k
     * @return
     */
    List<RecentMovieHeatRankedList> selectTopKRecentHeatMovies(Integer k);

}
