package com.test;

@Name(Name = "userService")
public class UserServiceDB implements IUserService
{

    @Autowired
    private IConfigService configService;
    
    public String getDisplayName(String UserGuid){
        System.out.println("test call config:" + configService.getValue("aa"));
        System.out.println("DoFun getDisplayName...");
        return "";
    }
}
