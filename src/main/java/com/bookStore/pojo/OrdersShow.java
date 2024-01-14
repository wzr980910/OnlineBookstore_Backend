package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName orders_show
 */
@TableName(value ="orders_show")
@Data
public class OrdersShow implements Serializable {
    private Long orderid;

    private String accountnumber;

    private Date date;

    private Integer totalpirce;

    private String shoppingaddress;

    private String contact;

    private String status;

    private static final long serialVersionUID = 1L;
}