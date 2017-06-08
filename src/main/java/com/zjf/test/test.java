package com.zjf.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class test
{

    public static void main(String[] args) throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");

        String dburl = "jdbc:oracle:thin:@192.168.201.60:1521:orcl";
        Connection conn = DriverManager.getConnection(dburl, "EPOINTBID_TP7", "11111");

        try {
            Statement stat = conn.createStatement();
            // conn.commit();
            ResultSet rst = stat.executeQuery("select * from Frame_Module");

            List<FrameModule> ret = new ArrayList<FrameModule>();
            while (rst.next()) {
                ResultSetMetaData rsmd = rst.getMetaData();
                FrameModule rec = new FrameModule();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    rec.put(rsmd.getColumnName(i + 1).toLowerCase(), rst.getObject(rsmd.getColumnName(i + 1)));
                }
                ret.add(rec);
            }
            rst.close();
            stat.close();

            long t = System.currentTimeMillis();
            int num =0;
            for (int p = 0; p < 4000; p++) {
                for (int i = 0; i < ret.size(); i++) {
                    String modecode = ret.get(i).getModuleCode();
                    String modecode2 = modecode;
                    num++;
                }
            }
            
            System.out.println(num +"次，" + (System.currentTimeMillis()-t)/1000.0 + "s");

        }
        catch (Exception e) {
            throw e;
        }
        finally {
            conn.close();
        }
    }

}
