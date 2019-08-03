package com.movie.moviesite.controller;

import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.User;
import com.movie.moviesite.service.MovieService;
import com.movie.moviesite.service.MoviesRecommendForAllUsersService;
import com.movie.moviesite.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页控制器
 */
@Controller
public class HomeController {

    @Autowired
    private MovieService movieService;
    @Autowired
    private MoviesRecommendForAllUsersService moviesRecommendForAllUsersService;

    /**
     * 返回主页Home
     * @param request
     * @return
     */
    @RequestMapping(value = "/")
    public String toHomePage(HttpServletRequest request){
        //从session中获取用户
        User user = (User)(request.getSession().getAttribute("user"));
        List<Movie> movieList = null;
        Map<String,Integer> movieMap = new HashMap<>();
        /**
         * 判断用户是否登录，登录则显示推荐给用户的电影，否则推荐历史热度前5的电影
         */

        //从session中删除之前的homePageRecommendedMovies
        request.getSession().removeAttribute("homePageRecommendedMovies");

        //用户未登录
        if (user == null){
            //获取按历史热度降序排名后前5的电影
            movieList = movieService.selectTopKMovies(5);
            //将获取的电影数据写入session，供前端调用
            request.getSession().setAttribute("homePageRecommendedMovies",movieList);
        }
        //用户登录
        else
        {
            //判断用户是不是新用户：有过评分行为则为老用户，否则为新用户
            //若有过评分行为，则已为每个老用户推荐了电影，而新用户则还没有推荐
            movieList = moviesRecommendForAllUsersService.selectByUserId(user.getUserId());
            //说明为老用户
            if (movieList!=null&&movieList.size()>0){
                //如果推荐给用户的电影数并不足5部
                if (movieList.size()<5){
                    //从历史热度电影中补足
                    List<Movie> list = movieService.selectTopKMovies(5 - movieList.size());
                    movieList.addAll(list);
                }
                //将推荐给用户的电影数据写入session
                request.getSession().setAttribute("homePageRecommendedMovies",movieList);
            }
            //说明为新用户,统一推荐历史热度前5的电影
            else {
                movieList = movieService.selectTopKMovies(5);
                request.getSession().setAttribute("homePageRecommendedMovies",movieList);
            }
        }
        //将电影ID与下标建立映射关系，以便在前端通过电影ID就知道轮播的位置
        //简历映射关系
        for (int i=0;i<movieList.size();i++){
            movieMap.put(String.valueOf(movieList.get(i).getMovieId()),i);
        }
        //将电影ID与下标的映射关系写入session
        request.getSession().setAttribute("homePageRecommendedMovieMap", JsonUtils.objectToJson(movieMap));

        //返回主页面
        return "Home";
    }
}
