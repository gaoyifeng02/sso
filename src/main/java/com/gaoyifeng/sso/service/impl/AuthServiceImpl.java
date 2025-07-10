package com.gaoyifeng.sso.service.impl;

import com.gaoyifeng.common.infrastructure.JwtUtil;
import com.gaoyifeng.sso.model.User;
import com.gaoyifeng.sso.service.AuthService;
import com.gaoyifeng.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务实现类
 * @author gaoyifeng
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public User login(String username, String password) {
        // 根据用户名查询用户
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return null;
        }
        
        // 验证密码
        String encryptedPassword = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!encryptedPassword.equals(user.getPassword())) {
            return null;
        }
        
        // 验证用户状态
        if (user.getStatus() != 1) {
            return null;
        }
        
        return user;
    }

    @Override
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getUsername());
        
        return jwtUtil.generateToken(claims);
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    @Override
    public String refreshToken(String token) {
        if (!validateToken(token)) {
            return null;
        }
        
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(token);
        return jwtUtil.generateToken(claims);
    }

    @Override
    public Long getUserIdFromToken(String token) {
        Map<String, Object> claims = jwtUtil.getClaimsFromToken(token);
        return Long.valueOf(claims.get("userId").toString());
    }
}