package com.good.moviemanager.controller;

import com.good.moviemanager.entity.Movie;
import com.good.moviemanager.service.MovieService;
import com.good.moviemanager.util.ImportAndExportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入导出电影数据
 */
@Controller
public class MovieDataImportAndExportController {

    @Autowired
    private MovieService movieService;

    /**
     * 电影数据导出
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportMovieExcel")
    @ResponseBody
    public void exportMovieExcel(HttpServletResponse response){
        List<Movie> movieList = movieService.selectAllMoviesNoCondition();
        //当表不为空时，导出表的数据到excel文件中
        if (movieList!=null&&movieList.size()>=1)
            ImportAndExportUtils.exportExcel(movieList, null, null, Movie.class, "movieList.xls", false, response);
        //当表为空时，也执行导出操作，不过此时传入的List为空集合，得到的excel文件中也没有数据
        else
            ImportAndExportUtils.exportExcel(Arrays.asList(), null, null, Movie.class, "movieList.xls", false, response);

    }

    /**
     * 电影数据导入
     * @return
     */
    @RequestMapping(value = "/importMovieExcel",method = RequestMethod.POST)
    @ResponseBody
    public Object importMovieExcel(@RequestParam("movieDataImportFile") MultipartFile multipartFile){
        Map<String,Object> map = new HashMap<>();
        List<Movie> movieList = ImportAndExportUtils.importExcel(multipartFile, 1, 1, Movie.class);
        boolean bool = movieService.batchInsertMovies(movieList);
        if (bool) {
            map.put("success",true);
            return map;
        } else {
            map.put("success",false);
            return map;
        }
    }

}