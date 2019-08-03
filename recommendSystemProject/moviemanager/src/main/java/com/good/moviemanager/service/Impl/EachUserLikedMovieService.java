package com.good.moviemanager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.EachUserLikedMovie;
import com.good.moviemanager.entity.QueryCondition;
import com.good.moviemanager.mapper.EachUserLikedMovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EachUserLikedMovieService implements com.good.moviemanager.service.EachUserLikedMovieService{

    @Autowired
    private EachUserLikedMovieMapper eachUserLikedMovieMapper;

    /**
     *查询所有用户收藏的电影
     * @param pageNum
     * @param pageSize
     * @param condition
     * @return
     */
    @Override
    public PageInfo<EachUserLikedMovie> selectUsersLikedMovies(int pageNum, int pageSize, QueryCondition condition) {
        PageHelper.startPage(pageNum, pageSize);
        List<EachUserLikedMovie> eachUserLikedMovies = eachUserLikedMovieMapper.selectAllUsersLikedMovies(condition);
        PageInfo<EachUserLikedMovie> info = new PageInfo<>(eachUserLikedMovies);
        return info;
    }
}