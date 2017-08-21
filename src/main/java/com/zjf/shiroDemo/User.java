package com.zjf.shiroDemo;

import java.io.Serializable;

public class User implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -8957654899594351835L;
    private String username; // 用户名
    private String password; // 密码
    private String name; // 姓名
    private String role; // 角色字段
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    
}
