package com.bookStore.config;

import com.bookStore.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public void setAuthenticationInterceptor(AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration authRegistration=registry.addInterceptor(authenticationInterceptor);
        authRegistration.addPathPatterns("/**");    //所有路径都被拦截
        authRegistration.excludePathPatterns(       //添加不拦截路径
                "/native/notify",
                "/user/login",
                "/user/wx",
                "/book/selectBookPage",
                "/**/*.html",
                "/**/*.js",
                "/**/*.css",
                "/**/*.jpg",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/webjars/**"
        );
    }
}
