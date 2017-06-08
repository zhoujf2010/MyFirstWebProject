package com.zjf.db3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommonDao
{
    private CommonDao(){
        try {
            Class.forName("com.mysql.jdbc.Driver");

            String dburl = "jdbc:mysql://localhost:3306/testDB";
            conn = DriverManager.getConnection(dburl, "root", "11111");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection conn;
    
    private static CommonDao instance;

    public static CommonDao getInstance() {
        if (instance == null)
            instance = new CommonDao();
        return instance;
    }

    public <T> List<T> findList(String sql, Class<T> cls) {
        try {
            Statement stat = conn.createStatement();
            ResultSet rst = stat.executeQuery(sql);
            List<T>  retlst = new ArrayList<T> ();
            
            while (rst.next()) {                
               T obj = cls.newInstance();
               HashMap<String,Object> rec = (HashMap<String,Object>)obj;
                
               ResultSetMetaData rsmd = rst.getMetaData();
               for(int i = 0; i<rsmd.getColumnCount();i++){
                   rec.put(rsmd.getColumnName(i+1).toLowerCase(), rst.getObject(rsmd.getColumnName(i+1)));
               }
               
               retlst.add(obj);
            }
            rst.close();
            stat.close();
            
            return retlst;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}
