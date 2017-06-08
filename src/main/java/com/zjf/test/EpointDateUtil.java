package com.zjf.test;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类 依赖jar:commons-lang.jar(DateUtil) 该工具类中所有的输入输出Date都是java.util.Date类型
 * 
 * @author komojoemary
 * @version [版本号, 2011-12-12]
 */
public class EpointDateUtil
{

    public static final long DAY_IN_MILLISECOND = 24 * 60 * 60 * 1000;

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_NOSPLIT_FORMAT = "yyyyMMdd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // public static final String DATE_US_FORMAT = "EEE MMM dd yyyy HH:mm:ss";

    /**
     * 把数字转换为日期 如20101212 变为Date(默认格式为yyyyMMdd)
     * 
     * @param dateint
     *            日期数字
     * @return Date(java.util.Date)
     */
    public static Date intToDate(int dateint) {
        SimpleDateFormat dd = new SimpleDateFormat(DATE_NOSPLIT_FORMAT);
        Date date = null;
        try {
            date = dd.parse(dateint + "");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    /**
     * 把字符串转换为日期 (默认格式yyyy-MM-dd HH:mm:ss)
     * 
     * @param datestr
     *            日期字符串
     * @return Date 日期对象(java.util.Date)
     */
    public static Date convertString2Date(String datestr) {
        SimpleDateFormat dateformat = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = dateformat.parse(datestr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 把字符串转换为日期 (将会自动判断要转成的日期格式)
     * 
     * @param datestr
     *            日期字符串
     * @return Date 日期对象(java.util.Date)
     */
    public static Date convertString2DateAuto(String datestr) {
        Date date = null;
        if (StringUtil.isNotBlank(datestr)) {
            datestr = datestr.trim();
            datestr = datestr.toString().replace("T", " ");
            String format = DATE_NOSPLIT_FORMAT;
            boolean haveTime = false;
            // 判断是否有时分秒
            if (datestr.indexOf(":") != -1) {
                haveTime = true;
                // 如果没有秒、自动拼接上秒
                String[] a = datestr.split(":");
                if (a.length == 2) {
                    datestr += ":00";
                }
            }
            if (datestr.indexOf("-") != -1) {
                format = DATE_FORMAT;
            }
            else if (datestr.indexOf("/") != -1) {
                format = "yyyy/MM/dd";
            }
            else {
                // 对年月日的写法做转换支持 edit by ko 2015-11-18
                if (datestr.indexOf("年") != -1 || datestr.indexOf("月") != -1 || datestr.indexOf("日") != -1) {
                    datestr = datestr.replaceAll("年", "-");
                    datestr = datestr.replaceAll("月", "-");
                    datestr = datestr.replaceAll("日", "");
                    format = DATE_FORMAT;
                }
            }
            if (haveTime) {
                format += "HH:mm:ss";
            }
            SimpleDateFormat dateformat = new SimpleDateFormat(format);
            try {
                date = dateformat.parse(datestr);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 把字符串转换为日期 (指定格式)
     * 
     * @param str
     *            时间字符串
     * @param formatStr
     *            格式字符串(诸如yyyy-MM-dd HH:mm:ss))
     * @return Date 日期对象(java.util.Date)
     */
    public static Date convertString2Date(String str, String formatStr) {
        DateFormat format = new SimpleDateFormat(formatStr);
        Date date = null;

        try {
            date = format.parse(str);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 日期转换成指定格式字符串(默认格式yyyy-MM-dd)
     * 
     * @param date
     *            日期对象
     * @return String 日期字符串
     */
    public static String convertDate2String(Date date) {
        if (date == null)
            return "";
        SimpleDateFormat formatter = null;
        try {
            formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.format(date);
        }
        catch (Exception e) {
            formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.format(date);
        }
    }

    /**
     * 日期转换成指定格式字符串
     * 
     * @param date
     *            日期对象
     * @param format
     *            需要转换的日期格式
     * @return String 日期字符串
     */
    public static String convertDate2String(Date date, String format) {
        if (date == null)
            return "";
        SimpleDateFormat formatter = null;
        try {
            formatter = new SimpleDateFormat(format);
            return formatter.format(date);
        }
        catch (Exception e) {
            formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.format(date);
        }
    }

    /**
     * 获取某个日期的开始时间(即那天的 00:00:00对应的时间)
     * 
     * @param datestr
     *            日期字符串
     * @return Date 日期对象
     */
    public static Date getBeginOfDateStr(String datestr) {
        Date result = null;
        if (StringUtil.isNotBlank(datestr)) {
            datestr = datestr.trim();
            if (datestr.indexOf(":") != -1) {
                int endIndex = datestr.lastIndexOf(" ");
                datestr = datestr.substring(0, endIndex);
            }
            result = getCombinationDateByStr(datestr, " 00:00:00");
        }
        return result;
    }

    /**
     * 获取某个日期的开始时间(即那天的 00:00:00对应的时间)
     * 
     * @param date
     *            日期对象
     * @return Date 日期对象
     */
    public static Date getBeginOfDate(Date date) {
        return getCombinationDate(date, " 00:00:00");
    }

    /**
     * 获取某个日期的结束时间(即那天的 23:59:59对应的时间)
     * 
     * @param date
     *            日期对象
     * @return Date 日期对象
     */
    public static Date getEndOfDate(Date date) {
        return getCombinationDate(date, " 23:59:59");
    }

    /**
     * 获取某个日期的结束时间(即那天的 23:59:59对应的时间)
     * 
     * @param datestr
     *            日期字符串
     * @return Date 日期对象
     */
    public static Date getEndOfDateStr(String datestr) {
        Date result = null;
        if (StringUtil.isNotBlank(datestr)) {
            datestr = datestr.trim();
            if (datestr.indexOf(":") != -1) {
                int endIndex = datestr.lastIndexOf(" ");
                datestr = datestr.substring(0, endIndex);
            }
            result = getCombinationDateByStr(datestr, " 23:59:59");
        }
        return result;
    }

    /**
     * 获取组合的日期(用于自定义时分秒)
     * 
     * @param date
     *            日期对象
     * @param condition
     *            拼接的条件(诸如23:59:59)
     * @return Date 组合后的日期
     */
    public static Date getCombinationDate(Date date, String condition) {
        if (date == null) {
            date = new Date();
        }
        return getCombinationDateByStr(convertDate2String(date), condition);
    }

    /**
     * 获取组合的日期(用于自定义时分秒)
     * 
     * @param date
     *            年月日的日期字符串
     * @param condition
     *            拼接的条件(诸如23:59:59)
     * @return Date 组合后的日期
     */
    private static Date getCombinationDateByStr(String date, String condition) {
        Date outDate = null;
        try {
            String strDate = date + condition;
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            outDate = dateFormat.parse(strDate);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return outDate;
    }

    /**
     * 获取某个日期的年份
     * 
     * @param date
     *            日期对象
     * @return 年份值
     */
    public static int getYearOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取某个日期的月份
     * 
     * @param date
     *            日期对象
     * @return 月份值
     */
    public static int getMonthOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取某个日期的天
     * 
     * @param date
     *            日期对象
     * @return 天值
     */
    public static int getDayOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取某个日期的小时(12小时)
     * 
     * @param date
     *            日期对象
     * @return 小时数
     */
    public static int getHourOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR);
    }

    /**
     * 获取某个日期的小时(24小时)
     * 
     * @param date
     *            日期对象
     * @return 小时数
     */
    public static int getHourOfDate24(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取某个日期的分钟
     * 
     * @param date
     *            日期对象
     * @return 分钟数
     */
    public static int getMinuteOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取某个日期的秒
     * 
     * @param date
     *            日期对象
     * @return 秒数
     */
    public static int getSecondOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取指定毫秒数之前的时间
     * 
     * @param millis
     *            指定毫秒数
     * @return Date 日期对象
     */
    public static Date getBeforeTimeByMillis(long millis) {
        Date rntDate = new Date();
        rntDate.setTime(Calendar.getInstance().getTimeInMillis() - millis);
        return rntDate;
    }

    public static Date convertLongToDate(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c.getTime();
    }

    /**
     * 判断是否是闰年
     * 
     * @param ddate
     *            日期字符串
     * @return boolean 是否闰年
     */
    public static boolean isLeapYear(String ddate) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = convertString2DateAuto(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0)
            return true;
        else if ((year % 4) == 0) {
            if ((year % 100) == 0)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    /**
     * 获取一个月的最后一天
     * 
     * @param dat
     *            日期字符串
     * @return String (yyyy-MM-dd HH:mm:ss)
     */
    public static String getEndDateOfMonth(String dat) {
        Date date = convertString2DateAuto(dat);
        int mon = getMonthOfDate(date) + 1;
        int day = 0;
        if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
            day = 31;
        }
        else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
            day = 0;
        }
        else {
            if (isLeapYear(dat)) {
                day = 29;
            }
            else {
                day = 28;
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, day);
        return convertDate2String(calendar.getTime(), DATE_TIME_FORMAT);
    }

    /**
     * 获取添加几天后的日期
     * 
     * @param date
     *            时间
     * @param day
     *            增加的天数
     * @return Date 返回日期对象
     */
    public static Date addDay(Date date, int day) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }

    /**
     * 获取添加几秒后的时间
     * 
     * @param date
     *            时间
     * @param seconds
     *            增加的秒数
     * @return Date 返回日期对象
     */
    public static Date addSeconds(Date date, int seconds) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 获取添加几分钟后的时间
     * 
     * @param date
     *            时间
     * @param minute
     *            增加的分钟
     * @return Date 返回日期对象
     */
    public static Date addMinute(Date date, int minute) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 获取添加几月后的时间
     * 
     * @param date
     *            时间
     * @param months
     *            增加的月数
     * @return Date 返回日期对象
     */
    public static Date addMonth(Date date, int months) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 获取添加几年后的时间
     * 
     * @param date
     *            时间
     * @param years
     *            增加的年数
     * @return Date 返回日期对象
     */
    public static Date addYear(Date date, int years) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 得到几天前的时间
     * 
     * @param d
     *            时间
     * @param day
     *            天数
     * @return Date 返回日期对象
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     *            时间
     * @param day
     *            天数
     * @return Date 返回日期对象
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 比较两个时间先后 当d1比d2晚返回正数，否则返回负数
     * 
     * @param d1
     *            Date左值
     * @param d2
     *            Date右值
     * @return int 比较结果
     */
    public static int compareDateOnDay(Date d1, Date d2) {
        return d1.compareTo(d2);
    }

    /**
     * 获得当前日期与本周日相差的天数
     * 
     * @return int day值
     */
    public static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        // 因为按中国礼拜一作为第一天所以这里减1
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        if (dayOfWeek == 1) {
            return 0;
        }
        else {
            return 1 - dayOfWeek;
        }
    }

    /**
     * 获得本周一的日期
     * 
     * @return Date 日期对象
     */
    public static Date getMondayOfWeek() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();

        return monday;
    }

    /**
     * 获得本周星期日的日期
     * 
     * @return Date 日期对象
     */
    public static Date getSundayOfWeek() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date sunday = currentDate.getTime();
        return sunday;
    }

    /**
     * 取得当前的系统时间，(默认格式为：yyyy-MM-dd)
     * 
     * @return String 当前日期字符串
     */
    public static String getCurrentDate() {
        return convertDate2String(new Date());
    }

    /**
     * 按指定格式得到当前日期字符串
     * 
     * @param format
     *            指定格式
     * @return String 日期字符串
     */
    public static String getCurrentDate(String format) {
        Date today = new Date();
        SimpleDateFormat formatter = null;
        try {
            formatter = new SimpleDateFormat(format);
            return formatter.format(today);
        }
        catch (Exception e) {
            formatter = new SimpleDateFormat(DATE_FORMAT);
            return formatter.format(today);
        }
    }

    /**
     * 
     * 将时长转换成天/小时/分/秒展示
     * 
     * @param timelong
     * @return String
     */
    public static String changeDateRangeToString(Long timelong) {

        if (timelong == null)
            return "";
        long d = timelong.longValue() / (24 * 60 * 60 * 1000);
        long h = timelong.longValue() % (24 * 60 * 60 * 1000) / (60 * 60 * 1000);
        long m = timelong.longValue() % (60 * 60 * 1000) / (60 * 1000);
        double s = (timelong.doubleValue() % (60 * 1000)) / 1000;

        return (d != 0 ? (d + "天") : "") + (h != 0 ? (h + "小时") : "") + (m != 0 ? (m + "分") : "")
                + (s != 0 ? (s + "秒") : "");

    }

    /**
     * 根据day获取星期几
     * 
     * @param weekNum
     *            day值
     * @return String 星期
     */
    public static String getCHSWeekNameByDayOfWeek(int weekNum) {
        switch (weekNum) {
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            case 0:
                return "星期天";
            default:
                return " ";
        }
    }

    /**
     * 判断两个日期是否为同一天
     * 
     * @param d1
     *            日期一
     * @param d2
     *            日期二
     * @return 同一天true，不是同一天false
     */
    public static boolean isSameDate(Date d1, Date d2) {
        boolean result = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)) {
            result = true;
        }
        return result;
    }

    /**
     * 计算2个日期相隔多少天
     * 
     * @param startday
     *            开始时间
     * @param endday
     *            结束时间
     * @return int
     */
    public static int getIntervalDays(Date startday, Date endday) {
        if (startday.after(endday)) {
            Date cal = startday;
            startday = endday;
            endday = cal;
        }
        long sl = startday.getTime();
        long el = endday.getTime();
        long ei = el - sl;
        return (int) (ei / (1000 * 60 * 60 * 24));
    }

    public static java.sql.Date getSqlDate(String dateStr) {
        try {
            return new java.sql.Date(convertString2DateAuto(dateStr).getTime());
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Time getSqlTime(String dateStr) {
        try {
            return new Time(convertString2DateAuto(dateStr).getTime());
        }
        catch (Exception e) {
            return null;
        }
    }

    public static Timestamp getSqlTimestamp(String dateStr) {
        try {
            return new Timestamp(convertString2DateAuto(dateStr).getTime());
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(convertDate2String(getMondayOfWeek()));
        System.out.println(convertDate2String(getSundayOfWeek()));
    }

}
