package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.util.MapUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Tian on 2017/3/9.
 */
@Controller
@Api(value="地图API")
@RequestMapping(value = "paotui/front/v1/map/")
public class MapFrontController {
    @ResponseBody
    @RequestMapping(value = "getDistanse", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "计算两个坐标间的路径距离", httpMethod = "GET", notes = "计算两个坐标间的路径距离")
    public String getDistanse(
            @ApiParam(value="地图类型，1：百度，2：腾讯") @RequestParam(value = "mapType") String mapType,
            @ApiParam(value="起点经度") @RequestParam(value = "longitudeA") String longitudeA,
            @ApiParam(value="起点纬度") @RequestParam(value = "latitudeA") String latitudeA,
            @ApiParam(value="终点经度") @RequestParam(value = "longitudeB") String longitudeB,
            @ApiParam(value="终点纬度") @RequestParam(value = "latitudeB") String latitudeB,
            HttpServletRequest request) {
        //参看：http://lbsyun.baidu.com/index.php?title=webapi/route-matrix-api-v2
        StringBuffer url= new StringBuffer("http://api.map.baidu.com/routematrix/v2/driving?output=json&ak=I2Dkb8VzGuEYqxTIpGkCc2Fr");
        url.append("&origins=");
        if("1".equals(mapType)){
            url.append(latitudeA);
            url.append(",");
            url.append(longitudeA);
        }
        else{
            url.append(MapUtil.map_tx2bd(Double.parseDouble(latitudeA),Double.parseDouble(longitudeA)));
        }

        url.append("&destinations=");

        if("1".equals(mapType)){
            url.append(latitudeB);
            url.append(",");
            url.append(longitudeB);
        }
        else{
            url.append(MapUtil.map_tx2bd(Double.parseDouble(latitudeB),Double.parseDouble(longitudeB)));
        }

        String json = loadJSON(url.toString());
        return json;
    }


    public static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(),"UTF-8"));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

}
