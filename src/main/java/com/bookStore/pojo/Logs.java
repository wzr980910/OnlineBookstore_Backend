package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName logs
 */
@TableName(value ="logs")
@Data
public class Logs implements Serializable {
    private Long id;

    private String content;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private String operator;

    private static final long serialVersionUID = 1L;
}