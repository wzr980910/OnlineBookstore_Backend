package com.bookStore.controller;

import com.bookStore.pojo.Shopping;
import com.bookStore.service.ShoppingService;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
     * 根据用户账号  查询购物车信息
     * @param userName
     * @return
     */
    @GetMapping(value = "/findAllByAccountName")
    @ApiOperation(value = "查询购物车信息", notes = "账户必填")
    public RestResult findAllByAccountName(@RequestParam String userName) {
        Map<String, Object> mapBooks = shoppingService.findAllByAccountName(userName);
        return RestResult.success(ResultCode.SUCCESS, "购物车信息", mapBooks);
    }

    @GetMapping(value = "/addShopping")
    @ApiOperation(value = "添加购物车信息", notes = "图书id 图书名字 用户账号 价格 购买数量 必填")
    public RestResult addShopping(@RequestParam Integer bookId,@RequestParam String userName,
                                  @RequestParam String bookName,@RequestParam BigDecimal price,
                                  @RequestParam Integer number){
        Shopping shopping = new Shopping();
        shopping.setBookId(Long.valueOf(bookId));
        shopping.setUserNumberBookId(userName);
        shopping.setBookName(bookName);
        shopping.setPrice(price);
        shopping.setNumber(number);
        Integer rows = shoppingService.addShopping(shopping);
        if (rows>0) {
            return RestResult.success(ResultCode.SUCCESS, "添加购物车成功", rows);
        }else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE,"添加失败");
        }
    }

    @PostMapping(value = "/deleteShopping")
    @ApiOperation(value = "删除购物车信息", notes = "可以删除一个，也可以删除多个")
    public RestResult deleteShopping(String[] idsList){
        List<String> strings = Arrays.asList(idsList);
        if (shoppingService.removeByIds(strings)){
            return RestResult.success(ResultCode.SUCCESS,"删除成功",idsList);
        }
        return RestResult.failure(ResultCode.OPERATION_FAILURE,"删除失败");
    }




}
