package com.bookStore.controller;


import com.bookStore.annotation.PassToken;
import com.bookStore.entity.CodeNumEntity;
import com.bookStore.entity.ResponseMessage;
import com.bookStore.entity.CodeNumEntity;
import com.bookStore.entity.ResponseMessage;
import com.bookStore.annotation.PassToken;
import com.bookStore.entity.User;
import com.bookStore.service.TokenService;
import com.bookStore.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
//说明接口文件
@Api(value = "用户接口", tags = "用户管理相关的接口", description = "用户测试接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 保存数据
     *
     * @param user
     * @return
     */

    @PostMapping(value = "/regist")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParam(name = "user", value = "新增用户数据")
    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public ResponseMessage regist(@Validated @RequestBody User user) {

        ResponseMessage responseMessage = userService.regist(user);
        return responseMessage;
    }

    @GetMapping("/checkUserName")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParam(name = "username", value = "用户名")
    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "校验用户名是否已经存在")
    public ResponseMessage checkUserName(String username) {
        ResponseMessage responseMessage = userService.checkUserName(username);
        return responseMessage;
    }
    /**
     * 用户登录
     * @param httpServletRequest
     * @param phone
     * @param password
     * @return
     */
    //登录接口，添加该注解跳过token验证

    @ApiOperation(value = "登录接口", notes = "用户登录（只传手机号和密码参数即可）,用接口测试前请先登录（缓存用户信息）", httpMethod = "GET")
    @GetMapping("/login")
    public ResponseMessage login(HttpServletRequest httpServletRequest, @RequestParam String phone, @RequestParam String password) {
        Map<String, Object> map = new HashMap<>();
        ResponseMessage responseMessage = new ResponseMessage();
        User user = userService.getByPhoneNumber(phone);
        if (user == null) {
            responseMessage.setStatusCode(CodeNumEntity.USER_NOTEXIST.getCode());
            responseMessage.setStatusMessage(CodeNumEntity.USER_NOTEXIST.getMessage());
        } else {
            if (!user.getPassword().equals(password)){
                responseMessage.setStatusCode(CodeNumEntity.USER_ERROR.getCode());
                responseMessage.setStatusMessage(CodeNumEntity.USER_ERROR.getMessage());
            }else {
                String token=tokenService.getToken(user,httpServletRequest);
                map.put("user",user);
                map.put("token",token);
                responseMessage.setStatusCode(CodeNumEntity.SUCCESS.getCode());
                responseMessage.setStatusMessage(CodeNumEntity.SUCCESS.getMessage());
                responseMessage.setContent(map);
            }
        }
        return responseMessage;
    }


}
