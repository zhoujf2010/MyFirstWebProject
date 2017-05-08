package com.test;

public class UserServiceFactory
{
    public IUserService Create(){
        return new UserServiceDB();
    }
}
