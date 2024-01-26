package com.bookStore.config.pay.alipay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/19/16:07
 * @Description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig {

    private String appId;
    private String appPrivateKey;
    private String alipayPublicKey;
    @Value("${localhost}")
    private String notifyUrl;


    @PostConstruct
    public void init() {
        // 设置参数（全局只需设置一次）
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com/gateway.do";
        config.signType = "RSA2";
        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayPublicKey = this.alipayPublicKey;
        config.notifyUrl = this.notifyUrl+"/alipay/notify";
        Factory.setOptions(config);
        System.out.println("======================" + this.appId);
        System.out.println("=======支付宝SDK初始化成功=======");
    }

}
