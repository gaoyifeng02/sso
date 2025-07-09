package com.gaoyifeng.sso.util;

import com.gaoyifeng.sso.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT工具类
 * 用于生成和验证JWT令牌，只暴露登录逻辑相关的接口
 */
@Component
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 生成令牌 - 用于用户登录成功后生成JWT令牌
     *
     * @param username 用户名
     * @return JWT令牌
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    /**
     * 验证令牌 - 用于验证JWT令牌是否有效
     *
     * @param token    JWT令牌
     * @param username 用户名
     * @return 是否有效
     */
    public Boolean validateToken(String token, String username) {
        final String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * 从令牌中获取用户名 - 用于获取当前登录用户
     *
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 刷新令牌 - 用于延长令牌有效期
     * 如果令牌已过期，则无法刷新，会返回null
     *
     * @param token JWT令牌
     * @return 新的JWT令牌，如果令牌已过期则返回null
     */
    public String refreshToken(String token) {
        try {
            // 检查令牌是否已过期
            if (isTokenExpired(token)) {
                return null; // 令牌已过期，无法刷新
            }
            
            final Date createdDate = new Date();
            final Date expirationDate = calculateExpirationDate(createdDate);

            final Claims claims = getAllClaimsFromToken(token);
            claims.setIssuedAt(createdDate);
            claims.setExpiration(expirationDate);

            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                    .compact();
        } catch (Exception e) {
            return null; // 令牌解析异常，无法刷新
        }
    }

    // 以下是私有辅助方法，不对外暴露

    /**
     * 从令牌中获取指定的声明
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从令牌中获取所有声明
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 检查令牌是否过期
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 从令牌中获取过期时间
     */
    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 计算过期时间
     */
    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + jwtConfig.getExpiration() * 1000);
    }

    /**
     * 生成令牌
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
    }
}