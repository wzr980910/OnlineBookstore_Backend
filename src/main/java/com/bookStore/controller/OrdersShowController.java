package com.bookStore.controller;


import com.bookStore.pojo.Shopping;
import com.bookStore.pojo.vo.InOrderBook;
import com.bookStore.pojo.vo.OrderBook;
import com.bookStore.pojo.vo.OrderVo;
import com.bookStore.service.OrdersShowService;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author wmh
 * @date 2024/01/17 18:48
 */
@RestController
@RequestMapping("/ordersshow")
@Api(value = "订单接口", tags = "订单相关的接口", description = "订单测试接口")
public class OrdersShowController {
    @Autowired
    private OrdersShowService ordersShowService;

    /**
     * 通过订单编号或图书名查订单
     *
     * @param currentPage
     * @param size
     * @param orderId
     * @param bookName
     * @return
     */
    @PostMapping("/queryAllByOrders")
    @ApiOperation(value = "查询订单信息", notes = "可以通过订单号查询，也可以通过书名查询")
    public RestResult queryAllByOrders(@RequestParam(defaultValue = "1") Integer currentPage,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       Long orderId, String bookName) {
        Long userId = ThreadLocalUtil.get();
        Map<String, Object> map = ordersShowService.queryOrdersPageByOrderId(currentPage, size, userId, orderId, bookName);
        return RestResult.success(ResultCode.SUCCESS, "查询成功", map);

    }

    /**
     * 通过订单状态查询订单
     *
     * @param currentPage
     * @param size
     * @param status
     * @return
     */
    @PostMapping("/queryAllByStatus")
    @ApiOperation(value = "通过订单状态查询订单", notes = "传 0:待付款 1:待发货 2:待收货")
    public RestResult queryAllByStatus(@RequestParam(defaultValue = "1") Integer currentPage,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam Integer status) {
        Long userId = ThreadLocalUtil.get();
        Map<String, Object> map = ordersShowService.queryOrdersPageByStatus(currentPage, size, userId, status);
        return RestResult.success(ResultCode.SUCCESS, "查询成功", map);
    }


//    @PostMapping("/addOrders")
//    @ApiOperation(value = "添加订单", notes = "图书id和图书数量 既可以传单个也可以传多个 但是要一一对应")
//    public RestResult addOrders(@RequestBody InOrderBook inOrderBook) {
//        Long userId = ThreadLocalUtil.get();
//        Integer rows = ordersShowService.addOrders(userId, inOrderBook);
//        if (rows > 0) {
//            return RestResult.success(ResultCode.SUCCESS, "添加订单成功", rows);
//        } else {
//            return RestResult.success(ResultCode.OPERATION_FAILURE, "添加失败");
//        }
//    }
    @PostMapping("addOrders")
    @ApiOperation(value = "添加订单")
    public RestResult addOrder(@RequestBody OrderVo orderVo){
        Long userId = (Long) ThreadLocalUtil.get();
        //生成订单
        Integer rows = ordersShowService.addOrders(userId, orderVo);
        return null;
    }



}
