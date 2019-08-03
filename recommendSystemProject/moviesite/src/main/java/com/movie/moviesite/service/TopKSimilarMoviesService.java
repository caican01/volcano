package com.movie.moviesite.service;

import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.TopKSimilarMovies;

import java.util.List;

public interface TopKSimilarMoviesService {

    /**
     * 根据电影Id查询该电影的相似电影的Id们
     * @param movieId
     * @return
     */
    List<Movie> selectSimilarMovieByMovieId(Integer movieId);
}
