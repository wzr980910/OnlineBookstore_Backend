package com.bookStore.pojo;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.bookStore.pojo.pojoenum.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @TableName user
 */
@TableName(value = "user")
@Data
public class User implements Serializable {
    @TableId
    private Long id;

    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,19}$",
            message = "账号由字母、数字和下划线组成，以字母开头，长度在6到20个字符之间")
    @ApiModelProperty(value = "用户账号",notes = "账号由字母、数字和下划线组成，以字母开头，长度在6到20个字符之间")
    private String accountNumber;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>?]{8,20}$"
            , message = "密码长度在8到20个字符之间，可以包含字母、数字和特殊字符")
    @ApiModelProperty(value = "用户密码",notes ="密码长度在8到20个字符之间，可以包含字母、数字和特殊字符")
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$", message = "用户名只包含字母和数字，长度在3到20个字符之间")
    @ApiModelProperty(value = "用户密码",notes ="用户名只包含字母和数字，长度在3到20个字符之间")
    private String username;

    @ApiModelProperty(value = "用户密码",notes = "1表示男，2表示女")
    private Gender gender;

    @ApiModelProperty(value = "用户头像地址",notes = "需要真实url地址")
    private String picture;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "11位正确的收集格式")
    @ApiModelProperty(value = "用户手机号",notes = "11位正确的收集格式")
    private String phoneNumber;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer isDeleted;

    private String wechatId;
    private static final long serialVersionUID = 1L;
}