package com.xgh.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证工具类
 *
 * @author：段晓刚
 * @update：2015年11月1日 下午6:47:41
 * @Email：
 */
public class MatcherUtil {

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {

        if (null == mobiles) {
            return false;
        }

        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(14[0-9])|(17[0-9]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    /**
     * 判断email格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {

        if (null == email) {
            return false;
        }

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        Pattern p = Pattern.compile(str);

        Matcher m = p.matcher(email);

        return m.matches();
    }


    /**
     * 检查整数
     *
     * @param num 检查字符串
     * @return
     */
    public static boolean checkNumber(String num) {

        return checkNumber(num, "");
    }

    /**
     * 检查整数
     *
     * @param num  检查字符串
     * @param type "+0":非负整数 "+":正整数 "-0":非正整数 "-":负整数 "":整数
     * @return
     */
    public static boolean checkNumber(String num, String type) {

        if (null == num) {
            return false;
        }

        String eL = "";
        if (type.equals("+0")) eL = "^\\d+$";//非负整数
        else if (type.equals("+")) eL = "^\\d*[1-9]\\d*$";//正整数
        else if (type.equals("-0")) eL = "^((-\\d+)|(0+))$";//非正整数
        else if (type.equals("-")) eL = "^-\\d*[1-9]\\d*$";//负整数
        else eL = "^-?\\d+$";//整数   
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(num);
        return m.matches();
    }

	/**
     *
     * @param sql
     * @return
     */
    public static boolean isSqlAttack(String sql) {

        String reg = "^.*where|select|update|truncate|drop|delete|exec.*$";
        Pattern p = Pattern.compile(reg);
        sql = sql.toLowerCase();
        //更新时间中的update不做计算
        String _sql = sql.replace("update_date","");
        return p.matcher(_sql).find();
    }


    /**
     * 随机生成 num位数字字符数组
     *
     * @param num
     * @return
     */
    public static char[] generateRandomArray(int num) {

        String chars = "0123456789";
        char[] rands = new char[num];
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            rands[i] = chars.charAt(rand);
        }
        return rands;
    }

    public static char[] randomArray() {

        int max=16;
        int min=3;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        System.out.println(s);
        // 随机生成6位数字
        System.out.println(generateRandomArray(s));

        return generateRandomArray(s);

    }

}
