package com.bookStore.controller;

import com.bookStore.pojo.Shopping;

import com.bookStore.service.ShoppingService;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 *
 * @author wmh
 * @date 2024/01/15 16:26
 */
@RestController
@RequestMapping("/shopping")
@Api(value = "购物车接口", tags = "购物车相关的接口", description = "购物车测试接口")
public class ShoppingController {
    private ShoppingService shoppingService;

    @Autowired
    public void setShoppingService(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    /**
     * 根据用户id  查询购物车信息
     *
     * @return
     */
    @GetMapping(value = "/findAllByUserId")
    @ApiOperation(value = "查询购物车信息")
    public RestResult findAllByUserId(@RequestParam(defaultValue = "1") Integer currentPage,
                                      @RequestParam(defaultValue = "10") Integer size) {
        //获得userId
        Long userId = ThreadLocalUtil.get();
        Map<String, Object> mapBooks = shoppingService.findAllByUserId(currentPage, size, userId);
        if (mapBooks != null) {
            return RestResult.success(ResultCode.SUCCESS, "查询成功", mapBooks);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "查询失败");
        }
    }

    @PostMapping(value = "/addShopping")
    @ApiOperation(value = "添加购物车信息", notes = "图书id 图书名字 图书价格 图书数量 必填")
    public RestResult addShopping(@RequestBody Shopping shopping) {
        //获得userId
        Long userId = ThreadLocalUtil.get();
        shopping.setUserId(userId);
        Integer rows = shoppingService.addShopping(shopping);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "添加购物车成功", rows);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "添加失败");
        }
    }

    @PostMapping(value = "/deleteShopping")
    @ApiOperation(value = "删除购物车信息", notes = "可以删除一个，也可以删除多个 通过bookId删除")
    public RestResult deleteShopping(@RequestBody String[] bookIdsList) {
        List<String> strings = Arrays.asList(bookIdsList);
        Long userId = ThreadLocalUtil.get();
        Integer rows = shoppingService.deleteShopping(userId, strings);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS,"删除成功",rows);
        }
        return RestResult.failure(ResultCode.OPERATION_FAILURE, "删除失败");
    }

    @PostMapping(value = "/updateShopping")
    @ApiOperation(value = "更新购物车信息", notes = "更新购物车信息")
    public RestResult updateShopping(@RequestBody Shopping shopping) {
        Long userId = ThreadLocalUtil.get();
        Integer rows = shoppingService.updateShopping(userId, shopping);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS,"更新成功",rows);
        }
        return RestResult.failure(ResultCode.OPERATION_FAILURE, "更新失败");
    }

}
