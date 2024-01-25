package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.exception.BizException;
import com.bookStore.pojo.User;
import com.bookStore.service.UserService;
import com.bookStore.mapper.UserMapper;
import com.bookStore.util.JwtHelper;
import com.bookStore.util.MD5Util;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.nio.charset.CoderResult;

/**
 * @author 邓桂材
 * @description 针对表【user(书城客户)】的数据库操作Service实现
 * @createDate 2024-01-14 16:56:54
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    private UserMapper userMapper;


    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectByAccount(String accountNumber) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccountNumber, accountNumber);
        User user;
        try {
            user = userMapper.selectOne(wrapper);
        } catch (Exception e) {
            throw new BizException(ResultCode.DB_SELECT_ONE_ERROR);
        }
        return user;
    }

    @Override
    public Integer insert(User user) {
        if (user.getPassword() != null) {
            String passwordEncrypt = MD5Util.encrypt(user.getPassword());
            user.setPassword(passwordEncrypt);
        }
        user.setIsDeleted(0);
        int rows = userMapper.insert(user);
        if (rows == 0) {
            throw new BizException(ResultCode.DB_INSERT_ERROR);
        }
        return rows;
    }

    @Override
    public User login(String accountNumber, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccountNumber, accountNumber);
        //加密密码
        String passwordEncry = MD5Util.encrypt(password);
        wrapper.eq(User::getPassword, passwordEncry);
        //通过用户名和加密后的密码查找用户
        User user = userMapper.selectOne(wrapper);
        return user;
    }

    @Override
    public Integer updateUser(User user) {
        int insert = userMapper.updateById(user);
        return insert;
    }

    @Override
    public int updateUserAvatar(Long userId, String avatarUrl) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getId, userId);
        wrapper.set(User::getPicture, avatarUrl);
        return userMapper.update(wrapper);
    }

    @Override
    public User queryUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User forgetPassword(String phone, String accountNumber) {
        QueryWrapper<User> query = Wrappers.query();
        query.eq("phoneNumber", phone).eq("accountNumber", accountNumber);
        User user = userMapper.selectOne(query);
        if (user != null) {
            user.setPassword(MD5Util.encrypt("123456"));
            userMapper.updateById(user);
        }
        return user;
    }

    @Override
    public User selectByWechatId(String openid) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getWechatId, openid);
        return userMapper.selectOne(wrapper);
    }
}



