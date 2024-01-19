package com.bookStore.service;

import com.bookStore.pojo.Orders;
import com.bookStore.pojo.wxpay.NotifyDto;

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
    String pay(Orders orders);


    String payNotify(NotifyDto dto) ;
}
