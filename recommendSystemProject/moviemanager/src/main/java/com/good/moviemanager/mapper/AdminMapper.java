package com.good.moviemanager.mapper;

import com.good.moviemanager.entity.Admin;

public interface AdminMapper {

    /**
     * 查询管理员记录，用作登录
     * @param adminName
     * @return
     */
    Admin selectByAdminName(String adminName);

}
