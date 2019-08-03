package com.good.moviemanager.controller;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.Movie;
import com.good.moviemanager.entity.QueryCondition;
import com.good.moviemanager.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * 获取所有的电影信息
     * @return
     */
    @RequestMapping(value = "/movie/selectAllMovies")
    @ResponseBody
    public Object selectAllMovies(@RequestParam(name = "page",required = false,defaultValue = "1") int pageNum,
                                  @RequestParam(name = "rows",required = false,defaultValue = "5") int pageSize,
                                  QueryCondition movie){
        try {
            if (movie.getMovieName()!=null&&!"".equals(movie.getMovieName())) {
                String movieName = URLDecoder.decode(movie.getMovieName(), "UTF-8");
                movie.setMovieName(movieName);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PageInfo<Movie> moviePageInfo = movieService.selectAllMovies(pageNum, pageSize, movie);
        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("rows",moviePageInfo.getList()); //电影记录集合
        mapResult.put("total",moviePageInfo.getTotal()); //电影记录总数
        mapResult.put("success",true);
        return mapResult;
    }

    /**
     * 获取指定电影信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/topKHistoryHeatMovieList")
    @ResponseBody
    public Object selectTopKHistoryHeatMovies(@RequestParam(name = "page",required = false,defaultValue = "1") int pageNum,
                                 @RequestParam(name = "rows",required = false,defaultValue = "5") int pageSize){
        PageInfo<Movie> pageInfo = movieService.selectTopKHistoryHeatMovies(pageNum, pageSize);
        Map<String,Object> mapResult = new HashMap<>();
        if (pageInfo.getTotal()>=pageSize) {
            mapResult.put("rows", pageInfo.getList().subList(0, pageSize)); //记录集合
            if (pageInfo.getTotal()>10)
                mapResult.put("total", 10); //记录总数
            else
                mapResult.put("total",pageInfo.getTotal());
        }else {
            mapResult.put("rows", pageInfo.getList()); //记录集合
            mapResult.put("total", pageInfo.getTotal()); //记录总数
        }
        mapResult.put("success",true);
        return mapResult;
    }

    /**
     * 插入一条电影信息
     * @param movie
     * @return
     */
    @RequestMapping(value = "/movie/addOneMovie",method = RequestMethod.POST)
    @ResponseBody
    public Object insertOneMovie(Movie movie){
        boolean bool = movieService.insert(movie);
        Map<String,Object> mapResult = null;
        if (bool){
            mapResult = new HashMap<>();
            mapResult.put("success",true);
        }
        return mapResult;
    }

    /**
     * 更新电影信息
     * @param movie
     * @return
     */
    @RequestMapping(value = "/movie/updateOneMovie",method = RequestMethod.POST)
    @ResponseBody
    public Object updateOneMovie(Movie movie){
        boolean bool = movieService.updateOneMovieSelective(movie);
        Map<String,Object> mapResult = null;
        if (bool){
            mapResult = new HashMap<>();
            mapResult.put("success",true);
        }
        return mapResult;
    }

    /**
     * 批量删除指定的电影数据
     * @param movieIds
     * @return
     */
    @RequestMapping(value = "/movie/deleteOneMovie",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteOneMovie(String movieIds){
        Map<String,Object> mapResult = new HashMap<>();
        if (movieIds!=null&&!"".equals(movieIds)){
            String[] split = movieIds.split(",");
            for (String str:split){
                movieService.deleteOneMovie(Integer.parseInt(str.trim()));
            }
            mapResult.put("success",true);
        }else{
            mapResult.put("success",false);
        }
        return mapResult;
    }

}
