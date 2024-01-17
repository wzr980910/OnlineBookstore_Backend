package com.bookStore.pojo.respojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wmh
 * @date 2024/01/15 16:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartBook {
    /**
     * 图书名
     */
    private String bookName;
    /**
     *图书ISBN
     */
    private String bookIsbn;
    /**
     * 作者
     */
    private String author;
    /**
     * 图书封面
     */
    private String img;
    /**
     * 出版社
     */
    private String publishName;
    /**
     * 出版时间
     */
    private Date publishDate;
    /**
     * 图书价格
     */
    private BigDecimal price;
    /**
     * 购买数量
     */
    private Integer number;

    /**
     * 库存数量
     */
    private Integer stockNum;

}
