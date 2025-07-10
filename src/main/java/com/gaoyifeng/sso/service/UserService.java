package com.gaoyifeng.sso.service;

import com.gaoyifeng.sso.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByPhone(String phone);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    
    /**
     * 更新用户状态
     * @param id 用户ID
     * @param status 账号状态：0-禁用，1-启用
     * @return 是否更新成功
     */
    boolean updateUserStatus(Long id, Integer status);
}