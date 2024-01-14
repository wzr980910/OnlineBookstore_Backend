package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName admin
 */
@TableName(value ="admin")
@Data
public class Admin implements Serializable {
    private Integer adminid;

    private String adminname;

    private String password;

    private Integer level;

    private Integer isdeleted;

    private Date createtime;

    private Date updatetime;

    private static final long serialVersionUID = 1L;
}