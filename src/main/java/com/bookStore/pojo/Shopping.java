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

    private Long bookid;

    private String usernumber;

    private String bookname;

    private Long pirce;

    private Integer number;

    private Date createtime;

    private Date updatetime;

    private static final long serialVersionUID = 1L;
}