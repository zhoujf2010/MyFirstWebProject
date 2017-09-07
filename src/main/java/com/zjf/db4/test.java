package com.zjf.db4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.JDBCException;


public class test
{
    private static ThreadLocal<DBOper> localDs = new ThreadLocal<DBOper>();


    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        String dburl = "jdbc:mysql://localhost:3306/testdb";
        // String dburl = "jdbc:mysql://localhost:3306/testDB";
        Connection conn = DriverManager.getConnection(dburl, "root", "11111");
        
        DBOper dbOper = DBOper.getInstance(conn);
//        String sql = "select * from pingbiao_tb_qingdanitem where qingdanno=?";
//        List<DataRow> lst = dbOper.QueryList(sql, 0, 20,"010101002001");
        
        DataRow peo = dbOper.QueryDataRow("select * from people where id=?",20);
        peo.setTableName("people");
        peo.setKeyField("id");
        peo.set("Note", "Hello");
        peo.set("address", "张家港cc");
        byte[] bts = "aaaaa".getBytes();
       // peo.set("pic", bts);
        dbOper.Update(peo,true);
        
//        DataRow npeo = new DataRow("people","id");
//        npeo.set("name", "李四");
//        npeo.set("age", 30);
//        npeo.set("Note", "test");
//        dbOper.Insert(npeo);
        
        System.out.println(peo);
        
        localDs.set(dbOper);
        System.out.println(dbOper.hashCode());
        Do();
        dbOper.Close();

    }
    
    private static void Do(){
        DBOper o = localDs.get();
        System.out.println(o.hashCode());
    }
}
