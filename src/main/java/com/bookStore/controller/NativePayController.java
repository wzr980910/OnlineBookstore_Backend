package com.bookStore.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.bookStore.exception.BizException;
import com.bookStore.pojo.pay.wxpay.NotifyDto;
import com.bookStore.service.NativePayService;
import com.bookStore.service.OrdersShowService;
import com.bookStore.util.ThreadLocalUtil;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
@Slf4j
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
            @ApiResponse(code = 200, message = "操作成功"),
            @ApiResponse(code = 5001, message = "订单状态异常")
    })
    @GetMapping("wxpay")
    public void wxPay(HttpServletResponse response, Long orderId) throws IOException {
        //支付url
        String code_url = nativePayService.pay(orderId);
        response.setContentType("image/png");
        ServletOutputStream outputStream = null;
        outputStream = response.getOutputStream();
        QrCodeUtil.generate(code_url, 300, 300, "jpg", outputStream);
    }

    @ApiOperation(value = "查询支付结果", notes = "支付成功未通知的兜底")
    @GetMapping("queryPayResult")
    public RestResult queryPayResult(Long orderId) {
        Long userId = ThreadLocalUtil.get();
        String msg = nativePayService.queryPayResult(userId, orderId);
        if (msg.equals("SUCCESS")) {
            int rows = nativePayService.updateOrderShowStatus(orderId);
            if (rows == 0) {
                return new RestResult(ResultCode.DB_UPDATE_ERROR);
            }
        }
        return new RestResult(ResultCode.SUCCESS);
    }

}
