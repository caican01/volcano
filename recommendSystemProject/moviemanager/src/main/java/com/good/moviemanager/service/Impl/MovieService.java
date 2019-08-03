package com.good.moviemanager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.Movie;
import com.good.moviemanager.entity.QueryCondition;
import com.good.moviemanager.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieService implements com.good.moviemanager.service.MovieService{

    @Autowired
    private MovieMapper movieMapper;

    @Override
    public List<Movie> selectAllMoviesNoCondition() {
        QueryCondition condition = new QueryCondition();
        List<Movie> movieList = movieMapper.selectAllMovies(condition);
        if (movieList==null||movieList.size()<1)
            return null;
        else
            return movieList;
    }

    @Override
    public PageInfo<Movie> selectAllMovies(int pageNum, int pageSize,QueryCondition movie) {
        PageHelper.startPage(pageNum,pageSize);
        List<Movie> movies = movieMapper.selectAllMovies(movie);
        PageInfo<Movie> info = new PageInfo<>(movies);
        return info;
    }

    @Override
    public PageInfo<Movie> selectTopKHistoryHeatMovies(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Movie> movies = movieMapper.selectTopKHistoryHeatMovies();
        PageInfo<Movie> info = null;
        if (movies!=null&&movies.size()>0){
            info = new PageInfo<>(movies);
        }
        return info;
    }

    @Override
    public Movie selectByMovieId(Integer movieId) {
        return movieMapper.selectByMovieId(movieId);
    }

    @Override
    public boolean insert(Movie movie) {
        int result = movieMapper.insertOneMovie(movie);
        return result > 0;
    }

    @Override
    public boolean updateOneMovieSelective(Movie movie) {
        int result = movieMapper.updateOneMovieSelective(movie);
        return result > 0;
    }

    @Override
    public boolean deleteOneMovie(Integer movieId) {
        int result = movieMapper.deleteOneMovie(movieId);
        return result > 0;
    }

    @Override
    public boolean batchInsertMovies(List<Movie> movies) {
        if(movies!=null&&movies.size()>=1) {
            for (Movie movie : movies) {
                if (movie != null && movie.getMovieId()>0) {
                    //判断导入的数据是否已经存在数据库中，避免重复导入
                    Movie selectByMovieId = movieMapper.selectByMovieId(movie.getMovieId());
                    //不存在数据库中，则导入，否则不导入
                    if (selectByMovieId==null)
                        movieMapper.importOneMovie(movie);
                }
            }
            return true;
        }
        return false;
    }
}
