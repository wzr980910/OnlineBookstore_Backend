package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.bookStore.pojo.pojoenum.DefaultAddress;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @TableName address
 */
@TableName(value = "address")
@Data
@ApiModel(description = "地址信息")
public class Address implements Serializable {
    @ApiModelProperty(value = "地址ID", example = "8")
    private Long id;

    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;

    @ApiModelProperty(value = "省份", example = "四川")
    private String province;

    @ApiModelProperty(value = "城市", example = "成都")
    private String city;

    @ApiModelProperty(value = "国家", example = "中国")
    private String county = "中国";

    @ApiModelProperty(value = "详细地址", example = "")
    private String detail;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "请输入正确的手机格式")
    @ApiModelProperty(value = "手机号", example = "16766565656")
    private String phone;

    @NotBlank
    @ApiModelProperty(value = "用户姓名", example = "不用传")
    private String userName;

    @EnumValue
    @ApiModelProperty(value = "是否默认地址", example = "0/1")
    private Integer defaultAddress=DefaultAddress.NOT_DEFAULT_ADDRESS.getValue();

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}