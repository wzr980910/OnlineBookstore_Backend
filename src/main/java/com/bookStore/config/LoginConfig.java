package com.bookStore.config;

import com.bookStore.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/12/19:39
 * @Description:登录拦截器
 */
@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        //注册LoginInterceptor拦截器
//        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
//        registration.addPathPatterns("/**");    //所有路径都被拦截
//        registration.excludePathPatterns(       //添加不拦截路径
//                "/login",
//                "/**/*.html",
//                "/**/*.js",
//                "/**/*.css",
//                "/**/*.jpg",
//                "/swagger-ui.html",
//                "/swagger-resources/**",
//                "/webjars/**"
//
//        );
//        InterceptorRegistration authRegistration=registry.addInterceptor(new AuthenticationInterceptor());
//        authRegistration.addPathPatterns("/**");    //所有路径都被拦截
//        authRegistration.excludePathPatterns(       //添加不拦截路径
//                "/user/login",
//                "/**/*.html",
//                "/**/*.js",
//                "/**/*.css",
//                "/**/*.jpg",
//                "/swagger-ui.html",
//                "/swagger-resources/**",
//                "/webjars/**"
//        );
    }
}
