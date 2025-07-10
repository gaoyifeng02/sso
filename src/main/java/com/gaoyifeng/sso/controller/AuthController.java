package com.gaoyifeng.sso.controller;

import com.gaoyifeng.common.infrastructure.Result;
import com.gaoyifeng.common.infrastructure.ResultCode;
import com.gaoyifeng.sso.model.User;
import com.gaoyifeng.sso.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * @author gaoyifeng
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     * @param params 包含用户名和密码的参数
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String password = params.get("password");
        
        // 参数校验
        if (username == null || password == null) {
            return Result.fail(ResultCode.PARAM_ERROR);
        }
        
        // 用户登录
        User user = authService.login(username, password);
        if (user == null) {
            return Result.fail(ResultCode.UNAUTHORIZED);
        }
        
        // 生成JWT令牌
        String token = authService.generateToken(user);
        
        // 构建返回结果
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("userId", user.getId());
        data.put("username", user.getUsername());
        
        return Result.success(data);
    }

    /**
     * 刷新令牌
     * @param token 原JWT令牌
     * @return 新的JWT令牌
     */
    @PostMapping("/refresh")
    public Result<Map<String, Object>> refreshToken(@RequestParam String token) {
        String newToken = authService.refreshToken(token);
        if (newToken != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("token", newToken);
            return Result.success(data);
        } else {
            return Result.fail(ResultCode.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户信息
     * @param token JWT令牌
     * @return 用户信息
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam String token) {
        if (!authService.validateToken(token)) {
            return Result.fail(ResultCode.UNAUTHORIZED);
        }
        
        Long userId = authService.getUserIdFromToken(token);
        // 这里可以根据userId获取更详细的用户信息
        
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        
        return Result.success(data);
    }
}