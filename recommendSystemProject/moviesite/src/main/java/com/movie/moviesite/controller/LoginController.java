package com.movie.moviesite.controller;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.MovieRating;
import com.movie.moviesite.entity.MovieReview;
import com.movie.moviesite.entity.User;
import com.movie.moviesite.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 登录控制器
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private MovieReviewService movieReviewService;
    @Autowired
    private EachUserLikedMovieService eachUserLikedMovieService;
    @Autowired
    private MovieRatingService movieRatingService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private MoviesRecommendForAllUsersService moviesRecommendForAllUsersService;

    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping(value = "/page/login")
    public String LoginPage(){
        //返回登录页面
        return "login";
    }

    /**
     * 登录判断
     * @param email
     * @param userPassword
     * @param request
     * @param movieId
     * @return
     */
    @RequestMapping(value = "/customer/login", method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(@RequestParam("email") String email, @RequestParam("userPassword") String userPassword,
                          @RequestParam("movieId") Integer movieId ,HttpServletRequest request){
        E3Result e3Result = loginService.userLogin(email, userPassword);
        User user = null;
        //说明登录成功
        if (e3Result.getStatus()==200){
            user = (User)e3Result.getData();
        }
        //将user写入session,如果登录成功，则user不为null，否则为null
        request.getSession().setAttribute("user",user);

        if (user!=null&&movieId!=0){
            request.getSession().removeAttribute("booluserunlikedmovie");
            request.getSession().removeAttribute("userstar");
            request.getSession().removeAttribute("userReview");
            request.getSession().removeAttribute("homePageRecommendedMovies");
            //判断用户是否收藏了电影，1表示收藏了，0表示未收藏
            int isLike = eachUserLikedMovieService.userIsLikeTheMovie(user.getUserId(), movieId);
            //用户评分数据
            MovieRating userRating = movieRatingService.selectByUserIdAndMovieId(user.getUserId(), movieId);
            //用户对该电影的评价数据
            MovieReview movieReview = movieReviewService.selectMovieReviewByMovieIdAndUserId(user.getUserId(), movieId);
            /**
             * 给用户推荐的电影
             */
            //判断用户是不是新用户：有过评分行为则为老用户，否则为新用户
            //若有过评分行为，则已为每个老用户推荐了电影，而新用户则还没有推荐
            List<Movie> recommendMovieList = moviesRecommendForAllUsersService.selectByUserId(user.getUserId());
            //说明为老用户
            if (recommendMovieList!=null&&recommendMovieList.size()>0){
                //如果推荐给用户的电影数并不足5部
                if (recommendMovieList.size()<5){
                    //从历史热度电影中补足
                    List<Movie> list = movieService.selectTopKMovies(5 - recommendMovieList.size());
                    recommendMovieList.addAll(list);
                }
            }
            //说明为新用户,统一推荐历史热度前5的电影
            else {
                recommendMovieList = movieService.selectTopKMovies(5);
            }
            //写入session
            request.getSession().setAttribute("booluserunlikedmovie",isLike);  //用于加载页面时判断控件的状态，进而做出改变
            request.getSession().setAttribute("userstar",userRating);  //用户对该电影的评分
            request.getSession().setAttribute("userReview",movieReview);  //用于判断登录用户是否评论过了
            request.getSession().setAttribute("homePageRecommendedMovies",recommendMovieList);
        }

        return e3Result;
    }

    /**
     * 退出登录并返回主页
     * @param request
     * @return
     */
    @RequestMapping(value = "/page/logout")
    public String logout(HttpServletRequest request){
        //退出登录，则需要注销该用户写入session的所有属性
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("userId");
        /**
         * 暂时写入的属性就这两个，后面肯定还会有，到时候再添加
         */
        return "Home";
    }

}
