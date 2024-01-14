package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.User;
import com.bookStore.service.UserService;
import com.bookStore.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 邓桂材
* @description 针对表【user(书城客户)】的数据库操作Service实现
* @createDate 2024-01-14 12:03:10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




