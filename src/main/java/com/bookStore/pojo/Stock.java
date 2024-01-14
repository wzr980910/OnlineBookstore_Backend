package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName stock
 */
@TableName(value ="stock")
@Data
public class Stock implements Serializable {
    private Long id;

    private Long bookid;

    private Integer stocknum;

    private String bookname;

    private Date createtime;

    private Date updatetime;

    private Integer isdeleted;

    private static final long serialVersionUID = 1L;
}