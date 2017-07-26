package com.xgh.paotui.entity;

/**
 * Created by Tian on 2017/3/23.
 */
public class AppToken {
    //身份id
    private long id;

    //身份类型："customer"：发货人app；"deliveryMan":收货人;"weixin"：微信公众平台
    private String  appType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }



}
