package com.good.moviemanager.service;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 不添加条件子句获取所有的用户，用于导出操作
     * @return
     */
    List<User> selectAllUsersNoCondition();

    /**
     * 按条件查询用户
     * @param pageNum
     * @param pageSize
     * @param user
     * @return
     */
    PageInfo<User> selectAllByCondition(int pageNum, int pageSize, User user);

    /**
     * 获取指定的用户
     * @param pageNum
     * @param pageSize
     * @param userName
     * @return
     */
    PageInfo<User> selectOneUser(int pageNum, int pageSize, String userName);

    /**
     * 删除指定用户
     * @param userId
     * @return
     */
    boolean deleteOneUser(Integer userId);

    /**
     * 更新用户登录权限
     * @param user
     * @return
     */
    boolean updateUserLoginAuthority(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    boolean updateUserMessage(User user);

    /**
     * 批量插入用户数据
     * @param userList
     * @return
     */
    boolean batchInsertUsers(List<User> userList);

}