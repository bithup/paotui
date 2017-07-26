package com.xgh.paotui.alipayApp.config;

/* *
 *类名：AlipayConfigApp
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfigApp {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	//合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String partner = "2088121753347357";

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBANe+c6B31VrMdt2X" +
			"pkbTU1CsZcPXfws3+f8Gm9p56Kl4QT75fkRaUR70FuxHh5dZP9suK5ia3kdH7+zG" +
			"r2aOGnOXmW94AmNkUn1/S7N1ihmiT/cwbQKb3ymGjdM+EGaiHTVvaQl+ptCseWAl" +
			"Xj6ajR+nbJkYbfESIM+d0jJbef5FAgMBAAECgYEAwYDruojgzzDGiFgFZk8S0i/e" +
			"krpNE6XgT5IA5gMj4CSg+LfUPcB4M/ggO0qo3eHJ+W++4IA/T9XA5zEJj0VziIZa" +
			"yT2qFceu+asWn/M+9YclxGU+8fZPTJx/OMkC1/yFG2zlxbmH8/1+2O2JCQBBMH5z" +
			"wbmtOjer3kIxA3M6BwECQQDuH/kD5+8/tEtVD/izZAm2LgY3s7geEmFqQ4oBB7M5" +
			"5txTuu45NH66ERTG27EK31RotfzKtwPDGiwVHFuE7MaFAkEA5/Bi25WoriIB4i4y" +
			"qjewP35CZlUraFhnW39HP0G5CEfxcCoul//zneV9cobB+WUabXBJhfu4rD0ilQjB" +
			"DFlEwQJBANlTuuxSsOt+PEa9Fel7wtVPegM6di6T7e6TB4/bHDsbNTkB4Uu6WnKt" +
			"Bj5b4WAf5aTPe5DzqW5WI6d8wxY1FxUCQQCWu1uTtvQzjnhI5JsakixhZw2B8bHP" +
			"EzzAcrv7uRul7RNUQKWdNMK/B6h6KGehS3pcSxANGeUPUn/J+TF0dANBAkEAjKsf" +
			"JtFSqCSadxrFLkstaRYNj66H7TGj4lG72n+AzBE0pf3CmaZGiTcxLEDHQYED3klr" +
			"fBnHbCndcDheod6MCg==";


	//支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path ="C://";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 接收通知的接口名
	public static String service = "mobile.securitypay.pay";


	//支付宝分配给开发者的跑腿应用ID
	public static String app_id = "";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}

