package com.ghw.crm.intercept;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginStatusIntercept implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果没有登录就跳转登录页面，如果登录就跳转用户列表
        //或请求的是登录页面
        if(request.getSession().getAttribute("user") != null){
            return true;
        }
        if(request.getRequestURI().toString().contains("login")){
            return true;
        }
        //将用户发送至登陆页面
        response.sendRedirect("/CRMDemo2/login");
        return false;
    }
}
