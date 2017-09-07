package com.zjf.db4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.zjf.common.SpringContextUtil;
import com.zjf.test.StringUtil;

public class DBOper
{
    //保存后，在同个线程中(同一次请求中)拿到的数据库连接都是同一个。
    private static ThreadLocal<DBOper> localDs = new ThreadLocal<DBOper>();

    private DBOper(Connection conn) {
        this.conn = conn;
    }    

    public static DBOper getInstance() {
        try {
            DBOper oper = localDs.get();
            if (oper == null) {
                DataSource datasource = (DataSource) SpringContextUtil.getContext().getBean("dataSource");
                Connection conn;
                conn = datasource.getConnection();
                oper = new DBOper(conn);
                localDs.set(oper);
            }
            return new DBOper(oper.conn);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DBOper getInstance(Connection conn) {
        return new DBOper(conn);
    }

    private Connection conn = null;
    
    public Connection getConnection() {
        return conn;
    }

    public void Close() {
        try {
            if (conn.isClosed())
                return;
            conn.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DataRow> QueryList(String sql, int pageindex, int pageSize, Object... args) {
        return QueryList(sql, pageindex, pageSize, DataRow.class, args);
    }

    public void Begin() {
        // conn.commit();
        // conn.rollback();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> QueryList(String sql, int pageindex, int pageSize, Class<T> clazz, Object... args) {
        List<T> ret = new ArrayList<T>();
        java.sql.PreparedStatement stat = null;
        ResultSet rst = null;
        try {
            stat = conn.prepareStatement(sql);
            // 设置参数
            for (int i = 0; i < args.length; i++)
                stat.setObject(i + 1, args[i]);
            rst = stat.executeQuery();
            int startindex = pageindex * pageSize;
            if (startindex > 0)
                rst.absolute(startindex + 1);// 跳到指定行
            int p = 0;
            while (rst.next()) {
                DataRow obj = (DataRow) clazz.newInstance();
                ResultSetMetaData rsmd = rst.getMetaData();
                int cols = rsmd.getColumnCount();
                for (int i = 1; i <= cols; i++)
                    obj.put(rsmd.getColumnLabel(i), rst.getObject(i));
                obj.AcceptChanges();// 首次读出后数据状态不变
                ret.add((T) obj);
                p++;
                if (p >= pageSize)
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                rst.close();
                stat.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public String ExecuteString(String sql, Object... args) {
        Object obj = ExecuteObject(sql, args);
        if (obj == null)
            return "";
        return obj.toString();
    }

    public int ExecuteInt(String sql, Object... args) {
        try {
            return Integer.parseInt(ExecuteString(sql, args));
        }
        catch (Exception e) {
            return 0;
        }
    }

    public Object ExecuteObject(String sql, Object... args) {
        DataRow dr = QueryDataRow(sql, args);
        if (dr != null)
            return dr.get(dr.keySet().iterator().next());
        return null;
    }

    public boolean ExecuteSql(String sql, Object... args) {
        java.sql.PreparedStatement stat = null;
        try {
            stat = conn.prepareStatement(sql);
            // 设置参数
            for (int i = 0; i < args.length; i++)
                stat.setObject(i + 1, args[i]);
            return stat.execute();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                stat.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public DataRow QueryDataRow(String sql, Object... args) {
        List<DataRow> lst = QueryList(sql, 0, 1, args);
        if (lst.size() > 0)
            return lst.get(0);
        return null;
    }

    public <T> T QueryDataRow(String sql, Class<T> clazz, Object... args) {
        List<T> lst = QueryList(sql, 0, 1, clazz, args);
        if (lst.size() > 0)
            return lst.get(0);
        return null;
    }

    public void Delete(DataRow dataRow) {
        String TableName = dataRow.getTableName();
        String keyFields = dataRow.getKeyField();
        if (!StringUtil.isNotBlank(TableName) || !StringUtil.isNotBlank(keyFields))
            throw new RuntimeException("未设置表名或主健，无法执行删除操作");
        Delete(dataRow, TableName, keyFields);
    }

    public void Delete(DataRow dataRow, String TableName, String keyFields) {
        String sql = "delete from " + TableName + " where " + keyFields + "=?";
        ExecuteSql(sql, dataRow.get(keyFields));
        dataRow.setRowState(DataRow.DataRowState.Deleted);
    }

    public void Update(DataRow dataRow) {
        Update(dataRow, false);
    }

    public void Update(DataRow dataRow, String TableName, String keyFields) {
        Update(dataRow, TableName, keyFields, false);
    }

    public void Update(DataRow dataRow, boolean checkDirty) {
        String TableName = dataRow.getTableName();
        String keyFields = dataRow.getKeyField();
        if (!StringUtil.isNotBlank(TableName) || !StringUtil.isNotBlank(keyFields))
            throw new RuntimeException("未设置表名或主健，无法执行更新操作");
        Update(dataRow, TableName, keyFields, checkDirty);
    }

    public void Update(DataRow dataRow, String TableName, String keyFields, boolean checkDirty) {
        if (dataRow.getRowState() == DataRow.DataRowState.Unchanged)
            return;

        String[] keys = dataRow.getChangedKeys();

        if (checkDirty) { // 检查脏数据
            String fields = "";
            for (String fd : keys)
                fields += fd + ",";
            fields = fields.substring(0, fields.length() - 1);
            String sql = "select " + fields + " from " + TableName + " where " + keyFields + "=?";
            Object keyvalue = dataRow.get(keyFields);
            DataRow oldrow = this.QueryDataRow(sql, keyvalue);
            // 比较数据
            String equal = "";
            for (String fd : keys) {
                Object val1 = dataRow.getOldValue(fd);
                Object val2 = oldrow.get(fd);
                if (val1 == null && val2 == null)
                    continue;
                if (val1 != null && !val1.equals(val2))
                    equal += "原值：" + val1 + "，现库中值为：" + val2;
            }
            if (!StringUtil.isBlank(equal))
                throw new RuntimeException("有脏数据:" + equal);
        }

        String sql = "update " + TableName + " set ";
        Object[] args = new Object[keys.length + 1];
        for (int i = 0; i < keys.length; i++) {
            sql += keys[i] + "=?,";
            args[i] = dataRow.get(keys[i]);
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " where " + keyFields + "=?";
        args[keys.length] = dataRow.get(keyFields);
        ExecuteSql(sql, args);
        dataRow.AcceptChanges();
    }

    public void Insert(DataRow dataRow) {
        String TableName = dataRow.getTableName();
        if (!StringUtil.isNotBlank(TableName))
            throw new RuntimeException("未设置表名或主健，无法执行插入操作");
        Insert(dataRow, TableName);
    }

    public void Insert(DataRow dataRow, String TableName) {
        if (dataRow.getRowState() != DataRow.DataRowState.Added
                && dataRow.getRowState() != DataRow.DataRowState.Modified)
            return;

        String[] keys = dataRow.keySet().toArray(new String[0]);

        String sql = "insert into " + TableName + "(";
        Object[] args = new Object[keys.length];
        String pamlst = "";
        for (int i = 0; i < keys.length; i++) {
            sql += keys[i] + ",";
            pamlst += "?,";
            args[i] = dataRow.get(keys[i]);
        }
        sql = sql.substring(0, sql.length() - 1);
        pamlst = pamlst.substring(0, pamlst.length() - 1);
        sql += ") values(" + pamlst + ")";
        ExecuteSql(sql, args);
        dataRow.AcceptChanges();
    }
}
