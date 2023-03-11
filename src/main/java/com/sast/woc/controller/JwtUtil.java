package com.sast.woc.controller;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import com.sast.woc.entity.*;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtUtil {
    // 生成Token的密钥
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * 生成Token
     * @param user 用户信息
     * @return Token字符串
     */
    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("role", user.getRole())
                .claim("id", user.getId())
                .signWith(key)
                .compact();
    }

    /**
     * 解析Token，获取用户信息
     * @param token Token字符串
     * @return 用户信息
     */
    public static Map<String, Object> parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Map<String, Object> result = new HashMap<>();
            result.put("id", claims.get("id"));
            result.put("name", claims.getSubject());
            result.put("role", claims.get("role"));
            return result;
        } catch (Exception e) {
            // Token不合法，解析失败
            log.error("parse token error: {}", e.getMessage());
            return null;
        }
    }

    public static String getUserNameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public static boolean isValidToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            // Token不合法，解析失败
            log.error("parse token error: {}", e.getMessage());
            return false;
        }
    }
}

