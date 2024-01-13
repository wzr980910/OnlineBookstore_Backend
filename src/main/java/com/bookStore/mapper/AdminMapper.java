package com.bookStore.mapper;

import com.bookStore.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/9:23
 * @Description:通过管理员名称查询管理员信息
 */
@Repository     //将类识别为Bean，将接口的实现类交给spring管理
@Mapper         //在运行时,会自动生成该接口的实现类对象(代理对象),并将该对象交给IOC容器管理
public interface AdminMapper {

    //登录时查询单个用户信息
    Admin selectAdmin(String adminName,String password);


}
