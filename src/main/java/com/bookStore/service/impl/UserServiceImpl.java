package com.bookStore.service.impl;

import com.bookStore.mapper.UserMapper;
import com.bookStore.entity.User;
import com.bookStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userDao;

    @Override
    public boolean addUser(User user) {
        return userDao.addUser(user);
    }
}
