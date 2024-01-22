package com.bookStore.config.wxpay;

import lombok.Data;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: WxPayProperties
 * Package: com.bookStore.config
 * Description:微信支付配置信息类
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -17:08
 * @Version: v1.0
 */
@Data
@ConfigurationProperties(prefix = "wxpay")
@Component
public class WxPayProperties {
    private String mchId = "";//商户号
    private String appId = "";//应用号
    //私钥字符串
    private String privateKey = "";
    private String mchSerialNo = "";//营业执照序列号
    private String apiV3Key = "";//V3密钥
    private CloseableHttpClient httpClient;
}
