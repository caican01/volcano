package com.movie.moviesite.service.Impl;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.User;
import com.movie.moviesite.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.List;

@Service
public class LoginService implements com.movie.moviesite.service.LoginService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByUserId(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public E3Result userLogin(String email, String password) {
        List<User> users = userMapper.selectByUserName(email);
        E3Result e3Result = new E3Result();
        if (users!=null&&users.size()==1){  //说明存在唯一账号
            User user = users.get(0);
            //账号对了，进一步检测密码是否正确
            if (DigestUtils.md5DigestAsHex(password.trim().getBytes()).equals(user.getUserPassword())){
                if(user.getAuthority()==1) {
                    e3Result.setStatus(200);
                    e3Result.setMsg("登录成功");
                    e3Result.setData(user);
                }else {
                    e3Result.setStatus(400);
                    e3Result.setMsg("账号被限制登录");
                }
            }else {
                e3Result.setStatus(400);
                e3Result.setMsg("密码错误");
            }
        }else if (users==null||users.size()<1){
            e3Result.setStatus(400);
            e3Result.setMsg("该账号不存在");
        }else {
            e3Result.setStatus(400);
            e3Result.setMsg("账号错误");
        }
        return e3Result;
    }
}