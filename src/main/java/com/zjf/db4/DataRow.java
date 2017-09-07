package com.zjf.db4;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DataRow extends HashMap<String, Object> implements Serializable
{
    private static final long serialVersionUID = -2689062767790807572L;

    private String m_tableName;
    private String m_keyField;

    public DataRow() {
    }

    public DataRow(String tableName, String keyField) {
        this.m_tableName = tableName;
        this.m_keyField = keyField;
    }

    public String getTableName() {
        return m_tableName;
    }

    public void setTableName(String m_tableName) {
        this.m_tableName = m_tableName;
    }

    public String getKeyField() {
        return m_keyField;
    }

    public void setKeyField(String m_keyField) {
        this.m_keyField = m_keyField;
    }

    private Map<String, Object> recOldval = null;

    // 记录修改历史值
    private void ChangeValue(String column, Object Oldval) {
        if (recOldval == null)
            recOldval = new HashMap<String, Object>();
        if (recOldval.containsKey(column))
            return;// 二次修改，不记录中间值

        rowState = DataRowState.Modified;
        recOldval.put(column, Oldval);
    }
    
    public String[] getChangedKeys(){
        return recOldval.keySet().toArray(new String[0]);
    }
    
    public Object getOldValue(String key){
        return recOldval.get(key);
    }
    
    public enum DataRowState
    {
        Detached(1), Unchanged(2), Added(4), Deleted(8), Modified(16);
        int value;

        DataRowState(int s) {
            value = s;
        }

        public int getValue() {
            return value;
        }
    }

    private DataRowState rowState = DataRowState.Added;

    /**
     * 取得行状态
     */
    public DataRowState getRowState() {
        return rowState;
    }

    public void setRowState(DataRowState rowState) {
        this.rowState = rowState;
    }
    
    public void AcceptChanges(){
        rowState = DataRowState.Unchanged;
        recOldval = null;
    }

    /**
     */
    public DataRow set(String column, Object value) {
        column = keyDeal(column);
        Object orgVal = get(column);
        if (orgVal == null && value == null)
            return this;
        if (orgVal != null && orgVal.equals(value))
            return this;

        ChangeValue(column, orgVal);
        super.put(column, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String column) {
        T result = (T) super.get(keyDeal(column));
        if (result == null)
            result = (T) super.get(column);
        return result;
    }

    public Object get(Object key) {
        return super.get(keyDeal(key.toString()));
    }

    private String keyDeal(String key) {
        return key.toLowerCase();//.replace("_", "");
    }

    @Override
    public Object put(String key, Object value) {
        key = keyDeal(key);
        return super.put(key, value);
    }
}
