package com.zjf.weixin;

import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixinAction2")
public class weixinAction2
{
    @ResponseBody
    @RequestMapping(value = "/fun1", method = RequestMethod.POST)
    public String fun1(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        
        for(Cookie c : request.getCookies()){
            System.out.println(c.getName() +":" + c.getValue());
        }
        
        Cookie cook = new Cookie("aaa","==" + UUID.randomUUID().toString());
        response.addCookie(cook);
        System.out.println("new :"+cook.getName() +":" + cook.getValue());
         return "{\"name\":\"Jeffrey\",\"age\":\"20\"}";
    }
}
