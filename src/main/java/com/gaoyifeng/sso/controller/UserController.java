package com.gaoyifeng.sso.controller;

import com.gaoyifeng.common.infrastructure.Result;
import com.gaoyifeng.sso.model.User;
import com.gaoyifeng.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 * @author gaoyifeng
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        // 1. 参数校验
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return Result.fail("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return Result.fail("密码不能为空");
        }
        
        // 2. 检查用户名是否已存在
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return Result.fail("用户名已存在");
        }
        
        // 3. 保存用户信息(密码加密在Service层处理)
        User createdUser = userService.createUser(user);
        
        // 4. 返回注册结果
        return Result.success("注册成功", createdUser);
    }

    /**
     * 获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public Result<User> getUserInfo(@PathVariable Long id) {
        // 1. 根据ID查询用户
        User user = userService.getUserById(id);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        
        // 2. 返回用户信息
        return Result.success("查询成功", user);
    }

    /**
     * 更新用户信息
     * @param id 用户ID
     * @param user 更新后的用户信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<User> updateUserInfo(
            @PathVariable Long id,
            @RequestBody User user) {
        // 1. 参数校验
        if (id == null) {
            return Result.fail("用户ID不能为空");
        }
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return Result.fail("用户名不能为空");
        }
        
        // 2. 检查用户是否存在
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return Result.fail("用户不存在");
        }
        
        // 3. 更新用户信息
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        
        // 4. 返回更新结果
        return Result.success("更新成功", updatedUser);
    }

    /**
     * 更新账号状态
     * @param id 用户ID
     * @param status 账号状态：0-禁用，1-启用
     * @return 状态更新结果
     */
    @PutMapping("/{id}/status")
    public Result<String> updateUserStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        // 1. 参数校验
        if (id == null) {
            return Result.fail("用户ID不能为空");
        }
        if (status == null || (status != 0 && status != 1)) {
            return Result.fail("状态值不合法");
        }
        
        // 2. 检查用户是否存在
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return Result.fail("用户不存在");
        }
        
        // 3. 更新账号状态
        boolean success = userService.updateUserStatus(id, status);
        if (!success) {
            return Result.fail("状态更新失败");
        }
        
        // 4. 返回更新结果
        return Result.success("状态更新成功");
    }

    /**
     * 用户登录接口
     * @param user 用户信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
        // 1. 参数校验
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return Result.fail("用户名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return Result.fail("密码不能为空");
        }
        
        // 2. 用户名密码校验
        User dbUser = userService.getUserByUsername(user.getUsername());
        if (dbUser == null) {
            return Result.fail("用户名或密码错误");
        }
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return Result.fail("用户名或密码错误");
        }
        
        // 3. 生成token (实际项目中应该使用更安全的加密方式)
        String token = dbUser.getId() + "-" + dbUser.getUsername();
        
        // 4. 返回登录结果
        return Result.success("登录成功", token);
    }
}