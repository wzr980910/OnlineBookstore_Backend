package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.bookStore.pojo.pojoenum.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @TableName orders_show
 */
@TableName(value ="orders_show")
@Data
public class OrdersShow implements Serializable {
    private Long id;

    private Long orderId;

    private Long userId;

    private Long addressId;


    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date date;

    private BigDecimal totalPrice;


    /**
     * 订单状态
     * 0:待付款 1:待发货 2:待收货
     */
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}