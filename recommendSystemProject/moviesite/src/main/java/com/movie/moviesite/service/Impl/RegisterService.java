package com.movie.moviesite.service.Impl;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.EachUserLikedMovie;
import com.movie.moviesite.entity.User;
import com.movie.moviesite.mapper.EachUserLikedMovieMapper;
import com.movie.moviesite.mapper.UserMapper;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterService implements com.movie.moviesite.service.RegisterService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EachUserLikedMovieMapper eachUserLikedMovieMapper;

    /**
     * 注册用户，将用户信息写入数据库
     * @param user
     * @return 返回主键ID
     */
    @Override
    public int insertUserMessage(User user) {
        //对密码进行MD5加密
        String Pwd = DigestUtils.md5DigestAsHex(user.getUserPassword().getBytes());
        //将加密后的密码替换之前的密码
        user.setUserPassword(Pwd);
        //补全用户的其他信息
        user.setRegisterTime(new Date());
        user.setLastLoginTime(new Date());
        int result = userMapper.insertUser(user);
        return result;
    }

    /**
     * 检查用户信息
     * @param email
     * @param userName
     * @return  返回主键ID
     */
    @Override
    public E3Result checkEmailAndUserName(String email, String userName) {
        List<User> byEmail = userMapper.selectByEmail(email);
        List<User> byUserName = userMapper.selectByUserName(userName);
        E3Result e3Result = new E3Result();
        if ((byEmail==null||byEmail.size()==0)&&(byUserName==null||byUserName.size()==0)){  //说明该账号还没注册过，该用户名也还没被使用过
            e3Result.setStatus(200);
        }else if (byEmail!=null&&byEmail.size()>0){  //说明该账号已注册过了
            e3Result.setStatus(400);
            e3Result.setMsg("该邮箱已被注册，不能重复注册");
        }else if(byUserName!=null&&byUserName.size()>0){  //说明该用户名已被使用
            e3Result.setStatus(400);
            e3Result.setMsg("该昵称已被使用，请换一个试试");
        }
        return e3Result;
    }

    /**
     * 检测邮箱或用户名是否已存在
     * @param param email 或 userName
     * @param type 1->userName  2->email
     * @return
     */
    @Override
    public E3Result cheEmailOrUserName(String param, int type) {

        E3Result e3Result = new E3Result();
        List<User> users = null;
        if (type==1){
            users = userMapper.selectByUserName(param);
        }else if (type==2){
            users = userMapper.selectByEmail(param);
        }else {
            e3Result.setStatus(400);
            e3Result.setMsg("数据类型错误");
            return e3Result;
        }

        if (users!=null&&users.size()>0){
            e3Result.setStatus(400);
            e3Result.setMsg("注册信息已存在，不能重复注册");
            return e3Result;
        }else {
            e3Result.setStatus(200);
            e3Result.setMsg("目标信息尚未被使用过");
            return e3Result;
        }
    }

    /**
     * 注册时选择喜欢的电影
     * @param eachUserLikedMovie
     * @return
     */
    @Override
    public boolean selectUserLikedMovies(EachUserLikedMovie eachUserLikedMovie) {
        int result = eachUserLikedMovieMapper.insert(eachUserLikedMovie);
        return result > 0;
    }
}