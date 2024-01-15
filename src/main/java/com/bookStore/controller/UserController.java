package com.bookStore.controller;


import com.bookStore.pojo.User;
import com.bookStore.service.UserService;
import com.bookStore.util.JwtHelper;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
//说明接口文件
@Api(value = "用户接口", tags = "用户管理相关的接口", description = "用户测试接口")
public class UserController {
    private UserService userService;
    private JwtHelper jwtHelper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtHelper(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

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
    public RestResult regist(@Validated @RequestBody User user) {
        RestResult restResult = new RestResult();
        //验证用户是否以及存在
        User userTemp = userService.selectByAccount(user.getAccountNumber());
        //用户不存在
        if (userTemp == null) {
            Integer rows = userService.insert(user);
            //插入成功
            if (rows > 0) {
                restResult.setCode(ResultCode.SUCCESS.getCode());
                restResult.setMessage(ResultCode.SUCCESS.getMessage());
                //插入失败
            } else {
                restResult.setCode(ResultCode.UNKNOWN_ERROR.getCode());
                restResult.setMessage(ResultCode.UNKNOWN_ERROR.getMessage());
            }
            //用户已存在
        } else {
            restResult.setCode(ResultCode.USER_HAS_EXISTED.getCode());
            restResult.setMessage(ResultCode.USER_HAS_EXISTED.getMessage());
        }
        return restResult;
    }

    @GetMapping("/checkUserAccount")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParam(name = "accountNumber", value = "用户账号")
    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "校验用户账号是否已经存在")
    public RestResult checkUserAccount(String accountNumber) {
        RestResult restResult = null;
        //验证用户是否以及存在
        User userTemp = userService.selectByAccount(accountNumber);
        if (userTemp != null) {
            restResult = new RestResult(ResultCode.USER_HAS_EXISTED);
        } else {
            restResult = new RestResult(ResultCode.USER_NOT_EXIST);
        }
        return restResult;
    }

    /**
     * 用户登录
     *
     * @param
     * @param
     * @param
     * @return
     */
    //登录接口，添加该注解跳过token验证
    @ApiOperation(value = "登录接口", notes = "用户登录（只传手机号和密码参数即可）,用接口测试前请先登录（缓存用户信息）", httpMethod = "GET")
    @GetMapping("/login")
    public RestResult login(HttpServletRequest httpServletRequest, @RequestParam String accountNumber, @RequestParam String password) {
        Map<String, Object> map = new HashMap<>();
        RestResult restResult = null;
        //验证用户账号是否存在
        User userTemp = userService.selectByAccount(accountNumber);
        //不存在
        if (userTemp == null) {
            restResult = new RestResult(ResultCode.USER_NOT_EXIST.getCode(), "用户账号输入错误");
            return restResult;
        }
        //用户账号存在
        User userLogin = userService.login(accountNumber, password);
        //验证密码失败
        if (userLogin == null) {
            restResult = new RestResult(ResultCode.USER_LOGIN_ERROR);
            return restResult;
            //登录验证成功
        } else {
            //根据唯一id生成token
            String token = jwtHelper.createToken(userLogin.getId());
            map.put("user", userLogin);
            map.put("token", token);
            restResult = new RestResult(ResultCode.SUCCESS, map);
            return restResult;
        }
    }
}
