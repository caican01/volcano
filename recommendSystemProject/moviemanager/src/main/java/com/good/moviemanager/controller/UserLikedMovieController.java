package com.good.moviemanager.controller;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.EachUserLikedMovie;
import com.good.moviemanager.entity.QueryCondition;
import com.good.moviemanager.service.EachUserLikedMovieService;
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
public class UserLikedMovieController {

    @Autowired
    private EachUserLikedMovieService eachUserLikedMovieService;

    @RequestMapping(value = "/getUsersLikedMovies")
    @ResponseBody
    public Object selectUsersLikedMovies(@RequestParam(name = "page",required = false,defaultValue = "1") int pageNum,
                                         @RequestParam(name = "rows",required = false,defaultValue = "5") int pageSize,
                                         @RequestParam(name = "type",required = false) String type,
                                         @RequestParam(name = "orderColumn",required = false) String orderColumn){
        Map<String,Object> map = new HashMap<>();
        QueryCondition condition = new QueryCondition();
        try {
            if (type!=null&&!"".equals(type)) {
                /**
                 * URLDecoder.decode(condition.getTypeList(), "UTF-8");是为了防止前端传回中文值而出现乱码现象，
                 * 所以前端传到后台的数据要先在前端按UTF-8格式编码，
                 * 传到后台后再进行解码，
                 * 防止出现乱码
                 */
                String typeList = URLDecoder.decode(type, "UTF-8");
                condition.setType(typeList);
            }else if (orderColumn!=null&&!"".equals(orderColumn)){
                String order = URLDecoder.decode(orderColumn, "UTF-8");
                condition.setOrderColumn(order);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PageInfo<EachUserLikedMovie> info = eachUserLikedMovieService.selectUsersLikedMovies(pageNum, pageSize, condition);
        map.put("rows",info.getList());
        map.put("total",info.getTotal());
        map.put("success",true);
        return map;
    }

}