package com.xgh.paotui.weixin.pay;


public class WeiChartConfig {

    /**
     * 预支付请求地址
     */
    public static final String  PrepayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 查询订单地址
     */
    public static final String  OrderUrl = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 关闭订单地址
     */
    public static final String  CloseOrderUrl = "https://api.mch.weixin.qq.com/pay/closeorder";

    /**
     * 申请退款地址
     */
    public static final String  RefundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 查询退款地址
     */
    public static final String  RefundQueryUrl = "https://api.mch.weixin.qq.com/pay/refundquery";

    /**
     * 下载账单地址
     */
    public static final String  DownloadBillUrl = "https://api.mch.weixin.qq.com/pay/downloadbill";

    /**
     * 企业向微信用户个人付款地址
     */
    public static final String  TransfersBillUrl = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    /**
     * 商户APPID
     */
    public static final String  AppId = "wx00a628d29f35e2db";

    /**
     * 商户账户 获取支付能力后，从邮件中得到
     */
    public static final String  MchId = "1458892202";

    /**
     * 商户秘钥  32位，在微信商户平台中设置
     */
    public static final String  AppSercret = "123456789zzfsqwlkjyxgs9876543211";

    /**
     * 服务器异步通知页面路径
     */
    public static String notify_url = "notify_url";

    /**
     * 页面跳转同步通知页面路径
     */
    public static String return_url = "return_url";

    /**
     * 退款通知地址
     */
    public static String refund_notify_url = "refund_notify_url";

    /**
     * 退款需要证书文件，证书文件的地址
     */
    public static String refund_file_path = "refund_file_path";


    /**
     * 提现需要证书文件，证书文件的地址
     */
    public static String transfers_file_path = "D:/apiclient_cert.p12";


//    private static  Properties properties;
//
//    public static synchronized Properties getProperties(){
//        if(properties == null){
//            String path = System.getenv(RSystemConfig.KEY_WEB_HOME_CONF) + "/weichart.properties";
//            properties = PropertiesUtil.getInstance().getProperties(path);
//        }
//        return properties;
//    }

}