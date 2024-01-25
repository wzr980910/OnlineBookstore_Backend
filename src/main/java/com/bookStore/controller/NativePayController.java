package com.bookStore.controller;

import com.bookStore.pojo.pay.wxpay.NotifyDto;
import com.bookStore.service.NativePayService;
import com.bookStore.service.OrdersShowService;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: NativePayController
 * Package: com.bookStore.controller
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -11:46
 * @Version: v1.0
 */
@RestController
@RequestMapping("/native")
public class NativePayController {

    @Autowired
    private NativePayService nativePayService;

    //支付成功后微信通知调用的接口
    @PostMapping("/notify")
    public Map<String, String> payNotify(@RequestBody NotifyDto dto) throws GeneralSecurityException {
        Map<String, String> resMap = new HashMap<>();
        String res = nativePayService.payNotify(dto);
        if (res.equals("Success")) {
            resMap.put("code", "SUCCESS");
            resMap.put("message", "成功");
        } else {
            resMap.put("code", "FAIL");
            resMap.put("message", "失败");
        }
        return resMap;
    }

    @ApiOperation(value = "微信支付", notes = "微信支付")
    @ApiResponses({
            @ApiResponse(code=200,message = "操作成功"),
            @ApiResponse(code = 5001,message = "订单状态异常")
    })
    @PostMapping("wxpay")
    public RestResult wxPay(Long orderId) {
        RestResult restResult;
        //支付
        String code_url = nativePayService.pay(orderId);
        if (code_url.equals("Fail")) {
            restResult = new RestResult(ResultCode.ORDER_STATUS_ERROR);
            return restResult;
        }
        return new RestResult(ResultCode.SUCCESS, code_url);
    }

}
