package com.bookStore.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.ReflectLambdaMeta;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookStore.exception.DBOperateException;
import com.bookStore.mapper.*;
import com.bookStore.pojo.*;
import com.bookStore.pojo.pojoenum.OrderStatus;
import com.bookStore.pojo.respojo.OrderReturn;
import com.bookStore.pojo.vo.InOrderBook;
import com.bookStore.pojo.vo.OrderBook;
import com.bookStore.pojo.vo.OrderSelectVo;
import com.bookStore.pojo.vo.OrderVo;
import com.bookStore.service.*;
import com.bookStore.util.OrderNumberGeneratorUtil;
import com.bookStore.util.ThreadLocalUtil;
import io.swagger.models.auth.In;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author 邓桂材
 * @description 针对表【orders_show(订单详情表)】的数据库操作Service实现
 * @createDate 2024-01-14 16:56:54
 */
@Service
public class OrdersShowServiceImpl extends ServiceImpl<OrdersShowMapper, OrdersShow> implements OrdersShowService {
    @Autowired
    private OrdersShowMapper ordersShowMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ShoppingMapper shoppingMapper;
    @Autowired
    private ShoppingService shoppingService;
    @Autowired
    private StockService stockService;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private AddressMapper addressMapper;


//    @Override
//    public Map<String, Object> queryOrdersPageByOrderId(Integer currentPage, Integer size, Long userId, Long orderId, String bookName) {
//        Page<OrderBook> page = new Page<>(currentPage, size);
//
//        IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPage(page, userId, orderId, bookName);
//
//        //封装查询到的内容
//        Map<String, Object> pageInfo = new HashMap<>();
//        pageInfo.put("pageInfo", ordersShowIPage);
//
//        return pageInfo;
//    }

//    @Override
//    public Map<String, Object> queryOrdersPageByStatus(Integer currentPage, Integer size, Long userId, Integer status) {
//        Page<OrderBook> page = new Page<>(currentPage, size);
//        //封装查询到的内容
//        Map<String, Object> pageInfo = new HashMap<>();
//        //待付款
//        if (status.equals(OrderStatus.WAIT_PAYMENT.getCode())) {
//            IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPageByStatus(page, userId, OrderStatus.WAIT_PAYMENT.getCode());
//            pageInfo.put("pageInfo", ordersShowIPage);
//        }
//        //待发货
//        if (status.equals(OrderStatus.WAIT_SEND.getCode())) {
//            IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPageByStatus(page, userId, OrderStatus.WAIT_SEND.getCode());
//            pageInfo.put("pageInfo", ordersShowIPage);
//        }
//        //待收货
//        if (status.equals(OrderStatus.WAIT_RECEIVE.getCode())) {
//            IPage<OrdersShow> ordersShowIPage = ordersShowMapper.selectOrderPageByStatus(page, userId, OrderStatus.WAIT_RECEIVE.getCode());
//            pageInfo.put("pageInfo", ordersShowIPage);
//        }
//        return pageInfo;
//    }

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
                //传入参数缺失，增加订单失败
                return -1;
            }
            Shopping shopping = new Shopping();
            shopping.setBookId(orderVo.getBookId());
            shopping.setNumber(orderVo.getBuyCount());
            List<Shopping> shoppings = new ArrayList<>();
            shoppings.add(shopping);
            return orderGenerate(userId, orderVo, shoppings);
        } else {//从购物车中点解结算后生成的订单
            List<Long> shoppingIdList = Arrays.asList(orderVo.getShoppingIds());
            if (shoppingIdList.size() == 0) {
                //传入参数缺失，增加订单失败
                return -1;
            }
            List<Shopping> shoppings = shoppingMapper.selectBatchIds(shoppingIdList);
            for (Shopping shopping : shoppings) {
                if (!shopping.getUserId().equals(userId)) {
                    //传入参数错误
                    return -3;
                }
            }
            if(shoppings.size() == 0){
                throw new IllegalArgumentException();
            }
            int rows = orderGenerate(userId, orderVo, shoppings);
            //批量删除shopping表中的数据
            shoppingMapper.deleteBatchIds(shoppingIdList);
            return rows;
        }
    }

    private int orderGenerate(Long userId, OrderVo orderVo, List<Shopping> shoppings) {
        //验证库存，如果某一项库存不足，那么整体结算失败
        LambdaQueryWrapper<Stock> stockWrapper = new LambdaQueryWrapper<>();
        List<Long> bookIds = new ArrayList<>();
        for (Shopping shopping : shoppings) {
            bookIds.add(shopping.getBookId());
        }
        stockWrapper.in(Stock::getBookId, bookIds);
        //查询图书id和图书库存
        stockWrapper.select(Stock::getBookId, Stock::getStockNum, Stock::getId);
        List<Map<String, Object>> bookNumMaps = stockMapper.selectMaps(stockWrapper);
        List<Stock> stockList = new ArrayList<>();
        for (Map<String, Object> bookNumMap : bookNumMaps) {
            //查询到的bookNumMaps不会按照bookIds中的id排序，需要重新遍历
            Long id = (Long) bookNumMap.get("id");
            Long bookId = (Long) bookNumMap.get("bookId");
            Integer stockNum = (Integer) bookNumMap.get("stockNum");
            for (Shopping shopping : shoppings) {
                if (shopping.getBookId().equals(bookId)) {
                    if (shopping.getNumber() > stockNum) {//该项商品库存不足
                        return -2;
                    } else {//更新库存
                        Stock stock = new Stock();
                        stock.setId(id);
                        stock.setBookId(bookId);
                        stock.setStockNum(stockNum - shopping.getNumber());
                        stockList.add(stock);
                    }
                }
            }
        }
        //批量更新库存
        stockService.updateBatchById(stockList);
        //查询图书id和图书价格
        LambdaQueryWrapper<Book> bookWrapper = new LambdaQueryWrapper<>();
        bookWrapper.in(Book::getId, bookIds);
        bookWrapper.select(Book::getId, Book::getPrice);
        List<Map<String, Object>> bookIdPriceMaps = bookMapper.selectMaps(bookWrapper);
        //Orders表中增加数据
        List<Orders> ordersList = new ArrayList<>();
        //OrderShow表中增加数据
        Long orderId = OrderNumberGeneratorUtil.generateOrderNumber();
        OrdersShow ordersShow = new OrdersShow();
        ordersShow.setOrderId(orderId);
        ordersShow.setUserId(userId);
        ordersShow.setAddressId(orderVo.getAddressId());
        BigDecimal totalPrice = new BigDecimal(0);
        ordersShow.setStatus(0);
        for (Shopping shopping : shoppings) { //对每一个购物车项插入一条订单信息
            Orders orders = new Orders();
            orders.setOrderId(orderId);
            orders.setBookId(shopping.getBookId());
            orders.setNumber(shopping.getNumber());
            ordersList.add(orders);
            //在OrderShow表中添加数据，可以通过连表查询或者遍历实现
            for (Map<String, Object> bookIdPriceMap : bookIdPriceMaps) {
                Long bookId = ((BigInteger) bookIdPriceMap.get("id")).longValue();
                if (shopping.getBookId().equals(bookId)) {
                    BigDecimal price = (BigDecimal) bookIdPriceMap.get("price");
                    totalPrice = totalPrice.add(price.multiply(new BigDecimal(shopping.getNumber())));
                }
            }
            ordersShow.setTotalPrice(totalPrice);
        }
        //批量添加到Orders表中
        ordersService.saveBatch(ordersList);
        //批量添加到OrderShow表中
        ordersShowMapper.insert(ordersShow);
        return shoppings.size();
    }

    @Override
    public IPage<OrderReturn> selectOrders(Long userId, OrderSelectVo orderSelectVo) {
        IPage<OrderReturn> page = new Page<>(orderSelectVo.getPageNum(), orderSelectVo.getPageSize());
        ordersShowMapper.selectOrderPage(page, userId, orderSelectVo);
        return page;
    }

    @Override
    public int updateOrders(Long userId, Long orderId, Address address) {
        address.setUserId(userId);
        //订单中修改的地址不会保存到用户的地址列表中
        address.setIsDeleted(1);
        address.setDefaultAddress(0);
        int rows = 0;
        try {
            rows = addressMapper.insert(address);
        } catch (Exception e) {
            //参数缺失异常
            throw new RuntimeException(e);
        }
        if (rows == 0) {
            //插入地址失败
            return 0;
        }
        LambdaUpdateWrapper<OrdersShow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OrdersShow::getOrderId, orderId);
        wrapper.set(rows > 0, OrdersShow::getAddressId, address.getId());
        return ordersShowMapper.update(wrapper);
    }

    //删除订单
    @Override
    public int deleteOrders(Long userId, Long orderId) {
        LambdaUpdateWrapper<OrdersShow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OrdersShow::getUserId, userId);
        wrapper.eq(OrdersShow::getOrderId, orderId);
        //设置为已删除
        wrapper.set(OrdersShow::getStatus, OrderStatus.IS_DELETED.getCode());
        return ordersShowMapper.update(wrapper);
    }
    //取消订单


    @Override
    public int cancelOrders(Long userId, Long orderId, Integer addToCart) {
        LambdaQueryWrapper<OrdersShow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrdersShow::getOrderId, orderId);
        OrdersShow ordersShow;
        try {
            ordersShow = ordersShowMapper.selectOne(wrapper);
        } catch (Exception e) {
            //数据库查询出错
            throw new RuntimeException(e);
        }
        if (ordersShow == null || !ordersShow.getStatus().equals(0)) {
            //订单状态异常
            return -1;
        }
        //先从orders表中查询订单详情，以便增加shopping表信息以及增加库存
        LambdaQueryWrapper<Orders> ordersWrapper = new LambdaQueryWrapper<>();
        ordersWrapper.eq(Orders::getOrderId, orderId);
        List<Orders> ordersList = ordersMapper.selectList(ordersWrapper);
        if (addToCart.equals(1)) {//用户想将取消订单的商品加回购物车
            //查询每一本书的价格，以及购买数量
            List<Shopping> shoppingList = ordersMapper.selectBookInfo(ordersList, orderId);
            //shoppingList是要插入或更新到shopping中的实体集合
            for (Shopping shopping : shoppingList) {
                shopping.setUserId(userId);
            }
            //将每一个shopping加入shopping表中
            //查询该用户中购物车项（shopping）信息
            LambdaQueryWrapper<Shopping> shoppingLambdaQueryWrapper = new LambdaQueryWrapper<>();
            shoppingLambdaQueryWrapper.eq(Shopping::getUserId, userId);
            shoppingLambdaQueryWrapper.select(Shopping::getId, Shopping::getBookId);
            List<Shopping> shoppings = shoppingMapper.selectList(shoppingLambdaQueryWrapper);
            //将bookId相同的shopping的id赋值给shoppingList中shopping的Id，用于标识shopping数据需要添加或更新
            for (Shopping shopping : shoppings) {
                for (Shopping shoppingEntity : shoppingList) {
                    if (shopping.getBookId().equals(shoppingEntity.getBookId())) {
                        shoppingEntity.setId(shopping.getId());
                    }
                }
            }
            //如果shoppingList中有主键id值，则会更新，否则会插入到
            shoppingMapper.insertOrUpdateBatch(shoppingList);
        }
        //将订单中每一件商品库存增加
        stockMapper.updateBatch(ordersList);
        //将订单状态改为已取消
        LambdaUpdateWrapper<OrdersShow> ordersShowLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        ordersShowLambdaUpdateWrapper.eq(OrdersShow::getOrderId, orderId);
        ordersShowLambdaUpdateWrapper.set(OrdersShow::getStatus, OrderStatus.CANCEL.getCode());
        return ordersShowMapper.update(ordersShowLambdaUpdateWrapper);
    }
}




