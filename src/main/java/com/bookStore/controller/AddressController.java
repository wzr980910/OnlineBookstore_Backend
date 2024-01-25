package com.bookStore.controller;

import com.bookStore.exception.BizException;
import com.bookStore.pojo.Address;
import com.bookStore.service.AddressService;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author wmh
 * @date 2024/01/15 16:04
 */
@RestController
@RequestMapping("/address")
@Api(value = "地址接口", tags = "地址相关的接口", description = "地址测试接口")
public class AddressController {
    @Autowired
    private AddressService addressService;

    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "添加地址", notes = "添加地址,此时地址Id不需要")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 101, message = "操作失败")
    })
    @PostMapping("addAddress")
    public RestResult addAddress(@RequestBody @Valid Address address) {
        Long userId = ThreadLocalUtil.get();
        int rows = addressService.addAddress(userId, address);
        //插入成功
        if (rows == 0) {
            return new RestResult(ResultCode.DB_INSERT_ERROR);
        } else {
            return new RestResult(ResultCode.SUCCESS);
        }
    }

    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "修改地址", notes = "修改地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 101, message = "操作失败"),
            @ApiResponse(code = 1004, message = "参数缺失")
    })
    @PutMapping("updateAddress")
    public RestResult updateAddress(@RequestBody @Valid Address address) {
        //没有地址id值
        if (address.getId() == null) {
            return new RestResult(ResultCode.PARAM_NOT_COMPLETE);
        }
        Long userId = ThreadLocalUtil.get();
        int rows = addressService.updateAddress(userId, address);
        if (rows > 0) {
            return new RestResult(ResultCode.SUCCESS);
        } else {
            return new RestResult(ResultCode.DB_UPDATE_ERROR);
        }
    }


    //说明是什么方法(可以理解为方法注释)
    @ApiImplicitParam(name = "addreddId", value = "需要删除地址的Id", required = true, paramType = "form")
    @ApiOperation(value = "删除地址", notes = "删除地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 101, message = "操作失败"),
            @ApiResponse(code = 1004, message = "参数缺失")
    })
    @DeleteMapping("deleteAddress")
    public RestResult deleteAddress(@NotNull Long addressId) {
        //没有传入地址Id
        if (addressId == null) {
            return new RestResult(ResultCode.PARAM_NOT_COMPLETE);
        }
        Long userId = ThreadLocalUtil.get();
        int rows = addressService.deleteAddress(userId, addressId);
        if (rows > 0) {
            return new RestResult(ResultCode.SUCCESS);
        } else {
            return new RestResult(ResultCode.DB_DELETE_ERROR);
        }
    }


    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "查询地址", notes = "查询地址")
    @GetMapping("selectAddress")
    public RestResult selectAddress() {
        Long userId = ThreadLocalUtil.get();
        Map<String, Object> addressMap = addressService.selectAddress(userId);
        return new RestResult(ResultCode.SUCCESS,addressMap);
    }
}
