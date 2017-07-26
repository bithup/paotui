package com.xgh.paotui.weixin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 微信创建自定义菜单
 */
public class CreateMenuServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        /** 向微信发送自定义菜单请求 */
        JSONArray buttonArray= new JSONArray();
        JSONObject button1= new JSONObject();
        button1.put("type","view");
        button1.put("name","我要下单");
        //网页授权回调地址
        String redirect_uri="http://jingxin2016.vicp.io/weixin.jsp";
        //网页授权
        String url1="https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
        url1+=WeixinUtil.APP_ID;
        url1+="&redirect_uri=";
        url1+=URLEncoder.encode(redirect_uri, "utf-8");
        url1+="&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
        button1.put("url",url1);
        //button1.put("url","http://www.xiangguohe.com/");
        buttonArray.add(button1);



        JSONObject button2= new JSONObject();
        button2.put("type","view");
        button2.put("name","发货记录");
        //网页授权回调地址
        String redirect_uri2="http://jingxin2016.vicp.io/weixin.jsp";
        //网页授权
        String url2="https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
        url2+=WeixinUtil.APP_ID;
        url2+="&redirect_uri=";
        url2+=URLEncoder.encode(redirect_uri2, "utf-8");
        url2+="&response_type=code&scope=snsapi_userinfo&state=2#wechat_redirect";
        button2.put("url",url2);
        buttonArray.add(button2);

        JSONObject button3= new JSONObject();
        button3.put("type","view");
        button3.put("name","个人中心");
        //网页授权回调地址
        String redirect_uri3="http://jingxin2016.vicp.io/weixin.jsp";
        //网页授权
        String url3="https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
        url3+=WeixinUtil.APP_ID;
        url3+="&redirect_uri=";
        url3+=URLEncoder.encode(redirect_uri3, "utf-8");
        url3+="&response_type=code&scope=snsapi_userinfo&state=3#wechat_redirect";
        button3.put("url",url3);
        buttonArray.add(button3);


        JSONObject menujson=new JSONObject();
        menujson.put("button", buttonArray);

        //这里为请求接口的url
        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
        url+=AccessToken.getInstance().getAccessToken();

        CloseableHttpClient httpClient = WeixinHttpUtil.getHTTPSClient();

        String result = WeixinHttpUtil.postJson(httpClient, url,menujson.toJSONString());

        try {
            OutputStream os = response.getOutputStream();
            os.write(result.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
