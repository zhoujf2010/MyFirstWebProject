package com.zjf.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.NClob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 转换工具类
 * 
 * @author komojoemary
 * @version [版本号, 2011-12-13]
 */
public class ConvertUtil
{
    /**
     * 特殊符号1
     */
    private static final String SPECIAL_MARK1 = "_convertutil_special1_";

    /**
     * 特殊符号2
     */
    private static final String SPECIAL_MARK2 = "_convertutil_special2_";

    /**
     * 将数据转换到需要的类型
     * 
     * @param ptype
     *            想要的数据类型
     * @param val
     *            数据值
     * @return Object 转换结果
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertDataType(Class<T> ptype, Object val) {
        T result = null;
        if (ptype == String.class) {
            if (StringUtil.isNotBlank(val)) {
                val = val.toString().trim();
            }
            else {
                val = "";
            }
        }
        else if (ptype == Boolean.class || ptype == boolean.class) {
            if (StringUtil.isNotBlank(val)) {
                if ("1".equals((String.valueOf(val))) || "true".equals((String.valueOf(val)))) {
                    val = true;
                }
                else {
                    val = false;
                }
            }
            else {
                if (ptype == boolean.class) {
                    val = false;
                }
                else {
                    val = null;
                }
            }
        }
        else if (ptype == Integer.class || ptype == int.class) {
            if (StringUtil.isNotBlank(val)) {
                String temp = String.valueOf(val);
                if (temp.toLowerCase().endsWith("px")) {
                    temp = temp.substring(0, temp.indexOf("px"));
                }
                else if (temp.endsWith("%")) {
                    val = null;
                }
                if (val != null) {
                    val = Integer.parseInt(temp);
                }
            }
            else {
                if (ptype == int.class) {
                    val = 0;
                }
                else {
                    val = null;
                }
            }
        }
        else if (ptype == Date.class) {
            if (val != null && !(val instanceof Date)) {
                val = val.toString().replace("T", " ");
                val = EpointDateUtil.convertString2DateAuto(val.toString());
            }
        }
        else if (ptype == Double.class || ptype == double.class) {
            if (StringUtil.isBlank(val))
                if (ptype == Double.class)
                    val = null;
                else
                    val = Double.parseDouble("0");
            else
                val = Double.parseDouble(String.valueOf(val));
        }
        else if (ptype == Long.class || ptype == long.class) {
            if (StringUtil.isBlank(val))
                if (ptype == Long.class)
                    val = null;
                else
                    val = 0L;
            else
                val = Long.parseLong(String.valueOf(val));
        }
        else if (ptype == Float.class || ptype == float.class) {
            if (StringUtil.isBlank(val))
                if (ptype == Float.class)
                    val = null;
                else
                    val = Float.parseFloat("0");
            else
                val = Float.parseFloat(String.valueOf(val));
        }
        else if (ptype == Short.class || ptype == short.class) {
            if (StringUtil.isBlank(val))
                if (ptype == Short.class)
                    val = null;
                else
                    val = Short.parseShort("0");
            else
                val = Short.parseShort(String.valueOf(val));
        }
        else if (ptype == BigDecimal.class) {
            if (StringUtil.isNotBlank(val)) {
                if (val instanceof Integer || val instanceof String) {
                    val = new BigDecimal(String.valueOf(val));
                }
                else if (!(val instanceof BigDecimal)) {
                    val = null;
                }
            }
        }
        if (val != null) {
            result = (T) val;
        }
        return result;
    }

    /**
     * 转换map为字符串对象(T为简单对象，不能是集合类对象)
     * 
     * @param param
     *            Map<String, T>对象
     * @return String 转换后的结果
     */
    public static <T> String convertMapToString(Map<String, T> param) {
        String result = null;
        if (param != null && param.size() > 0) {
            List<T> values = new ArrayList<T>();
            List<String> keys = new ArrayList<String>();
            Set<String> key = param.keySet();
            for (String item : key) {
                T obj = param.get(item);
                // 对于空数据不会进行序列化，避免出现顺序错位问题
                if (obj != null) {
                    values.add(obj);
                    keys.add(item);
                }
            }
            result = "";//convertListToString(keys, SPECIAL_MARK2) + SPECIAL_MARK1 + BeanXmlUtil.beanListToXml(values);
        }
        return result;
    }

    /**
     * 转换字符串对象为Map<String, T>
     * 
     * @param selectValue
     *            字符串对象
     * @param cls
     *            需要转换的T类
     * @return Map<String,T> 转换后的结果
     */
    public static <T> Map<String, T> convertStringToMap(String selectValue, Class<T> cls) {
        Map<String, T> map = new HashMap<String, T>();
        if (StringUtil.isNotBlank(selectValue)) {
            String[] labelValues = selectValue.split(SPECIAL_MARK1);
            List<String> keys = convertStringToList(labelValues[0], SPECIAL_MARK2);
            List<T> values = null;// BeanXmlUtil.xmlToBeanList(labelValues[1], cls);
            int i = 0;
            for (String item : keys) {
                map.put(item, values.get(i));
                i++;
            }
        }
        return map;
    }

    /***
     * 将list转换字符串
     * 
     * @param inList
     * @param token
     *            分隔符,默认为;
     * @return String
     */
    public static String convertListToString(List<?> inList, String token) {
        // 设置默认分隔符
        if (token == null || token.length() == 0) {
            token = ";";
        }
        if (inList != null) {
            int length = inList.size();
            if (length > 0) {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < length; i++) {
                    sb.append(inList.get(i).toString()).append(token);
                }
                return (sb.toString());
            }
        }
        return "";
    }

    /***
     * 将list转换为数组
     * 
     * @param inList
     * @return Object[]
     */
    public static Object[] convertListToObjectArray(List<?> inList) {
        Object[] result = null;
        if (inList != null) {
            int length = inList.size();
            if (length > 0) {
                result = new Object[length];
                for (int i = 0; i < length; i++) {
                    result[i] = inList.get(i);
                }
            }
        }
        return result;
    }

    /***
     * 将数组转换为字符串
     * 
     * @param array
     * @return String
     */
    public static String convertArrayToString(Object[] array) {
        StringBuffer result = new StringBuffer();
        if (array != null) {
            for (Object item : array) {
                if (item != null) {
                    result.append(item.toString() + ";");
                }
                else {
                    result.append("null;");
                }
            }
        }
        return result.toString();
    }

    /***
     * 将list转换为数组
     * 
     * @param inList
     * @return Object[]
     */
    public static String[] convertListToStringArray(List<?> inList) {
        String[] result = null;
        if (inList != null) {
            int length = inList.size();
            if (length > 0) {
                result = new String[length];
                for (int i = 0; i < length; i++) {
                    result[i] = (String) inList.get(i);
                }
            }
        }
        return result;
    }

    /***
     * 将字符串转为list
     * 
     * @param str
     *            要转换的字符串
     * @param token
     *            分隔符
     * @return List<String>分割后的list结果
     */
    public static List<String> convertStringToList(String str, String token) {
        if (token == null || token.length() == 0) {
            token = ";";
        }
        List<String> rntList = new ArrayList<String>();
        if (StringUtil.isNotBlank(str)) {
            String[] a = str.split(token);
            for (String item : a) {
                rntList.add(item);
            }
        }
        return rntList;
    }

    /**
     * 将布尔值转换成整数
     * 
     * @param bool
     *            boolean值
     * @return Integer 整数
     */
    public static Integer convertBooleanToInteger(Boolean bool) {
        if (bool == null)
            return 0;
        if (bool)
            return 1;
        else
            return 0;
    }

    /**
     * 将整数转为布尔值
     * 
     * @param intvalue
     *            整数
     * @return Boolean boolean值
     */
    public static Boolean convertIntegerToBoolean(Integer intvalue) {
        if (intvalue == null)
            return false;
        if (intvalue > 0)
            return true;
        else
            return false;
    }

    /**
     * 将布尔值转换为字符串
     * 
     * @param b
     *            boolean值
     * @return String 字符串
     */
    public static String convertBooleanToString(Boolean b) {
        if (b != null) {
            return b.toString();
        }
        return null;
    }

    /**
     * 将字符串转换为boolean值
     * 
     * @param s
     *            字符串
     * @return Boolean boolean值
     */
    public static Boolean convertStringToBoolean(String s) {
        if (s != null)
            return Boolean.valueOf(s);
        return null;
    }

    /**
     * 转换长度为容量(b/kb/mb)
     * 
     * @param dataSize
     *            长度
     * @return String 容量
     */
    public static String convertLengthToCapacity(long dataSize) {
        if (dataSize >= 1073741824) {
            return ((double) Math.round(dataSize / 1073741824d * 100) / 100) + " GB";
        }
        else if (dataSize >= 1048576) {
            return ((double) Math.round(dataSize / 1048576d * 100) / 100) + " MB";
        }
        else if (dataSize >= 1024) {
            return ((double) Math.round(dataSize / 1024d * 100) / 100) + " KB";
        }
        else {
            return dataSize + " B";
        }
    }

    /**
     * 将clob转换为字符串
     * 
     * @param clob
     *            clob字段
     * @return String 转换结果
     */
    public static String changeClobtoString(Clob clob) {
        String str = "";
        if (clob != null) {
            Reader inStream = null;
            try {
                inStream = clob.getCharacterStream();
                char[] ch = new char[(int) clob.length()];
                inStream.read(ch);
                str = new String(ch);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (inStream != null) {
                        inStream.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    /**
     * 
     * 将string对象转换为NClob对象(在大文本类型字段jdbc连接时使用)
     * 
     * @param str
     *            字符串
     * @param conn
     *            jdbc连接
     * @return NClob nclob对象
     */
    public static NClob changeStringToNClob(String str, Connection conn) {
        NClob nclob = null;
        try {
            nclob = (NClob) createOracleLob(conn, "java.sql.NClob");
            Method methodToInvoke = nclob.getClass().getMethod("getCharacterOutputStream", (Class[]) null);
            Writer writer = (Writer) methodToInvoke.invoke(nclob, (Object[]) null);
            writer.write(str);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        } // BLOB的话传oracle.sql.BLOB
        return nclob;
    }

    /**
     * 
     * Description:创建Clob
     * 
     * @param conn数据库连接对象
     * @param lobClassName
     *            oracle.sql.CLOB
     * @return oracle.sql.CLOB
     * @throws Exception
     * 
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private static Object createOracleLob(Connection conn, String lobClassName) throws Exception {
        Class lobClass = conn.getClass().getClassLoader().loadClass(lobClassName);
        final Integer DURATION_SESSION = new Integer(lobClass.getField("DURATION_SESSION").getInt(null));
        final Integer MODE_READWRITE = new Integer(lobClass.getField("MODE_READWRITE").getInt(null));
        Method createTemporary = lobClass.getMethod("createTemporary",
                new Class[] {Connection.class, boolean.class, int.class });
        Object lob = createTemporary.invoke(null, new Object[] {conn, false, DURATION_SESSION });
        Method open = lobClass.getMethod("open", new Class[] {int.class });
        open.invoke(lob, new Object[] {MODE_READWRITE });
        return lob;
    }

    /**
     * 将blob转换为字符串
     * 
     * @param blob
     *            blob字段
     * @return String 转换结果
     */
    public static String changeBlobtoString(Blob blob) {
        String str = "";
        if (blob != null) {
            InputStream inStream = null;
            try {
                inStream = blob.getBinaryStream();
                byte[] bytes = new byte[inStream.available()];
                inStream.read(bytes);
                str = new String(bytes);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (inStream != null) {
                        inStream.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    /**
     * BLOB转byte[]
     * 
     * @param blob
     *            blob字段
     * @return byte[]转换结果
     */
    public static byte[] changeBolbToByte(Blob blob) {
        if (blob == null) {
            return null;
        }
        int BUFFER_SIZE = 1024 * 1024;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            is = blob.getBinaryStream();
            baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] b = new byte[BUFFER_SIZE];
            while ((len = is.read(b, 0, BUFFER_SIZE)) != -1) {
                baos.write(b, 0, len);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                is.close();
                baos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
        return baos.toByteArray();
    }

    /**
     * 将异常转换为字符串
     * 
     * @param aThrowable
     *            异常对象
     * @return String
     */
    public static String getStackTrace(Throwable aThrowable) {
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        aThrowable.printStackTrace(printWriter);
        return result.toString();
    }

    public static void main(String[] args) {
        Map<String, String> extvalue = new HashMap<String, String>();
        extvalue.put("key1", "value1");
        extvalue.put("key2", "value2");
        extvalue.put("key3", "value3");
        extvalue.put("key4", "value4");
        extvalue.put("key5", "value5");
        extvalue.put("key6", "value6");
        String v = ConvertUtil.convertMapToString(extvalue);

        extvalue = ConvertUtil.convertStringToMap(v, String.class);

        Set<String> keys = extvalue.keySet();
        for (String key : keys) {
            System.out.println("key:" + key + "value:" + extvalue.get(key));
        }
        System.out.println();
    }

}
