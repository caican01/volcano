package com.movie.moviesite.entity;

import java.io.Serializable;

/**
 * 相似电影表
 */
public class TopKSimilarMovies implements Serializable{

    private int movieId;//指定的电影Id
    private String simsMovieIds;//与movieId电影相似的电影们

    public TopKSimilarMovies(){}

    public TopKSimilarMovies(int movieId,String simsMovieIds){
        initTopKSimilarMovies(movieId,simsMovieIds);
    }

    public void initTopKSimilarMovies(int movieId,String simsMovieIds){
        this.movieId = movieId;
        this.simsMovieIds = simsMovieIds;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getSimsMovieIds() {
        return simsMovieIds;
    }

    public void setSimsMovieIds(String simsMovieIds) {
        this.simsMovieIds = simsMovieIds;
    }

    @Override
    public String toString() {
        return this.movieId+":"+simsMovieIds;
    }
}
