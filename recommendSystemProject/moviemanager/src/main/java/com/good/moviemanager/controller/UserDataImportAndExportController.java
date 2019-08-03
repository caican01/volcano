package com.good.moviemanager.controller;

import com.good.moviemanager.entity.User;
import com.good.moviemanager.service.UserService;
import com.good.moviemanager.util.ImportAndExportUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 导入导出用户数据
 */
@Controller
public class UserDataImportAndExportController {

    @Autowired
    private UserService userService;

    /**
     * 用户数据导出
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportUserExcel")
    @ResponseBody
    public void exportUserExcel(HttpServletResponse response){
        List<User> userList = userService.selectAllUsersNoCondition();
        //当表不为空时，导出表的数据到excel文件中
        if (userList!=null&&userList.size()>=1)
            ImportAndExportUtils.exportExcel(userList, null, null, User.class, "userList.xls", false, response);
        //当表为空时，也执行导出操作，不过此时传入的List为空集合，得到的excel文件中也没有数据
        else
            ImportAndExportUtils.exportExcel(Arrays.asList(), null, null, User.class, "userList.xls", false, response);
    }

    /**
     * 用户数据导入
     * @param multipartFile
     * @return
     */
//    @RequestMapping(value = "/importUserExcel",method = RequestMethod.POST)
//    @ResponseBody
//    public Object importUserExcel(@RequestParam("userDataImportFile") MultipartFile multipartFile){
//        Map<String,Object> map = new HashMap<>();
//        List<User> userList = ImportAndExportUtils.importExcel(multipartFile, 1, 1, User.class);
//        boolean bool = userService.batchInsertUsers(userList);
//        if (bool==true) {
//            map.put("success",true);
//        } else {
//            map.put("success",false);
//        }
//        return map;
//    }

}