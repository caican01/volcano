package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.RecentMovieHeatRankedList;
import com.movie.moviesite.mapper.RecentMovieHeatRankedListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecentMovieHeatRankedListService implements com.movie.moviesite.service.RecentMovieHeatRankedListService{

    @Autowired
    private RecentMovieHeatRankedListMapper recentMovieHeatRankedListMapper;

    @Override
    public List<RecentMovieHeatRankedList> selectTopKRecentHeatMovies(Integer k) {
        return recentMovieHeatRankedListMapper.selectTopKRecentHeatMovies(k);
    }
}
