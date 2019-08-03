package com.movie.moviesite.mapper;

import com.movie.moviesite.entity.User;
import java.util.List;

/**
 * UserMapper,处理对User表的操作
 */
public interface UserMapper {

    /**
     * 用户注册,
     * 向数据库中插入新添加的用户
     * @param user
     * @return 插入的记录的主键ID
     */
    int insertUser(User user);

    /**
     *
     * @param user
     * @return 返回插入的记录的主键ID
     */
    int insertSelective(User user);

    /**
     * 根据主键查询
     * @param userId
     * @return
     */
    User selectByPrimaryKey(Integer userId);

    /**
     * 根据用户名查询
     * @param email
     * @return
     */
    List<User> selectByUserName(String email);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    List<User> selectByEmail(String email);

    /**
     * 修改用户所有信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 修改用户名或密码
     * @param user
     * @return
     */
    int updateUserNameAndUserPassword(User user);

    /**
     * 选择性修改用户信息
     * @param user
     * @return
     */
    int updateUserSelective(User user);

}
