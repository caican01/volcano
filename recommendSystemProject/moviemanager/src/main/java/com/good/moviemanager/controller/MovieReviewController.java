package com.good.moviemanager.controller;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.MovieReview;
import com.good.moviemanager.entity.QueryCondition;
import com.good.moviemanager.service.MovieReviewService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MovieReviewController {

    @Autowired
    private MovieReviewService movieReviewService;

    /**
     * 查询电影评论
     * @param pageNum
     * @param pageSize
     * @param type
     * @param orderColumn
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMovieReviews")
    public Object selectAllMovieReviews(@RequestParam(name = "page",required = false,defaultValue = "1") int pageNum,
                                        @RequestParam(name = "rows",required = false,defaultValue = "10") int pageSize,
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
                condition.setType(URLDecoder.decode(type, "UTF-8"));
            }
            if (orderColumn!=null&&!"".equals(orderColumn)){
                condition.setOrderColumn(URLDecoder.decode(orderColumn, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PageInfo<MovieReview> info = movieReviewService.selectAllMovieReviews(pageNum, pageSize, condition);
        map.put("rows",info.getList());
        map.put("total",info.getTotal());
        map.put("success",true);
        return map;
    }

}
