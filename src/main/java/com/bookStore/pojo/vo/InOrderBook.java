package com.bookStore.pojo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wmh
 * @date 2024/01/18 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class InOrderBook {
//    @ApiModelProperty("收货地址id")
    private Long addressId;
//    @ApiModelProperty("购买图书ids 可以传多个 也可以传一个")
    private String[] bookIds;
//    @ApiModelProperty("订单总价")
    private BigDecimal totalPrice;
//    @ApiModelProperty("订单状态")
    private Integer status;
    private String[] numbers;
}
