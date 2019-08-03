package com.good.moviemanager.mapper;

import com.good.moviemanager.entity.EachUserLikedMovie;
import com.good.moviemanager.entity.QueryCondition;

import java.util.List;

public interface EachUserLikedMovieMapper {

    List<EachUserLikedMovie> selectAllUsersLikedMovies(QueryCondition condition);

}
