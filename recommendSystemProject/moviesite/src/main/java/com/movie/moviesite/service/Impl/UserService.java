package com.movie.moviesite.service.Impl;

import com.movie.moviesite.entity.User;
import com.movie.moviesite.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * 用户查找
 * 用户信息修改
 */
@Service
public class UserService implements com.movie.moviesite.service.UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByUserId(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean updateUserMessage(User user) {
        String userPassword = user.getUserPassword();
        if (userPassword!=null&&!"".equals(userPassword)){
            String pwd = DigestUtils.md5DigestAsHex(userPassword.trim().getBytes());
            user.setUserPassword(pwd);
        }
        int result = userMapper.updateUserNameAndUserPassword(user);
        return result > 0;
    }
}
