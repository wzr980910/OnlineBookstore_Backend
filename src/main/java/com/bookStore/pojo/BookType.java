package com.bookStore.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName book_type
 */
@TableName(value ="book_type")
@Data
public class BookType implements Serializable {
    private Long id;

    private Long bookId;

    private Long typeId;

    private static final long serialVersionUID = 1L;
}