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
    private Long publishid;

    private String publishname;

    private String publishaddress;

    private Long publishphone;

    private String publishemail;

    private Date createtime;

    private Date updatetime;

    private Integer isdeleted;

    private static final long serialVersionUID = 1L;
}