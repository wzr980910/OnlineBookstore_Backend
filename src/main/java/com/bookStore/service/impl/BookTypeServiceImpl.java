package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.BookType;
import com.bookStore.service.BookTypeService;
import com.bookStore.mapper.BookTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author 邓桂材
* @description 针对表【book_type】的数据库操作Service实现
* @createDate 2024-01-17 10:40:09
*/
@Service
public class BookTypeServiceImpl extends ServiceImpl<BookTypeMapper, BookType>
    implements BookTypeService{

}




