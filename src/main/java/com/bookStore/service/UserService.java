package com.bookStore.service;

import com.bookStore.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.nio.charset.CoderResult;

/**
 * @author 邓桂材
 * @description 针对表【user(书城客户)】的数据库操作Service
 * @createDate 2024-01-14 16:56:54
 */
public interface UserService extends IService<User> {
    public User selectByAccount(String accountNumber);

    Integer insert(User user);

    User login(String username, String password);

}
