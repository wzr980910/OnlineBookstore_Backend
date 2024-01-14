package com.bookStore.service.impl;

import com.bookStore.entity.CodeNumEntity;
import com.bookStore.entity.ResponseMessage;
import com.bookStore.entity.User;
import com.bookStore.mapper.UserMapper;
import com.bookStore.service.UserService;
import com.bookStore.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    //@Autowired
    private UserMapper userDao;

    @Autowired
    public void setUserDao(UserMapper userDao) {
        this.userDao = userDao;
    }

    /**
     * 注册业务
     * 1.检查账号是否被注册
     * 2.密码加密
     * 3.账号数据保存
     * 4.返回结果
     *
     * @param user
     * @return
     */
    @Override
    public ResponseMessage regist(User user) {
        Long count=userDao.selectCount(user.getUsername());
        //查询数据库中是否有这个用户
        if (count != 0L) {
            return new ResponseMessage(CodeNumEntity.USER_EXISTS.getCode(), CodeNumEntity.USER_EXISTS.getMessage());
        }
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        Integer rows = userDao.addUser(user);
        //插入失败
        if (rows == 0) {
            return new ResponseMessage(CodeNumEntity.FAIL.getCode(), CodeNumEntity.FAIL.getMessage());
        }
        return new ResponseMessage(CodeNumEntity.SUCCESS.getCode(), CodeNumEntity.SUCCESS.getMessage());
    }

    //检查用户名是否已经存在
    @Override
    public ResponseMessage checkUserName(String username) {
        Long count=userDao.selectCount(username);
        //查询数据库中是否有这个用户
        //没有这个用户
        if (count == 0L) {
            return new ResponseMessage(CodeNumEntity.SUCCESS.getCode(), CodeNumEntity.SUCCESS.getMessage());
        } else {
            return new ResponseMessage(CodeNumEntity.USER_EXISTS.getCode(), CodeNumEntity.USER_EXISTS.getMessage());
        }
    }

}
