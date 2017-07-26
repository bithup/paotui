package com.xgh.paotui.weixin;

/**
 * 微信AccessToken
 */
public class AccessToken {

    private String accessToken;

    private int expiresin;


    // 定义一个私有的构造方法
    private AccessToken() {
    }

    private static final AccessToken instance = new AccessToken();

    // 静态方法返回该类的实例
    public static AccessToken getInstance() {
        return instance;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(int expiresin) {
        this.expiresin = expiresin;
    }

}
