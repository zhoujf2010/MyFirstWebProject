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

    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.jdbc.Driver");

        String dburl = "jdbc:mysql://localhost:3306/testdb";
        // String dburl = "jdbc:mysql://localhost:3306/testDB";
        Connection conn = DriverManager.getConnection(dburl, "root", "11111");

        test db = new test();
        try {
            Statement stat = conn.createStatement();
            // conn.commit();
            ResultSet rst = stat.executeQuery("select * from pingbiao_tb_qingdanitem");
            // rst.
            rst.absolute(5);// 跳到指定行
            while (rst.next()) {
                // System.out.println(rst.getString(1) + " " + rst.getString(2) + " " + rst.getString(3));
                Map<String, Object> v = db.toMap(rst);
                Map<String, Object> c = v;
            }
            rst.close();
            stat.close();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            conn.close();
        }

        // Query sqlQuery = null;
        // sqlQuery = crudService.createNativeQuery(sql, HashMap.class);
        
//        List<Map<String, Object>> l = (List<Map<String, Object>>) entities;
//        List<Record> records = new ArrayList<Record>();
//        for (Map<String, Object> m : l) {
//            Record record = new Record();
//            record.setColumns(m);
//            record.getModifyFlag().clear();
//            records.add(record);
//        }
//        entities = (List<T>) records;

    }

    public Map<String, Object> toMap(ResultSet rs) throws SQLException, JDBCException {
        Map<String, Object> result = new HashMap<String, Object>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();
        for (int i = 1; i <= cols; i++) {
            result.put(rsmd.getColumnLabel(i), rs.getObject(i));
        }
        return result;
    }

    public Object[] toArray(ResultSet rs) throws SQLException, JDBCException {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        Object[] result = new Object[cols];
        boolean haveRownum = false;
        for (int i = 0; i < cols; i++) {
            // 对于oracle数据库的分页,会额外多查询出一列rowNum,在这里进行处理 edit by zjf 2014/8/4
            if (!haveRownum) {
                String columnName = meta.getColumnName(i + 1);
                if ("rownum_".equalsIgnoreCase(columnName)) {
                    haveRownum = true;
                }
            }
            // 如果不用类型进行判断,直接getObject出来的内容是各个驱动厂商自己的值,比如日期,就是oracle.time,无法直接转为我们常用的java的date进行使用，edit
            // by zjf 2013/5/13
            result[i] = rs.getObject(i + 1);// SqlTypeFactory.getSqlType(meta.getColumnType(i + 1)).get(rs, i + 1);
        }
        if (haveRownum) {
            Object[] tempResult = new Object[cols - 1];
            for (int i = 0; i < cols - 1; i++) {
                tempResult[i] = result[i];
            }
            result = tempResult;
        }

        return result;
    }

}
