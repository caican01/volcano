package com.movie.moviesite.service;

import com.movie.moviesite.entity.User;

public interface UserService {

    /**
     * 根据用户Id查询唯一用户
     * @param userId
     * @return
     */
    User selectByUserId(Integer userId);

    /**
     * 修改用户信息(用户名和密码)
     * @param user
     * @return
     */
    boolean updateUserMessage(User user);

}
