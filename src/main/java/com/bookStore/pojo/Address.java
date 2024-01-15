package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName address
 */
@TableName(value ="address")
@Data
public class Address implements Serializable {
    private Long id;

    private String userNumberBookId;

    private String province;

    private String city;

    private String county;

    private String detail;

    private Long phone;

    private static final long serialVersionUID = 1L;
}