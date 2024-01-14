package com.bookStore.mapper;


import com.bookStore.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {


    /**
     * 添加用户信息
     * @param user 用户信息
     * @return 返回是否添加成功
     */
   Integer addUser(User user);

    Long selectCount(String username);

    /**
     * 通过手机号查询用户
     * @param phone
     * @return  用户信息
     */
   User getByPhoneNumber(String phone);

    /**
     * 通过用户账户就是id查询用户
     * @param accountNumber
     * @return
     */
   User findUserById(String accountNumber);





}
