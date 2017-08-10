package com.zjf.weixin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjf.common.SpringContextUtil;

@Controller
@RequestMapping("/weixin")
public class WxBindAction
{

    @RequestMapping("/weixinbind")
    public String init(HttpServletRequest request,Model model){

        String openId = request.getParameter("openId");
        String token = request.getParameter("token");
        System.out.println("===openID:" + openId + "\r\ntoken:" + token);
         
        return "weixin/weixinbind";
    }

    @RequestMapping("/weixininfo")
    public String info(HttpServletRequest request,Model model){

        String openId = request.getParameter("openId");
        String token = request.getParameter("token");
        System.out.println("===openID:" + openId + "\r\ntoken:" + token);
         
        return "weixin/weixininfo";
    }
    

    @RequestMapping("/weixinbind.do")
    public String login(HttpServletRequest request,Model model){

        String openId = request.getParameter("openId");
        String token = request.getParameter("token");
        System.out.println("openID:" + openId + "\r\ntoken:" + token);
         
        return "weixin/weixininfo";

   }
}
