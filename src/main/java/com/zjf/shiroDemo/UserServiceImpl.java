package com.zjf.shiroDemo;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl
{

    public User doUserLogin(User userLogin) {
        userLogin.setRole("admin,user");
        return userLogin;
    }

}
