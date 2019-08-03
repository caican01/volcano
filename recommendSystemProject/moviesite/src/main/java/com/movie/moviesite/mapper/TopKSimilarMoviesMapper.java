package com.movie.moviesite.mapper;

import com.movie.moviesite.entity.TopKSimilarMovies;

/**
 * 相似电影
 */
public interface TopKSimilarMoviesMapper {

    /**
     * 根据电影Id查询该电影的相似电影的Id们
     * @param movieId
     * @return
     */
    TopKSimilarMovies selectSimilarMovieByMovieId(Integer movieId);

}
