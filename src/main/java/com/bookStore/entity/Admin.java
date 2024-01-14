package com.bookStore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/12/19:44
 * @Description:管理员实体类
 */
@Data                   //自动生成getter setter方法
@NoArgsConstructor      //生成无参构造函数
@AllArgsConstructor     //生成有参构造函数
public class Admin {

    private Integer adminID;                //管理员id

    private String adminName;               //管理员名称

    private String password;                //管理员密码

    private String level;                   //管理员权限
}
