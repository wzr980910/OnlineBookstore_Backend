package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName shopping
 */
@TableName(value ="shopping")
@Data
public class Shopping implements Serializable {
    private Long id;

    private Long bookId;

    private String userNumberBookId;

    private String bookName;

    private Long price;

    private Integer number;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}