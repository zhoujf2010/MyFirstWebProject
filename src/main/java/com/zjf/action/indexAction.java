package com.zjf.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.zjf.common.SpringContextUtil;

@Component("indexAction")
@Scope("request")
public class indexAction
{
    public String fun1() throws SQLException {
        DataSource datasource = (DataSource)SpringContextUtil.getContext().getBean("dataSource");
        Connection conn = datasource.getConnection();

        
        Statement stat = conn.createStatement();
        // conn.commit();
        ResultSet rst = stat.executeQuery("select * from Frame_user where rownum<=5");
        while (rst.next()) {
            System.out.println(rst.getString(1) + "  " + rst.getString(2) + "  " + rst.getString("displayName"));
        }

        rst.close();
        stat.close();
        //conn.close();//连接泄漏
        
         return "{\"name\":\"Jeffrey\",\"age\":\"20\"}";

      
    }

    // #region aaa
    public String getName() {
        return "";
    }

    // #endregion
}
