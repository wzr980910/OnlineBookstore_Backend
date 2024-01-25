package com.bookStore.controller;


import cn.hutool.db.Session;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.json.JSON;
import com.bookStore.pojo.User;
import com.bookStore.pojo.login.wxlogin.WechatUser;
import com.bookStore.pojo.login.wxlogin.WxMpConfig;
import com.bookStore.pojo.pojoenum.Gender;
import com.bookStore.service.UserService;
import com.bookStore.service.WeChatMpService;
import com.bookStore.util.AliOssUtil;
import com.bookStore.util.JwtHelper;
import com.bookStore.util.MD5Util;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import com.bookStore.util.result.WechatLoginUtil;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Validated
//说明接口文件
@Api(value = "用户接口", tags = "用户管理相关的接口", description = "用户测试接口")
public class UserController {
    private UserService userService;
    private JwtHelper jwtHelper;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    WeChatMpService weChatMpService;
    @Autowired
    private WxMpConfig wxMpConfig;
    private HttpSession session;

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
     * @param
     * @return
     */

    @PostMapping(value = "/regist")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public RestResult regist(@Valid @RequestBody User user) {
        //验证用户是否以及存在
        User userTemp = userService.selectByAccount(user.getAccountNumber());
        //用户不存在
        if (userTemp == null) {
            userService.insert(user);
            //这里给用户一个默认头像
            String imgPath = "https://bookstore-picture.oss-cn-chengdu.aliyuncs.com/userPicture/93d5bcc1-7ff0-4645-b524-ce70cc5535bb.jpg";
            user.setPicture(imgPath);
            userService.insert(user);
            //插入成功
            return new RestResult(ResultCode.SUCCESS);
        } else {//用户已存在
            return new RestResult(ResultCode.USER_HAS_EXISTED);
        }
    }

    @GetMapping("/checkUserAccount")
    //方法参数说明，name参数名；value参数说明，备注；dataType参数类型；required 是否必传；defaultValue 默认值
    @ApiImplicitParam(name = "accountNumber", value = "用户账号")
    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "校验用户账号是否已经存在")
    public RestResult checkUserAccount(String accountNumber) {
        //验证用户是否以及存在
        User userTemp = userService.selectByAccount(accountNumber);
        if (userTemp != null) {
            return new RestResult(ResultCode.USER_HAS_EXISTED);
        } else {
            return new RestResult(ResultCode.USER_NOT_EXIST);
        }
    }

    /**
     * 用户登录
     *
     * @param
     * @param accountNumber
     * @param password
     * @return
     */
    @ApiOperation(value = "登录接口", notes = "用户登录（只传手机号和密码参数即可）,用接口测试前请先登录（缓存用户信息）", httpMethod = "GET")
    @GetMapping("/login")
    public RestResult login(@RequestParam String accountNumber, @RequestParam String password) {
        RestResult restResult = null;
        Long userId = null;
        if (this.session != null) {
            userId = (Long) this.session.getAttribute("userId");
        }
        //已经通过其他方式登录成功了
        if (userId != null) {
            User user = userService.queryUserById(userId);
            return getRestResult(user);
        }
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
            restResult = getRestResult(userLogin);
            return restResult;
        }
    }

    private RestResult getRestResult(User userLogin) {
        RestResult restResult;
        //根据唯一id生成token
        Map<String, Object> map = new HashMap<>();
        String token = jwtHelper.createToken(userLogin.getId());
        map.put("user", userLogin);
        map.put("token", token);
        restResult = new RestResult(ResultCode.SUCCESS, map);
        return restResult;
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping(value = "/updatePassword")
    @ApiOperation(value = "修改密码", notes = "旧密码 新密码 必填")
    public RestResult updatePassword(@RequestParam String oldPassword,
                                     @RequestParam String newPassword) {
        //通过id查到该用户
        Long userId = ThreadLocalUtil.get();
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
     * 修改个人信息
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/updateUser")
    @ApiOperation(value = "修改用户基本信息", notes = "参数可选")
    public RestResult updateUser(@RequestBody User user) {
        Long userId = ThreadLocalUtil.get();
        user.setId(userId);
        //通过id查到该用户
        Integer rows = userService.updateUser(user);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "修改成功");
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "修改失败");
        }
    }


//    @PostMapping(value = "/forgetPassword")
//    @ApiOperation(value = "忘记密码", notes = "账号 电话号码 必填")
//    public RestResult forgetPassword(@RequestParam String phone, @RequestParam String accountNumber) {
//        User user = userService.forgetPassword(phone, accountNumber);
//        if (user != null) {
//            return RestResult.success(ResultCode.SUCCESS, "密码已重置为123456!", user);
//        } else {
//            return RestResult.failure(ResultCode.OPERATION_FAILURE, "操作失败,账号或手机号填写有误!");
//        }
//    }

    /**
     * userId 查询用户信息
     *
     * @param id
     * @return
     */
//    @GetMapping(value = "/queryUserById")
//    @ApiOperation(value = "通过id查用户信息", notes = "用户id 必填")
//    public RestResult queryUserById(Long id) {
//        User user = userService.queryUserById(id);
//        if (user == null) {
//            return RestResult.failure(ResultCode.OPERATION_FAILURE, "查询失败");
//        }
//        return RestResult.success(ResultCode.SUCCESS, "查询成功", user);
//    }

//    @PatchMapping(value = "/updateAvatar")
//    @ApiOperation(value = "更新用户头像信息", notes = "传入一个头像的url地址,param传参：avatarUrl")
//    public RestResult updateAvatar(@RequestParam @URL String avatarUrl) {
//        Long userId = (Long) ThreadLocalUtil.get();
//        int rows = userService.updateUserAvatar(userId, avatarUrl);
//        RestResult restResult = new RestResult(ResultCode.SUCCESS);
//        if (rows == 0) {
//            restResult = new RestResult(ResultCode.OPERATION_FAILURE);
//        }
//        return restResult;
//    }

    /**
     * 更新头像直接调用此接口
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("头像上传")
    public RestResult imgUpload(@RequestParam(value = "file") MultipartFile file) {
        String basePath = "userPicture/";
        Long userId = ThreadLocalUtil.get();
        try {
            //返回文件请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), file, basePath);
            //保存到user表中
            userService.updateUserAvatar(userId, filePath);
            return RestResult.success(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RestResult.failure("文件上传失败");
    }

    /*
     * @param signature 微信加密签名，signature结合了开发者填写的 token 参数和请求中的 timestamp 参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     这是个随机数
     * @param echostr   随机字符串，验证成功后原样返回
     */
    @GetMapping("wx")
    public String get(@RequestParam(required = false) String signature,
                      @RequestParam(required = false) String timestamp,
                      @RequestParam(required = false) String nonce,
                      @RequestParam(required = false) String echostr
    ) {
        if (!this.weChatMpService.checkSignature(timestamp, nonce, signature)) {
            return null;
        }
        return echostr;
    }

    //前端调用
    @GetMapping("/wxLogin")
    @ResponseBody
    public void wxLogin(HttpServletResponse response, HttpSession session) throws IOException {
        this.session = session;
        //redirece_url是回调的地址，要转成UrlEncode格式
        String redirecrUrl = URLEncoder.encode("http://72bc314d.r5.cpolar.top/user/wxCallback", StandardCharsets.UTF_8);
        //构造二维码地址
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + wxMpConfig.getAppid()
                + "&redirect_uri=" + redirecrUrl + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
        //生成二维码，扫描后跳转上面地址
        response.setContentType("image/png");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            QrCodeUtil.generate(url, 300, 300, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    //微信服务器调用
    @RequestMapping("/wxCallback")
    @ResponseBody
    public String pcCallback(String code) throws IOException {
        WechatUser wechatUser = WechatLoginUtil.getUserInfo(code, wxMpConfig.getAppid(), wxMpConfig.getSecret());
        //在数据库查询个人信息
        User user = userService.selectByWechatId(wechatUser.getOpenid());
        //没有该数据就添加用户
        if (user == null) {
            User userAdd = new User();
            //将微信提供的信息设置到用户信息中
            userAdd.setWechatId(wechatUser.getOpenid());
            userAdd.setUsername(wechatUser.getNickname());
            userAdd.setPicture(wechatUser.getHeadimgurl());
            userAdd.setGender(wechatUser.getSex());
            userService.insert(userAdd);
        }
        //重新查询，获取userId
        User userNew = userService.selectByWechatId(wechatUser.getOpenid());
        this.session.setAttribute("userId", userNew.getId());
        return "登录成功";
    }
}
