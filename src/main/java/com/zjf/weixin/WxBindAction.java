package com.zjf.weixin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjf.common.SpringContextUtil;
import com.zjf.db4.DBOper;
import com.zjf.mybaties.User;
import com.zjf.mybaties.UserDao;

@Controller
@RequestMapping("/weixin")
public class WxBindAction
{

    @RequestMapping("/weixinbind")
    public String init(HttpServletRequest request, Model model) {

        String openId = request.getParameter("openId");
        String token = request.getParameter("token");
        System.out.println("===openID:" + openId + "\r\ntoken:" + token);

        return "weixin/weixinbind";
    }

    @RequestMapping("/weixininfo")
    public String info(HttpServletRequest request, Model model) {

        String openId = request.getParameter("openId");
        String token = request.getParameter("token");
        System.out.println("===openID:" + openId + "\r\ntoken:" + token);

        return "weixin/weixininfo";
    }

    @RequestMapping("/weixinbind.do")
    public String login(HttpServletRequest request, Model model) {

        String openId = request.getParameter("openId");
        String token = request.getParameter("token");
        System.out.println("openID:" + openId + "\r\ntoken:" + token);

        try {

            // Test mybaties and DB Conn
//            UserDao userdao = (UserDao) SpringContextUtil.getContext().getBean(UserDao.class);
//            User u = new User();
//            u.setUsername("admin");
//            u = userdao.getUser(u);

//            SqlSessionFactory factory = (SqlSessionFactory) SpringContextUtil.getContext().getBean("sqlSessionFactory");
//            SqlSession sqlSession = factory.openSession();
//            Connection conn = sqlSession.getConnection();
//            System.out.println(conn.hashCode());

//            DataSource datasource = (DataSource) SpringContextUtil.getContext().getBean("dataSource");
//            Connection conn2 = datasource.getConnection();
//            System.out.println(conn2.hashCode());
//
//            DataSource datasource2 = (DataSource) SpringContextUtil.getContext().getBean("dataSource");
//            Connection conn3 = datasource2.getConnection();
//            System.out.println(conn3.hashCode());
            
            System.out.println(DBOper.getInstance().getConnection().hashCode());
            System.out.println(DBOper.getInstance().getConnection().hashCode());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "weixin/weixininfo";

    }
}
