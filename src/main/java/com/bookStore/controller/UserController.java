package com.bookStore.controller;


import com.bookStore.entity.CodeNumEntity;
import com.bookStore.entity.ResponseMessage;
import com.bookStore.entity.User;
import com.bookStore.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
//说明接口文件
@Api(value = "测试接口", tags = "用户管理相关的接口", description = "用户测试接口")
public class UserController {

    @Autowired
    private UserService userService;

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

}
