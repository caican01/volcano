package com.good.moviemanager.controller;

import com.github.pagehelper.PageInfo;
import com.good.moviemanager.entity.User;
import com.good.moviemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有的用户信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/user/selectAllUsers")
    @ResponseBody
    public Object selectAllUsers(@RequestParam(name = "page",required = false,defaultValue = "1") int pageNum,
                                 @RequestParam(name = "rows",required = false,defaultValue = "5") int pageSize,
                                 User user){
        //后端进行decode是为了避免前端传中文值而出现乱码（用户名可能是中文的）
        try {
            if (user.getUserName()!=null&&!"".equals(user.getUserName())){
                String userName = URLDecoder.decode(user.getUserName(), "UTF-8");
                user.setUserName(userName);
            }
            if (user.getEmail()!=null&&!"".equals(user.getEmail())) {
                String email = URLDecoder.decode(user.getEmail(), "UTF-8");
                user.setEmail(email);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        PageInfo<User> userPageInfo = userService.selectAllByCondition(pageNum, pageSize, user);
        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("rows",userPageInfo.getList());
        mapResult.put("total",userPageInfo.getTotal());
        mapResult.put("success",true);
        return mapResult;
    }

    /**
     * 查询指定用户
     * @param pageNum
     * @param pageSize
     * @param userName
     * @return
     */
    @RequestMapping(value = "/user/selectOneUser")
    @ResponseBody
    public Object selectOnUser(@RequestParam(name = "page",required = false,defaultValue = "1") int pageNum,
                               @RequestParam(name = "rows",required = false,defaultValue = "5") int pageSize,
                               @RequestParam(name = "userName") String userName){
        PageInfo<User> pageInfo = userService.selectOneUser(pageNum, pageSize, userName);
        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("rows",pageInfo.getList());
        mapResult.put("total",pageInfo.getTotal());
        mapResult.put("success",true);
        return mapResult;
    }

    /**
     * 批量删除指定用户信息
     * @param userIds
     * @return
     */
    @RequestMapping(value = "/user/deleteOneUser")
    @ResponseBody
    public Object deleteOneUser(String userIds){
        Map<String,Object> mapResult = new HashMap<>();
        if (userIds!=null&&!"".equals(userIds)){
            String[] split = userIds.split(",");
            for (String str:split){
                userService.deleteOneUser(Integer.parseInt(str.trim()));
            }
            mapResult.put("success",true);
        }else {
            mapResult.put("success",false);
        }
        return mapResult;
    }

    /**
     * 修改用户登录权限
     * @param users
     * @return
     */
    @RequestMapping(value = "/user/updateUserAuthority",method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserAuthority(String users){
        Map<String,Object> mapResult = new HashMap<>();
        if (users==null||"".equals(users)){
            mapResult.put("success",false);
            return mapResult;
        }else {
            String[] split = users.split(",");
            for (String str : split) {
                String[] idAndAuth = str.split(":");
                User user = new User();
                user.setUserId(Integer.parseInt(idAndAuth[0].trim()));
                int authority = Integer.parseInt(idAndAuth[1].trim());
                if (authority == 1)
                    user.setAuthority(0); //禁用
                else if (authority == 0)
                    user.setAuthority(1); //启用
                userService.updateUserLoginAuthority(user);
            }
            mapResult.put("success",true);
            return mapResult;
        }
    }

    /**
     * 修改用户密码
     * @param userId
     * @param userPassword
     * @return
     */
    @RequestMapping(value = "/user/updateUserMessage",method = RequestMethod.POST)
    @ResponseBody
    public Object updateUserMessage(Integer userId,String userPassword){
        Map<String,Object> mapResult = new HashMap<>();
        User user = new User();
        user.setUserId(userId);
        user.setUserPassword(userPassword);
        boolean bool = userService.updateUserMessage(user);
        if (bool){
            mapResult.put("success",true);
        }else {
            mapResult.put("success",false);
        }
        return mapResult;
    }

}
