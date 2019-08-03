package com.good.moviemanager.controller;

import com.good.moviemanager.common.E3Result;
import com.good.moviemanager.entity.Admin;
import com.good.moviemanager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 进入登录页面
     * @return
     */
    @RequestMapping(value = "/login.page")
    public String pageLogin(){
        return "login";
    }

    /**
     * 管理员登录判断
     * @param admin
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/login",method = RequestMethod.POST)
    @ResponseBody
    public Object adminLogin(Admin admin, HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        E3Result e3Result = adminService.adminLogin(admin);
        Admin adminRes = (Admin) e3Result.getData();
        if (e3Result.getStatus()==200) {
            request.getSession().setAttribute("admin", adminRes);
            map.put("success", true);
        }else
            map.put("success",false);
        return map;
    }

    /**
     * 管理员退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/toLogout")
    public String adminLogout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "login";
    }

    /**
     * 管理员修改密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/modify/pwd")
    @ResponseBody
    public String modifyAdminPwd(HttpServletRequest request){
        return null;
    }

    /**
     * 进入管理系统主页
     * @return
     */
    @RequestMapping(value = "/toIndex")
    public String index(){
        return "index";
    }

}
