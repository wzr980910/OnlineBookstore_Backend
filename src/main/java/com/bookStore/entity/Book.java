package com.bookStore.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/13/20:28
 * @Description:图书实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Integer bookID;                     //图书id

    private String bookName;                    //图书名称

    private String author;                      //图书作者

    private String press;                       //出版社

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date pressDate;                     //出版日期

    private Integer stockNum;                   //库存数量

    private String picture;                     //图书封面

    private String content;                     //图书简介

    private Integer typeID;                     //图书类型

}
