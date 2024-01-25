package com.bookStore.service;

import com.bookStore.pojo.pay.wxpay.NotifyDto;

import java.security.GeneralSecurityException;

/**
 * ClassName: NativePayService
 * Package: com.bookStore.service
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -11:52
 * @Version: v1.0
 */
public interface NativePayService {
    String pay(Long orderId);


    String payNotify(NotifyDto dto) throws GeneralSecurityException;

    String queryPayResult(Long userId, Long orderId);
    int updateOrderShowStatus(Long outTradeNo);
}
