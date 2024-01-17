package com.bookStore.mapper;

import com.bookStore.pojo.BookType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 邓桂材
* @description 针对表【book_type】的数据库操作Mapper
* @createDate 2024-01-17 10:40:09
* @Entity com.bookStore.pojo.BookType
*/
@Mapper
public interface BookTypeMapper extends BaseMapper<BookType> {

}




