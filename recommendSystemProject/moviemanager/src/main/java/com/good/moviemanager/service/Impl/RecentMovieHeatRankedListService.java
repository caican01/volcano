package com.good.moviemanager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.RecentMovieHeatRankedList;
import com.good.moviemanager.mapper.RecentMovieHeatRankedListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecentMovieHeatRankedListService implements com.good.moviemanager.service.RecentMovieHeatRankedListService{

    @Autowired
    private RecentMovieHeatRankedListMapper recentMovieHeatRankedListMapper;

    @Override
    public PageInfo<RecentMovieHeatRankedList> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<RecentMovieHeatRankedList> recentMovieHeatRankedLists = recentMovieHeatRankedListMapper.selectAll();
        PageInfo<RecentMovieHeatRankedList> info = new PageInfo<>(recentMovieHeatRankedLists);
        return info;
    }

}