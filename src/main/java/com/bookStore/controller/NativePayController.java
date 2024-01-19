package com.bookStore.controller;

import com.bookStore.pojo.Orders;
import com.bookStore.pojo.wxpay.NotifyDto;
import com.bookStore.service.NativePayService;
import com.bookStore.util.WxPayTemplate;
import com.bookStore.util.result.RestResult;
import com.bookStore.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Map<String, String> payNotify(@RequestBody NotifyDto dto) {
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
    //未写完的支付
    @PostMapping("wxpay")
    public RestResult wxPay(@RequestBody Orders orders)  {
        String code_url=nativePayService.pay(orders);
        return new RestResult(ResultCode.SUCCESS,code_url);
    }
}
