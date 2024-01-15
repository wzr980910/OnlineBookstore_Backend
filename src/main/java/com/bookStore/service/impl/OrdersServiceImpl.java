package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.Orders;
import com.bookStore.service.OrdersService;
import com.bookStore.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author 邓桂材
* @description 针对表【orders(订单)】的数据库操作Service实现
* @createDate 2024-01-14 16:56:54
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




