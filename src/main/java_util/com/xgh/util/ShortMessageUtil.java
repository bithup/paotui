package com.xgh.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/28.
 */
public class ShortMessageUtil {


    public static int sendMessage(String telPhone,String content) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String sendUrl = "http://www.139000.com/send/gsend.asp?name={用户名}&pwd={短信密码}&dst={短信号码}&sender=&time=&txt=ccdx&msg={短信内容}。";
        System.out.println(sendUrl);
        String sendUrl1 = sendUrl.replace("{用户名}", URLEncoder.encode("fsq_xgh"));
        String sendUrl2 = sendUrl1.replace("{短信密码}", URLEncoder.encode("xghwep999"));
        String sendUrl3 = sendUrl2.replace("{短信号码}", telPhone);
        String sendUrl4 = sendUrl3.replace("{短信内容}", content);
        String sTemp1 = sendUrl4.substring(0, sendUrl4.lastIndexOf("&") + 5);
        String sTemp2 = sendUrl4.substring(sendUrl4.lastIndexOf("&") + 5);

        try {
            sTemp2 = URLEncoder.encode(sTemp2, "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sendUrl4 = sTemp1 + sTemp2;

        InputStream in = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(sendUrl4);
            in = url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            String inputLine = "";
            while ((inputLine = reader.readLine()) != null) {
                sb.append(inputLine);
            }
            System.out.println("短信批次号=" + sb.toString());
            /*return sb.toString();*/
            String[] result = sb.toString().split("&");
            String num = result[0].substring(result[0].lastIndexOf("=") + 1, result[0].length());
            String errId = result[4].substring(result[4].lastIndexOf("=") + 1, result[4].length());

            if (!"0".equals(num)) {
                return 1;
            } else {
                return 0;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception var13) {
                var13.printStackTrace();
            }
        }
        return 0;
    }
}
