package com.bookStore.service;

import com.bookStore.entity.ResponseMessage;
import com.bookStore.entity.User;


public interface UserService {

     /**
      * 添加用户
      * @param user 用户信息
      * @return  用户是否添加成功
      */


     ResponseMessage regist(User user);

     ResponseMessage checkUserName(String username);
}
