package com.gaoyifeng.sso.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 * 用于读取application.yml中的JWT配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    /**
     * JWT加解密使用的密钥
     */
    private String secret;

    /**
     * JWT的有效期（单位：秒）
     */
    private long expiration;

    /**
     * JWT的签发者
     */
    private String issuer;

    /**
     * Token前缀
     */
    private String tokenPrefix;

    /**
     * 存储Token的请求头
     */
    private String header;
}