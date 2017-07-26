package com.xgh.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {


    /**
     * 对象转换为字符串,对象为null时转化为空字符串
     *
     * @param obj
     * @return
     */
    public static String convertNullToEmpty(Object obj) {
        return obj == null ? "" : String.valueOf(obj);
    }

    /**
     * 对象转换为字符串，避免"null"的出现
     *
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {
        return obj == null ? null : obj + "";
    }

    /**
     * 判断字符串 是否 为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        if (null == str || "null".equals(str) || "".equals(str))
            return true;
        else {
            return false;
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static double formatDouble(double d){
        BigDecimal bigDecimal = BigDecimal.valueOf(d);
        return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
