package com.bookStore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    //用户账号 相当于用户id
    private String accountNumber;
    //用户名
    private String userName;
    //密码
    private String password;
    @NotNull
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号码格式不正确")
    private String phoneNumber;


}
