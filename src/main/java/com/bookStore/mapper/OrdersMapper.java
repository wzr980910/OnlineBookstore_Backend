package com.bookStore.mapper;

import com.bookStore.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 邓桂材
* @description 针对表【orders(订单)】的数据库操作Mapper
* @createDate 2024-01-14 16:56:54
* @Entity com.bookStore.pojo.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    int insertOrdersBatch(List<Orders> ordersList);

}




