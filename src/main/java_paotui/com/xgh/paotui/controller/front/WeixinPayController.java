package com.xgh.paotui.controller.front;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.services.ICapitalLogService;
import com.xgh.paotui.services.ICustomerService;
import com.xgh.paotui.services.IOrderPayService;
import com.xgh.paotui.services.IOrderService;
import com.xgh.paotui.weixin.pay.WeiChartConfig;
import com.xgh.paotui.weixin.pay.WeiChartUtil;
import com.xgh.paotui.weixin.pay.XmlUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Controller
@Scope("prototype")
@Api(value="微信支付API")
@RequestMapping(value = "paotui/front/v1/WeixinPay/")
public  class WeixinPayController extends BaseController {

    private static Logger logger = Logger.getLogger(WeixinPayController.class);



    @Autowired
    protected IOrderService orderService;


    @Autowired
    protected IOrderPayService orderPayService;

    @Autowired
    protected ICustomerService customerService;


    @Autowired
    protected ICapitalLogService capitalLogService;


    /**
     * 帮送、帮取微信支付跑腿费回调处理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("sendOrderWeixinNotifyCallBack")
    @ApiIgnore
    public void sendOrderWeixinNotifyCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XmlUtil.xml2mapWithAttr(sb.toString(),false);

        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<String,String>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = WeiChartConfig.AppId; // key

        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();

        packageParams.remove("sign");


        String mysign= WeiChartUtil.getSign(packageParams);

        //判断签名是否正确
        if(mysign.equals(tenpaySign)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");

                String total_fee = (String)packageParams.get("total_fee");

                //String prepay_id=(String)packageParams.get("prepay_id");
                //add 20170426
                //微信支付订单号
                String transaction_id=(String)packageParams.get("transaction_id");

                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);

                //////////执行自己的业务逻辑////////////////
                Map<String,Long> orderMap=new HashMap<String,Long>();
                String capitalLogId= out_trade_no.replace("pt","");
                int ret = orderPayService.updateOfSendOrderWeixinPaySuccess(Long.parseLong(capitalLogId),transaction_id,total_fee,orderMap);
                if(ret==1){
                    logger.info("帮送、帮取微信支付跑腿费成功.......");
                    Long customerId= orderMap.get("customerId");
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                    //执行极光推送,提示支付成功
                    orderPayService.getJpushPayOrderSuccess(customerId);
                }
                else if(ret==9){
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }
                else{
                    logger.info("帮送、帮取微信支付跑腿费失败.......");
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![更新支付跑腿费状态失败]]></return_msg>" + "</xml> ";
                }



            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }

    }



    /**
     * 帮买微信支付跑腿费（可能有商品费）回调处理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("buyOrderWeixinNotifyCallBack")
    @ApiIgnore
    public void buyOrderWeixinNotifyCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XmlUtil.xml2mapWithAttr(sb.toString(),false);

        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<String,String>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = WeiChartConfig.AppId; // key

        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();

        packageParams.remove("sign");


        String mysign= WeiChartUtil.getSign(packageParams);

        //判断签名是否正确
        if(mysign.equals(tenpaySign)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");

                String total_fee = (String)packageParams.get("total_fee");

                //String prepay_id=(String)packageParams.get("prepay_id");
                //add 20170426
                //微信支付订单号
                String transaction_id=(String)packageParams.get("transaction_id");

                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);

                //////////执行自己的业务逻辑////////////////
                Map<String,Long> orderMap=new HashMap<String,Long>();
                String capitalLogId= out_trade_no.replace("pt","");
                int ret = orderPayService.updateOfBuyOrderWeixinPaySuccess(Long.parseLong(capitalLogId),transaction_id,total_fee,orderMap);
                if(ret==1){
                    logger.info("帮买app微信订单状态修改成功.......");
                    Long customerId= orderMap.get("customerId");
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                    //执行极光推送,提示支付成功
                    orderPayService.getJpushPayOrderSuccess(customerId);
                }
                else if(ret==9){
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }
                else{
                    logger.info("帮买微信支付跑腿费失败.......");
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![更新充值状态失败]]></return_msg>" + "</xml> ";
                }



            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }

    }


    /**
     * 帮买微信支付商品费回调处理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("buyGoodsWeixinNotifyCallBack")
    @ApiIgnore
    public void buyGoodsWeixinNotifyCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XmlUtil.xml2mapWithAttr(sb.toString(),false);

        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<String,String>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = WeiChartConfig.AppId; // key
        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();
        packageParams.remove("sign");


        String mysign= WeiChartUtil.getSign(packageParams);

        //判断签名是否正确
        if(mysign.equals(tenpaySign)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");

                String total_fee = (String)packageParams.get("total_fee");

                //String prepay_id=(String)packageParams.get("prepay_id");
                //add 20170426
                //微信支付订单号
                String transaction_id=(String)packageParams.get("transaction_id");

                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);

                //////////执行自己的业务逻辑////////////////
                String capitalLogId= out_trade_no.replace("gd","");
                int ret = orderPayService.updateOfBuyGoodsWeixinPayMoneySuccess(Long.parseLong(capitalLogId),transaction_id,total_fee);
                if(ret==1){
                    logger.info("帮买微信支付商品费成功.......");
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }
                else if(ret==9){
                    logger.info("帮买微信支付商品费成功.......");
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }
                else{
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[更新帮买微信支付商品费支付状态失败]]></return_msg>" + "</xml> ";
                }



            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }

    }


    /**
     * 代排队微信支付跑腿费回调处理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("lineupOrderWeixinNotifyCallBack")
    @ApiIgnore
    public void lineupOrderWeixinNotifyCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();
        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XmlUtil.xml2mapWithAttr(sb.toString(),false);
        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<String,String>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 账号信息
        String key = WeiChartConfig.AppId; // key

        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();

        packageParams.remove("sign");

        String mysign= WeiChartUtil.getSign(packageParams);

        //判断签名是否正确
        if(mysign.equals(tenpaySign)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");
                String total_fee = (String)packageParams.get("total_fee");
                //String prepay_id=(String)packageParams.get("prepay_id");
                //add 20170426
                //微信支付订单号
                String transaction_id=(String)packageParams.get("transaction_id");
                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);
                //////////执行自己的业务逻辑////////////////
                Map<String,Long> orderMap=new HashMap<String,Long>();
                String capitalLogId= out_trade_no.replace("pt","");
                int ret = orderPayService.updateOfLineupOrderWeixinPaySuccess(Long.parseLong(capitalLogId),transaction_id,total_fee,orderMap);
                if(ret==1){
                    logger.info("代排队微信支付跑腿费成功.......");
                    Long customerId= orderMap.get("customerId");
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                    //执行极光推送,提示支付成功
                    orderPayService.getJpushPayOrderSuccess(customerId);
                }else if(ret==9){
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }else{
                    logger.info("代排队微信支付跑腿费失败.......");
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![更新支付跑腿费状态失败]]></return_msg>" + "</xml> ";
                }
            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }
    }

    /**
     * 代排队微信支付加时费回调处理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("lineupAddPayWeixinNotifyCallBack")
    @ApiIgnore
    public void lineupAddPayWeixinNotifyCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();
        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XmlUtil.xml2mapWithAttr(sb.toString(),false);
        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<String,String>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        // 账号信息
        String key = WeiChartConfig.AppId; // key
        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();
        packageParams.remove("sign");
        String mysign= WeiChartUtil.getSign(packageParams);

        //判断签名是否正确
        if(mysign.equals(tenpaySign)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");
                String total_fee = (String)packageParams.get("total_fee");
                //String prepay_id=(String)packageParams.get("prepay_id");
                //add 20170426
                //微信支付订单号
                String transaction_id=(String)packageParams.get("transaction_id");
                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);
                //////////执行自己的业务逻辑////////////////
                String capitalLogId= out_trade_no.replace("gd","");
                int ret = orderPayService.updateOfLineupAddPayWeixinPayMoneySuccess(Long.parseLong(capitalLogId),transaction_id);
                if(ret==1){
                    logger.info("代排队微信支付加时费成功.......");
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else if(ret==9){
                    logger.info("代排队微信支付加时费成功.......");
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else{
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[更新代排队微信支付加时费支付状态失败]]></return_msg>" + "</xml> ";
                }
            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }
    }


    /**
     * 微信支付充值回调处理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("addBalanceWithWeixinPayNotifyCallBack")
    @ApiIgnore
    public void addBalanceWithWeixinPayNotifyCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XmlUtil.xml2mapWithAttr(sb.toString(),false);

        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<String,String>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = WeiChartConfig.AppId; // key
        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();

        packageParams.remove("sign");
        String mysign= WeiChartUtil.getSign(packageParams);

        //判断签名是否正确
        if(mysign.equals(tenpaySign)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");

                String total_fee = (String)packageParams.get("total_fee");

                //String prepay_id=(String)packageParams.get("prepay_id");
                //add 20170426
                //微信支付订单号
                String transaction_id=(String)packageParams.get("transaction_id");
                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("total_fee:"+total_fee);

                //////////执行自己的业务逻辑////////////////
                String capitalLogId= out_trade_no.replace("cz","");
                int ret = customerService.addBalanceWithWeixinPay(Long.parseLong(capitalLogId),transaction_id,total_fee);

                if(ret==1 || ret==9){
                    logger.info("微信支付充值成功.......");
                    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }
                else{
                    logger.info("支付失败");
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[更新充值状态失败]]></return_msg>" + "</xml> ";
                }



            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }

    }





    /**
     * 微信支付小费回调处理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("addTipsWeixinPayNotifyCallBack")
    @ApiIgnore
    public void addTipsWeixinPayNotifyCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //读取参数
        InputStream inputStream ;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s ;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null){
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XmlUtil.xml2mapWithAttr(sb.toString(),false);

        //过滤空 设置 TreeMap
        SortedMap<String,String> packageParams = new TreeMap<String,String>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if(null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = WeiChartConfig.AppId; // key

        String tenpaySign = ((String)packageParams.get("sign")).toUpperCase();

        packageParams.remove("sign");


        String mysign= WeiChartUtil.getSign(packageParams);

        //判断签名是否正确
        if(mysign.equals(tenpaySign)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if("SUCCESS".equals((String)packageParams.get("result_code"))){
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String)packageParams.get("mch_id");
                String openid = (String)packageParams.get("openid");
                String is_subscribe = (String)packageParams.get("is_subscribe");
                String out_trade_no = (String)packageParams.get("out_trade_no");

                String total_fee = (String)packageParams.get("total_fee");

                //String prepay_id=(String)packageParams.get("prepay_id");
                //add 20170426
                //微信支付订单号
                String transaction_id=(String)packageParams.get("transaction_id");

                logger.info("mch_id:"+mch_id);
                logger.info("openid:"+openid);
                logger.info("is_subscribe:"+is_subscribe);
                logger.info("out_trade_no:"+out_trade_no);
                logger.info("transaction_id:"+transaction_id);
                logger.info("total_fee:"+total_fee);

                //////////执行自己的业务逻辑////////////////
                String capitalLogId= out_trade_no.replace("xf","");
                int ret = orderPayService.addTipsWithWeixinPay(Long.parseLong(capitalLogId),transaction_id, total_fee);
                if(ret==1  || ret==9 ){
                    logger.info("微信支付小费成功.......");
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }

                else{
                    logger.info("支付失败");
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[更新订单状态失败]]></return_msg>" + "</xml> ";
                }



            } else {
                logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else{
            logger.info("通知签名验证失败");
        }

    }

}
