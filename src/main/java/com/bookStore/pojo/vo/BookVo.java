package com.bookStore.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bookStore.pojo.pojoenum.OrderMethod;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName: BookVo
 * Package: com.bookStore.pojo.vo
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/15 -11:32
 * @Version: v1.0
 */
@Data
@ApiModel(description = "图书信息")
@AllArgsConstructor
@NoArgsConstructor
public class BookVo {
    @ApiModelProperty(value="图书id")
    private Long id;

    @ApiModelProperty(value="图书ISBN号")
    private String bookIsbn;

    @ApiModelProperty(value="书名")
    private String bookName;

    @ApiModelProperty(value="作者")
    private String author;

    private Long publishId;

    private String publishDate;

    @ApiModelProperty(value="查找时的价格下限")
    private BigDecimal priceLow = BigDecimal.ZERO;

    @ApiModelProperty(value="找时的价格上限")
    private BigDecimal priceHigh;

    private BigDecimal price;

    private String picture;

    private String content;

    private Integer typeId;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted;

    @ApiModelProperty(value="分页查找时的页数")
    private Integer pageNum = 1;

    @ApiModelProperty(value="分页查找时的页容量")
    private Integer pageSize = 10;

    @ApiModelProperty(value="出版社名称")
    private String publishName;

    @ApiModelProperty(value="图书类型/标签名")
    private String type;

    //综合排序,按照综合排序的降序排列
    @ApiModelProperty(value="综合排序",example = "0/1/2",notes = "0不按照这一项排序，1升序，2降序")
    private Integer comprehensiveOrder;

    //按照价格排序
    @ApiModelProperty(value="价格排序",example = "0/1/2",notes = "0不按照这一项排序，1升序，2降序")
    private Integer priceOrder;

    //按照销量排序
    @ApiModelProperty(value="销量排序",example = "0/1/2",notes = "0不按照这一项排序，1升序，2降序，默认按照价格升序")
    private Integer saleCountOrder = OrderMethod.ASC.getValue();
}
