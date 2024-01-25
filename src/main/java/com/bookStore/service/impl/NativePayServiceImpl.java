package com.bookStore.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.bookStore.exception.BizException;
import com.bookStore.mapper.OrdersShowMapper;
import com.bookStore.pojo.OrdersShow;
import com.bookStore.pojo.pojoenum.OrderStatus;
import com.bookStore.pojo.pay.wxpay.NotifyDto;
import com.bookStore.service.NativePayService;
import com.bookStore.util.WxPay;
import com.bookStore.util.result.ResultCode;
import com.google.gson.Gson;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        OrdersShow ordersShow;
        try {
            ordersShow = ordersShowMapper.selectOne(wrapper);
        } catch (Exception e) {
            throw new BizException(ResultCode.DB_SELECT_ONE_ERROR);
        }
        //未查询到订单信息或订单状态不是未支付
        if (ordersShow == null || !ordersShow.getStatus().equals(OrderStatus.WAIT_PAYMENT.getCode())) {
            throw new BizException(ResultCode.ORDER_STATUS_ERROR);
        }
//        //将订单状态改为待付款
//        LambdaUpdateWrapper<OrdersShow> updateWrapper = new LambdaUpdateWrapper<>();
//        updateWrapper.eq(OrdersShow::getOrderId, ordersShow.getOrderId());
//        updateWrapper.set(OrdersShow::getStatus, OrderStatus.WAIT_PAYMENT.getCode());
//        ordersShowMapper.update(updateWrapper);
        String code_url;

        try {
            code_url = wxPay.CreateOrder(ordersShow.getTotalPrice(), "购买图书", ordersShow.getOrderId());
        } catch (Exception e) {
            log.info("支付信息生成失败", e);
            throw new BizException("支付信息生成失败");
        }
        return code_url;
    }

    @Override
    public String payNotify(NotifyDto dto) throws GeneralSecurityException {
        String outTradeNo;
        //解密微信传递过来的参数
        String json = new AesUtil(apiV3Key.getBytes()).decryptToString(dto.getResource().getAssociated_data().getBytes(),
                dto.getResource().getNonce().getBytes(),
                dto.getResource().getCiphertext());
        Gson gson = new Gson();
        Map infoMap = gson.fromJson(json, Map.class);
        outTradeNo = infoMap.get("out_trade_no").toString();//订单号
        String tradeCode = infoMap.get("trade_state").toString();//订单支付状态：
        // SUCCESS：支付成功
//        REFUND：转入退款
//        NOTPAY：未支付
//        CLOSED：已关闭
//        REVOKED：已撤销（付款码支付）
//        USERPAYING：用户支付中（付款码支付）
//        PAYERROR：支付失败(其他原因，如银行返回失败)
        if (tradeCode.equals("SUCCESS")) {
            updateOrderShowStatus(Long.parseLong(outTradeNo));
            return "Success";
        } else {
            return "FAIL";
        }
    }

    //将订单状态改为代发货
    @Override
    public int updateOrderShowStatus(Long outTradeNo) {
        LambdaUpdateWrapper<OrdersShow> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OrdersShow::getOrderId, outTradeNo);
        wrapper.set(OrdersShow::getStatus, OrderStatus.WAIT_SEND.getCode());
        return ordersShowMapper.update(wrapper);

    }

    @Override
    public String queryPayResult(Long userId, Long orderId) {
        LambdaQueryWrapper<OrdersShow> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(OrdersShow::getUserId,userId);
        wrapper.eq(OrdersShow::getOrderId,orderId);
        OrdersShow ordersShow;
        try {
            ordersShow = ordersShowMapper.selectOne(wrapper);
        } catch (Exception e) {
            throw new BizException(ResultCode.DB_SELECT_ONE_ERROR);
        }
        if(ordersShow == null){
            throw new BizException(ResultCode.DB_SELECT_ERROR);
        }
        try {
            return wxPay.queryOrder(String.valueOf(orderId));
        } catch (Exception e) {
            log.info("支付信息查询异常", e);
            throw new BizException(ResultCode.PAY_SELECT_ERROR);
        }
    }
}
