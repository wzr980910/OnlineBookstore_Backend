package com.bookStore.pojo.respojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: OrderReturn
 * Package: com.bookStore.pojo.respojo
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/23 -16:58
 * @Version: v1.0
 */
@Data
public class OrderReturn implements Serializable {

    @ApiModelProperty(value = "订单展示ID", example = "1",notes = "订单自增id")
    private Long orderShowId;
    @ApiModelProperty(value = "订单编号", example = "1")
    private Long orderId;
    @ApiModelProperty(value = "地址ID", example = "1")
    private Long addressId;
    @ApiModelProperty(value = "总价", example = "1000")
    private BigDecimal totalPrice;
    @ApiModelProperty(value = "订单状态", example = "1",notes = "0待付款1待发货2待收货")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "支付时间")
    private Date payTime;
    @ApiModelProperty(value = "书籍ID", example = "1")
    private Long bookId;
    @ApiModelProperty(value = "书籍名称", example = "Java编程思想")
    private String bookName;
    @ApiModelProperty(value = "书籍单价", example = "100")
    private BigDecimal price;
    @ApiModelProperty(value = "书籍图片地址", example = "http://localhost:8080/book.jpg")
    private String picture;
    @ApiModelProperty(value = "书籍数量", example = "10")
    private Integer number;
    @ApiModelProperty(value = "省份", example = "广东省")
    private String province;
    @ApiModelProperty(value = "城市", example = "深圳市")
    private String city;
    @ApiModelProperty(value = "区县", example = "福田区")
    private String county;
    @ApiModelProperty(value = "详细地址", example = "科技南十二路")
    private String detail;
    @ApiModelProperty(value = "收货电话号码", example = "13800138000")
    private String phone;
    @ApiModelProperty(value = "收货人姓名", example = "张三")
    private String userName;
    private static final long serialVersionUID = 1L;
}
