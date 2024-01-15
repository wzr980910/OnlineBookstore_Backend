package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.User;
import com.bookStore.service.UserService;
import com.bookStore.mapper.UserMapper;
import com.bookStore.util.JwtHelper;
import com.bookStore.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
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
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(User::getAccountNumber, accountNumber);
        String sql = wrapper.getSqlSegment();
        System.out.println(sql);
        User userTemp = userMapper.selectOne(wrapper);
        return userTemp;
    }

    @Override
    public Integer insert(User user) {
        String passwordEncrypt = MD5Util.encrypt(user.getPassword());
        user.setPassword(passwordEncrypt);
        int insert = userMapper.insert(user);
        return insert;
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
    public User queryUserById(Integer id) {
        return userMapper.selectById(id);
    }


}




