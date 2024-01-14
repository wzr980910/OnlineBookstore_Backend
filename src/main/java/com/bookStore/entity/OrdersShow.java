package com.bookStore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/20:37
 * @Description:用户发货单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersShow {

    private Integer orderID;                            //送货单ID

    private Integer accountNumber;                      //用户ID

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date date;                                  //下单日期

    private Double totalPrice;                          //订单价格

    private String shoppingAddress;                     //送货地址

    private String contact;                             //备注

    private String status;                              //送货状态

}
