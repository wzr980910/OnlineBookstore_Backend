package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Stock;
import com.bookStore.service.StockService;
import com.bookStore.mapper.StockMapper;
import org.springframework.stereotype.Service;

/**
* @author 邓桂材
* @description 针对表【stock(库存表)】的数据库操作Service实现
* @createDate 2024-01-14 16:56:54
*/
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock>
    implements StockService{

}




