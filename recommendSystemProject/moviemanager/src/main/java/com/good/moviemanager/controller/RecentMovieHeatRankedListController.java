package com.good.moviemanager.controller;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.RecentMovieHeatRankedList;
import com.good.moviemanager.service.RecentMovieHeatRankedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RecentMovieHeatRankedListController {

    @Autowired
    private RecentMovieHeatRankedListService recentMovieHeatRankedListService;

    /**
     * 获取近期热度前10的电影
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getTopKRecentHeatMovies")
    public Object selectTopKRecentHeatMovies(@RequestParam(name = "page",required = false,defaultValue = "1") int pageNum,
                                             @RequestParam(name = "rows",required = false,defaultValue = "5") int pageSize){
        Map<String,Object> map = new HashMap<>();
        PageInfo<RecentMovieHeatRankedList> info = recentMovieHeatRankedListService.selectAll(pageNum, pageSize);
        map.put("rows",info.getList());
        map.put("total",info.getTotal());
        map.put("success",true);
        return map;
    }

}
