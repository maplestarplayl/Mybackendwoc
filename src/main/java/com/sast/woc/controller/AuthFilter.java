package com.sast.woc.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/login")|| requestURI.equals("/user/register")) {
            // 登录请求，放行  ||   注册请求，放行
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            // 解析请求头中的Token，获取用户信息
            String token = header.substring(7);
            Map<String, Object> claims = JwtUtil.parseToken(token);
            if (claims != null )
            {
                // 用户具有操作权限，放行请求
                String role = (String) claims.get("role");
                if (role != null && role.equals("ADMIN"))
                {
                    // 管理员具有操作权限，放行请求
                    filterChain.doFilter(request, response);
                    return;
                } else if (role != null && role.equals("user"))
                {
                    if (isUserRequest(request))
                    {
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            }
        }
        // 用户未登陆或没有操作权限，返回没有权限的提示信息
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write("Access denied");
    }
    //Helper Method
    private boolean isUserRequest(HttpServletRequest request) {
        // 判断请求是否为查询操作
        String method = request.getMethod();
        String uri = request.getRequestURI();
        // 根据实际情况判断请求是否为查询操作
        return  uri.startsWith("/user") ;
    }
}
