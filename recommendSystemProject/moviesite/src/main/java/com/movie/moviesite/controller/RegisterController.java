package com.movie.moviesite.controller;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.EachUserLikedMovie;
import com.movie.moviesite.entity.Movie;
import com.movie.moviesite.entity.User;
import com.movie.moviesite.service.EachUserLikedMovieService;
import com.movie.moviesite.service.MovieService;
import com.movie.moviesite.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * 注册控制器
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private EachUserLikedMovieService eachUserLikedMovieService;

    /**
     * 进入注册页面，
     * 进入后立即弹出电影选择窗，供待注册用户选择喜欢的电影,以初始化一些用户数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/page/register")
    public String RegisterPage(HttpServletRequest request){
        //按历史热度降序排序选出热度前10的电影作为供用户注册时选择的电影
        List<Movie> movieList = movieService.selectTopKMovies(10);
        //将这些电影数据写入session中，方便后续使用
        request.getSession().setAttribute("userSelectLikedMoviesWhenRegister",movieList);
        //返回注册页面
        return "register";
    }

    /**
     * 检查邮箱或用户名是否已存在（当输入框失去焦点时即触发检查操作）
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "/customer/check/{param}/{type}")
    @ResponseBody
    public E3Result checkEmailOrUserNameIsExist(@PathVariable String param,@PathVariable int type){
        System.out.println("我来自邮箱或用户名检查操作："+param+"-"+type);
        try {
            //后端进行decode是为了避免前端传中文值而出现乱码（用户名可能是中文的）
            String parameter = URLDecoder.decode(param, "UTF-8");
            E3Result e3Result = registerService.cheEmailOrUserName(parameter, type);
            return e3Result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 提交注册信息前检查邮箱和用户名是否存在
     * @param userName
     * @param email
     * @return
     */
    @RequestMapping(value = "/customer/checkboth/{userName}/{email}")
    @ResponseBody
    public E3Result checkEmailAndUserName(@PathVariable String userName,@PathVariable String email){
        System.out.println("我来自用户名且邮箱检查操作："+ userName+"-"+email);
        try {
            //后端进行decode是为了避免前端传中文值而出现乱码（用户名可能是中文的）
            String nameParameter = URLDecoder.decode(userName, "UTF-8");
            E3Result e3Result = registerService.checkEmailAndUserName(nameParameter, email);
            return e3Result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 注册用户（在检查完所有的输入项之后）
     * @param user
     * @param request
     * @return
     */
    @RequestMapping(value = "/customer/register",method = RequestMethod.POST)
    @ResponseBody
    public E3Result register(User user, HttpServletRequest request){
        System.out.println("我来自注册操作："+user.getUserName()+"-"+user.getUserPassword()+"-"+user.getEmail());
        //这里获取userId是为了用于选取用户喜欢的电影
        int userId = registerService.insertUserMessage(user);  //该方法返回的就是该记录的主键ID
        if (userId>0){
            //将userId写入session，在选择喜欢的电影时才能获取到userId
            request.getSession().setAttribute("userId",userId);
            return E3Result.build(200,"注册成功");
        }else {
            return E3Result.build(400,"注册失败");
        }
    }

    /**
     * 用户注册后选择喜欢的电影，并将喜欢的电影写入数据库
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(value = "/customer/register/movieSubmit",method = RequestMethod.POST)
    @ResponseBody
    public String selectUserLikedMovies(String ids,HttpServletRequest request){
        //用户还没有选择电影
        if (ids==null||ids=="") return "fail";
        else {
            //从session中获取userId
            int userId = (Integer) (request.getSession().getAttribute("userId"));
            //对ids进行切分，得到每部电影的id
            String[] split = ids.trim().split(",");
            for (String movieId:split){
                EachUserLikedMovie eachUserLikedMovie = new EachUserLikedMovie(userId,Integer.parseInt(movieId.trim()),System.currentTimeMillis());
                eachUserLikedMovieService.insert(eachUserLikedMovie);
            }
            return "ok";
        }
    }

}
