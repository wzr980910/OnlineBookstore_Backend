package com.bookStore.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * ClassName: OrderSelectVo
 * Package: com.bookStore.pojo.vo
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/23 -15:17
 * @Version: v1.0
 */
@Data
public class OrderSelectVo {
    @ApiModelProperty(value = "订单编号", notes = "需要根据订单编号查询时传入")
    private Long orderId;
    @ApiModelProperty(value = "订单状态", notes = "0待付款1待发货2待收货")
    private Integer status;
    @ApiModelProperty(value = "图书名称", notes = "需要根据书名查询时传入")
    private String bookName;
    @ApiModelProperty(value = "订单创建时间", notes = "需要根据订单创建时间查询时传入,默认为1970-1-1")
    private Date createDateOld=new Date(0);
    @ApiModelProperty(value = "订单创建时间", notes = "需要根据订单创建时间查询时传入，默认为现在系统时间")
    private Date createDateNew=new Date();
    @ApiModelProperty(value = "当前页数", notes = "需要分页查询时传入，默认第一页")
    private Integer pageNum=1;
    @ApiModelProperty(value = "页容量", notes = "需要分页查询时传入，默认为10条记录")
    private Integer pageSize=10;
}
