package com.good.moviemanager.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.User;
import com.good.moviemanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements com.good.moviemanager.service.UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAllUsersNoCondition() {
        List<User> userList = userMapper.selectAllUsersNoCondition();
        if (userList==null||userList.size()<1)
            return null;
        else
            return userList;
    }

    @Override
    public PageInfo<User> selectAllByCondition(int pageNum, int pageSize, User user) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> users = userMapper.selectAllUsersByCondition(user);
        PageInfo<User> info = new PageInfo<>(users);
        return info;
    }

    @Override
    public PageInfo<User> selectOneUser(int pageNum,int pageSize,String userName) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> users = userMapper.selectOneUser(userName);
        PageInfo<User> info = null;
        if (users!=null&&users.size()==1) {
            info = new PageInfo<>(users);
        }else if (users!=null&&users.size()>1){
            User user = users.get(0);
            info = new PageInfo<>(Arrays.asList(user));
        }
        return info;
    }

    @Override
    public boolean deleteOneUser(Integer userId) {
        int result = userMapper.deleteOneUser(userId);
        return result > 0;
    }

    @Override
    public boolean updateUserLoginAuthority(User user) {
        int result = userMapper.updateUserLoginAuthority(user);
        return result > 0;
    }

    @Override
    public boolean updateUserMessage(User user) {
        int result = 0;
        if (user!=null&&user.getUserPassword()!=null&&!"".equals(user.getUserPassword())&&user.getUserId()>0){
            user.setUserPassword(DigestUtils.md5DigestAsHex(user.getUserPassword().trim().getBytes()));
            result = userMapper.updateUserMessage(user);
        }else{
            result = 0;
        }
        return result > 0;
    }

    @Override
    public boolean batchInsertUsers(List<User> userList) {
        if (userList!=null&&userList.size()>=1) {
            for (User user : userList) {
                if (user!=null) {
                    //判断导入的数据是否已经存在数据库中，避免重复导入
                    User selectByUserId = userMapper.selectByPrimaryKey(user.getUserId());
                    //不存在数据库中，则导入，否则不导入
                    if (selectByUserId==null)
                        userMapper.insertOneUser(user);
                }
            }
            return true;
        }
        return false;
    }
}
