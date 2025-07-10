# SSO (Single Sign-On) 服务

## 项目简介
本项目提供基于Spring Boot的单点登录服务，包含用户注册、登录、状态管理等基础功能。

## 主要功能
- 用户注册
- 用户登录
- 用户信息管理
- 账号状态管理

## API文档

### 用户状态管理
`PUT /user/{id}/status` - 更新用户状态

**请求参数**:
- id: 用户ID (路径参数)
- status: 账号状态 (查询参数)
  - 0: 禁用
  - 1: 启用

**响应示例**:
```json
{
  "code": "200",
  "info": "状态更新成功",
  "data": null
}
```

## 服务层方法
### updateUserStatus
```java
boolean updateUserStatus(Long id, Integer status)
```
更新用户账号状态

**参数**:
- id: 用户ID
- status: 账号状态(0-禁用,1-启用)

**返回值**:
- true: 更新成功
- false: 更新失败

## 使用示例
```java
// 更新用户状态
boolean success = userService.updateUserStatus(1L, 1);
```