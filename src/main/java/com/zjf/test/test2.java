package com.zjf.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test2
{
    /**
     * 读写分离测试
     * @throws Exception 
     */

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String dburl = "jdbc:mysql://192.168.201.60:3306/epointbid_pb6j";
        Connection conn = DriverManager.getConnection(dburl, "root", "11111");

        Statement stat = conn.createStatement();
        stat.execute("delete from frame_user where userguid='test'");
        stat.executeUpdate("insert into frame_user (userguid,displayname,loginid)values('test','测试读写分离','testrdw')");
        // conn.commit();
        ResultSet rst = stat.executeQuery("select * from frame_user where userguid='test'");
        while (rst.next()) {
            System.out.println(rst.getString(1) + "  " + rst.getString("displayname") );
        }
       // stat.execute("delete from frame_user where userguid='test'");
        rst.close();
        stat.close();
        conn.close();
        Thread.sleep(100);
        Read();
    }
    
    public static void Read() throws SQLException{
        String dburl = "jdbc:mysql://192.168.201.59:3306/epointbid_pb6j";
        Connection conn = DriverManager.getConnection(dburl, "root", "11111");

        Statement stat = conn.createStatement();
        ResultSet rst = stat.executeQuery("select * from frame_user where userguid='test'");
        while (rst.next()) {
            System.out.println(rst.getString(1) + "  " + rst.getString("displayname") );
        }
        rst.close();
        stat.close();
        conn.close();
    }

}
