package com.bookStore.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName: OrderVo
 * Package: com.bookStore.pojo.vo
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/23 -9:41
 * @Version: v1.0
 */
@Data
public class OrderVo {
    @ApiModelProperty(value = "购买数量", notes = "单独购买时需要传入")
    private Integer buyCount;
    @ApiModelProperty(value = "图书Id", notes = "单独购买时需要传入")
    private Long bookId;
    @ApiModelProperty(value = "购物车中每一项的Id", notes = "从购物车点击结算时需要传入")
    private Long[] ShoppingIds;
    @ApiModelProperty(value = "收获Id", required = true)
    private Long addressId;
}
