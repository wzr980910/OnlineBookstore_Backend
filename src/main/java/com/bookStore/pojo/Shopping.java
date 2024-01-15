package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import lombok.Data;

/**
 * @TableName shopping
 */
@TableName(value ="shopping")
@Data
public class Shopping implements Serializable {
    private Long id;

    private Long bookId;

    @TableField("userId")
    private String userNumberBookId;

    private String bookName;

    private BigDecimal price;

    private Integer number;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}