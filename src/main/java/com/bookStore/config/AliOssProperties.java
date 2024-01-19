package com.bookStore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/19/10:31
 * @Description:
 */


//阿里云oss属性配置类
@Component
@ConfigurationProperties(prefix = "sky.alioss")
@Data
public class AliOssProperties {
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
