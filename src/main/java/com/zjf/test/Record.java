package com.zjf.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Record
 */
public class Record extends HashMap<String, Object>implements Serializable {

	private static final long serialVersionUID = 905784513600884082L;

	transient private String sql_tableName;
	transient private String[] primaryKeys;
	transient private Set<String> modifyFlag = null;

	public Record() {
	}

	public Record(String sql_tableName) {
		this.sql_tableName = EpointDaoUtil.caps(sql_tableName);
	}

	public Record(String sql_tableName, String primaryKey) {
		this.sql_tableName = EpointDaoUtil.caps(sql_tableName);
		this.primaryKeys = new String[] { primaryKey };
	}

	public Record(String sql_tableName, String[] primaryKeys) {
		this.sql_tableName = EpointDaoUtil.caps(sql_tableName);
		this.primaryKeys = primaryKeys;
	}

	@TransientK
	public Set<String> getModifyFlag() {
		if (modifyFlag == null) {
			modifyFlag = new HashSet<String>();
		}
		return modifyFlag;
	}

	public void setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public void setPrimaryKeys(String primaryKey) {
		this.primaryKeys = new String[] { primaryKey };
	}

	public void setSql_TableName(String sql_tableName) {
		this.sql_tableName = EpointDaoUtil.caps(sql_tableName);
	}

	@TransientK
	public String[] getPrimaryKeys() {
		return primaryKeys;
		/*if (primaryKeys != null && primaryKeys.length > 0)
			return primaryKeys;
		else {
		
			boolean findit = false;
			Class<?> clazz = this.getClass();
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				if (clazz.getName().equals(BaseEntity.class.getName())) {
					findit = true;
					break;
				}
			}
			if (findit) {
				String[] pks = EntityMapping.me().getEntity(this.getClass()).getPrimaryKey();
				if (pks != null && pks.length > 0)
					return pks;
				else
					throw new EpointDaoException("主键不存在");
			} else
				throw new EpointDaoException("主键不存在");
		}*/
	}

	@TransientK
	public String getSql_TableName() {
		if (StringUtil.isNotBlank(sql_tableName))
			return sql_tableName;
		else {
			boolean findit = false;
			Class<?> clazz = this.getClass();
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				if (clazz.getName().equals(BaseEntity.class.getName())) {
					findit = true;
					break;
				}
			}
//			if (findit) {
//				String tabName = EntityMapping.me().getEntity(this.getClass()).getName();
//				if (StringUtil.isNotBlank(tabName))
//					return EpointDaoUtil.caps(tabName);
//				else
//					throw new EpointDaoException("表名不存在");
//			} else
				throw new EpointDaoException("实体必须继承com.epoint.core.BaseEntity");
		}
	}

	public boolean containsKey(String column) {
		return super.containsKey(EpointDaoUtil.caps(column));
	}

	/**
	 * Return columns map.
	 */
	/*
	 * @SuppressWarnings("unchecked") public Map<String, Object> getColumns() {
	 * return columns; }
	 */

	/**
	 * Set columns value with map.
	 * 
	 * @param columns
	 *            the columns map
	 */
	public Record setColumns(Map<String, Object> columns) {
		for (Map.Entry<String, Object> entry : columns.entrySet()) {
			set(EpointDaoUtil.caps(entry.getKey()), entry.getValue());
		}
		return this;
	}

	/**
	 * Set columns value with Record.
	 * 
	 * @param record
	 *            the Record object
	 */
	public Record setColumns(Record record) {
		super.putAll(record);
		return this;
	}

	/**
	 * Remove attribute of this record.
	 * 
	 * @param column
	 *            the column name of the record
	 */
	public Record remove(String column) {
		super.remove(EpointDaoUtil.caps(column));
		return this;
	}

	/**
	 * Remove columns of this record.
	 * 
	 * @param columns
	 *            the column names of the record
	 */
	public Record remove(String... columns) {
		if (columns != null)
			for (String c : columns)
				super.remove(EpointDaoUtil.caps(c));
		return this;
	}

	/**
	 * Remove columns if it is null.
	 */
	public Record removeNullValueColumns() {
		for (java.util.Iterator<Map.Entry<String, Object>> it = super.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> e = it.next();
			if (e.getValue() == null) {
				it.remove();
			}
		}
		return this;
	}

	/**
	 * Keep columns of this record and remove other columns.
	 * 
	 * @param columns
	 *            the column names of the record
	 */
	public Record keep(String... columns) {
		if (columns != null && columns.length > 0) {
			Map<String, Object> newColumns = new HashMap<String, Object>(columns.length); // getConfig().containerFactory.getColumnsMap();
			for (String c : columns)
				if (super.containsKey(EpointDaoUtil.caps(c))) // prevent put
																// null value to
																// the
																// newColumns
					newColumns.put(EpointDaoUtil.caps(c), get(c));

			super.clear();
			super.putAll(newColumns);
		} else
			super.clear();
		return this;
	}

	/**
	 * Keep column of this record and remove other columns.
	 * 
	 * @param column
	 *            the column names of the record
	 */
	public Record keep(String column) {
		column = EpointDaoUtil.caps(column);
		if (super.containsKey(column)) { // prevent put null value to the
											// newColumns
			Object keepIt = get(column);
			super.clear();
			super.put(column, keepIt);
		} else
			super.clear();
		return this;
	}

	/**
	 * Remove all columns of this record.
	 */
	public void clear() {
		super.clear();
	}

	/**
	 * Set column to record.
	 * 
	 * @param column
	 *            the column name
	 * @param value
	 *            the value of the column
	 */
	public Record set(String column, Object value) {
		column = EpointDaoUtil.caps(column);
		getModifyFlag().add(column);
		super.put(column, value);
		return this;
	}

	  public <T> T getNative(String column)
	  {
	    return (T)super.get(column);
	  }

	/**
	 * Get column of any mysql type
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String column) {
		T result = (T) super.get(EpointDaoUtil.caps(column));
		if (result == null) {
			result = (T) super.get(column);
		}
		// 还没有的话遍历一把
        if (result == null) {
            Set<String> keys = this.keySet();
            for (String item : keys) {
                if (column.equalsIgnoreCase(item)) {
                    result = (T) super.get(item);
                    break;
                }
            }
        }
		return result;
	}

	public Object get(Object key) {
		return super.get(EpointDaoUtil.caps(key.toString()));
	}

	/**
	 * Get column of any mysql type. Returns defaultValue if null.
	 */
	public <T> T get(String column, Object defaultValue) {
		Object result = get(column);
		return (T) (result != null ? result : defaultValue);
	}

	public Record getRecord(String column) {
		Object o = get(column);
		if (o != null && o instanceof Record)
			return (Record) o;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<Record> getRecordList(String column) {
		return (List<Record>) get(column);
	}

	/**
	 * Get column of mysql type: varchar, char, enum, set, text, tinytext,
	 * mediumtext, longtext
	 */
	public String getStr(String column) {
		return ConvertUtil.convertDataType(String.class, get(column));
	}

	/**
	 * Get column of mysql type: int, integer, tinyint(n) n > 1, smallint,
	 * mediumint
	 */
	public Integer getInt(String column) {
		return ConvertUtil.convertDataType(Integer.class, get(column));
	}

	/**
	 * Get column of mysql type: bigint
	 */
	public Long getLong(String column) {
		return ConvertUtil.convertDataType(Long.class, get(column));
	}

	/**
	 * Get column of mysql type: unsigned bigint
	 */
	public java.math.BigInteger getBigInteger(String column) {
		return (java.math.BigInteger) get(column);
	}

	/**
	 * Get column of mysql type: date, year
	 */
	public java.util.Date getDate(String column) {
		return ConvertUtil.convertDataType(Date.class, get(column));
	}

	/**
	 * Get column of mysql type: time
	 */
	public java.sql.Time getTime(String column) {
		return (java.sql.Time) get(column);
	}

	/**
	 * Get column of mysql type: timestamp, datetime
	 */
	public java.sql.Timestamp getTimestamp(String column) {
		return (java.sql.Timestamp) get(column);
	}

	/**
	 * Get column of mysql type: real, double
	 */
	public Double getDouble(String column) {
		return ConvertUtil.convertDataType(Double.class, get(column));
	}

	/**
	 * Get column of mysql type: float
	 */
	public Float getFloat(String column) {
		return ConvertUtil.convertDataType(Float.class, get(column));
	}

	/**
	 * Get column of mysql type: bit, tinyint(1)
	 */
	public Boolean getBoolean(String column) {
		return ConvertUtil.convertDataType(Boolean.class, get(column));
	}

	/**
	 * Get column of mysql type: decimal, numeric
	 */
	public java.math.BigDecimal getBigDecimal(String column) {
		return ConvertUtil.convertDataType(BigDecimal.class, get(column));
	}

	/**
	 * Get column of mysql type: binary, varbinary, tinyblob, blob, mediumblob,
	 * longblob I have not finished the test.
	 */
	public byte[] getBytes(String column) {
		return (byte[]) get(column);
	}

	/**
	 * Get column of any type that extends from Number
	 */
	public Number getNumber(String column) {
		return (Number) get(column);
	}

	public boolean equals(Object o) {
		if (!(o instanceof Record))
			return false;
		if (o == this)
			return true;
		return super.equals(((Record) o));
	}

	/**
	 * Return column names of this record.
	 */
	@TransientK
	public String[] getColumnNames() {
		Set<String> attrNameSet = super.keySet();
		return attrNameSet.toArray(new String[attrNameSet.size()]);
	}

	/**
	 * Return column values of this record.
	 */
	@TransientK
	public Object[] getColumnValues() {
		java.util.Collection<Object> attrValueCollection = super.values();
		return attrValueCollection.toArray(new Object[attrValueCollection.size()]);
	}

	public <T extends BaseEntity> T toEntity(Class<? extends BaseEntity> clazz) {
		T t = null;
		try {
			t = (T) clazz.newInstance();
			t.setColumns(this);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

}
