package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName slide
 */
@TableName(value ="slide")
@Data
public class Slide implements Serializable {
    private Long id;

    private String imgurl;

    private Integer isdisplay;

    private Date createtime;

    private Date updatetime;

    private Integer isdelete;

    private static final long serialVersionUID = 1L;
}