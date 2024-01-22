package com.bookStore.util;

import com.bookStore.config.wxpay.WxPayProperties;
import com.bookStore.pojo.pay.wxpay.Amount;
import com.bookStore.pojo.pay.wxpay.NativePayParams;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * ClassName: WxPayTemplate
 * Package: com.bookStore.util
 * Description:微信支付工具类
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -17:02
 * @Version: v1.0
 */
@Component
public class WxPay {
    @Autowired
    private WxPayProperties wxPayProperties;
    private CloseableHttpClient httpClient;

    public WxPay(WxPayProperties wxPayProperties, CloseableHttpClient httpClient) {
        this.wxPayProperties = wxPayProperties;
        this.httpClient = httpClient;
    }

    //支付
    public String CreateOrder(BigDecimal total, String description, Long outTradeNo) throws Exception {
        String code_url = "";
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/native");
        Amount amount = Amount.builder()
                .currency("CNY")
                .total(total.multiply(new BigDecimal(1)).intValue())
                .build();
        NativePayParams payParams = NativePayParams
                .builder()
                .appid(wxPayProperties.getAppId())
                .mchid(wxPayProperties.getMchId())
                .description(description)
                .out_trade_no(String.valueOf(outTradeNo))
                .notify_url("https://8fb99a7.r3.cpolar.top/native/notify")
                .amount(amount)
                .build();
        Gson gson = new Gson();
        String reqdata = gson.toJson(payParams);
        StringEntity entity = new StringEntity(reqdata, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        CloseableHttpResponse response = this.httpClient.execute(httpPost);

        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                System.out.println("success,return body = " + EntityUtils.toString(response.getEntity()));
                code_url = gson.fromJson(EntityUtils.toString(response.getEntity()), Map.class).get("code_url").toString();
            } else {
                if (statusCode != 204) {
                    System.out.println("failed,resp code = " + statusCode + ",return body = " + EntityUtils.toString(response.getEntity()));
                    throw new IOException("request failed");
                }
                System.out.println("success");
            }
        } finally {
            response.close();
        }
        return code_url;
    }

    //查询支付结果
    public String queryOrder(String outTradeNo) throws Exception {
        HttpGet httpGet = new HttpGet("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" + outTradeNo + "?mchid=" + wxPayProperties.getMchId());
        httpGet.setHeader("Accept", "application/json");
        CloseableHttpResponse response = this.httpClient.execute(httpGet);
        Gson gson = new Gson();
        String res = gson.fromJson(EntityUtils.toString(response.getEntity()), Map.class).get("trade_state_desc").toString();
        response.close();
        return res;
    }
}
