package com.good.moviemanager.controller;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.MoviesRecommendForAllUsers;
import com.good.moviemanager.entity.QueryCondition;
import com.good.moviemanager.service.MoviesRecommendForAllUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MoviesRecommendForAllUsersController {

    @Autowired
    private MoviesRecommendForAllUsersService moviesRecommendForAllUsersService;

    /**
     * 获取为用户推荐的电影
     * @param pageNum
     * @param pageSize
     * @param userName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMoviesRecommendForAllUsers")
    public Object selectAllMoviesRecommendForUsers(@RequestParam(name = "page",required = false,defaultValue = "1") int pageNum,
                                                   @RequestParam(name = "rows",required = false,defaultValue = "5") int pageSize,
                                                   @RequestParam(name = "userName",required = false) String userName){
        String name = null;
        try {
            if (userName!=null&&!"".equals(userName)){
                name = URLDecoder.decode(userName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String,Object> map = new HashMap<>();
        PageInfo<MoviesRecommendForAllUsers> info = moviesRecommendForAllUsersService.selectAllMoviesRecommendForUsers(pageNum, pageSize, name);
        map.put("rows",info.getList());
        map.put("total",info.getTotal());
        map.put("success",true);
        return map;
    }

}
