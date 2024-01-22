package com.bookStore.pojo.pay.wxpay;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: NativePayParams
 * Package: com.bookStore.pojo.pay
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -10:09
 * @Version: v1.0
 */
@Builder
@Data
public class NativePayParams {
    private String appid;//应用id
    private String mchid;//商户id
    private String description;//商品描述
    private String out_trade_no;//订单号
    private String notify_url;//支付成功回调通知地址
    private Amount amount;//订单金额信息

}
