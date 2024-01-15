package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName publishing_house
 */
@TableName(value ="publishing_house")
@Data
public class PublishingHouse implements Serializable {
    private Long id;

    private String publishName;

    private String publishAddress;

    private Long publishPhone;

    private String publishEmail;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}