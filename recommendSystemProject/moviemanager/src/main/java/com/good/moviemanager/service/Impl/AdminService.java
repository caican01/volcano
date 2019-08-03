package com.good.moviemanager.service.Impl;

import com.good.moviemanager.common.E3Result;
import com.good.moviemanager.entity.Admin;
import com.good.moviemanager.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminService implements com.good.moviemanager.service.AdminService{

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public E3Result adminLogin(Admin adminParam) {
        Admin admin = adminMapper.selectByAdminName(adminParam.getAdminName());
        if (admin!=null){
            if (adminParam.getAdminPassword().equals(admin.getAdminPassword())){
                return E3Result.build(200,"登录成功",admin);
            }else {
                return E3Result.build(400,"密码错误");
            }
        }else {
            return E3Result.build(400,"账号错误");
        }
    }
}
