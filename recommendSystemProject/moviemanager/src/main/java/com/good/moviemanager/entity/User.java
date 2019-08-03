package com.good.moviemanager.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import org.springframework.util.DigestUtils;
import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

    @Excel(name = "ID",orderNum = "1")
    private int userId;//用户Id
    @Excel(name = "昵称",orderNum = "2")
    private String userName;//用户名
    @Excel(name = "密码",orderNum = "3")
    private String userPassword;//用户密码
    @Excel(name = "邮箱",orderNum = "4")
    private String email;//邮箱账号
    @Excel(name = "注册时间",orderNum = "5",importFormat = "yyyy-MM-dd",format = "yyyy-MM-dd")
    private Date registerTime;//注册时间
    @Excel(name = "上次登录时间",orderNum = "6",importFormat = "yyyy-MM-dd",format = "yyyy-MM-dd")
    private Date lastLoginTime;//上次登录时间
    @Excel(name = "登录权限",orderNum = "7")
    private int authority;//用户登录权限，0->无权限登录，1->有权限登录

    public User(){}

    public User(int userId,String userName,String userPassword,String email,Date registerTime,Date lastLoginTime,int authority){
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.email = email;
        this.registerTime = registerTime;
        this.lastLoginTime = lastLoginTime;
        this.authority = authority;
    }

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

    @Override
    public String toString() {
        return getUserId()+"_"+getUserName()+"_"+getEmail()+"_"+ DigestUtils.md5DigestAsHex(getUserPassword().getBytes())+"_"+getRegisterTime()+"_"+getLastLoginTime();
    }
}

