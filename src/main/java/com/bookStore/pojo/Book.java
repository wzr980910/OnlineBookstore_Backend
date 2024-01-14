package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName book
 */
@TableName(value ="book")
@Data
public class Book implements Serializable {
    private Long bookid;

    private String bookname;

    private String author;

    private Long publishid;

    private String publishdate;

    private Long price;

    private String picture;

    private String content;

    private Integer typeid;

    private Date createtime;

    private Date updatetime;

    private Integer isdeleted;

    private static final long serialVersionUID = 1L;
}