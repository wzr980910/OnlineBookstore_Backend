package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Book;
import com.bookStore.pojo.order.ComprehensiveOrder;
import com.bookStore.pojo.vo.BookVo;
import com.bookStore.service.BookService;
import com.bookStore.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 邓桂材
 * @description 针对表【book(图书表)】的数据库操作Service实现
 * @createDate 2024-01-14 16:56:54
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
        implements BookService {
    private BookMapper bookMapper;
    @Autowired
    private ComprehensiveOrder comprehensiveOrder;

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }


    public Page<BookVo> selectBookPage(BookVo bookVo) {
        //分页
        Page<BookVo> page = new Page<>(bookVo.getPageNum(), bookVo.getPageSize());
        //查询
        bookMapper.selectBookPage(page, bookVo, comprehensiveOrder);
        return page;


    }
}




