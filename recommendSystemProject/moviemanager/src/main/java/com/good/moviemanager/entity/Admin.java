package com.good.moviemanager.entity;

import java.io.Serializable;

/**
 * 系统管理员
 */
public class Admin implements Serializable{

    private int adminId;
    private String adminName;
    private String adminPassword;

    public Admin(){}

    public Admin(String adminName,String adminPassword){
        this.adminName = adminName;
        this.adminPassword = adminPassword;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
