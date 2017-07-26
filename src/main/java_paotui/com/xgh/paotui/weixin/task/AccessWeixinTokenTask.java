package com.xgh.paotui.weixin.task;


import com.alibaba.fastjson.JSONObject;
import com.xgh.paotui.weixin.AccessToken;
import com.xgh.paotui.weixin.WeixinHttpUtil;
import com.xgh.paotui.weixin.WeixinUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AccessWeixinTokenTask {


    /**
     * 定时任务，获取微信access_token
     */
    //@Scheduled(cron = "0 10 * * * ? ")
   @Scheduled(cron = "5 * * * * ? ")
    public void AccessWeixinTokenGetter() {
        CloseableHttpClient httpClient = WeixinHttpUtil.getHTTPSClient();
        String accessTokenJSONStr = WeixinHttpUtil.get(httpClient, WeixinUtil.ACCESS_TOKEN_URL);

        JSONObject accessTokenJson= (JSONObject)JSONObject.parse(accessTokenJSONStr);

        String access_token=(String)accessTokenJson.get("access_token");
        if(StringUtils.isNotEmpty(access_token)){
            AccessToken.getInstance().setAccessToken((String)accessTokenJson.get("access_token"));
            AccessToken.getInstance().setExpiresin((Integer)accessTokenJson.get("expires_in"));
        }


    }
}
