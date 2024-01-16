package com.bookStore.service;

import com.bookStore.pojo.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookStore.pojo.vo.BookVo;

import java.util.HashMap;
import java.util.Map;

/**
* @author 邓桂材
* @description 针对表【book(图书表)】的数据库操作Service
* @createDate 2024-01-14 16:56:54
*/
public interface BookService extends IService<Book> {

    Map<String,Object> selectBookPage(BookVo bookVo);

}
