package com.good.moviemanager.mapper;

import com.good.moviemanager.entity.User;
import java.util.List;

public interface UserMapper {

    /**
     * 无条件子句查找全部用户
     * @return
     */
    List<User> selectAllUsersNoCondition();

    /**
     * 按条件查找用户
     * @param user
     * @return
     */
    List<User> selectAllUsersByCondition(User user);

    /**
     * 根据主键查找记录
     * @param userId
     * @return
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * 根据用户Id查找唯一用户信息
     * @param userName
     * @return
     */
    List<User> selectOneUser(String userName);

    /**
     * 根据用户Id删除指定用户
     * @param userId
     * @return
     */
    int deleteOneUser(Integer userId);

    /**
     * 修改用户登录权限，1->允许登录,0->禁止登录
     * 该项值默认为0
     * @param user
     * @return
     */
    int updateUserLoginAuthority(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUserMessage(User user);

    /**
     * 插入一条用户记录
     * @param user
     * @return
     */
    int insertOneUser(User user);
}