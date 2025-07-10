package com.gaoyifeng.sso.service;

import com.gaoyifeng.sso.model.User;

/**
 * 认证服务接口
 * @author gaoyifeng
 */
public interface AuthService {
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户信息，失败返回null
     */
    User login(String username, String password);
    
    /**
     * 生成JWT令牌
     * @param user 用户信息
     * @return JWT令牌
     */
    String generateToken(User user);
    
    /**
     * 验证JWT令牌
     * @param token JWT令牌
     * @return 验证成功返回true，失败返回false
     */
    boolean validateToken(String token);
    
    /**
     * 刷新JWT令牌
     * @param token 原JWT令牌
     * @return 新的JWT令牌
     */
    String refreshToken(String token);
    
    /**
     * 从JWT令牌中获取用户ID
     * @param token JWT令牌
     * @return 用户ID
     */
    Long getUserIdFromToken(String token);
}