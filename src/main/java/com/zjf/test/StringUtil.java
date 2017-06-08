package com.zjf.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author komojoemary
 * @version [版本号, 2011-12-12]
 */
public class StringUtil
{
    public static String ATTACH_FILE_SEPARATOR = "▍";

    /**
     * 判断是否简单类型
     * 
     * @param clazz
     * @return boolean
     */
    public static boolean isSimple(Class<?> clazz) {
        boolean simple = false;
        if (clazz.isPrimitive() || String.class.equals(clazz) || Integer.class.equals(clazz) || Date.class.equals(clazz)
                || Double.class.equals(clazz) || Float.class.equals(clazz) || Long.class.equals(clazz)
                || Short.class.equals(clazz) || Boolean.class.equals(clazz) || Byte.class.equals(clazz)
               ) {
            simple = true;
        }
        return simple;
    }

    /**
     * 去除数组内重复的元素
     * 
     * @param myData
     *            源数组
     * 
     * @return String[]
     */
    public static String[] removeDup(String[] myData) {
        if (myData.length > 0) {
            // 先经过排序
            Arrays.sort(myData);

            int size = 1; // at least 1
            for (int i = 1; i < myData.length; i++) {
                if (!myData[i].equals(myData[i - 1])) {
                    size++;
                }
            }
            String[] myTempData = new String[size];

            int j = 0;
            myTempData[j++] = myData[0];

            for (int i = 1; i < myData.length; i++) {
                if (!myData[i].equals(myData[i - 1])) {
                    myTempData[j++] = myData[i];
                }
            }
            return myTempData;
        }
        return myData;
    }

    /**
     * 从Include数组中去除Except中包含的项
     * 
     * @param include
     *            字符串数组
     * @param except
     *            字符串数组
     * @return String[]
     */
    public static String[] decrease(String[] include, String[] except) {
        ArrayList<String> AL_IncludeRaw = change2ArrayList(include);
        ArrayList<String> AL_Include = change2ArrayList(include);
        ArrayList<String> AL_Except = change2ArrayList(except);
        for (String Item_Include : AL_IncludeRaw) {
            if (AL_Except.contains(Item_Include)) {
                AL_Include.remove(Item_Include);
            }
        }
        return AL_Include.toArray(new String[0]);
    }

    /**
     * 将字符串数组转换为list
     * 
     * @param src
     *            字符串数组
     * 
     * @return ArrayList<String>
     */
    public static ArrayList<String> change2ArrayList(String[] src) {
        ArrayList<String> des = new ArrayList<String>();
        for (int i = 0; i < src.length; i++) {
            des.add(src[i]);
        }
        return des;
    }

    /**
     * 将数组转换为字符串
     * 
     * @param transactors
     *            数组值
     * @return String
     */
    public static String changeArrayToString(String[] transactors) {
        String strTransactors = "";
        for (String transactor : transactors) {
            if (transactor != "") {
                strTransactors += transactor + ";";
            }
        }
        return strTransactors.equals("") ? strTransactors : strTransactors.trim();
    }

    /**
     * 将参数添加到URL后，自动解决?还是&的问题,并且自动加上虚拟目录的地址;Params是形如
     * x=123或x=123&y=234的字符串，第一个字符不需要加?或&
     * 
     * @param RawUrl
     *            已经存在的url
     * @param ParamList
     *            新增加的参数
     * @return String
     */
    public static String convertUrlWithoutApplicationPath(String RawUrl, String ParamList) {
        if (RawUrl != null) {
            String Params = ParamList.trim();
            if (Params != null && !Params.equals(""))
                return ((RawUrl.indexOf('?') > 0) ? RawUrl + "&" + Params : RawUrl + "?" + Params);
            else
                return RawUrl;
        }
        else {
            return "";
        }
    }

    /**
     * 将参数添加到URL后，自动解决?还是&的问题,并且自动加上虚拟目录的地址;Params是形如
     * x=123或x=123&y=234的字符串，第一个字符不需要加?或&
     * 
     * @param RawUrl
     *            已经存在的url
     * @param ParamList
     *            新增加的参数
     * @param path
     *            应用程序路径
     * @return String
     */
    public static String convertUrl(String RawUrl, String ParamList, String path) {
        if (RawUrl != null) {
            String Params = ParamList.trim();
            if (Params != null && !Params.equals(""))
                return path + ((RawUrl.indexOf('?') > 0) ? RawUrl + "&" + Params : RawUrl + "?" + Params);
            else
                return path + RawUrl;
        }
        else {
            return "";
        }
    }

    /**
     * 截取指定长度字符串
     * 
     * @param instr
     *            字符串
     * @param len
     *            截取的长度
     * @return String 返回字符串
     */
    public static String getFixLengthString(String instr, int len) {
        if (instr == null)
            return "";
        if (instr.length() > len) {
            return instr.substring(0, len);
        }
        else {
            return instr;
        }
    }

    /**
     * 获取非空的字符
     * 
     * @param instr
     *            对象
     * @return String 返回字符串
     */
    public static String getNotNullString(Object instr) {
        return instr == null ? "" : instr.toString();
    }

    /**
     * 字符串str如果存在指定字符prefix则去掉str中的第一个字符
     * 
     * @param str
     * @param prefix
     * @return String
     */
    public static String trimPrefix(String str, String prefix) {
        if (str.startsWith(prefix))
            return str.substring(1);

        return str;
    }

    private static String splitString(int num, int len, String sMsg) {
        StringBuffer sbf = new StringBuffer();
        StringBuffer sbf1 = new StringBuffer();
        sbf.append("");
        String temp = "";
        int startNum = 0;
        String s = sMsg.replaceAll("[^\\x00-\\xff]", "**");// 判断长度
        int strLen = s.length();
        // System.out.println(num + "num");
        for (int i = 0; i < num; i++) {
            // System.out.println(i);
            int lenc = 0;
            if (i != 0) {
                lenc = sbf1.toString().replaceAll("[^\\x00-\\xff]", "**").length();
            }
            if (strLen > startNum + len) {
                temp = substring2(sMsg, lenc, len, false);
                System.out.println(temp + "##" + temp.replaceAll("[^\\x00-\\xff]", "**").length());
                startNum = startNum + len;
            }
            else {
                temp = substring2(sMsg, lenc, strLen - lenc, true);
                System.out.println(temp + "##" + temp.replaceAll("[^\\x00-\\xff]", "**").length());
            }
            sbf1.append(temp);
            // sbf.append("(" + (i + 1) + "/" + (num) +
            // ")").append(temp).append("ξ");
            sbf.append(temp).append("ξ");

            // System.out.println(sbf + "##" +
            // sbf1.toString().replaceAll("[^\\x00-\\xff]", "**").length());
            // System.out.println(sbf);

        }

        return sbf.toString();
    }

    /*
     * 截取一段字符的长度(汉、日、韩文字符长度为2),不区分中英文,如果数字不正好，则少取一个字符位
     * 
     * @param str 原始字符串
     * 
     * @param srcPos 开始位置
     * 
     * @param specialCharsLength 截取长度(汉、日、韩文字符长度为2)
     * 
     * @return
     */
    private static String substring2(String str, int srcPos, int specialCharsLength, boolean islast) {
        if (str == null || "".equals(str) || specialCharsLength < 1) {
            return "";
        }
        if (srcPos < 0) {
            srcPos = 0;
        }
        if (specialCharsLength <= 0) {
            return "";
        }
        // 获得字符串的长度
        char[] chars = str.toCharArray();
        if (srcPos > chars.length) {
            // return "";
        }
        int charsLength = getCharsLength(chars, specialCharsLength);
        System.out.println("charsLength" + charsLength);

        int aa = getCharsLength(chars, srcPos);

        if (islast) {
            charsLength = chars.length - aa;
        }
        return new String(chars, aa, charsLength);
    }

    public static String[] getMessages(String str, int len) {
        int msgLen = str.trim().replaceAll(" ", "").replaceAll("[^\\x00-\\xff]", "**").length();
        String finalsMsg = "";
        if (msgLen >= len) {
            String s = str.replaceAll("[^\\x00-\\xff]", "**");// 判断长度
            // System.out.println("字符长度" + s.length());
            double a = 0.0;
            a = Double.parseDouble(s.length() + "") / Double.parseDouble(len + "");// 取带有小数点的

            int a1 = s.length() / len;// 取整数
            int xm = Integer.valueOf((Math.round((a - a1) * 100) + ""));
            int len1 = a1;
            if (xm > 0) {
                len1 = len1 + 1;
            }
            int num = len1;// (int) Math.round((double) msgLen / (double) len);
            finalsMsg = splitString(num, len, str);
        }
        else {
            finalsMsg = str;
        }
        // System.out.println(finalsMsg);
        String[] tempArray = finalsMsg.split("ξ");

        return tempArray;
    }

    /**
     * 判断某个字符是不是英文字母
     * 
     * @param c
     *            要判断的字符
     * @return boolean
     */
    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * 
     * 
     * 
     * @param s
     *            需要得到长度的字符串
     * 
     * @return i得到的字符串长度
     */
    public static int length(String s) {
        if (s == null) {
            return 0;
        }
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }

    /**
     * 
     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
     * 
     * @param origin
     *            原始字符串
     * 
     * @param start
     *            起始位置
     * 
     * @param len
     *            截取长度(一个汉字长度按2算的) ,长度超过取到原始字符串末
     * 
     * @return 返回的字符串
     */
    public static String substring(String origin, int start, int len) {
        if (origin == null || origin.equals("") || len < 1) {
            return "";
        }
        byte[] strByte = new byte[len];

        if (start + len > length(origin)) {
            len = length(origin) - start;
        }
        try {
            System.arraycopy(origin.getBytes("UTF-8"), start, strByte, 0, len);
            int count = 0;
            for (int i = 0; i < len; i++) {
                int value = (int) strByte[i];
                if (value < 0) {
                    count++;
                }
            }
            if (count % 2 != 0) {
                len = (len == 1) ? ++len : --len;
            }
            return new String(strByte, 0, len, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 截取一段字符的长度(汉、日、韩文字符长度为2),不区分中英文,如果数字不正好，则少取一个字符位
     * 
     * @param str
     *            原始字符串
     * 
     * @param srcPos
     *            开始位置
     * 
     * @param specialCharsLength
     *            截取长度(汉、日、韩文字符长度为2)
     * 
     * @return String
     */
    public static String substring2(String str, int srcPos, int specialCharsLength) {
        if (str == null || "".equals(str) || specialCharsLength < 1) {
            return "";
        }
        if (srcPos < 0) {
            srcPos = 0;
        }
        if (specialCharsLength <= 0) {
            return "";
        }
        // 获得字符串的长度
        char[] chars = str.toCharArray();
        if (srcPos > chars.length) {
            // return "";
        }
        int charsLength = getCharsLength(chars, specialCharsLength);

        int aa = getCharsLength(chars, srcPos);

        if (charsLength + aa > chars.length) {
            charsLength = chars.length - aa;
        }
        return new String(chars, aa, charsLength);
    }

    /**
     * 获取一段字符的长度，输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1
     * 
     * @param chars
     *            一段字符
     * @param specialCharsLength
     *            输入长度，汉、日、韩文字符长度为2
     * @return int 输出长度，所有字符均长度为1
     */
    private static int getCharsLength(char[] chars, int specialCharsLength) {
        int count = 0;
        int normalCharsLength = 0;
        for (int i = 0; i < chars.length; i++) {
            int specialCharLength = getSpecialCharLength(chars[i]);
            if (count <= specialCharsLength - specialCharLength) {
                count += specialCharLength;
                normalCharsLength++;
            }
            else {
                break;
            }
        }
        return normalCharsLength;
    }

    /**
     * 获取字符长度：汉、日、韩文字符长度为2，ASCII码等字符长度为1
     * 
     * @param c
     *            字符
     * @return int 字符长度
     */
    private static int getSpecialCharLength(char c) {
        if (isLetter(c)) {
            return 1;
        }
        else {
            return 2;
        }
    }

    /**
     * JAVA从指定位置开始截取指定长度的字符串
     * 
     * @param input
     *            输入字符串
     * @param index
     *            截取位置，左侧第一个字符索引值是1
     * @param count
     *            截取长度
     * @return 截取字符串
     */
    public static String middle(String input, int index, int count) {

        count = (count > input.length() - index + 1) ? input.length() - index + 1 : count;
        return input.substring(index - 1, index + count - 1);
    }

    /**
     * 把个位数转成非带0的形式
     * 
     * @param inDigits
     * @return String
     */
    public static String changeSingleDigits2Double(String inDigits) {
        int i = 0;
        try {
            i = Integer.parseInt(inDigits);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (i >= 10)
            return "" + i;
        else
            return "0" + i;
    }

    /**
     * 判断对象是否是空的(判断依据:字符串为null,或者截取空格后长度为0)
     * 
     * @param string
     *            要判断的对象
     * @return 检查结果 true:空;false:不空
     */
    public static boolean isBlank(final Object string) {
        boolean blank = false;
        if (string == null) {
            blank = true;
        }
        else {
            if (string instanceof String) {
                blank = string.toString().trim().length() == 0 || string.toString().equalsIgnoreCase("null");
            }
        }
        return blank;
    }

    /**
     * 判断字符串是否非空(判断依据:字符串不为null,并且截取空格后长度不为0)
     * 
     * @param string
     *            要判断的对象
     * @return 检查结果 true:不空;false:空
     */
    public static boolean isNotBlank(final Object string) {
        return !isBlank(string);
    }

    /**
     * 从文件路径中截取出文件名字
     * 
     * @param fileFullPath
     *            文件全路径
     * @return String 文件名
     */
    public static String getFileNameFromPath(String fileFullPath) {
        int last1 = fileFullPath.lastIndexOf("\\");
        int last2 = fileFullPath.lastIndexOf("/");
        if (last1 < last2) {
            last1 = last2;
        }

        if (last1 != -1) {
            fileFullPath = fileFullPath.substring(last1 + 1, fileFullPath.length());
        }
        return fileFullPath;
    }

    /**
     * 替换sql里面的特殊字符
     * 
     * @param sql
     *            要替换的sql文
     * @return String 替换结果字符串
     */
    public static String replaceSql(String sql) {
        if (sql == null) {
            return "";
        }
        // return sql.replace("[", "[']").replace("'", "''").trim();
        return sql.replace("'", "''").trim();
    }

    /**
     * 格式化sql参数值(替换查询条件中手动输入的%)
     * 
     * @param instr
     *            参数值
     * @return String 返回字符串
     */
    public static String formatSqlStringParameter(String instr) {
        String rntstr = "";
        if (instr == null)
            return rntstr;
        rntstr = instr.trim().replace("%", "");
        return rntstr;
    }

    /**
     * sql中特殊字符处理
     * 
     * @param param
     *            sql文
     * @return String
     */
    public static String getSearchPattern(String param) {
        return param == null ? "%" : '%' + param.replace('*', '%') + '%';
    }

    /**
     * 通过传入文件内容，关键字获取含有此关键字的文件名和文件guid
     * 
     * @param fileContent
     *            字符串内容
     * @param keyword
     *            关键字
     * @return List<String[]>
     */
    public static List<String[]> getMatchedFileInfo(String fileContent, String keyword) {
        if (fileContent == null || keyword == null)
            return null;
        keyword = keyword.trim();

        String regex_string = "<file\\s*(filename=[^" + ATTACH_FILE_SEPARATOR + "]+)\\s*(fileguid=[^"
                + ATTACH_FILE_SEPARATOR + "]+)\\s*>[^" + ATTACH_FILE_SEPARATOR + "]*" + keyword + "[^"
                + ATTACH_FILE_SEPARATOR + "]*" + ATTACH_FILE_SEPARATOR + "";
        Matcher match = Pattern.compile(regex_string).matcher(fileContent);
        List<String[]> list = new ArrayList<String[]>();
        while (match.find()) {
            String[] str = new String[2];
            String s = match.group(1);// 添加文件名
            str[0] = s.substring(s.indexOf("=") + 1).replace("\"", "");
            s = match.group(2);// 添加文件guid
            str[1] = s.substring(s.indexOf("=") + 1).replace("\"", "");
            list.add(str);
        }
        return list;
    }

    /**
     * 将某个编码过的字符串转为中文
     * 
     * @param strvalue
     *            字符串
     * @return String 转换结果
     */
    public static String toChinese(String strvalue) {
        try {
            if (strvalue == null)
                return null;
            else {
                strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
                return strvalue;
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * 格式链接地址,将配置的地址格式化成框架相对路径
     * 
     * @param strUrl
     *            配置地址
     * @return String 格式化地址,
     */
    public static String formatLinkUrl(String strUrl) {
        if (strUrl == null) {
            return "";
        }
        strUrl = strUrl.replace("\\", "/");
        if (strUrl.startsWith("/") || strUrl.startsWith("http:") || strUrl.startsWith("\\\\")
                || strUrl.startsWith("://"))
            return strUrl;
        else
            return "../" + strUrl;
    }

    /**
     * 获取排版文字(暂时的排版规则是size>6)
     * 
     * @param in
     *            输入字符串
     * @param size
     *            总记录数
     * @return String 排版后的字符串
     */
    public static String getTypeSettingString(String in, int size) {
        String out = in;
        // 如果超过6个,那么竖起显示
        if (size > 6) {
            char[] names = in.toCharArray();
            StringBuilder sb = new StringBuilder();
            // sb.append("<table style=\"border:0;\"><tr><td style=\"border:0;\"
            // align=\"center\">");
            for (char item : names) {
                sb.append(item);
                sb.append("</br>");
            }
            out = sb.toString();
            sb = null;
        }
        return out;
    }

    public static String getParameterByNameFromUrl(String url, String name) {
        String parames = url.substring(url.indexOf(name + '=') + 1 + name.length(), url.length());
        if (parames.indexOf('&') != -1) {
            String[] paramvalue = parames.split("&");
            return paramvalue[0];
        }
        return parames;
    }

    /**
     * 解析字符串中重复出现的标签内容
     * 
     * @return 字符串集合
     */
    public static List<String> parseStrByTag(String startTag, String endTag, String content, boolean withTag) {
        List<String> result = new ArrayList<String>();
        Pattern p1 = Pattern.compile(startTag);
        Matcher m1 = p1.matcher(content);
        while (m1.find()) {
            int start = m1.end();
            if (withTag) {
                start -= startTag.length();
            }
            int end = content.indexOf(endTag, start);
            if (withTag) {
                end += endTag.length();
            }
            result.add(content.substring(start, end));
        }
        return result;
    }

    public static String firstSmallStr(String str, int length) {
        String result = str.substring(length, str.length());
        result = result.substring(0, 1).toLowerCase() + result.substring(1);
        return result;
    }

    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toLowerCase(firstChar) + tail;
        return str;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(String str) {
        Character firstChar = str.charAt(0);
        String tail = str.substring(1);
        str = Character.toUpperCase(firstChar) + tail;
        return str;
    }

    /**
     * 默认以 , 号来拼接字符串数组
     * 
     * @param ss
     * @return String
     */
    public static String join(String[] ss) {
        return join(ss, ",");
    }

    /**
     * 对于List集合，会先调用 toString 方法，然后进行拼接
     * 
     * @param list
     *            集合
     * @return String
     */
    public static <T> String join(List<T> list) {
        return join(list, ",");
    }

    /**
     * 对字符串数组进行拼接
     * 
     * @param ss
     * @param subStr
     * @return String
     */
    public static String join(String[] ss, String subStr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = ss.length; i < size; i++) {
            sb.append(ss[i]);
            if (i != size - 1) {
                sb.append(subStr);
            }
        }
        return sb.toString();
    }

    /**
     * 对于List集合，会先调用 toString 方法，然后进行拼接
     * 
     * @param list
     *            集合
     * @param subStr
     *            子字符串
     * @return String
     */
    public static <T> String join(List<T> list, String subStr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = list.size(); i < size; i++) {
            sb.append(list.get(i).toString());
            if (isNotBlank(subStr) && i != size - 1) {
                sb.append(subStr);
            }
        }
        return sb.toString();
    }

    /**
     * 将字符串数组拼接成 SQL语句里面 in 需要传入的形式，如: 'aaa','bbb'
     * 
     * @param ss
     *            字符串数组
     * @return String
     */
    public static String joinSql(String[] ss) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = ss.length; i < size; i++) {
            sb.append("'").append(ss[i]).append("'");
            if (i != size - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 对于List集合，会先调用 toString 方法，然后进行拼接
     * 
     * @param list
     *            集合
     * @return String
     */
    public static <T> String joinSql(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = list.size(); i < size; i++) {
            sb.append("'").append(list.get(i).toString()).append("'");
            if (i != size - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 首字母大写
     * 
     * @param name
     *            字符串
     * @return String
     */
    public static String firstUp(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * 计算一个字符串中某段字符出现多少次
     * 
     * @param strSource
     *            源字符串
     * @param key
     *            关键字
     * @return int
     */
    public static int countSameStr(String strSource, String key) {
        int count = 0;
        Pattern p1 = Pattern.compile(key);
        Matcher m1 = p1.matcher(strSource);
        while (m1.find()) {
            count++;
        }
        return count;
    }

    /**
     * 根据原字符串去除分词后的字符串数组内不需要出现的词
     * 
     * @param old
     *            原字符串
     * @param args
     *            分词后的字符串数组
     * @return String[]
     */
    public static String[] removeNoNeed(String old, String[] args) {
        List<String> fit = new ArrayList<String>();
        int length = args.length;
        for (int i = 0; i < length; i++) {
            // 如果与原始的打头不一致
            if (!old.startsWith(args[i])) {
                // 2种可能：
                // 1.多了,跳过此词，去匹配后面的，看有没有满足的
                // 2.少了
                boolean less = true;
                for (int j = i + 1; j < length; j++) {
                    if (old.startsWith(args[j])) {
                        fit.add(args[j]);
                        old = old.replace(args[j], "");
                        less = false;
                        i = j;
                        break;
                    }
                }
                if (less) {
                    for (int j = 0; j < old.length(); j++) {
                        old = old.substring(1, old.length());
                        if (old.startsWith(args[i])) {
                            fit.add(args[i]);
                            old = old.replace(args[i], "");
                            break;
                        }
                    }
                }
            }
            else {
                fit.add(args[i]);
                old = old.replace(args[i], "");
            }

        }
        return fit.toArray(new String[] {});
    }

    /**
     * 从某字符串中截取出指定内容,截取的同时源内容会截掉
     * 
     * @param start
     *            开始截取的字符串标记
     * @param to
     *            结束截取的字符串标记
     * @param tag
     *            与结束相对应的标记
     * @param fileContent
     *            源字符串
     * @param result
     *            用于存储截取结果的list，需要外部new后传入
     * @param uidefine
     *            截去元素之后 留下的占位符，可以为空
     * @param recure
     *            是否递归
     * @return List<String> 截取出的字符串
     */
    public static String subStrSql(String start, String to, String tag, String fileContent, List<String> result,
            String uidefine, boolean recure) {
        if (fileContent != null && fileContent.trim().length() > 0) {
            int index = fileContent.indexOf(start);
            // 先找到开始
            if (index != -1) {
                int extra = 0;
                if (tag.equalsIgnoreCase(start)) {
                    extra = 1;
                }
                int endIndex = getEndIndex(to, tag, fileContent, index + start.length(), extra);
                if (endIndex != -1) {
                    result.add(fileContent.substring(index, endIndex + to.length()));
                    // 从源字符串中截取掉处理过的
                    String begin = fileContent.substring(0, index);
                    String end = fileContent.substring(endIndex + to.length());
                    if (StringUtil.isNotBlank(uidefine)) {
                        begin = begin + uidefine;
                    }
                    fileContent = begin + end;
                    if (recure) {
                        fileContent = subStrSql(start, to, tag, fileContent, result, uidefine, recure);
                    }
                }
            }
        }
        return fileContent;
    }

    public static int getEndIndex(String to, String tag, String fileContent, int start, int totalBeforeCount) {
        // 找到第一个结束标记位
        int endIndex = fileContent.indexOf(to, start);
        if (endIndex != -1) {
            // 查询在它之前的开始标记位数量
            int beforeCount = getIndex(fileContent, tag, start, endIndex).size();
            // 减去找到的结束标记
            beforeCount -= 1;
            // 累积前面剩余的
            totalBeforeCount += beforeCount;
            // 如果还有开始，那么证明找到的这个结束不是真正的结束，继续往后
            if (totalBeforeCount > 0) {
                endIndex = getEndIndex(to, tag, fileContent, endIndex + to.length(), totalBeforeCount);
            }
        }
        return endIndex;
    }

    public static List<Integer> getIndex(String fileContent, String tag, int start, int end) {
        List<Integer> result = new ArrayList<Integer>();
        int index = fileContent.indexOf(tag, start);
        // 如果找到了，并且在结束之前
        if (index != -1 && index < end) {
            result.add(index);
            // 进入递归
            result.addAll(getIndex(fileContent, tag, index + tag.length(), end));
        }

        return result;
    }

    /**
     * 从某字符串中截取出指定内容,截取的同时源内容会截掉
     * 
     * @param startBegin
     *            开始字符串的起始标记
     * @param startMiddle
     *            开始字符串的中间标记
     * @param startEnd
     *            开始字符串的结束标记
     * @param from
     *            从开始的多少偏移量开始截取
     * @param to
     *            结束截取的字符串标记,可以有多种情况，用@分割即可
     * @param keep
     *            是否保留首尾标签
     * @param fileContent
     *            源字符串
     * @param result
     *            用于存储截取结果的list，需要外部new后传入
     * @param uidefine
     *            截去元素之后 留下的占位符，可以为空
     * @return List<String> 截取出的字符串
     */
    public static String subStrSql(String startBegin, String startMiddle, String startEnd, int from, String to,
            boolean keep, String fileContent, List<String> result, String uidefine) {
        if (fileContent != null && fileContent.trim().length() > 0 && fileContent.indexOf(startBegin) != -1) {
            // 开始index
            int startIndex = -1;
            // 开始标签的长度
            int startWidth = -1;
            // 结束index
            int endIndex = -1;
            // 结束标签长度
            int endWidth = -1;

            int index = -1;
            // 如果有中间位置进行定位，那么开始的位置就需要很小心的处理了，不只是简单的用startBegin去定位
            if (StringUtil.isNotBlank(startMiddle)) {
                index = fileContent.indexOf(startMiddle);
                if (index != -1) {
                    // 查询出所有在index之前出现的位置
                    List<Integer> indexList = getIndex(fileContent, startBegin, 0, index);
                    // 拿出最近的一个,即这个才是真正的开始位置
                    startIndex = indexList.get(indexList.size() - 1);
                }
            }
            // 其他的话简单点
            else {
                startIndex = fileContent.indexOf(startBegin);
            }

            if (StringUtil.isBlank(startEnd)) {
                startWidth = startBegin.length();
            }
            else {
                int startEndIndex = fileContent.indexOf(startEnd, startIndex);
                startWidth = startEndIndex - startIndex + startEnd.length();
            }

            // 先找到开始
            if (startIndex != -1) {
                // 结束标签可能有多种情况，首先需要找到正确的那个
                String[] toParams = to.split("@");

                // 找到正确的结束位置
                for (String item : toParams) {
                    int tempEndIndex = fileContent.indexOf(item, startIndex);
                    // 如果找到
                    if (tempEndIndex != -1) {
                        // 如果是第一次找到，或者找到的位置靠前，那么重新指向
                        if (endIndex == -1 || tempEndIndex < endIndex) {
                            endIndex = tempEndIndex;
                            endWidth = item.length();
                        }
                    }
                }
                if (endIndex == -1) {
                    endIndex = fileContent.length();
                }

                // 把需要的内容截取出来// 判断是否需要保留标记
                if (!keep) {
                    result.add(fileContent.substring(startIndex + startWidth, endIndex).trim());
                }
                else {
                    result.add(fileContent.substring(startIndex, endIndex + endWidth).trim());
                }
                // 从源字符串中截取掉处理过的
                String begin = fileContent.substring(0, startIndex);
                String end = fileContent.substring(endIndex + endWidth);
                if (StringUtil.isNotBlank(uidefine)) {
                    begin = begin + uidefine;
                }
                fileContent = begin + end;
                // 进入递归
                fileContent = subStrSql(startBegin, startMiddle, startEnd, from, to, keep, fileContent, result,
                        uidefine);

            }
        }
        return fileContent;
    }

    /**
     * 从某字符串中截取掉指定内容
     * 
     * @param start
     *            开始截取的字符串标记
     * @param from
     *            从开始的多少偏移量开始截取
     * @param to
     *            结束截取的字符串标记
     * @param fileContent
     *            源字符串
     * @param recure
     *            是否递归
     * @return String 截取后的字符串
     */
    public static String subStrSql(String start, int from, String to, String fileContent, boolean recure) {
        int index = fileContent.indexOf(start);
        if (index != -1) {
            int endIndex = fileContent.indexOf(to, index + start.length());
            if (endIndex == -1) {
                fileContent = "";
            }
            else {
                fileContent = fileContent.substring(0, index + from) + fileContent.substring(endIndex + to.length());
                if (recure) {
                    fileContent = subStrSql(start, from, to, fileContent, recure);
                }
            }
        }
        return fileContent;
    }

    /**
     * 插入某个字符串到指定字符串的指定位置
     * 
     * @param str
     *            源字符串
     * @param index
     *            用来定位位置的标识字符
     * @param from
     *            从多少位置开始定位上面的字符串
     * 
     * @param indexTo
     *            用来计算偏移量,默认0为index的起始位置
     * @param toStr
     *            要插入的字符串
     * @return String
     */
    public static String insertStr(String str, String index, int from, int indexTo, String toStr) {
        int start = str.indexOf(index, from);
        if (start != -1) {
            start += indexTo;
            str = str.substring(0, start) + toStr + str.substring(start);
        }
        return str;
    }

    private static final String[] specialStr = new String[] {"$", "&", "[", "'", "(" };

    private static String getSpecial(String str) {
        String result = null;
        for (String item : specialStr) {
            if (str.indexOf(item) != -1) {
                result = item;
            }
        }
        return result;
    }

    public static String filterSpecialStr(String str) {
        String sReturn = "";
        if (isNotBlank(str)) {
            String special = getSpecial(str);
            if (StringUtil.isNotBlank(special)) {
                while (str.length() > 0) {
                    if (str.indexOf(special, 0) > -1) {
                        sReturn += str.subSequence(0, str.indexOf(special, 0));
                        sReturn += "\\" + special;
                        str = str.substring(str.indexOf(special, 0) + 1, str.length());
                    }
                    else {
                        sReturn += str;
                        str = "";
                    }
                }
            }
            else {
                sReturn = str;
            }
        }
        return sReturn;
    }

    /**
     * 获取XML标签之间的属性 如: bs = "<name>epoint</name>" att = "name" return "epoint"
     * 
     * @param bs
     * @param att
     * @return String
     */
    public static String getXMLAtt(String bs, String att) {
        String result = "";
        try {
            String head = "<" + att + ">";
            String tail = "</" + att + ">";
            result = bs.substring(bs.indexOf(head) + head.length(), bs.indexOf(tail));
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    /**
     * 获取标签如 xml = <root><outs><out>beijing</out></outs></root> bs = xml ,att =
     * "outs" return <outs><out>beijing</out></outs>
     * 
     * @param bs
     * @param att
     * @return String
     */
    public static String getXMLAttOut(String bs, String att) {
        try {
            String bs1 = getXMLAtt(bs, att);
            return "<" + att + ">" + bs1 + "</" + att + ">";
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 计算字符串中中文的个数
     * 
     * @param str
     * @return int 数量
     */
    public static int countChinese(String str) {
        int result = 0;
        if (isNotBlank(str)) {
            // 中文
            String E1 = "[\u4e00-\u9fa5]";
            String temp;
            for (int i = 0; i < str.length(); i++) {
                temp = String.valueOf(str.charAt(i));
                if (temp.matches(E1)) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * 计算字符串中英文的个数
     * 
     * @param str
     * @return int 数量
     */
    public static int countEnglish(String str) {
        int result = 0;
        if (isNotBlank(str)) {
            // 英文
            String E1 = "[a-zA-Z]";
            String temp;
            for (int i = 0; i < str.length(); i++) {
                temp = String.valueOf(str.charAt(i));
                if (temp.matches(E1)) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * 计算字符串中数字的个数
     * 
     * @param str
     * @return int 数量
     */
    public static int countNumber(String str) {
        int result = 0;
        if (isNotBlank(str)) {
            // 数字
            String E1 = "[0-9]";
            String temp;
            for (int i = 0; i < str.length(); i++) {
                temp = String.valueOf(str.charAt(i));
                if (temp.matches(E1)) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * 替换掉影响页面展示的特殊字符
     * 
     * @param content
     *            要替换的内容
     * @return 替换后的结果
     */
    public static String replaceSpecialChar(String content) {
        if (content == null)
            return "";
        return content.replace("\r\n", "").replace("\r", "").replace("\n", "").replace("'", "&#39")
                .replace("\\", "&#92").replace("\"", "&quot;");
    }

    //
    // public static void main(String[] args) {
    // String[] ss = {"aaa", "bbb" };
    // List list = new ArrayList();
    // list.add("aaa");
    // list.add(3232);
    // System.out.println(StringUtil.joinSql(list));
    // }

    /**
     * 判断字符串是否为Guid
     * 
     * @param guid
     * @return boolean
     */
    public static boolean isGuidString(String guid) {
        boolean isValid = false;
        if (guid != null) {
            Pattern pattern = Pattern.compile(
                    "^(\\{){0,1}[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}(\\}){0,1}$");
            Matcher matcher = pattern.matcher(guid);
            if (matcher.matches()) {
                isValid = true;
            }
        }
        return isValid;
    }

//    /**
//     * 中文转拼音首字母的方法
//     * 
//     * @param content
//     *            中文内容
//     * @return 拼音首字母
//     */
//    public static String chineseToPinyin(String content) {
//        String key = ChineseToPinyin.getPinYinHeadChar(content);
//        key = key.replaceAll("-", "");
//        key = key.replaceAll("：", "");
//        key = key.replaceAll(":", "");
//        key = key.replaceAll("\\/", "");
//        key = key.replaceAll("!", "");
//        // 如果有空格，要处理掉
//        if (key.indexOf(" ") != -1) {
//            String[] keys = key.split(" ");
//            key = "";
//            for (String item : keys) {
//                key += item;
//            }
//        }
//        key = key.trim();
//        return key;
//    }

    /**
     * 判断文件是否是一个图片,以后缀判断
     * 
     * @param fileName
     * @return boolean
     */
    public static boolean isImg(String fileName) {
        boolean img = false;
        fileName = fileName.toLowerCase();
        if (fileName.endsWith(".jpg") || fileName.endsWith(".bmp") || fileName.endsWith(".png")
                || fileName.endsWith(".gif")) {
            img = true;
        }
        return img;
    }

    /**
     * 将字符串换成utf-8字符集
     * @param s
     * @return
     */
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            }
            else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                }
                catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
}
