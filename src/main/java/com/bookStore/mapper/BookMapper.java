package com.bookStore.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bookStore.pojo.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookStore.pojo.strategy.ComprehensiveOrder;
import com.bookStore.pojo.vo.BookVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 邓桂材
* @description 针对表【book(图书表)】的数据库操作Mapper
* @createDate 2024-01-14 16:56:54
* @Entity com.bookStore.pojo.Book
*/
@Mapper
public interface BookMapper extends BaseMapper<Book> {

    IPage<BookVo> selectBookPage(IPage<?> page , @Param("bookVo") BookVo bookVo, @Param("comprehensiveOrderParam") ComprehensiveOrder comprehensiveOrder);
}




