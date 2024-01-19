package com.bookStore.config;


import com.bookStore.util.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/19/10:32
 * @Description:配置类，创建AliOssUtils对象
 */
@Configuration
@Slf4j
public class OSSConfiguration {
    //注入bean AliOssUtil
    //AliOssProperties是已经注入的bean，作为aliOssUtil的参数
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始注入阿里云oss文件上传工具类对象：{}",aliOssProperties);
        return new AliOssUtil(aliOssProperties.getEndpoint(),aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),aliOssProperties.getBucketName() );
    }
}
