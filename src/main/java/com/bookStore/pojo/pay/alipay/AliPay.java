package com.bookStore.pojo.pay.alipay;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/19/16:41
 * @Description:编写Get请求，（方法参数是一个AliPay的配置类里面包括自己生成的订单号
 *               、总金额、支付的名称、支付宝交易凭证号和HttpServletResponse）
 */
@Data
public class AliPay {

    //订单号
    private String traceNo;

    //总金额
    private String totalAmount;

    //支付名称
    private String subject;

    //支付宝交易凭证号
    private String alipayTraceNo;

}