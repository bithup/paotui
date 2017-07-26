package com.xgh.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Tian on 2017/3/13.
 */
public class MapUtil {

    private static double EARTH_RADIUS = 6371.393;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 1000);
        return s;
    }

    /**
     * 坐标转换，腾讯地图转换成百度地图坐标
     * @param lat 腾讯纬度
     * @param lon 腾讯经度
     * @return 返回结果：纬度,经度
     */
    public static String map_tx2bd(double lat,double lon){
        double bd_lon;
        double bd_lat;
        double x_pi=3.14159265358979324;
        double x = lon, y = lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        bd_lon = z * Math.cos(theta) + 0.0065;
        bd_lat = z * Math.sin(theta) + 0.006;

        System.out.println("bd_lon:"+bd_lon);
        System.out.println("bd_lat:"+bd_lat);
        return bd_lat+","+bd_lon;
    }


    /**
     * 坐标转换，百度地图坐标转换成腾讯地图坐标
     * @param lat  百度坐标纬度
     * @param lon  百度坐标经度
     * @return 返回结果：纬度,经度
     */
    public static String map_bd2tx(double lat, double lon){
        double tx_lat;
        double tx_lon;
        double x_pi=3.14159265358979324;
        double x = lon - 0.0065, y = lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        tx_lon = z * Math.cos(theta);
        tx_lat = z * Math.sin(theta);
        return tx_lat+","+tx_lon;
    }

    /**
     * 根据百度地图返回值解析出来实际距离。
     * -1:获取距离异常
     * @param mapJson
     * @return
     */
    public static int getRealDistanceOfBaiduMap(String mapJson){
        int distance=-1;
        JSONObject mapObject= JSONObject.parseObject(mapJson);
        if(mapObject!=null){
            String status=String.valueOf(mapObject.get("status"));
            if("0".equals(status)){
                JSONArray result= (JSONArray)mapObject.get("result") ;
                JSONObject distanceObject =result.getJSONObject(0).getJSONObject("distance");

                distance=Integer.parseInt(String.valueOf(distanceObject.get("value")));
            }
        }

        return distance;
    }


    /**
     * 根据url调百度地图信息
     * @param url
     * @return
     */
    public static  String loadBaiduMapJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(), "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

}
