package com.bookStore.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bookStore.pojo.Address;
import com.bookStore.pojo.OrdersShow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bookStore.pojo.pojoenum.OrderStatus;
import com.bookStore.pojo.respojo.OrderReturn;
import com.bookStore.pojo.vo.InOrderBook;
import com.bookStore.pojo.vo.OrderBook;
import com.bookStore.pojo.vo.OrderSelectVo;
import com.bookStore.pojo.vo.OrderVo;

import java.util.List;
import java.util.Map;

/**
 * @author 邓桂材
 * @description 针对表【orders_show(订单详情表)】的数据库操作Service
 * @createDate 2024-01-14 16:56:54
 */
public interface OrdersShowService extends IService<OrdersShow> {

//    Map<String,Object> queryOrdersPageByOrderId(Integer currentPage, Integer size,
//                                                Long userId, Long orderId, String bookName);
//
//    Map<String,Object> queryOrdersPageByStatus(Integer currentPage, Integer size,
//                                               Long userId,Integer status);

    Long addOrders(Long userId, OrderVo orderVo);

    IPage<OrderReturn> selectOrders(Long userId, OrderSelectVo orderSelectVo);

    int updateOrders(Long userId, Long orderId, Address address);

    int deleteOrders(Long userId,Long orderId);

    int cancelOrders(Long userId, Long orderId,Integer addToCart);

//    Integer addOrders(Long userId, InOrderBook inOrderBook);

}
