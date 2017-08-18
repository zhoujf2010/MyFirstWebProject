package com.zjf.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zjf.common.SpringContextUtil;

//通过连接池，多线程操作
public class testdb2
{

    public static void main(String[] args) throws Exception {
        new ClassPathXmlApplicationContext("spring.xml");
        
        for (int i = 0; i < 20; i++) {
            Thread thd = new Thread(new Runnable()
            {
                @Override
                public void run() {
                    try {
                        DoFun();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("end1");
                }
            });

            thd.start();
        }

    }
    
    static int xh = 0;
    private static void DoFun() throws Exception {
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//            String dburl = "jdbc:oracle:thin:@192.168.201.60:1521:orcl";
           // Connection conn = DriverManager.getConnection(dburl, "EpointBid_TP7", "11111");
            
            DataSource datasource = (DataSource)SpringContextUtil.getContext().getBean("dataSource");
            Connection conn = datasource.getConnection();

            xh++;
            System.out.println(xh);
            
            Statement stat = conn.createStatement();
            // conn.commit();
            ResultSet rst = stat.executeQuery("select * from Frame_user where rownum<=5");
            while (rst.next()) {
                System.out.println(rst.getString(1) + "  " + rst.getString(2) + "  " + rst.getString("displayName"));
            }

            Thread.sleep(10000);
            rst.close();
            stat.close();
            conn.close();
        }

}
