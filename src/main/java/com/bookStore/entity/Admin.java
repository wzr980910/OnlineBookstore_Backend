package com.bookStore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/12/19:44
 * @Description:
 */
@Data                   //自动生成getter setter方法
@NoArgsConstructor      //生成无参构造函数
@AllArgsConstructor     //生成有参构造函数
public class Admin {
    private Integer id;
    private String adminName;
    private String password;
    private String level;
}
