package com.bookStore.controller;

import com.bookStore.pojo.Slide;
import com.bookStore.service.SlideService;
import com.bookStore.util.AliOssUtil;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author wmh
 * @date 2024/01/22 11:29
 */
@RestController
@RequestMapping("/slide")
@Api(value = "轮播图接口", tags = "轮播图相关的接口", description = "轮播图测试接口")
public class SlideController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private SlideService slideService;

    @PostMapping("/uploadSlide")
    @ApiOperation(value = "轮播图上传", notes = "file 必填")
    public RestResult uploadSlide(@RequestParam(value = "file") MultipartFile file) {
        String basePath = "slidePicture/";
        try {
            //返回文件请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), file, basePath);
            return RestResult.success(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RestResult.failure("文件上传失败");
    }

    @PostMapping("/addSlide")
    @ApiOperation(value = "添加轮播图信息", notes = "file 必填")
    public RestResult addSlide(@RequestBody Slide slide) {
        Integer rows = slideService.addSlide(slide);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "添加成功", rows);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "添加失败");
        }
    }


    @PostMapping("/updateSlide")
    @ApiOperation(value = "更新轮播图", notes = "id 必填")
    public RestResult updateSlide(@RequestBody Slide slide) {
        Integer rows = slideService.updateSlide(slide);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "更新成功", rows);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "更新失败");
        }
    }

    @PostMapping("/deleteSlide")
    @ApiOperation(value = "删除轮播图", notes = "可以删除一个，也可以删除多个")
    public RestResult deleteSlide(String[] slideIdsList) {
        List<String> strings = Arrays.asList(slideIdsList);
        Integer rows = slideService.deleteSlide(strings);
        if (rows > 0) {
            return RestResult.success(ResultCode.SUCCESS, "删除成功", rows);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "删除失败");
        }
    }

    @GetMapping("/queryAllSlide")
    @ApiOperation(value = "分页查询轮播图", notes = "分页查询")
    public RestResult queryAllSlide(@RequestParam(defaultValue = "1") Integer currentPage,
                                    @RequestParam(defaultValue = "10") Integer size) {
        Map<String, Object> map = slideService.querySlidePage(currentPage, size);
        if (map != null) {
            return RestResult.success(ResultCode.SUCCESS, "查询成功", map);
        } else {
            return RestResult.failure(ResultCode.OPERATION_FAILURE, "查询失败");
        }
    }


}
