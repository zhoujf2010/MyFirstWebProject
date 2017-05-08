package com.test;

@Name(Name = "userService")
public class UserServiceDB implements IUserService
{
    public String getDisplayName(String UserGuid){
        System.out.println("DoFun getDisplayName...");
        return "";
    }
}
