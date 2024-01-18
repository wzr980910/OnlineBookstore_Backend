package com.bookStore.controller;

import com.bookStore.annotation.PassToken;
import com.bookStore.pojo.Type;
import com.bookStore.service.TypeService;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wmh
 * @date 2024/01/17 16:05
 */
@RestController
@RequestMapping("/type")
@Api(value = "图书类别接口", tags = "图书类别相关的接口", description = "图书类别测试接口")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @PassToken
    @GetMapping(value = "/queryAllType")
    @ApiOperation(value = "查询所有类别信息" ,notes="不需要传参数")
    public RestResult queryAllType(){
        List<Type> types = typeService.queryAllType();
        if (types == null){
            return RestResult.failure(ResultCode.OPERATION_FAILURE,"查询失败");
        }else {
            return RestResult.success(ResultCode.SUCCESS,"查询成功",types);
        }
    }
    @PassToken
    @GetMapping(value = "/queryByParentId")
    @ApiOperation(value = "根据父类查询所有子类信息" ,notes="parentId 必填")
    public RestResult queryByParentId(@RequestParam Long parentId){
        List<Type> types = typeService.queryByParentId(parentId);
        if (types == null){
            return RestResult.failure(ResultCode.OPERATION_FAILURE,"查询失败");
        }else {
            return RestResult.success(ResultCode.SUCCESS,"查询成功",types);
        }
    }


}
