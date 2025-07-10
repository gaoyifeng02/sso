package com.gaoyifeng.sso.service.impl;

import com.gaoyifeng.sso.mapper.UserMapper;
import com.gaoyifeng.sso.model.User;
import com.gaoyifeng.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        userMapper.update(user);
        return userMapper.selectById(user.getId());
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public boolean updateUserStatus(Long id, Integer status) {
        // 1. 查询用户
        User user = userMapper.selectById(id);
        if (user == null) {
            return false;
        }
        
        // 2. 更新状态
        user.setStatus(status);
        
        // 3. 保存更新
        int affectedRows = userMapper.update(user);
        return affectedRows > 0;
    }
}