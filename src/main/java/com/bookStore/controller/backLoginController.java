package com.bookStore.controller;

import com.bookStore.entity.CodeNumEntity;
import com.bookStore.entity.ResponseMessage;
import com.bookStore.service.AdminService;
import com.bookStore.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bookStore.entity.Admin;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/12/19:32
 * @Description:
 */
@RestController
public class backLoginController {
    private AdminService adminService;

    @Autowired
    public backLoginController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/adminLogin")
    public ResponseMessage login(HttpServletRequest request, @RequestParam String adminName, @RequestParam String password) {
        ResponseMessage responseMessage = new ResponseMessage();
        if(!adminName.trim().equals("") && !password.trim().equals("")) {
            Admin admin = adminService.login(adminName, password);
            if (admin != null) {
                Map<String, Object> map = new HashMap<>();
                request.getSession().setAttribute(ConstantsUtil.Admin_Session, admin);
                map.put("adminName", admin.getAdminName());
                responseMessage.setStatusCode(CodeNumEntity.SUCCESS.getCode());
                responseMessage.setStatusMessage(CodeNumEntity.SUCCESS.getMessage());
                responseMessage.setContent(map);
            } else {
                responseMessage.setStatusCode(CodeNumEntity.USER_ERROR.getCode());
                responseMessage.setStatusMessage(CodeNumEntity.USER_ERROR.getMessage());
            }
        } else {
            responseMessage.setStatusCode(CodeNumEntity.USER_ERROR.getCode());
            responseMessage.setStatusMessage(CodeNumEntity.USER_ERROR.getMessage());
        }
        return responseMessage;
    }
}
