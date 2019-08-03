package com.good.moviemanager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.Movie;
import com.good.moviemanager.entity.MoviesRecommendForAllUsers;
import com.good.moviemanager.mapper.MovieMapper;
import com.good.moviemanager.mapper.MoviesRecommendForAllUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MoviesRecommendForAllUsersService implements com.good.moviemanager.service.MoviesRecommendForAllUsersService{

    @Autowired
    private MoviesRecommendForAllUsersMapper moviesRecommendForAllUsersMapper;
    @Autowired
    private MovieMapper movieMapper;

    /**
     * 为每个用户推荐的电影
     * @param pageNum
     * @param pageSize
     * @param userName
     * @return
     */
    @Override
    public PageInfo<MoviesRecommendForAllUsers> selectAllMoviesRecommendForUsers(int pageNum, int pageSize, String userName) {
        PageHelper.startPage(pageNum,pageSize);
        List<MoviesRecommendForAllUsers> moviesRecommendForAllUsers = moviesRecommendForAllUsersMapper.selectAllMoviesRecommendForUsers(userName);
        for (MoviesRecommendForAllUsers moviesRecommended : moviesRecommendForAllUsers){
            String[] split = moviesRecommended.getMovieIds().split(",");
            StringBuffer buffer = new StringBuffer();
            for (int i=0;i<split.length;i++){
                int index = split[i].indexOf(":");
                Movie movie = movieMapper.selectByMovieId(Integer.parseInt(split[i].substring(0,index)));
                if (i==split.length-1)
                    buffer.append(movie.getMovieName());
                else
                    buffer.append(movie.getMovieName()+",");
            }
            moviesRecommended.setMovieIds(buffer.toString());
        }
        PageInfo<MoviesRecommendForAllUsers> info = new PageInfo<>(moviesRecommendForAllUsers);
        return info;
    }
}
