package com.bookStore.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.mapper.*;
import com.bookStore.pojo.*;
import com.bookStore.pojo.pojoenum.OrderStatus;
import com.bookStore.pojo.vo.InOrderBook;
import com.bookStore.pojo.vo.OrderBook;
import com.bookStore.pojo.vo.OrderVo;
import com.bookStore.service.OrdersShowService;
import com.bookStore.util.OrderNumberGeneratorUtil;
import com.bookStore.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ShoppingMapper shoppingMapper;

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
            pageInfo.put("pageInfo", ordersShowIPage);
        }
        //待发货
        if (status.equals(OrderStatus.WAIT_SEND.getCode())) {
            IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPageByStatus(page, userId, OrderStatus.WAIT_SEND.getCode());
            pageInfo.put("pageInfo", ordersShowIPage);
        }
        //待收货
        if (status.equals(OrderStatus.WAIT_RECEIVE.getCode())) {
            IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPageByStatus(page, userId, OrderStatus.WAIT_RECEIVE.getCode());
            pageInfo.put("pageInfo", ordersShowIPage);
        }
        return pageInfo;
    }

//    @Override
//    public Integer addOrders(Long userId, InOrderBook inOrderBook) {
//        OrdersShow ordersShow = new OrdersShow();
//        //自动生成订单编号
//        Long orderId = OrderNumberGeneratorUtil.generateOrderNumber();
//        ordersShow.setOrderId(orderId);
//        ordersShow.setAddressId(inOrderBook.getAddressId());
//        ordersShow.setUserId(userId);
//        String[] bookIds = inOrderBook.getBookIds();
//        String[] numbers = inOrderBook.getNumbers();
//        List<Orders> ordersList = new ArrayList<>();
//        for (int i = 0; i < bookIds.length; i++) {
//            Orders orders = new Orders(orderId, Long.valueOf(bookIds[i]), Integer.valueOf(numbers[i]));
//            ordersList.add(orders);
//        }
//
//        //批量插入数据
//        ordersMapper.insertOrdersBatch(ordersList);
//
//        ordersShow.setTotalPrice(inOrderBook.getTotalPrice());
//        //待付款
//        ordersShow.setStatus(OrderStatus.WAIT_PAYMENT.getCode());
//        int insert = ordersShowMapper.insert(ordersShow);
//        return insert;
//    }

    @Override
    public Integer addOrders(Long userId, OrderVo orderVo) {
        if (orderVo.getShoppingIds() == null || orderVo.getShoppingIds().length == 0) {//直接购买图书
            if (orderVo.getBookId() == null) {
                //传入参数不合法，增加订单失败
                return -1;
            }
            //验证是否有库存
            LambdaQueryWrapper<Stock> stockWrapper = new LambdaQueryWrapper<>();
            stockWrapper.eq(Stock::getBookId, orderVo.getBookId());
            Stock stock = stockMapper.selectOne(stockWrapper);
            if (stock.getStockNum() < orderVo.getBuyCount()) {
                //库存不足，增加订单失败
                return -2;
            }
            stock.setStockNum(stock.getStockNum() - orderVo.getBuyCount());
            //更新库存表
            stockMapper.updateById(stock);
            //在订单详情表中插入一条数据
            Long orderId = OrderNumberGeneratorUtil.generateOrderNumber();
            Orders orders = new Orders();
            orders.setOrderId(orderId);
            orders.setBookId(orderVo.getBookId());
            orders.setNumber(orders.getNumber());
            ordersMapper.insert(orders);
            //在订单表中增加一条数据
            Book book = bookMapper.selectById(orderVo.getBookId());
            BigDecimal price = book.getPrice();
            BigDecimal totalPrice = price.multiply(new BigDecimal(orderVo.getBuyCount()));
            Date date = new Date();
            OrdersShow ordersShow = new OrdersShow();
            ordersShow.setOrderId(orderId);
            ordersShow.setUserId(userId);
            ordersShow.setAddressId(orderVo.getAddressId());
            ordersShow.setDate(date);
            ordersShow.setTotalPrice(totalPrice);
            ordersShow.setStatus(0);
            return ordersShowMapper.insert(ordersShow);
        } else {//从购物车中点解结算后生成的订单
            List<Long> shoppingIdList = Arrays.asList(orderVo.getShoppingIds());
            List<Shopping> shoppings = shoppingMapper.selectBatchIds(shoppingIdList);
            //验证库存，如果某一项库存不足，那么整体结算失败
            LambdaQueryWrapper<Stock> stockWrapper = new LambdaQueryWrapper<>();
            List<Long> bookIds = new ArrayList<>();
            for (Shopping shopping : shoppings) {
                bookIds.add(shopping.getBookId());
            }
            stockWrapper.in(Stock::getBookId, bookIds);
            //查询图书id和图书库存
            stockWrapper.select(Stock::getBookId, Stock::getStockNum);
            List<Map<String, Object>> bookNumMaps = stockMapper.selectMaps(stockWrapper);
            for (Map<String, Object> bookNumMap : bookNumMaps) {
                //查询到的bookNumMaps不会按照bookIds中的id排序，需要重新遍历
                Long bookId = (Long)bookNumMap.get("bookId");
                Long stockNum = (Long)bookNumMap.get("stockNum");
                for (Shopping shopping : shoppings) {
                    if(shopping.getBookId().equals(bookId)){
                        if(shopping.getNumber()>stockNum){//该项商品库存不足
                            return -2;
                        }else{//

                        }
                    }
                }

            }
        }
return null;
    }
}




