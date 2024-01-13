package com.bookStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration  //告诉springboot这是一个配置类
public class CorsConfig {
    //当前跨域请求最大有效时长，默认设置为1天
    private static final long MAX_AGE = 24*60*60;

    @Bean   //给容器中添加组件，方法名为id，返回类型为组件类型，返回值是组件在容器中的实例
    public CorsFilter corsFilter() {
        System.out.println("通过过滤器");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");        //设置访问源地址
        corsConfiguration.addAllowedHeader("*");                            //设置访问源请求
        corsConfiguration.addAllowedMethod("*");                            //设置访问源请求方法
        corsConfiguration.setAllowCredentials(true);                        //是否支持安全证书
        corsConfiguration.setMaxAge(MAX_AGE);                               //预检请求的有效期
        source.registerCorsConfiguration("/**", corsConfiguration);  //对接口配置跨域设置
        return new CorsFilter(source);
    }
}
