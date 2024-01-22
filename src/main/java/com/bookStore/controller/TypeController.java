package com.bookStore.controller;

import com.bookStore.annotation.PassToken;
import com.bookStore.pojo.Type;
import com.bookStore.service.TypeService;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/queryAllType")
    @ApiOperation(value = "查询所有类别信息", notes = "不需要传参数")
    public RestResult queryAllType() {
        Map<String, Object> map = typeService.queryAllType();
        if (map != null) {
            return RestResult.success(ResultCode.SUCCESS, "查询成功", map);
        }
        return RestResult.failure("查询失败");
    }


    @GetMapping(value = "/queryByParentId")
    @ApiOperation(value = "根据父类查询所有子类信息", notes = "parentId 必填")
    public RestResult queryByParentId(@RequestParam Long parentId) {
        List<Type> types = typeService.queryByParentId(parentId);
        if (types == null) {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "查询失败");
        } else {
            return RestResult.success(ResultCode.SUCCESS, "查询成功", types);
        }
    }


    @PostMapping(value = "/addParentType")
    @ApiOperation(value = "添加父类", notes = "parentType 必填")
    public RestResult addParentType(@RequestParam String parentType) {
        Integer rows = typeService.insertParentType(parentType);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "添加成功",rows);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "添加失败");
        }
    }


    @PostMapping(value = "/addType")
    @ApiOperation(value = "添加子类类别", notes = "type parentId 必填")
    public RestResult addType(@RequestBody Type type) {
        Integer rows = typeService.insertType(type);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "添加成功",rows);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "添加失败");
        }
    }

    @PostMapping(value = "/deleteType")
    @ApiOperation(value = "删除图书类别", notes = "typeId 必填 不管是删除父类别，还是删除子类别都用该接口")
    public RestResult deleteType(@RequestParam Long typeId) {
        Integer rows = typeService.deleteType(typeId);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "删除成功",rows);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "删除失败");
        }
    }

    @PostMapping(value = "/updateType")
    @ApiOperation(value = "更新图书类别", notes = "typeId 必填")
    public RestResult updateType(@RequestBody Type type) {
        Integer rows = typeService.updateType(type);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "更新成功",rows);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "更新失败");
        }
    }





}
