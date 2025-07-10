package com.gaoyifeng.sso.model;

import lombok.Data;
import java.util.Date;

/**
 * 用户实体类
 * @author gaoyifeng
 */
@Data
public class User {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码（加密存储）
     */
    private String password;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 账号状态：0-禁用，1-启用
     */
    private Integer status = 1;
    
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    
    /**
     * 更新时间
     */
    private Date updateTime = new Date();
}