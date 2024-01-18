package com.bookStore.controller;

import com.bookStore.pojo.Address;
import com.bookStore.service.AddressService;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "添加地址", notes = "添加地址")
    @PostMapping("addAddress")
    public RestResult addAddress(@RequestBody @Valid Address address) {
        RestResult restResult = new RestResult(ResultCode.OPERATION_FAILURE);
        Long userId =  ThreadLocalUtil.get();
        int rows = addressService.addAddress(userId, address);
        //插入成功
        if (rows > 0) {
            restResult = new RestResult(ResultCode.SUCCESS);
        }
        return restResult;
    }

    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "修改地址", notes = "修改地址")
    @PutMapping("updateAddress")
    public RestResult updateAddress(@RequestBody @Valid Address address) {
        RestResult restResult = new RestResult(ResultCode.OPERATION_FAILURE);
        //没有地址id值
        if (address.getId() == null) {
            return restResult;
        }
        Long userId = (Long) ThreadLocalUtil.get();
        int rows = addressService.updateAddress(userId, address);
        if (rows > 0) {
            restResult = new RestResult(ResultCode.SUCCESS);
        }
        return restResult;
    }

    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "删除地址", notes = "删除地址")
    @DeleteMapping("deleteAddress")
    public RestResult deleteAddress(@NotNull Long addressId) {
        RestResult restResult = new RestResult(ResultCode.OPERATION_FAILURE);
        //没有传入地址Id
        if(addressId == null){
            return restResult;
        }
        Long userId = (Long) ThreadLocalUtil.get();
        int rows = addressService.deleteAddress(userId, addressId);
        if (rows > 0) {
            restResult = new RestResult(ResultCode.SUCCESS);
        }
        return restResult;
    }


    //说明是什么方法(可以理解为方法注释)
    @ApiOperation(value = "查询地址", notes = "查询地址")
    @GetMapping("selectAddress")
    public RestResult selectAddress() {
        Long userId = (Long) ThreadLocalUtil.get();
        RestResult restResult = new RestResult(ResultCode.SUCCESS);
        Map<String, Object> addressMap = addressService.selectAddress(userId);
        restResult.setData(addressMap);
        return restResult;
    }
}
