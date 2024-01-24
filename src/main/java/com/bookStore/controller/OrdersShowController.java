package com.bookStore.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bookStore.pojo.Address;
import com.bookStore.pojo.OrdersShow;
import com.bookStore.pojo.Shopping;
import com.bookStore.pojo.respojo.OrderReturn;
import com.bookStore.pojo.vo.InOrderBook;
import com.bookStore.pojo.vo.OrderBook;
import com.bookStore.pojo.vo.OrderSelectVo;
import com.bookStore.pojo.vo.OrderVo;
import com.bookStore.service.OrdersShowService;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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
//    @PostMapping("/queryAllByOrders")
//    @ApiOperation(value = "查询订单信息", notes = "可以通过订单号查询，也可以通过书名查询")
//    public RestResult queryAllByOrders(@RequestParam(defaultValue = "1") Integer currentPage,
//                                       @RequestParam(defaultValue = "10") Integer size,
//                                       Long orderId, String bookName) {
//        Long userId = ThreadLocalUtil.get();
//        Map<String, Object> map = ordersShowService.queryOrdersPageByOrderId(currentPage, size, userId, orderId, bookName);
//        return RestResult.success(ResultCode.SUCCESS, "查询成功", map);
//
//    }

    /**
     * 通过订单状态查询订单
     *
     * @param
     * @param
     * @param
     * @return
     */
//    @PostMapping("/queryAllByStatus")
//    @ApiOperation(value = "通过订单状态查询订单", notes = "传 0:待付款 1:待发货 2:待收货")
//    public RestResult queryAllByStatus(@RequestParam(defaultValue = "1") Integer currentPage,
//                                       @RequestParam(defaultValue = "10") Integer size,
//                                       @RequestParam Integer status) {
//        Long userId = ThreadLocalUtil.get();
//        Map<String, Object> map = ordersShowService.queryOrdersPageByStatus(currentPage, size, userId, status);
//        return RestResult.success(ResultCode.SUCCESS, "查询成功", map);
//    }


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
    @PostMapping("/addOrders")
    @ApiOperation(value = "添加订单")
    public RestResult addOrders(@RequestBody OrderVo orderVo) {
        RestResult restResult = new RestResult(ResultCode.SUCCESS);
        Long userId = ThreadLocalUtil.get();
        //生成订单
        Integer rows = ordersShowService.addOrders(userId, orderVo);
        if (rows == -2) {//库存不足
            restResult = new RestResult(ResultCode.STOCK_NUM_ZERO);
            return restResult;
        } else if (rows == -1) {//传入参数缺失
            restResult = new RestResult(ResultCode.PARAM_NOT_COMPLETE);
            return restResult;
        }
        return restResult;
    }

    @PostMapping("selectOrders")
    @ApiOperation(value = "查询订单")
    public RestResult selectOrders(@RequestBody OrderSelectVo orderSelectVo) {
        RestResult restResult = new RestResult(ResultCode.SUCCESS);
        Long userId = ThreadLocalUtil.get();
        //查询订单
        IPage<OrderReturn> page = ordersShowService.selectOrders(userId, orderSelectVo);
        restResult.setData(page);
        return restResult;
    }

    @PostMapping("updateOrders")
    @ApiOperation(value = "修改订单地址")
    public RestResult updateOrders(@RequestParam Long orderId, @RequestBody Address address) {
        Long userId = ThreadLocalUtil.get();
        if (orderId == null || orderId.toString().isEmpty()) {
            //参数缺失
            return new RestResult(ResultCode.PARAM_NOT_COMPLETE);
        }
        int rows = ordersShowService.updateOrders(userId, orderId, address);
        if (rows == 0) {
            return new RestResult(ResultCode.DB_ERROR);
        }
        return new RestResult(ResultCode.SUCCESS);
    }

    @GetMapping("deleteOrders")
    @ApiOperation(value = "删除订单地址")
    public RestResult deleteOrders(@RequestParam Long orderId) {
        Long userId = ThreadLocalUtil.get();
        if (orderId == null || orderId.toString().isEmpty()) {
            //参数缺失
            return new RestResult(ResultCode.PARAM_NOT_COMPLETE);
        }
        int rows = ordersShowService.deleteOrders(userId, orderId);
        if (rows == 0) {
            return new RestResult(ResultCode.OPERATION_FAILURE);
        }
        return new RestResult(ResultCode.SUCCESS);
    }

    @GetMapping("cancelOrders")
    @ApiOperation(value = "取消未付款订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单编号", required = true),
            @ApiImplicitParam(name = "addToCart", value = "取消订单后商品加入购物车,0否1是")
    })
    public RestResult cancelOrders(@RequestParam Long orderId, @RequestParam(defaultValue = "0") Integer addToCart) {
        Long userId = ThreadLocalUtil.get();
        if (orderId == null || orderId.toString().isEmpty()) {
            //参数缺失
            return new RestResult(ResultCode.PARAM_NOT_COMPLETE);
        }
        int rows = ordersShowService.cancelOrders(userId, orderId, addToCart);
        if (rows == 0) {
            return new RestResult(ResultCode.OPERATION_FAILURE);
        }
        return new RestResult(ResultCode.SUCCESS);
    }

}
