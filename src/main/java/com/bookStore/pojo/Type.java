package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName type
 */
@TableName(value ="type")
@Data
public class Type implements Serializable {
    private Long id;

    private String type;

    private Long parentid;

    private static final long serialVersionUID = 1L;
}