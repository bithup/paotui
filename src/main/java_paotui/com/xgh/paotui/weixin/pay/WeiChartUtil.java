package com.xgh.paotui.weixin.pay;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


public class WeiChartUtil{

    /**
     * 返回状态码
     */
    public static final String ReturnCode = "return_code";

    /**
     * 返回信息
     */
    public static final String ReturnMsg = "return_msg";

    /**
     * 业务结果
     */
    public static final String ResultCode = "result_code";

    /**
     * 预支付交易会话标识
     */
    public static final String PrepayId = "prepay_id";
    /**
     * 得到微信预付单的返回ID
     * api:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
     * @param orderId  商户自己的订单号
     * @param goodsBody  商品描述
     * @param totalFee  总金额  （分）
     * @param openid   用户openid，为空时，表示APP端微信支付，否则表示公众号支付
     * @return
     */
    public static Map<String, String> getPreyId(String orderId,String goodsBody,
                                                String totalFee,String notify_url,String openid){
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("appid", WeiChartConfig.AppId);
        reqMap.put("mch_id", WeiChartConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("body", goodsBody);
        //reqMap.put("detail", WeiChartConfig.subject); //非必填
        //reqMap.put("attach", "附加数据"); //非必填
        reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
        reqMap.put("total_fee", totalFee); //订单总金额，单位为分
        reqMap.put("spbill_create_ip", getHostIp()); //用户端实际ip
        // reqMap.put("time_start", "172.16.40.18"); //交易起始时间 非必填
        // reqMap.put("time_expire", "172.16.40.18"); //交易结束时间  非必填
        // reqMap.put("goods_tag", "172.16.40.18"); //商品标记 非必填
        reqMap.put("notify_url", notify_url); //通知地址
        if(StringUtils.isEmpty(openid)){
            reqMap.put("trade_type", "APP"); //交易类型
        }
        else{
            reqMap.put("trade_type", "JSAPI"); //交易类型
            reqMap.put("openid", openid); //公众号支付
        }

        //reqMap.put("limit_pay", "no_credit"); //指定支付方式,no_credit 指定不能使用信用卡支  非必填
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        System.out.println("传递的xml："+reqStr);
        String retStr = HttpClientUtil.postHttplient(WeiChartConfig.PrepayUrl, reqStr);
        return getInfoByXml(retStr);
    }

    /**
     * 关闭订单
     * @param orderId  商户自己的订单号
     * @return
     */
    public static Map<String, String> closeOrder(String orderId){
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("appid", WeiChartConfig.AppId);
        reqMap.put("mch_id", WeiChartConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        String retStr = HttpClientUtil.postHttplient(WeiChartConfig.CloseOrderUrl, reqStr);
        return getInfoByXml(retStr);
    }


    /**
     * 查询订单
     * @param orderId 商户自己的订单号
     * @return
     */
    public static String getOrder(String orderId){
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("appid", WeiChartConfig.AppId);
        reqMap.put("mch_id", WeiChartConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        String retStr = HttpClientUtil.postHttplient(WeiChartConfig.OrderUrl, reqStr);
        return retStr;
    }


    /**
     * 退款
     * @param orderId  商户订单号
     * @param refundId  退款单号
     * @param totralFee 总金额（分）
     * @param refundFee 退款金额（分）
     * @param opUserId 操作员ID
     * @return
     */
    public static Map<String, String> refundWei(String orderId,String refundId,String totralFee,String refundFee,String opUserId){
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("appid", WeiChartConfig.AppId);
        reqMap.put("mch_id", WeiChartConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("out_trade_no", orderId); //商户系统内部的订单号,
        reqMap.put("out_refund_no", refundId); //商户退款单号
        reqMap.put("total_fee", totralFee); //总金额
        reqMap.put("refund_fee", refundFee); //退款金额
        reqMap.put("op_user_id", opUserId); //操作员
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        String retStr = "";
        try{
            retStr = HttpClientUtil.postHttplientNeedSSL(WeiChartConfig.RefundUrl, reqStr, WeiChartConfig.refund_file_path, WeiChartConfig.MchId);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return getInfoByXml(retStr);
    }


    /**
     * 退款查询
     * @param refundId  退款单号
     * @return
     */
    public static Map<String, String> getRefundWeiInfo(String refundId){
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("appid", WeiChartConfig.AppId);
        reqMap.put("mch_id", WeiChartConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("out_refund_no", refundId); //商户退款单号
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);
        String retStr = HttpClientUtil.postHttplient(WeiChartConfig.RefundQueryUrl, reqStr);
        return getInfoByXml(retStr);
    }

    /**
     * 企业向微信用户个人付款
     * api:https://pay.weixin.qq.com/wiki/doc/api/tools/mch_pay.php?chapter=14_2
     * @param openid  商户appid下，某用户的openid
     * @param orderId  商户订单号
     * @param userName  用户名
     * @param amount  转账金额  （分）
     * @return
     */
    public static Map<String, String> transfers(String openid,String orderId,String userName,
                                                String amount) throws Exception {
        Map<String, String> reqMap = new HashMap<String, String>();
        //reqMap.put("appid", WeiChartConfig.AppId);
        reqMap.put("mch_appid", WeiChartConfig.AppId);
        reqMap.put("mchid", WeiChartConfig.MchId);
        reqMap.put("nonce_str", getRandomString());
        reqMap.put("partner_trade_no", orderId); //商户系统内部的订单号,
        reqMap.put("openid", openid);
        reqMap.put("check_name", "NO_CHECK");
        //reqMap.put("re_user_name", userName); //收款用户姓名


        reqMap.put("amount", amount); //订单总金额，单位为分
        reqMap.put("spbill_create_ip", getHostIp()); //用户端实际ip

        reqMap.put("desc", "提现"); //企业付款描述信息
        reqMap.put("sign", getSign(reqMap));

        String reqStr = creatXml(reqMap);


        String retStr = HttpClientUtil.postHttplientNeedSSL(WeiChartConfig.TransfersBillUrl, reqStr, WeiChartConfig.transfers_file_path, WeiChartConfig.MchId);
        return getInfoByXml(retStr);

    }
    /**
     * 传入map  生成头为XML的xml字符串，例：<xml><key>123</key></xml>
     * @param reqMap
     * @return
     */
//    public static String creatXml(Map<String, String> reqMap){
//        Set<String> set = reqMap.keySet();
//        FXmlNode rootXml = new FXmlNode();
//        rootXml.setName("xml");
//        for(String key : set){
//            rootXml.createNode(key, reqMap.get(key));
//        }
//        return rootXml.xml().toString();
//    }

    /**
     * 得到加密值
     * @param map
     * @return
     */
    public static String getSign(Map<String, String> map){
        String[] keys = map.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuffer reqStr = new StringBuffer();
        for(String key : keys){
            String v = map.get(key);
            if(v != null && !v.equals("")){
                reqStr.append(key).append("=").append(v).append("&");
            }
        }
        reqStr.append("key").append("=").append(WeiChartConfig.AppSercret);
        System.out.println("getSign返回值："+reqStr.toString());

        String resultString = "";
        //MD5加密
        return DigestUtils.md5Hex(reqStr.toString()).toUpperCase();


//        try{
//           // return DigestUtils.md5Hex(reqStr.toString().getBytes("UTF-8")).toUpperCase();
//            resultString = DigestUtils.md5Hex(getContentBytes(reqStr.toString(), "UTF-8")).toUpperCase();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return resultString;
    }

//    /**
//     * @param content
//     * @param charset
//    * @return
//            * @throws SignatureException
//    * @throws UnsupportedEncodingException
//    */
////    private static byte[] getContentBytes(String content, String charset) {
////        if (charset == null || "".equals(charset)) {
////            return content.getBytes();
////        }
////        try {
////            return content.getBytes(charset);
////        } catch (UnsupportedEncodingException e) {
////            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
////        }
////    }


    /**
     * 得到10 位的时间戳
     * 如果在JAVA上转换为时间要在后面补上三个0
     * @return
     */
    public static String getTenTimes(){
        String t = new Date().getTime()+"";
        t = t.substring(0, t.length()-3);
        return t;
    }

    /**
     * 得到随机字符串
     * @param
     * @return
     */
    public static String getRandomString(){
        int length = 32;
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i){
            int number = random.nextInt(62);//[0,62)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 得到本地机器的IP
     * @return
     */
    private static String getHostIp(){
        String ip = "";
        try{
            ip = InetAddress.getLocalHost().getHostAddress();
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 将XML转换为Map 验证加密算法 然后返回
     * @param xml
     * @return
     */
    public static Map<String, String>  getInfoByXml(String xml){
        Map<String, String> map = new HashMap<String, String>();
        try{


            map = XmlUtil.xml2mapWithAttr(xml,false);
            //对返回结果做校验.去除sign 字段再去加密

//            String rightSing = getSign(map);
//            if(rightSing.equals(retSign)){
//                return map;
//            }
        }catch(Exception e){
            return null;
        }
        return map;
    }


//    /**
//     * 将金额转换成分
//     * @param fee 元格式的
//     * @return 分
//     */
//    public static String changeToFen(Double fee){
//        String priceStr = "";
//        if(fee != null){
//            int p = (int)(fee * 100); //价格变为分
//            priceStr = Integer.toString(p);
//        }
//        return priceStr;
//    }

    public static String changeToFen(String amount) {
        String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
        // 或者$的金额
        int index = currency.indexOf(".");
        int length = currency.length();
        Long amLong = 0l;
        if (index == -1) {
            amLong = Long.valueOf(currency + "00");
        } else if (length - index >= 3) {
            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
        } else if (length - index == 2) {
            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
        } else {
            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
        }
        return amLong.toString();
    }


    //输出XML
    public static String creatXml(Map<String, String> reqMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = reqMap.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(null != v && !"".equals(v) && !"appkey".equals(k)) {

                sb.append("<" + k +">" + reqMap.get(k) + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }




    public static void main(String[] args) throws DocumentException, IOException {
//        String notify_url= PaotuiUtil.PAOTUI_URL+"paotui/front/v1/WeixinPay/addBalanceWithWeixinPayNotifyCallBack.do";
//        Map<String, String> map=WeiChartUtil.getPreyId("11111111","test","1",notify_url,"otByv1Xsjr44uR2Qx10kunrrRSA0");
        System.out.println(changeToFen("99890.19"));
    }
}