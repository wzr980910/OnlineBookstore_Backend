package com.bookStore.pojo.login.wxlogin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ClassName: WxMpConfig
 * Package: com.bookStore.pojo.login.wxlogin
 * Description:
 *
 * @Author: 邓桂材
 * @Create: 2024/1/22 -9:12
 * @Version: v1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpConfig {
    private String appid;
    private String secret;
    private String token;
    private String aesKey;
}
