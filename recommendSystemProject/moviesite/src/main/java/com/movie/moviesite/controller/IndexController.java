package com.movie.moviesite.controller;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.Category;
import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.RecentMovieHeatRankedList;
import com.movie.moviesite.entity.SelectCondition;
import com.movie.moviesite.service.CategoryService;
import com.movie.moviesite.service.MovieService;
import com.movie.moviesite.service.RecentMovieHeatRankedListService;
import com.movie.moviesite.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 电影页以及搜索结果页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RecentMovieHeatRankedListService recentMovieHeatRankedListService;

    /**
     * 进入电影主页界面
     * @param request
     * @return
     */
    @RequestMapping(value = "/index")
    public String toIndexPage(HttpServletRequest request){
        //获取所有的电影类别
        List<Category> categoryList = categoryService.selectAllCategory();
        //获取所有的电影（默认每次取出20条，sql语句那里做了限制，如果传值过去，那就按传过去的值取出相应的条数，否则默认取20条）
        //进入电影界面，默认加载电影是按历史热度降序排序的
        //默认选中的是"全部"标签，即category=all
        String category = "all";
        //创建封装条件的对象
        SelectCondition condition = new SelectCondition();
        condition.setCategory(category);  //标签
        condition.setOrderColumn("history_heat");  //排序依据
        condition.setLimitCount(0);  //每次取出的记录数目
        List<Movie> movieList = movieService.selectByCondition(condition);

        //分别获取历史热度及近期热度前5的电影
        List<Movie> historyHeatMovieList = movieService.selectTopKMovies(5);
        List<RecentMovieHeatRankedList> recentHeatMovieList = recentMovieHeatRankedListService.selectTopKRecentHeatMovies(5);

        //写入session
        request.getSession().removeAttribute("category");
        request.getSession().setAttribute("category",categoryList);//所有电影类别
        request.getSession().removeAttribute("movie");
        request.getSession().setAttribute("movie",movieList);//电影
        request.getSession().removeAttribute("historyHeatMovie");
        request.getSession().setAttribute("historyHeatMovie",historyHeatMovieList);//历史热度电影
        request.getSession().removeAttribute("recentHeatMovie");
        request.getSession().setAttribute("recentHeatMovie",recentHeatMovieList);//近期热度电影
        //返回电影界面
        return "index";
    }

    /**
     * 加载更多电影（通过电影类别标签、页面现有呈现的电影数目、电影的排序依据这三个条件来加载更多电影）
     * @param request
     * @return
     */
    @RequestMapping(value = "/loadingmore",method = RequestMethod.POST)
    @ResponseBody
    public E3Result loadMore(HttpServletRequest request){

        //电影类型id与电影类型文本的映射
//        Map<Integer, String> map = CommonUtils.CategoryDictionary();
//
//        System.out.println("类型："+request.getParameter("type"));
//        System.out.println("数量："+request.getParameter("limitCount"));
//        System.out.println("排序依据："+request.getParameter("orderColumn"));

        SelectCondition condition = new SelectCondition();
        //获取电影类别
//        condition.setCategory(map.get(request.getParameter("type").trim()));
        condition.setCategory(request.getParameter("type"));
        //获取需要跳过的电影数量
        condition.setLimitCount(Integer.parseInt(request.getParameter("limitCount")));
        //获取加载电影的排序依据
        condition.setOrderColumn(request.getParameter("orderColumn"));
        List<Movie> movieList = movieService.selectByCondition(condition);
        if (movieList==null||movieList.size()==0){
            return E3Result.build(400,"加载电影出错");
        }else {
            return E3Result.ok(movieList);
        }
    }

    /**
     * 根据电影类型以及排序依据选择排序电影
     * @param request
     * @return
     */
    @RequestMapping(value = "/typesortmovie",method = RequestMethod.POST)
    @ResponseBody
    public E3Result showMoviesByTypeAndOrderColumn(HttpServletRequest request){

//        Map<Integer, String> map = CommonUtils.CategoryDictionary();

        //创建封装条件的对象，数据库中并不存在这样一张表，也不需要这样一张表
        SelectCondition condition = new SelectCondition();
        //获取电影类别
//        condition.setCategory(map.get(request.getParameter("type").trim()));
        condition.setCategory(request.getParameter("type"));
        //获取需要跳过的电影数量
        condition.setLimitCount(Integer.parseInt(request.getParameter("limitCount").trim()));
        //获取加载电影的排序依据
        condition.setOrderColumn(request.getParameter("orderColumn"));
        List<Movie> movieList = movieService.selectByCondition(condition);
        if (movieList==null||movieList.size()==0){
            return E3Result.build(400,"电影排序出错");
        }else {
            return E3Result.ok(movieList);
        }
    }

    /**
     * 根据电影名进行模糊搜索，结果显示在智能提示框中
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchLikeName",method = RequestMethod.POST)
    @ResponseBody
    public E3Result selectMoviesLikeName(HttpServletRequest request){
        //获取搜索框的输入
        String movieName = request.getParameter("search_text");
        //得到模糊查询的结果
        List<Movie> movieList = movieService.selectLikeMovieName(movieName);
        if (movieList==null||movieList.size()==0)
            return E3Result.build(400,"无法搜索到电影");
        else
            return E3Result.ok(movieList);
    }

    /**
     * 根据电影名进行搜索，结果显示在搜索结果页
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchByName",method = RequestMethod.POST)  //**这个接口在前端还没有对应的处理，到时候记得加上去**
    @ResponseBody
    public E3Result selectMoviesByName(HttpServletRequest request){
        //分别获取历史热度及近期热度前5的电影
        List<Movie> historyHeatMovieList = movieService.selectTopKMovies(5);
        List<RecentMovieHeatRankedList> recentHeatMovieList = recentMovieHeatRankedListService.selectTopKRecentHeatMovies(5);

        request.getSession().removeAttribute("historyHeatMovie");
        request.getSession().setAttribute("historyHeatMovie",historyHeatMovieList);//历史热度电影
        request.getSession().removeAttribute("recentHeatMovie");
        request.getSession().setAttribute("recentHeatMovie",recentHeatMovieList);//近期热度电影

        //获取搜索结果框的输入
        String movieName = request.getParameter("search_text");
        if (movieName==null||movieName=="") {
            return null;
        } else {
            //得到查询的电影结果
            List<Movie> movieList = movieService.selectLikeMovieName(movieName);
            request.getSession().removeAttribute("movieSearchResult");
            request.getSession().setAttribute("movieSearchResult",movieList);
            if (movieList==null||movieList.size()==0) {
                return E3Result.build(400, "无法搜索到该电影");
            } else {
                return E3Result.ok(movieList);
            }
        }
    }

    /**
     * 进入搜索结果页
     * @return
     */
    @RequestMapping(value = "/searchResult")
    public String pageResult(HttpServletRequest request){

        //分别获取历史热度及近期热度前5的电影
        List<Movie> historyHeatMovieList = movieService.selectTopKMovies(5);
        List<RecentMovieHeatRankedList> recentHeatMovieList = recentMovieHeatRankedListService.selectTopKRecentHeatMovies(5);

        request.getSession().removeAttribute("historyHeatMovie");
        request.getSession().setAttribute("historyHeatMovie",historyHeatMovieList);//历史热度电影
        request.getSession().removeAttribute("recentHeatMovie");
        request.getSession().setAttribute("recentHeatMovie",recentHeatMovieList);//近期热度电影

        //返回搜索结果页
        return "result";
    }

}