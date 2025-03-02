package com.ingker.blogvue.security;

import com.ingker.blogvue.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果是 OPTIONS 请求，直接放行
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }

        // 检查 session 中的用户信息
        User user = (User) request.getSession().getAttribute("user");

        // 如果用户未登录，拒绝访问
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized access");
            return false; // 终止请求处理
        }

        // 用户权限不够，拒绝访问
        if (!user.getUsername().equals("ingker")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Insufficient permissions");
            return false;
        }

        // 放行请求
        return true;
    }
}

