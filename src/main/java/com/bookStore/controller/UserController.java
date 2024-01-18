package com.bookStore.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bookStore.mapper.UserMapper;
import com.bookStore.pojo.User;
import com.bookStore.util.AliOssUtil;
import com.bookStore.util.JwtHelper;
import com.bookStore.util.MD5Util;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.service.UserService;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@Validated
//说明接口文件
@Api(value = "用户接口", tags = "用户管理相关的接口", description = "用户测试接口")
public class UserController {
    private UserService userService;
    private JwtHelper jwtHelper;

    private UserMapper userMapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJwtHelper(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 保存数据
     *
     * @param user
     * @return
     */

    @PostMapping(value = "/regist")
    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public RestResult regist(@Valid @RequestBody User user) {
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
     * @param httpServletRequest
     * @param accountNumber
     * @param password
     * @return
     */
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

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping(value = "/updatePassword")
    @ApiOperation(value = "修改密码", notes = "用户id 旧密码 新密码 必填")
    public RestResult updatePassword(@RequestParam Integer userId,
                                     @RequestParam String oldPassword,
                                     @RequestParam String newPassword) {
        //通过id查到该用户
        User user = userService.queryUserById(userId);
        if (!user.getPassword().equals(MD5Util.encrypt(oldPassword))) {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "原密码输入错误");
        } else {
            //设置新密码
            user.setPassword(MD5Util.encrypt(newPassword));
        }
        Integer rows = userService.updateUser(user);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "修改成功", user);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "修改失败");
        }
    }

    /**
     * 个人信息
     *
     * @param userId
     * @param accountNumber
     * @param userName
     * @param phone
     * @return
     */
    @PostMapping(value = "/updateUser")
    @ApiOperation(value = "修改用户基本信息", notes = "用户id 账号 昵称 电话号码 必填")
    public RestResult updateUser(@RequestParam Integer userId,
                                 @RequestParam String accountNumber,
                                 @RequestParam String userName,
                                 @RequestParam String phone) {
        //通过id查到该用户
        User user = userService.queryUserById(userId);
        user.setAccountNumber(accountNumber);
        user.setUsername(userName);
        user.setPhoneNumber(phone);
        Integer rows = userService.updateUser(user);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "修改成功");
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "修改失败");
        }
    }

    /**
     * 忘记密码 将用户密码重置为123456
     *
     * @param phone
     * @param accountNumber
     * @return
     */
    @PostMapping(value = "/forgetPassword")
    @ApiOperation(value = "忘记密码", notes = "账号 电话号码 必填")
    public RestResult forgetPassword(String phone, String accountNumber) {
        QueryWrapper<User> query = Wrappers.query();
        query.eq("phoneNumber", phone).eq("accountNumber", accountNumber);
        User user = userMapper.selectOne(query);
        if (user != null) {
            user.setPassword(MD5Util.encrypt("123456"));
            userMapper.updateById(user);
            return RestResult.success(ResultCode.SUCCESS, "密码已重置为123456!", user);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "操作失败,账号或手机号填写有误!");
        }
    }

    /**
     * userId 查询用户信息
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryUserById")
    @ApiOperation(value = "通过id查用户信息", notes = "用户id 必填")
    public RestResult queryUserById(Integer id) {
        User user = userService.queryUserById(id);
        if (user == null) {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "查询失败");
        }
        return RestResult.success(ResultCode.SUCCESS, "查询成功", user);
    }

    @PatchMapping(value = "/updateAvatar")
    @ApiOperation(value = "更新用户头像信息", notes = "传入一个头像的url地址,param传参：avatarUrl")
    public RestResult updateAvatar(@RequestParam @URL String avatarUrl) {
        Long userId = (Long) ThreadLocalUtil.get();
        int rows = userService.updateUserAvatar(userId, avatarUrl);
        RestResult restResult = new RestResult(ResultCode.SUCCESS);
        if (rows == 0) {
            restResult = new RestResult(ResultCode.OPERATION_FAILURE);
        }
        return restResult;
    }

    @PatchMapping(value = "/upload")
    @ApiOperation(value = "上传头像", notes = "参数类型：MultipartFile")
    public RestResult upload(MultipartFile file) throws Exception {
        RestResult restResult = new RestResult(ResultCode.FILE_UPLOAD_FAILURE);
        if (file == null) {
            return restResult;
        }
        //把文件的内容存到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        //保证文件名是唯一的，从而防止文件覆盖
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("C:\\Users\\邓桂材\\Desktop\\files\\" + filename));
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        restResult = new RestResult(ResultCode.SUCCESS);
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        restResult.setData(map);
        return restResult;
    }
}
