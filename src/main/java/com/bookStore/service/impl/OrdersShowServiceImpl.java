package com.bookStore.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.mapper.OrdersMapper;
import com.bookStore.pojo.Orders;
import com.bookStore.pojo.OrdersShow;
import com.bookStore.pojo.pojoenum.OrderStatus;
import com.bookStore.pojo.vo.InOrderBook;
import com.bookStore.pojo.vo.OrderBook;
import com.bookStore.service.OrdersShowService;
import com.bookStore.mapper.OrdersShowMapper;
import com.bookStore.util.OrderNumberGeneratorUtil;
import com.bookStore.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 邓桂材
 * @description 针对表【orders_show(订单详情表)】的数据库操作Service实现
 * @createDate 2024-01-14 16:56:54
 */
@Service
public class OrdersShowServiceImpl extends ServiceImpl<OrdersShowMapper, OrdersShow>
        implements OrdersShowService {
    @Autowired
    private OrdersShowMapper ordersShowMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public Map<String, Object> queryOrdersPageByOrderId(Integer currentPage, Integer size, Long userId, Long orderId, String bookName) {
        Page<OrderBook> page = new Page<>(currentPage, size);

        IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPage(page, userId, orderId, bookName);

        //封装查询到的内容
        Map<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("pageInfo", ordersShowIPage);

        return pageInfo;
    }

    @Override
    public Map<String, Object> queryOrdersPageByStatus(Integer currentPage, Integer size, Long userId, Integer status) {
        Page<OrderBook> page = new Page<>(currentPage, size);
        //封装查询到的内容
        Map<String, Object> pageInfo = new HashMap<>();
        //待付款
        if (status.equals(OrderStatus.WAIT_PAYMENT.getCode())) {
            IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPageByStatus(page, userId, OrderStatus.WAIT_PAYMENT.getCode());
            pageInfo.put("pageInfo",ordersShowIPage);
        }
        //待发货
        if (status.equals(OrderStatus.WAIT_SEND.getCode())) {
            IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPageByStatus(page, userId, OrderStatus.WAIT_SEND.getCode());
            pageInfo.put("pageInfo",ordersShowIPage);
        }
        //待收货
        if (status.equals(OrderStatus.WAIT_RECEIVE.getCode())) {
            IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPageByStatus(page, userId, OrderStatus.WAIT_RECEIVE.getCode());
            pageInfo.put("pageInfo",ordersShowIPage);
        }
        return pageInfo;
    }

    @Override
    public Integer addOrders(Long userId, InOrderBook inOrderBook) {
        OrdersShow ordersShow = new OrdersShow();
        //自动生成订单编号
        Long orderId = OrderNumberGeneratorUtil.generateOrderNumber();
        ordersShow.setOrderId(orderId);
        ordersShow.setAddressId(inOrderBook.getAddressId());
        ordersShow.setUserId(userId);
        String[] bookIds = inOrderBook.getBookIds();
        String[] numbers = inOrderBook.getNumbers();
        List<Orders> ordersList = new ArrayList<>();
        for (int i = 0; i < bookIds.length; i++) {
            Orders orders = new Orders(orderId, Long.valueOf(bookIds[i]), Integer.valueOf(numbers[i]));
            ordersList.add(orders);
        }

        //批量插入数据
        ordersMapper.insertOrdersBatch(ordersList);

        ordersShow.setTotalPrice(inOrderBook.getTotalPrice());
        //待付款
        ordersShow.setStatus(OrderStatus.WAIT_PAYMENT.getCode());
        int insert = ordersShowMapper.insert(ordersShow);
        return insert;
    }
}




