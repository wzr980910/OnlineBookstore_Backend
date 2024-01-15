package com.bookStore.controller;

import com.bookStore.pojo.Admin;
import com.bookStore.util.result.RestResult;
import com.bookStore.service.AdminService;
import com.bookStore.util.ConstantsUtil;
import com.bookStore.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/12/19:32
 * @Description:管理员登录Controller类
 */
@RestController
public class BackLoginController {
    private AdminService adminService;

    @Autowired
    public BackLoginController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/adminLogin")
    public RestResult login(HttpServletRequest request, @RequestParam String adminName, @RequestParam String password) {
        RestResult restResult = new RestResult();
        if(!adminName.trim().equals("") && !password.trim().equals("")) {
            Admin admin = adminService.login(adminName, password);
            if (admin != null) {
                Map<String, Object> map = new HashMap<>();
                request.getSession().setAttribute(ConstantsUtil.Admin_Session, admin);
                map.put("adminName", admin.getAdminName());
                map.put("level",admin.getLevel());
                restResult.setCode(ResultCode.SUCCESS.getCode());
                restResult.setMessage(ResultCode.SUCCESS.getMessage());
                restResult.setData(map);
            } else {
                restResult.setCode(ResultCode.USER_LOGIN_ERROR.getCode());
                restResult.setMessage(ResultCode.USER_LOGIN_ERROR.getMessage());
            }
        } else {
            restResult.setCode(ResultCode.USER_LOGIN_ERROR.getCode());
            restResult.setMessage(ResultCode.USER_LOGIN_ERROR.getMessage());
        }
        return restResult;
    }
}
