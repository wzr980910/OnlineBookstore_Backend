package com.bookStore.mapper;

import com.bookStore.pojo.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 邓桂材
* @description 针对表【stock(库存表)】的数据库操作Mapper
* @createDate 2024-01-14 16:56:54
* @Entity com.bookStore.pojo.Stock
*/
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

}




