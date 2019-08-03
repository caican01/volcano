package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.EachUserLikedMovie;
import com.movie.moviesite.mapper.EachUserLikedMovieMapper;
import com.movie.moviesite.service.EachUserLikedMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EachUserLikedMoviesService implements EachUserLikedMovieService{

    @Autowired
    private EachUserLikedMovieMapper eachUserLikedMovieMapper;

    @Override
    public boolean insert(EachUserLikedMovie eachUserLikedMovie) {
        int result = eachUserLikedMovieMapper.insert(eachUserLikedMovie);
        return result > 0;
    }

    @Override
    public boolean delete(EachUserLikedMovie eachUserLikedMovie) {
        int result = eachUserLikedMovieMapper.delete(eachUserLikedMovie);
        return result > 0;
    }

    @Override
    public List<EachUserLikedMovie> selectByUserId(Integer userId) {
        return eachUserLikedMovieMapper.selectByUserId(userId);
    }

    @Override
    public int userIsLikeTheMovie(Integer userId,Integer movieId) {
        EachUserLikedMovie eachUserLikedMovie = new EachUserLikedMovie();
        eachUserLikedMovie.setUserId(userId);
        eachUserLikedMovie.setMovieId(movieId);
        EachUserLikedMovie userLikedMovie = eachUserLikedMovieMapper.userIsLikeTheMovie(eachUserLikedMovie);
        int result = 0;
        if (userLikedMovie!=null)
            result = 1;
        return result;
    }
}
