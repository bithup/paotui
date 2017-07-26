package com.xgh.util;

import java.util.Random;

/**
 * Created by Administrator on 2016/12/26.
 */
public class ValidationCode {

        /**
         * @Description:	获得验证码
         * @param			count 验证码长度,codeType 验证码类型(1:英文和数字，2纯英文，3纯数字)
         * @return
         * @throws
         * @author         bsx
         * @date			2016-12-26
         */
        public static String getSecurityCode(int count,int codeType){
            char[] codeSequence = null;
            if (codeType==1) {
                codeSequence = new char[]{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                        'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
            }else if(codeType==2){
                codeSequence = new char[]{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                        'X', 'Y', 'Z'};
            }else{
                codeSequence = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
            }
            Random random = new Random();
            StringBuffer randomCode = new StringBuffer();
            for (int i = 0; i < count; i++) {
                // 得到随机产生的验证码数字。
                String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length-1)]);
                // 将产生的随机数组合在一起。
                randomCode.append(strRand);
            }
            System.out.println("本次验证码"+randomCode);
            return randomCode.toString();
        }

}
