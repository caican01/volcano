package com.movie.moviesite.service;

import com.movie.moviesite.entity.Movie;

public interface MovieDetailsService {

    /**
     * 获取电影详情
     * @param movieId
     * @return
     */
    Movie getMovieDetailsByMovieId(Integer movieId);

}
