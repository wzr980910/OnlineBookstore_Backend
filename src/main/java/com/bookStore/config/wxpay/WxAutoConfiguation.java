package com.bookStore.config.wxpay;

import com.bookStore.util.WxPay;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.PrivateKey;

/**
 * ClassName: WxAutoConfiguation
 * Package: com.bookStore.config
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/19 -18:47
 * @Version: v1.0
 */
@Configuration
public class WxAutoConfiguation {
    @Bean
    public CloseableHttpClient httpClient(WxPayProperties wxPayProperties) throws IOException {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(wxPayProperties.getPrivateKey().getBytes("utf-8")));
        AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(new WechatPay2Credentials(wxPayProperties.getMchId(), new PrivateKeySigner(wxPayProperties.getMchSerialNo(), merchantPrivateKey)), wxPayProperties.getApiV3Key().getBytes("utf-8"));
        return WechatPayHttpClientBuilder.create().withMerchant(wxPayProperties.getMchId(), wxPayProperties.getMchSerialNo(), merchantPrivateKey).withValidator(new WechatPay2Validator(verifier)).build();
    }
    @Bean
    public WxPay WxPayTemplate(WxPayProperties wxPayProperties, CloseableHttpClient httpClient){
        return new WxPay(wxPayProperties,httpClient);
    }
}
