package com.bookStore.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookStore.pojo.Book;
import com.bookStore.pojo.vo.BookVo;
import com.bookStore.service.BookService;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: BookController
 * Package: com.bookStore.controller
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/15 -10:53
 * @Version: v1.0
 */
@RestController
@RequestMapping("/book")
//说明接口文件
@Api(value = "图书接口", tags = "图书相关接口", description = "图书测试接口")
public class BookController {
    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "查询图书")
    @ApiResponses({
            @ApiResponse(code = 1, message = "操作成功")
    })
    @PostMapping("/selectBookPage")
    public RestResult selectBookByPage(@RequestBody BookVo bookVo) {
        Map<String, Object> map = bookService.selectBookPage(bookVo);
        //查询成功，包装数据返回
        return new RestResult(ResultCode.SUCCESS, map);
    }

}
