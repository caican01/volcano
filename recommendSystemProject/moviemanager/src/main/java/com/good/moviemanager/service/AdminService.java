package com.good.moviemanager.service;

import com.good.moviemanager.common.E3Result;
import com.good.moviemanager.entity.Admin;

public interface AdminService {

    /**
     * 管理员登录判断
     * @param admin
     * @return
     */
    E3Result adminLogin(Admin admin);

}
