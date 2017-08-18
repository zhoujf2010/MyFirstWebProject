package com.zjf.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//基本jdbc处理
public class testdb
{

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dburl = "jdbc:mysql://localhost:3306/testDB";
        Connection conn = DriverManager.getConnection(dburl, "root", "11111");

        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate("insert into people(name,age)values('张三','10')");
            // conn.commit();
            ResultSet rst = stat.executeQuery("select * from people");
            while (rst.next()) {                
                System.out.println(rst.getString(1) + "  " + rst.getString(2) + "  " + rst.getString(3));
            }
//            stat.execute("delete from people");
            rst.close();
            stat.close();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            conn.close();
        }
        
//        PeopleList lst = null;
//        lst.add(new People());
//        lst.get(0).getName();
//        
//        OuList lst2 = null;
//        

//        List<OuInfo> lst3 = null;
//        
//        lst3.get(0).g
        
    }

}
