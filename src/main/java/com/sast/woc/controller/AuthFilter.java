package com.sast.woc.controller;

import com.sast.woc.entity.User;
import com.sast.woc.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthFilter extends OncePerRequestFilter {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/login")|| requestURI.equals("/user/register")) {
            // 登录请求，放行  ||   注册请求，放行
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("token");
        if (token != null && JwtUtil.isValidToken(token)) {
            // 解析请求头中的Token，获取用户信息
            User user = userService.returnUserByToken(token);
            String role = user.getRole();
            if (role != null )
            {
                // 用户具有操作权限，放行请求
                if ( role.equals("Admin"))
                {
                    // 管理员具有操作权限，放行请求
                    filterChain.doFilter(request, response);
                    return;
                } else if ( role.equals("user"))
                {
                    if (isUserRequest(request))
                    {
                        filterChain.doFilter(request, response);
                        return;
                    }
                    else{
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        response.getWriter().write("Access denied : Not permitted");
                        return;
                    }
                }
            }
        }
        // 用户未登陆或没有操作权限，返回没有权限的提示信息
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write("Access denied : invalid token");
    }
    //Helper Method
    private boolean isUserRequest(HttpServletRequest request) {
        // 判断请求是否为查询操作
        String method = request.getMethod();
        String uri = request.getRequestURI();
        // 根据实际情况判断请求是否为查询操作
        return  uri.startsWith("/user") ;
    }
    //Helper Method 2
    public static String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("token");

        return token;
    }
}
