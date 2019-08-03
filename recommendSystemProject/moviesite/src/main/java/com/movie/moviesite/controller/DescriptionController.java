package com.movie.moviesite.controller;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.*;
import com.movie.moviesite.service.*;
import com.movie.moviesite.utils.KafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * 详情页控制器
 */
@Controller
public class DescriptionController {

    @Autowired
    private MovieRatingService movieRatingService;
    @Autowired
    private MovieReviewService movieReviewService;
    @Autowired
    private EachUserLikedMovieService eachUserLikedMovieService;
    @Autowired
    private MovieDetailsService movieDetailsService;
    @Autowired
    private TopKSimilarMoviesService topKSimilarMoviesService;

    /**
     * 电影详情页传值
     * @param request
     * @return
     */
    @RequestMapping(value = "/Customer/Description")
    @ResponseBody
    public String getMovieDetails(HttpServletRequest request){
        //booluserunlikedmovie属性的作用：判断用户是否喜欢某部电影
        //每次电影详情传值都需要先从session中删除booluserunlikedmovie属性，以免它对数据产生影响。
        request.getSession().removeAttribute("booluserunlikedmovie");
        //获取用户点击的movieId
        int  movieId = Integer.parseInt(request.getParameter("movieId"));
        //根据电影Id获取该电影的信息
        Movie movie = movieDetailsService.getMovieDetailsByMovieId(movieId);
        //将电影信息写入session
        request.getSession().setAttribute("movieDescription",movie);

        //获取该电影的相似电影
        List<Movie> similarMovieList = topKSimilarMoviesService.selectSimilarMovieByMovieId(movieId);
        //将该相似电影写入session
        request.getSession().setAttribute("SimilarMovies",similarMovieList);

        //该电影的所有影评,第二个参数用来控制评论的排序方式，0（全部）->按点赞数降序排序，1（最新）->按评论时间降序排序，默认是0（即按点赞数降序排序获取全部评论信息）
        //查询条件SelectCondition类来封装
        CommonCondition condition = new CommonCondition();
        condition.setCategoryId(movieId);
        condition.setLimitCount(0);
        List<MovieReview> movieReviews = movieReviewService.selectMovieReviewByMovieId(condition);
        //将该电影的所有评论信息写入session
        request.getSession().removeAttribute("reviews");
        request.getSession().setAttribute("reviews",movieReviews);  //该电影的所有评论


        //从session中取出user
        User user  = (User)(request.getSession().getAttribute("user"));
        //判断用户是否已经登录
        if (user!=null){  //说明已经登录
            //判断用户是否收藏了电影，1表示收藏了，0表示未收藏
            int isLike = eachUserLikedMovieService.userIsLikeTheMovie(user.getUserId(), movieId);
            //用户评分数据
            MovieRating userRating = movieRatingService.selectByUserIdAndMovieId(user.getUserId(), movieId);
            //用户对该电影的评价数据
            MovieReview movieReview = movieReviewService.selectMovieReviewByMovieIdAndUserId(user.getUserId(), movieId);
            //写入session
            request.getSession().setAttribute("booluserunlikedmovie",isLike);  //用于加载页面时判断控件的状态，进而做出改变
            request.getSession().removeAttribute("userstar");
            request.getSession().setAttribute("userstar",userRating);  //用户对该电影的评分
            request.getSession().removeAttribute("userReview");
            request.getSession().setAttribute("userReview",movieReview);  //用于判断登录用户是否评论过了
        }
        //否则未登录
        else {
            //do nothing
        }
        return "success";
    }

    /**
     * 进入电影详情页
     * @return
     */
    @RequestMapping(value = "/MovieDescription")
    public String pageMovieDetails(){
        return "MovieDescription";
    }

    /**
     * 电影评分
     * @return
     */
    @RequestMapping(value = "/getstar",method = RequestMethod.POST)
    @ResponseBody
    public String movieRating(HttpServletRequest request){
        //获取前端传来的评分数据
        String userId = request.getParameter("userId").trim();
        String movieId = request.getParameter("movieId").trim();
        String rating = request.getParameter("star").trim();
        long currentTimeMillis = System.currentTimeMillis();  //评分时间戳
        //封装到实体中
        MovieRating movieRating = new MovieRating();
        movieRating.setUserId(Integer.parseInt(userId));
        movieRating.setMovieId(Integer.parseInt(movieId));
        movieRating.setRating(Double.parseDouble(rating));
        movieRating.setTimestamp(currentTimeMillis);
        //实时将电影评分数据发送到kafka
        String message = userId+"_"+movieId+"_"+rating+"_"+currentTimeMillis;
        try {
            KafkaUtils.sendMessage(message);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //将该用户对该电影的评分数据写入session
        request.getSession().setAttribute("userstar",movieRating);
        return "success";
    }

    /**
     * 电影详情页点击"喜欢"按钮,收藏电影
     * @param request
     * @return
     */
    @RequestMapping(value = "/likedmovie", method = RequestMethod.POST)
    @ResponseBody
    public String userIsLikeMovie(HttpServletRequest request){
        EachUserLikedMovie eachUserLikedMovie = new EachUserLikedMovie();
        eachUserLikedMovie.setUserId(Integer.parseInt(request.getParameter("userId").trim()));
        eachUserLikedMovie.setMovieId(Integer.parseInt(request.getParameter("movieId").trim()));
        eachUserLikedMovie.setTimestamp(System.currentTimeMillis());
        //通过这个数据来判断当前行为是收藏还是取消收藏
        int isLike = Integer.parseInt(request.getParameter("boollike"));
        //isLike==0说明用户还没收藏电影，该点击行为为收藏行为,isLike==1则说明该用户已收藏该电影，该点击行为为取消收藏行为
        if (isLike==0){
            boolean bool = eachUserLikedMovieService.insert(eachUserLikedMovie);
            return bool?"success":"fail";
        }else {
            boolean bool = eachUserLikedMovieService.delete(eachUserLikedMovie);
            return bool?"success":"fail";
        }
    }


    /**
     * 添加电影评论记录到数据库
     * @param userId
     * @param movieId
     * @param content
     * @param request
     * @return
     */
    @RequestMapping(value = "/insertMovieReview",method = RequestMethod.POST)
    @ResponseBody
    public E3Result insertMovieReview(Integer userId,Integer movieId,String content,Integer sort,HttpServletRequest request){
        MovieReview movieReview = new MovieReview();
        movieReview.setUserId(userId);
        movieReview.setMovieId(movieId);
        movieReview.setContent(content.trim());
        movieReview.setCount(0);
        movieReview.setTimestamp(System.currentTimeMillis());
        movieReview.setStatus("");
        boolean bool = movieReviewService.insertMovieReview(movieReview);
        if (bool){
            //条件封装类
            CommonCondition condition = new CommonCondition();
            condition.setCategoryId(movieId); //categoryId字段用来存储了movieId
            condition.setLimitCount(sort); //limitCount字段用来存储了排序依据
            List<MovieReview> movieReviews = movieReviewService.selectMovieReviewByMovieId(condition);
            request.getSession().removeAttribute("reviews");
            request.getSession().setAttribute("reviews",movieReviews);

            //用户对该电影的评价数据
            MovieReview review = movieReviewService.selectMovieReviewByMovieIdAndUserId(userId, movieId);
            request.getSession().removeAttribute("userReview");
            request.getSession().setAttribute("userReview",review);

            return E3Result.build(200,"success",movieReviews);
        }else {
            return E3Result.build(400, "fail", null);
        }
    }

    /**
     * 更新影评的点赞数(点赞与取消点赞行为)
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUsefulCount",method = RequestMethod.POST)
    @ResponseBody
    public E3Result updateUsefulCount(HttpServletRequest request){
        //当前点赞该条评论的用户
        String specifiedUserId = request.getParameter("specifiedUserId").trim();
        //点赞数
//        Integer count = Integer.parseInt(request.getParameter("usefulCount").trim());
        //该评论对应的电影Id
        int movieId = Integer.parseInt(request.getParameter("movieId").trim());
        //发表这条评论的用户
        int userId = Integer.parseInt(request.getParameter("userId").trim());

//        System.out.println("specifiedUserId: "+specifiedUserId+"  "+"userId: "+userId+"  "+"movieId: "+movieId+"  "+"count: "+count);

        //获取这条评论的点赞者
        MovieReview movieReview = movieReviewService.selectMovieReviewByMovieIdAndUserId(userId, movieId);
        String status = movieReview.getStatus();
        int oldCount = movieReview.getCount();

        E3Result e3Result = new E3Result();//新加的代码

        //说明用户之前还没有点赞该条评论，此次点击行为是点赞行为
        if (!status.contains(specifiedUserId)) {
            movieReview.setCount(oldCount+1);
            e3Result.setStatus(200);//新加的代码
            if ("".equals(status))
                movieReview.setStatus(specifiedUserId);
            else
                movieReview.setStatus(status+","+specifiedUserId);
        }
        //说明点赞过了，该点击行为为取消点赞行为，点赞数减1，同时删除点赞字段中该用户的Id
        else if (status.contains(specifiedUserId)){
//            movieReview.setCount(oldCount-1);
//            if (specifiedUserId.equals(status))
//                movieReview.setStatus(status.replace(specifiedUserId,""));
//            else
//                movieReview.setStatus(status.replace(","+specifiedUserId,""));
            e3Result.setStatus(400);//新加的代码
        }
        //更新点赞数
        movieReviewService.updateUsefulCount(movieReview);
        e3Result.setData(movieReview);//新加的代码
        //更新成功后，并改变控件的状态
        return e3Result;
    }


    /**
     * 切换标签，查询指定电影按指定方式排序的所有评论信息
     * @param movieId
     * @param sort
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/selectMovieReviews",method = RequestMethod.POST)
    public E3Result selectMovieReviews(Integer movieId, Integer sort, HttpServletRequest request){

        //获取指定电影的所有评论
        //封装查询条件
        CommonCondition condition = new CommonCondition();
        condition.setCategoryId(movieId);
        condition.setLimitCount(sort);
        List<MovieReview> movieReviewList = movieReviewService.selectMovieReviewByMovieId(condition);
        for (MovieReview movieReview:movieReviewList)
            System.out.println(movieReview.toString());
        //将之前的评论内容从session中删除
        request.getSession().removeAttribute("reviews");
        //将新查询到的评论信息写入session
        request.getSession().setAttribute("reviews",movieReviewList);

        return E3Result.build(200,"success",movieReviewList);
    }

}