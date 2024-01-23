package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName orders
 */
@TableName(value ="orders")
@NoArgsConstructor
@Data
public class Orders implements Serializable {
    private Long id;

    private Long orderId;

    private Long bookId;

    private Integer number;


    private static final long serialVersionUID = 1L;

    public Orders(Long orderId, Long bookId,Integer number) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.number = number;
    }
}