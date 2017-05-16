package com.test;

import org.springframework.beans.factory.annotation.Autowired;

@Name(Name = "userService")
public class UserServiceDB implements IUserService
{

    @Autowired
    private IConfigService configService;
    
    private String message;
    
    
    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public String getDisplayName(String UserGuid){
        System.out.println("test call config:" + configService.getValue("aa"));
        System.out.println("DoFun getDisplayName..." + message);
        return "";
    }
}
