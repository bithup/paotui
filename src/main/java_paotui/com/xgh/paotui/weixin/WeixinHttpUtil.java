package com.xgh.paotui.weixin;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class WeixinHttpUtil {



    /**
     * HttpClient连接SSL
     */
    public static CloseableHttpClient getHTTPSClient() {
        CloseableHttpClient httpclient = null;
        try {
            TrustManager[] tm = { new MyX509TrustManager() };

            SSLContext sslContext;
            sslContext = SSLContext.getInstance("SSL", "SunJSSE");

            sslContext.init(null, tm, new java.security.SecureRandom());


            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpclient;
    }


    public static String postNameValuePair(CloseableHttpClient httpClient, String url, List<BasicNameValuePair> formparams) {
        String responseBody = "";
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseBody = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }


    public static String postXml(CloseableHttpClient httpClient, String url, String xml) {
        String responseBody = "";
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        try {
            StringEntity paramEntity = new StringEntity(xml);
            httppost.setEntity(paramEntity);
            httppost.setHeader("Content-Type", "text/xml;charset=UTF-8");

            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseBody = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }


    public static String postJson(CloseableHttpClient httpClient, String url, String json) {
        String responseBody = "";
        // 创建httppost
        HttpPost httppost = new HttpPost(url);
        try {
            StringEntity paramEntity = new StringEntity(json,"UTF-8");
            //paramEntity.setContentEncoding("UTF-8");
            httppost.setEntity(paramEntity);
            httppost.setHeader("Content-Type", "application/json;charset=UTF-8");

            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseBody = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }

    /**
     * 发送 get请求
     */
    public static String get(CloseableHttpClient httpClient, String url) {
        String responseBody = "";
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            // 执行get请求.
            CloseableHttpResponse response = httpClient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseBody = EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseBody;
    }


    private static class MyX509TrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }
}