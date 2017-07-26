package com.xgh.paotui.weixin.pay;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.util.Map;

public class HttpClientUtil{

    public static String post(String url,
                              Map<String, String> headMap,
                              Map<String, String> params){
        try{
            HttpClient httpclient = new HttpClient();
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            PostMethod httpPost = new PostMethod(url);
            if(null != headMap){
                for(String key : headMap.keySet()){
                    httpPost.setRequestHeader(key, headMap.get(key));
                }
            }

            if(null != params){
                for(String pkey : params.keySet()){
                    httpPost.addParameter(pkey, params.get(pkey));
                }
            }
            httpclient.executeMethod(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpPost.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while((str = reader.readLine()) != null){
                stringBuffer.append(str);
            }
            reader.close();
            return stringBuffer.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String postHttplient(String url,
                                       String xmlInfo){
        try{
            HttpClient httpclient = new HttpClient();
            httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            PostMethod httpPost = new PostMethod(url);
            httpPost.setRequestEntity(new StringRequestEntity(xmlInfo,"text/xml","UTF-8"));
            httpclient.executeMethod(httpPost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpPost.getResponseBodyAsStream(),"UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while((str = reader.readLine()) != null){
                stringBuffer.append(str);
            }
            reader.close();
            return stringBuffer.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 需要加密执行的
     * @param url
     * @param xmlInfo
     * @return
     * @throws Exception
     */
    public static String postHttplientNeedSSL(String url,
                                              String xmlInfo,
                                              String cretPath,
                                              String mrchId)
            throws Exception{
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(cretPath));
        try {
            keyStore.load(instream, mrchId.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mrchId.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        StringBuffer ret = new StringBuffer();
        try {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(xmlInfo,"UTF-8"));

            CloseableHttpResponse response = httpclient.execute(httpPost);

            try {
                HttpEntity entity = response.getEntity();

                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        System.out.println(text);
                        ret.append(text);
                    }

                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return ret.toString();
    }

    public static String postHtpps(String urlStr,
                                   String xmlInfo){
        try{
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "utf-8");
            out.write(xmlInfo);
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer lines = new StringBuffer();
            String line = "";
            for(line = br.readLine(); line != null; line = br.readLine()){
                lines.append(line);
            }
            return lines.toString();
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

}