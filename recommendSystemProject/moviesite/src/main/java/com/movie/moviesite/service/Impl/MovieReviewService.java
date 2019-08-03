package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.CommonCondition;
import com.movie.moviesite.entity.MovieReview;
import com.movie.moviesite.entity.SelectCondition;
import com.movie.moviesite.mapper.MovieReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieReviewService implements com.movie.moviesite.service.MovieReviewService{

    @Autowired
    private MovieReviewMapper movieReviewMapper;

    @Override
    public boolean insertMovieReview(MovieReview movieReview) {
        int result = movieReviewMapper.insertMovieReview(movieReview);
        return result > 0;
    }

    @Override
    public List<MovieReview> selectMovieReviewByMovieId(CommonCondition condition) {
        return movieReviewMapper.selectMovieReviewByMovieId(condition);
    }

    @Override
    public MovieReview selectMovieReviewByMovieIdAndUserId(Integer userId, Integer movieId) {
        MovieReview movieReview = new MovieReview();
        movieReview.setUserId(userId);
        movieReview.setMovieId(movieId);
        return movieReviewMapper.selectMovieReviewByMovieIdAndUserId(movieReview);
    }

    @Override
    public boolean deleteMovieReviewByMovieIdAndUserId(MovieReview movieReview) {
        int result = movieReviewMapper.deleteMovieReviewByMovieIdAndUserId(movieReview);
        return result > 0;
    }

    @Override
    public boolean updateUsefulCount(MovieReview movieReview) {
        int result = movieReviewMapper.updateUsefulCount(movieReview);
        return result <= 0;
    }

    @Override
    public boolean updateReviewContent(MovieReview movieReview) {
        int result = movieReviewMapper.updateReviewContent(movieReview);
        return result > 0;
    }
}
