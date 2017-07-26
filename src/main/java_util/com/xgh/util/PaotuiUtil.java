package com.xgh.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import java.math.BigDecimal;

/**
 * Created by Tian on 2017/3/23.
 */
public class PaotuiUtil {


    //默认可抢单距离
    public static int DEFAULT_ORDER_DISTANCE = 10000;


    //极光推送
    public static String JPUSH_MASTER_SECRET = "df18f34080e8255d497a941a";

    public static String JPUSH_APP_KEY = "9ffe3090f243546c36684797";

    //百度地图应用AK
    public static String BAIDU_APP_KEY = "WK4nD4s33KziKeLoamGDW2nliwhgDmci";


    //微信支付回调网址
    public static String PAOTUI_URL = "http://jingxin2016.vicp.io/";

    /**
     * 根据实际充值金额返回含赠送的账户金额
     * @param realMoney :实际支付金额
     * @param appVersion :app版本，不同版本执行不同的优惠策略
     * @return
     */
    public static BigDecimal getAccountMoneyByRealMoney(BigDecimal realMoney,int appVersion)
    {
        BigDecimal accountMoney=new BigDecimal(0);
        if(appVersion==1){

            if(realMoney.compareTo(new BigDecimal(100))==0){
                //冲100送10元
                accountMoney=realMoney.add(new BigDecimal(10));
            }
            else if(realMoney.compareTo(new BigDecimal(500))==0){
                //冲500送100元
                accountMoney=realMoney.add(new BigDecimal(100));
            }
            else{
                accountMoney=realMoney;
            }
        }
        else{
            accountMoney=realMoney;
        }

        return accountMoney;
    }


}
