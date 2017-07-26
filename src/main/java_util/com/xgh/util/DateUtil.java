package com.xgh.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * 日期格式化工具
 *
 * @author：段晓刚
 * @update：2015年11月1日 下午6:44:36
 * @Email：
 */
public class DateUtil {

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static String YYYY_MM_DD_HH_MM_SSS = "yyyyMMddHHmmsss";
    public static String YYYYMM = "yyyyMM";

    public static String YYYYMMDD = "yyyyMMdd";

    private static Calendar calendar;
    private static SimpleDateFormat sdf;

    /**
     * 获取时间格式："yyyy-MM-dd"
     *
     * @param date
     * @return
     */
    public static Date toDate(String date) {

        return toDate(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * ※日期的字符串格式转Date对象实例
     *
     * @param date    字符串格式的日期
     * @param pattern 日期格式
     * @return 返回Date对象实例
     */
    public static Date toDate(String date, String pattern) {
        Date date2;

        if (isEmpty(pattern)) {
            pattern = YYYY_MM_DD_HH_MM_SS;
        }
        try {
            if (date != null && !date.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                date2 = sdf.parse(date);
            } else {
                date2 = toLocalDate(new Date(), pattern);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
        return date2;
    }

    /**
     * 日期的字符串格式转Date对象实例
     *
     * @param date
     * @return
     */
    public static String toStr(Date date) {
        return toStr(date, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 日期的字符串格式转Date对象实例
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String toStr(Date date, String pattern) {
        String date2 = "";

        if (isEmpty(pattern)) {
            pattern = YYYY_MM_DD_HH_MM_SS;
        }

        if (date != null && !date.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            date2 = sdf.format(date);
        }

        return date2;
    }

    //得到系统时间
    public static Date getSystemTime() {
        Calendar now = Calendar.getInstance();
        return now.getTime();
    }

    //得到系统时间
    public static Date getMinTime() {
        Calendar date = Calendar.getInstance();
        date.set(1997, 1, 1);
        return date.getTime();
    }

    public static String DateNow() {
        return toStr(getSystemTime(), YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * ※其他地区时间转成北京时间
     *
     * @param date    日期实例对象
     * @param pattern 日期格式
     */
    public static Date toLocalDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        TimeZone zone = new SimpleTimeZone(28800000, "Asia/Shanghai");// +8区
        sdf.setTimeZone(zone);
        String sdate = sdf.format(date);

        SimpleDateFormat sdf2 = new SimpleDateFormat(pattern);
        try {
            return sdf2.parse(sdate);
        } catch (Exception e) {
            e.printStackTrace();
            return new Date();
        }
    }

    /**
     * 获取年月日结构路径片段
     *
     * @return
     */
    public static String getDatePath() {
        Calendar calendar = Calendar.getInstance();

        String path = "";
        int year = calendar.get(Calendar.YEAR);
        path = year + "";
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10)
            path = path + File.separator + "0" + month;
        else {
            path = year + File.separator + month;
        }
        int day = calendar.get(Calendar.DATE);
        if (day < 10)
            return path + File.separator + "0" + day + File.separator;
        else {
            return path + File.separator + day + File.separator;
        }
    }

    /**
     * 返回毫秒
     *
     * @param date
     * @return
     */
    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    private static boolean isEmpty(String str) {
        if (str != null && str.trim().length() > 0) {
            return false;
        }
        return true;
    }


    /**
     * 获取前一周的时间
     *
     * @param date
     * @return
     */
    public static Date getLastWeek(Date date) {

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        date = calendar.getTime();

        return date;
    }

    /**
     * 获取前一个月的时间
     *
     * @param date
     * @return
     */
    public static Date getLastMonth(Date date) {

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        date = calendar.getTime();

        return date;
    }

    /**
     * 获取前三个月的时间
     *
     * @param date
     * @return
     */
    public static Date reduceMonth(Date date,int month) {

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -month);
        date = calendar.getTime();

        return date;
    }

    /**
     * 获取半年之前的时刻
     *
     * @param date
     * @return
     */
    public static Date addMonth(Date date,int month) {

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        date = calendar.getTime();

        return date;
    }

    /**
     * 获取九个月之前的时刻
     *
     * @param date
     * @return
     */
    public static Date reduceYear(Date date,int year) {

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -year);
        date = calendar.getTime();

        return date;
    }

    /**
     * 获取一年之前的时刻
     *
     * @param date
     * @return
     */
    public static Date addYear(Date date,int year) {

        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        date = calendar.getTime();

        return date;
    }


    /**
     * 返回中文的日期
     * @param date
     * @return
     */
    public static String getChineseDateTime(Date date) {

        //date 是存储的时间戳
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        //所在时区时8，系统初始时间是1970-01-01 80:00:00，注意是从八点开始，计算的时候要加回去
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        long today = (System.currentTimeMillis()+offSet)/86400000;
        long start = (date.getTime()+offSet)/86400000;
        long intervalTime = start - today;
        //-2:前天,-1：昨天,0：今天,1：明天,2：后天
        String strDes="";
        if(intervalTime==2){
            strDes= "后天 "+dateFormat.format(date);//后天
        }else if(intervalTime==1){
            strDes= "明天 "+dateFormat.format(date);//明天
        }
        else if(intervalTime==0){
            strDes= "今天 "+dateFormat.format(date);//今天
        }else if(intervalTime==-1){
            strDes= "昨天 "+dateFormat.format(date);//昨天
        }else{
            dateFormat = new SimpleDateFormat("MM-dd HH:mm");
            strDes=dateFormat.format(date);//直接显示时间
        }
        return strDes;
    }
}