package com.bookStore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/20:34
 * @Description:订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    private Integer orderID;            //订单id

    private Integer bookID;             //订单图书

    private Double price;               //订单价格

}
