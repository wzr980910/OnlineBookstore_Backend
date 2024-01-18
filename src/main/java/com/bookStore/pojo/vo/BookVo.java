package com.bookStore.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bookStore.pojo.pojoenum.OrderMethod;
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
@AllArgsConstructor
@NoArgsConstructor
public class BookVo {
    private Long id;

    private String bookIsbn;

    private String bookName;

    private String author;

    private Long publishId;

    private String publishDate;

    private BigDecimal priceLow = BigDecimal.ZERO;

    private BigDecimal priceHigh;

    private BigDecimal price;

    private String picture;

    private String content;

    private Integer typeId;

    private Date createTime;

    private Date updateTime;

    private Integer isDeleted;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String publishName;

    private String type;
    //综合排序,默认按照综合排序的降序排列
    private OrderMethod comprehensiveOrder;
    //按照价格排序
    private OrderMethod priceOrder;
    //按照销量排序
    private OrderMethod saleCountOrder;
}
