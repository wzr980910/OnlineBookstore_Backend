package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @TableName book
 */
@TableName(value ="book")
@Data
public class Book implements Serializable {

    private Long id;

    private String bookName;

    private String author;

    private Long publishId;

    private String publishDate;

    private BigDecimal price;

    private String picture;

    private String content;

    private Integer typeId;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}