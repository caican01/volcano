package com.movie.moviesite.controller;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.EachUserLikedMovie;
import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.MovieRating;
import com.movie.moviesite.entity.User;
import com.movie.moviesite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * 个人中心控制器
 */
@Controller
public class MineController {

    @Autowired
    private UserService userService;
    @Autowired
    private EachUserLikedMovieService eachUserLikedMovieService;
    @Autowired
    private MovieRatingService movieRatingService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MoviesRecommendForAllUsersService moviesRecommendForAllUsersService;

    /**
     * 通过userId查询获取user
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/edit")
    @ResponseBody
    public User getUserByUserId(Integer id){
        return userService.selectByUserId(id);
    }

    /**
     * 修改用户名或密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/user/update",method = RequestMethod.GET)
    public E3Result updateUserMessage(HttpServletRequest request){
        User user = new User();
        user.setUserId(Integer.parseInt(request.getParameter("userId").trim()));
        user.setUserName(request.getParameter("userName"));
        user.setUserPassword(request.getParameter("userPassword"));
        boolean result = userService.updateUserMessage(user);
        User sessionUser = (User) request.getSession().getAttribute("user");
        //说明密码被修改了，需要重新登录
        if (!request.getParameter("userPassword").equals(sessionUser.getUserPassword())){
            request.getSession().removeAttribute("user");
        }
        E3Result e3Result = new E3Result();
        if (result){
            e3Result.setStatus(200);
        }else {
            e3Result.setStatus(400);
        }
        return e3Result;
    }

    /**
     * 点击"个人中心"按钮，传值
     * @return
     */
    @RequestMapping(value = "/page/profile",method = RequestMethod.POST)
    @ResponseBody
    public String goToPersonalCenter(HttpServletRequest request){
        //获取用户Id
        int userId = Integer.parseInt(request.getParameter("userId"));
        //获取用户喜欢（收藏）的电影列表
        List<EachUserLikedMovie> likedMovies = eachUserLikedMovieService.selectByUserId(userId);
        //获取用户评价过的电影列表
        List<MovieRating> ratingList = movieRatingService.selectByUserId(userId);
        List<Movie> movieList = new LinkedList<>();
        for (MovieRating rating:ratingList){
            Movie movie = movieService.selectByPrimaryKey(rating.getMovieId());
            movie.setTimestamp(rating.getTimestamp());
            movie.setAverageRating(rating.getRating());
            movieList.add(movie);
        }
        List<Movie> recommendMovieList = moviesRecommendForAllUsersService.selectByUserId(userId);
        //写入session
        request.getSession().removeAttribute("userLikedList");
        request.getSession().setAttribute("userLikedList",likedMovies);
        request.getSession().removeAttribute("userRatingList");
        request.getSession().setAttribute("userRatingList",movieList);
        request.getSession().removeAttribute("homePageRecommendedMovies");
        request.getSession().setAttribute("homePageRecommendedMovies",recommendMovieList);
        return "success";
    }

    /**
     * 进入个人中心页
     * @return
     */
    @RequestMapping(value = "/profile")
    public String pagePersonalCenter(){
        return "profile";
    }
}
