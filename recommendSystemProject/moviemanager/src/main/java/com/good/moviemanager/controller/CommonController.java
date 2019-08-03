package com.good.moviemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {

    @RequestMapping(value = "/movie")
    public String toUser(){
        return "movie";
    }

    @RequestMapping(value = "/user")
    public String toMovie(){
        return "user";
    }

    @RequestMapping(value = "/topKHistoryHeatMovies")
    public String toTopKHistoryHeatMovie(){
        return "topKHistoryHeatMovies";
    }

    @RequestMapping(value = "/movieReview")
    public String toMovieReview(){
        return "movieReview";
    }

    @RequestMapping(value = "/userLikedMovies")
    public String toUserLikedMovies(){
        return "userLikedMovies";
    }

    @RequestMapping(value = "/moviesRecommendForUsers")
    public String toMoviesRecommendForUsers(){
        return "moviesRecommendForUsers";
    }

    @RequestMapping(value = "/main")
    public String toMain(){
        return "main";
    }

    @RequestMapping(value = "/movieDataUtils")
    public String toMovieDataUtils(){
        return "movieDataUtils";
    }

//    @RequestMapping(value = "/userDataUtils")
//    public String toUserDataUtils(){
//        return "userDataUtils";
//    }

    @RequestMapping(value = "/recentMovieHeatRankedList")
    public String toRecentMovieHeatRankedList(){
        return "recentMovieHeatRankedList";
    }
}
