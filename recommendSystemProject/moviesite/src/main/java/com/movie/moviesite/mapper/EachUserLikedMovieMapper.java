package com.movie.moviesite.mapper;

import com.movie.moviesite.entity.EachUserLikedMovie;

import java.util.List;

/**
 * 用户收藏（喜欢）电影、显示用户收藏（喜欢）的电影
 */
public interface EachUserLikedMovieMapper {

    /**
     * 收藏电影
     * @param eachUserLikedMovie
     * @return
     */
    int insert(EachUserLikedMovie eachUserLikedMovie);

    /**
     * 取消收藏
     * @param eachUserLikedMovie
     * @return
     */
    int delete(EachUserLikedMovie eachUserLikedMovie);

    /**
     * 查询该用户收藏(喜欢)的所有电影
     * @param userId
     * @return
     */
    List<EachUserLikedMovie> selectByUserId(Integer userId);

    /**
     * 判断用户是否喜欢该电影
     * 通过userId和movieId查询记录，若存在，则喜欢（即已收藏），否则未收藏
     * @param eachUserLikedMovie
     * @return
     */
    EachUserLikedMovie userIsLikeTheMovie(EachUserLikedMovie eachUserLikedMovie);

}
