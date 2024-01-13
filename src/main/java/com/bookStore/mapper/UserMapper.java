package com.bookStore.mapper;


import com.bookStore.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface UserMapper {
    /**
     * 添加用户信息
     * @param user 用户信息
     * @return 返回是否添加成功
     */
   boolean addUser(User user);

}
