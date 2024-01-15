package com.bookStore.interceptor;


import com.bookStore.service.UserService;
import com.bookStore.util.JwtHelper;
import com.bookStore.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 该类进行token验证
 *
 * @author wmh
 * @date 2024/01/13 18:57
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private JwtHelper jwtHelper;

    @Autowired
    public void setJwtHelper(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        // 验证 token是否过期
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration) {
            //过期重定向
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        // 获取 token 中的 user id
        Long userId = jwtHelper.getUserId(token);
        //存入ThreadLocal中，避免在其他地方重复解析token
        ThreadLocalUtil.set(userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
