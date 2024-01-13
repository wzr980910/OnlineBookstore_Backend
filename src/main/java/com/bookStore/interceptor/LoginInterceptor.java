package com.bookStore.interceptor;

import com.bookStore.entity.Admin;
import com.bookStore.util.ConstantsUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with Intellij IDEA.
 *
 * @Author: wzr
 * @Date: 2024/01/12/19:40
 * @Description:
 */
public class LoginInterceptor implements HandlerInterceptor {
    //在controller方法之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //拦截器，从Session中获取管理员
        Admin admin = (Admin) request.getSession().getAttribute(ConstantsUtil.Admin_Session);
        if (admin != null) {
            //为true时，拦截器执行到此处将不会继续操作，而是去执行对应的controller
            return true;
        }
        try {
            response.sendRedirect(request.getContextPath()+"login");
        } catch (IOException io) {
            io.printStackTrace();
        }
        return false;
    }
}
