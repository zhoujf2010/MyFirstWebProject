package com.test;

@Name(Name = "userService")
public class UserServiceDB implements IUserService
{
    
    @Autowired
    public  IConfigService configService;
    
    public String getDisplayName(String UserGuid){
        
        
        System.out.println("DoFun getDisplayName...");
        return "";
    }
}
