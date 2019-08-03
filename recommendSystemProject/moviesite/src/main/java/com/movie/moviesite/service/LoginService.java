package com.movie.moviesite.service;

import com.movie.moviesite.common.E3Result;
import com.movie.moviesite.entity.User;

public interface LoginService {

    /**
     *根据主键查询用户
     * @param userId
     * @return
     */
    User selectByUserId(Integer userId);

    /**
     * 用户登录，
     * 通过email作为账号登录，
     * 查询该账号是否存在，若存在则进一步检验密码是否正确，若正确，返回该用户记录user,若不正确，返回null;
     * 若该账号不存在，返回null;
     * 简单表述为：账号存在且密码正确，登录成功，返回user;账号不存在或密码错误，即账号或密码错误，登录失败，返回null。
     * @param email
     * @param password
     * @return
     */
    E3Result userLogin(String email, String password);

}
