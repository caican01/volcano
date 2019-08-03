package com.movie.moviesite.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
public class User implements Serializable{

    private int userId;//用户Id
    private String userName;//用户名
    private String userPassword;//用户密码
    private String email;//邮箱账号
    private Date registerTime;//注册时间
    private Date lastLoginTime;//上次登录时间
    private int authority = 1;//用户登录权限，0->无权限登录，1->有权限登录(数据库值默认为1)

    public User(){}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }
}
