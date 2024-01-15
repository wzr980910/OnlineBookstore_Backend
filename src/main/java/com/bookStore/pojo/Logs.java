package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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

    private Date data;

    private String operator;

    private static final long serialVersionUID = 1L;
}