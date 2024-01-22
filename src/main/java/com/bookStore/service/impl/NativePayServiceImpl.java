package com.bookStore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bookStore.mapper.OrdersShowMapper;
import com.bookStore.pojo.OrdersShow;
import com.bookStore.pojo.pojoenum.OrderStatus;
import com.bookStore.pojo.pay.wxpay.NotifyDto;
import com.bookStore.service.NativePayService;
import com.bookStore.util.WxPay;
import com.google.gson.Gson;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * ClassName: NativePayServiceImpl
 * Package: com.bookStore.service.impl
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -11:52
 * @Version: v1.0
 */
@Service
public class NativePayServiceImpl implements NativePayService {
    @Value("${wxpay.api-v3-key}")
    private String apiV3Key;
    @Autowired
    private WxPay wxPay;
    @Autowired
    private OrdersShowMapper ordersShowMapper;


    @Override
    //支付
    public String pay(Long orderId) {
        //从数据库中查询订单状态
        LambdaQueryWrapper<OrdersShow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrdersShow::getOrderId, orderId);
        OrdersShow ordersShow = null;
        try {
            ordersShow = ordersShowMapper.selectOne(wrapper);
            //未查询到订单信息
            if (ordersShow == null) {
                return "Fail";
            }
            //订单状态异常
            if (!ordersShow.getStatus().equals(OrderStatus.WAIT_PAYMENT.getCode())) {
                return "Fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            //数据库错误
            throw new RuntimeException(e);
        }
        //将订单状态改为待付款
        LambdaUpdateWrapper<OrdersShow> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(OrdersShow::getOrderId, ordersShow.getOrderId());
        updateWrapper.set(OrdersShow::getStatus, OrderStatus.WAIT_PAYMENT.getCode());
        ordersShowMapper.update(updateWrapper);
        String code_url;
        try {
            code_url = wxPay.CreateOrder(ordersShow.getTotalPrice(), "购买图书", ordersShow.getOrderId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return code_url;
    }

    @Override
    public String payNotify(NotifyDto dto) {
        String outTradeNo;
        try {
            //解密微信传递过来的参数
            String json = new AesUtil(apiV3Key.getBytes()).decryptToString(dto.getResource().getAssociated_data().getBytes(),
                    dto.getResource().getNonce().getBytes(),
                    dto.getResource().getCiphertext());
            Gson gson = new Gson();
            outTradeNo = gson.fromJson(json, Map.class).get("out_trade_no").toString();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return "Fail";
        }
        updateOrderShowStatus(outTradeNo);
        return "Success";
    }

    //将订单状态改为代发货
    private void updateOrderShowStatus(String outTradeNo) {
        LambdaUpdateWrapper<OrdersShow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OrdersShow::getOrderId, outTradeNo);
        wrapper.set(OrdersShow::getStatus, OrderStatus.WAIT_SEND.getCode());
        ordersShowMapper.update(wrapper);
    }
}
