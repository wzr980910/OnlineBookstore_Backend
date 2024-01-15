package com.bookStore.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.pojo.OrdersShow;
import com.bookStore.service.OrdersShowService;
import com.bookStore.mapper.OrdersShowMapper;
import org.springframework.stereotype.Service;

/**
* @author 邓桂材
* @description 针对表【orders_show(订单详情表)】的数据库操作Service实现
* @createDate 2024-01-14 16:56:54
*/
@Service
public class OrdersShowServiceImpl extends ServiceImpl<OrdersShowMapper, OrdersShow>
    implements OrdersShowService{

}




