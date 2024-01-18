package com.bookStore.pojo.vo;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.bookStore.pojo.pojoenum.OrderStatus;
import com.bookStore.pojo.respojo.CartBook;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wmh
 * @date 2024/01/17 19:26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderBook {
    /**
     * 图书名
     */
    private String bookName;
    /**
     *图书ISBN
     */
    private String ISBN;
    /**
     * 作者
     */
    private String author;
    /**
     * 图书封面
     */
    private String picture;
    /**
     * 出版社
     */
    private String publishName;
    /**
     * 出版时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date publishDate;
    /**
     * 图书简介
     */
    private String content;
    /**
     * 图书价格
     */
    private BigDecimal price;
    /**
     * 购买数量
     */
    private Integer number;
    /**
     * 收货地址  收货人姓名和手机号
     */
    private String province;
    private String city;
    private String county;
    private String detail;
    private String userName;
    private String phone;
    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 订单总价
     */
    private BigDecimal totalPrice;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:ss:mm",timezone = "GMT+8")
    private Date date;

}
