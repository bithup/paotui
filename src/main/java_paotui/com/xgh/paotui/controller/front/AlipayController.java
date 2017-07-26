package com.xgh.paotui.controller.front;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wordnik.swagger.annotations.Api;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.alipayApp.util.AlipayCoreApp;
import com.xgh.paotui.alipayApp.util.AlipayNotifyApp;
import com.xgh.paotui.services.ICapitalLogService;
import com.xgh.paotui.services.ICustomerService;
import com.xgh.paotui.services.IOrderPayService;
import com.xgh.paotui.services.IOrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/8.
 */
@Controller
@Scope("prototype")
@Api(value="支付宝支付API")
@RequestMapping(value = "paotui/front/v1/Alipay/")
public  class AlipayController extends BaseController {

    @Autowired
    protected IOrderService orderService;


    @Autowired
    protected IOrderPayService orderPayService;

    @Autowired
    protected ICustomerService customerService;


    @Autowired
    protected ICapitalLogService capitalLogService;

    private static Logger logger = Logger.getLogger(AlipayController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    /**
     * 帮送、帮取App支付宝付款接口异步通知
     */
    @RequestMapping("sendOrderAliPayNotifyCallBack")
    @ApiIgnore
    public void sendOrderAliPayNotifyCallBack() {
        logger.info("帮送、帮取app支付宝.....init");
        logger.info("帮送、帮取app支付.............");
        PrintWriter out = null;
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        logger.info(requestParams + "requestParams..................");
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            logger.info("name............." + name);
            String[] values = (String[]) requestParams.get(name);
            logger.info("values............." + values);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            logger.info("帮送、帮取直接输出......" + valueStr);
            params.put(name, valueStr);
        }
        logger.info("帮送、帮取params............" + params);

        try {
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("帮送、帮取trade_status.........." + trade_status);
            //异步通知ID
            String notify_id = request.getParameter("notify_id");
            logger.info("帮送、帮取notify_id............." + notify_id);
            //sign
            String sign = request.getParameter("sign");
            logger.info("帮送、帮取sign............." + sign);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            logger.info("帮送、帮取支付宝app............0");
            if (notify_id != "" && notify_id != null) { ////判断接受的post通知中有无notify_id，如果有则是异步通知。
                if (AlipayNotifyApp.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                {
                    logger.info("帮送、帮取支付宝app..............1");
                    if (AlipayNotifyApp.getSignVeryfy(params, sign))//使用支付宝公钥验签
                    {
                        logger.info("帮送、帮取支付宝app................2");
                        //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                        if (trade_status.equalsIgnoreCase("trade_success")) {
                            logger.info("帮送、帮取app支付宝成功进入回调页面...........post" + new Date());




                            int ret = orderPayService.updateOfSendOrderAlipayPaySuccess(out_trade_no,trade_no);
                            if(ret==1){
                                logger.info("帮送、帮取app支付宝订单状态修改成功.......");
                            }
                            else{
                                logger.info("帮送、帮取app支付宝订单状态修改失败.......");
                            }


                            //调试打印log
                            AlipayCoreApp.logResult("notify_url success!", "notify_url");

                        }
                        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        out.print("success");//请不要修改或删除

                        //调试打印log
                        //AlipayCore.logResult("notify_url success!","notify_url");
                    } else//验证签名失败
                    {
                        out.print("sign fail");
                    }
                } else//验证是否来自支付宝的通知失败
                {
                    out.print("response fail");
                }
            } else {
                out.print("no notify message");
            }

        } catch (Exception e) {
            logger.info("2");
        }
    }



    /**
     * 帮买App支付宝付款接口异步通知
     */
    @RequestMapping("buyOrderAliPayNotifyCallBack")
    @ApiIgnore
    public void buyOrderAliPayNotifyCallBack() {
        logger.info("帮买app支付宝.....init");
        logger.info("帮买app支付.............");
        PrintWriter out = null;
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        logger.info(requestParams + "requestParams..................");
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            logger.info("name............." + name);
            String[] values = (String[]) requestParams.get(name);
            logger.info("values............." + values);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            logger.info("直接输出......" + valueStr);
            params.put(name, valueStr);
        }
        logger.info("params............" + params);

        try {
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("trade_status.........." + trade_status);
            //异步通知ID
            String notify_id = request.getParameter("notify_id");
            logger.info("notify_id............." + notify_id);
            //sign
            String sign = request.getParameter("sign");
            logger.info("sign............." + sign);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            logger.info("帮买支付宝app............0");
            if (notify_id != "" && notify_id != null) { ////判断接受的post通知中有无notify_id，如果有则是异步通知。
                if (AlipayNotifyApp.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                {
                    logger.info("帮买支付宝app..............1");
                    if (AlipayNotifyApp.getSignVeryfy(params, sign))//使用支付宝公钥验签
                    {
                        logger.info("帮买支付宝app................2");
                        //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                        if (trade_status.equalsIgnoreCase("trade_success")) {
                            logger.info("帮买app支付宝成功进入回调页面...........post" + new Date());



                            Map<String,Long> orderMap=new HashMap<String,Long>();
                            int ret = orderPayService.updateOfBuyOrderAlipayPaySuccess(out_trade_no,trade_no,orderMap);
                            if(ret==1){
                                logger.info("帮买app支付宝订单状态修改成功.......");
                                //执行极光推送,提示支付成功
                                Long customerId= orderMap.get("customerId");


                                orderPayService.getJpushPayOrderSuccess(customerId);
                            }
                            else{
                                logger.info("帮买app支付宝订单状态修改失败.......");
                            }


                            //调试打印log
                            AlipayCoreApp.logResult("notify_url success!", "notify_url");

                        }
                        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        out.print("success");//请不要修改或删除

                        //调试打印log
                        //AlipayCore.logResult("notify_url success!","notify_url");
                    } else//验证签名失败
                    {
                        out.print("sign fail");
                    }
                } else//验证是否来自支付宝的通知失败
                {
                    out.print("response fail");
                }
            } else {
                out.print("no notify message");
            }

        } catch (Exception e) {
            logger.info("2");
        }
    }

    /**
     * 代排队App支付宝付款接口异步通知
     */
    @RequestMapping("lineupOrderAliPayNotifyCallBack")
    @ApiIgnore
    public void lineupOrderAliPayNotifyCallBack() {
        logger.info("代排队app支付宝.....init");
        logger.info("代排队app支付.............");
        PrintWriter out = null;
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        logger.info(requestParams + "requestParams..................");
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            logger.info("name............." + name);
            String[] values = (String[]) requestParams.get(name);
            logger.info("values............." + values);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            logger.info("代排队直接输出......" + valueStr);
            params.put(name, valueStr);
        }
        logger.info("代排队params............" + params);
        try {
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("代排队trade_status.........." + trade_status);
            //异步通知ID
            String notify_id = request.getParameter("notify_id");
            logger.info("代排队notify_id............." + notify_id);
            //sign
            String sign = request.getParameter("sign");
            logger.info("代排队sign............." + sign);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            logger.info("代排队支付宝app............0");
            if (notify_id != "" && notify_id != null) { ////判断接受的post通知中有无notify_id，如果有则是异步通知。
                if (AlipayNotifyApp.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                {
                    logger.info("代排队支付宝app..............1");
                    if (AlipayNotifyApp.getSignVeryfy(params, sign))//使用支付宝公钥验签
                    {
                        logger.info("代排队支付宝app................2");
                        //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
                        if (trade_status.equalsIgnoreCase("trade_success")) {
                            logger.info("代排队app支付宝成功进入回调页面...........post" + new Date());
                            int ret = orderPayService.updateOfLineupOrderAlipayPaySuccess(out_trade_no,trade_no);
                            if(ret==1){
                                logger.info("代排队app支付宝订单状态修改成功.......");
                            }
                            else{
                                logger.info("代排队app支付宝订单状态修改失败.......");
                            }
                            //调试打印log
                            AlipayCoreApp.logResult("notify_url success!", "notify_url");
                        }
                        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        out.print("success");//请不要修改或删除

                        //调试打印log
                        //AlipayCore.logResult("notify_url success!","notify_url");
                    } else//验证签名失败
                    {
                        out.print("sign fail");
                    }
                } else//验证是否来自支付宝的通知失败
                {
                    out.print("response fail");
                }
            } else {
                out.print("no notify message");
            }
        } catch (Exception e) {
            logger.info("2");
        }
    }


    /**
     * App支付宝充值接口异步通知
     */
    @RequestMapping("addBalanceAliPayNotifyCallBack")
    @ApiIgnore
    public void addBalanceAliPayNotifyCallBack() {
        logger.info("app支付宝.....init");
        logger.info("app支付.............");
        PrintWriter out = null;
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        logger.info(requestParams + "requestParams..................");
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            logger.info("name............." + name);
            String[] values = (String[]) requestParams.get(name);
            logger.info("values............." + values);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            logger.info("直接输出......" + valueStr);
            params.put(name, valueStr);
        }
        logger.info("params............" + params);

        try {
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("trade_status.........." + trade_status);
            //异步通知ID
            String notify_id = request.getParameter("notify_id");
            logger.info("notify_id............." + notify_id);
            //sign
            String sign = request.getParameter("sign");
            logger.info("sign............." + sign);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            logger.info("支付宝app............0");
            if (notify_id != "" && notify_id != null) { ////判断接受的post通知中有无notify_id，如果有则是异步通知。
                if (AlipayNotifyApp.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                {
                    logger.info("支付宝app..............1");
                    if (AlipayNotifyApp.getSignVeryfy(params, sign))//使用支付宝公钥验签
                    {
                        logger.info("支付宝app................2");
                        //
                        if (trade_status.equalsIgnoreCase("trade_success")) {
                            logger.info("app支付宝成功进入回调页面...........post" + new Date());


                            String capitalLogId= out_trade_no.replace("cz","");
                            int ret = customerService.addBalanceWithAliPay(Long.parseLong(capitalLogId),trade_no);
                            if(ret==1){
                                logger.info("app支付宝充值成功.......");
                            }
                            else{
                                logger.info("app支付宝充值失败.......");
                            }


                            //调试打印log
                            AlipayCoreApp.logResult("notify_url success!", "notify_url");

                        }
                        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        out.print("success");//请不要修改或删除

                        //调试打印log
                        //AlipayCore.logResult("notify_url success!","notify_url");
                    } else//验证签名失败
                    {
                        out.print("sign fail");
                    }
                } else//验证是否来自支付宝的通知失败
                {
                    out.print("response fail");
                }
            } else {
                out.print("no notify message");
            }

        } catch (Exception e) {
            logger.info("2");
        }
    }


    /**
     * App支付宝付小费接口异步通知
     */
    @RequestMapping("addTipsAliPayNotifyCallBack")
    @ApiIgnore
    public void addTipsAliPayNotifyCallBack() {
        logger.info("app支付宝.....init");
        logger.info("app支付.............");
        PrintWriter out = null;
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        logger.info(requestParams + "requestParams..................");
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            logger.info("name............." + name);
            String[] values = (String[]) requestParams.get(name);
            logger.info("values............." + values);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            logger.info("直接输出......" + valueStr);
            params.put(name, valueStr);
        }
        logger.info("params............" + params);

        try {
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("trade_status.........." + trade_status);
            //异步通知ID
            String notify_id = request.getParameter("notify_id");
            logger.info("notify_id............." + notify_id);
            //sign
            String sign = request.getParameter("sign");
            logger.info("sign............." + sign);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            logger.info("支付宝app............0");
            if (notify_id != "" && notify_id != null) { ////判断接受的post通知中有无notify_id，如果有则是异步通知。
                if (AlipayNotifyApp.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                {
                    logger.info("支付宝app..............1");
                    if (AlipayNotifyApp.getSignVeryfy(params, sign))//使用支付宝公钥验签
                    {
                        logger.info("支付宝app................2");
                        //
                        if (trade_status.equalsIgnoreCase("trade_success")) {
                            logger.info("app支付宝成功进入回调页面...........post" + new Date());


                            String capitalLogId= out_trade_no.replace("xf","");
                            int ret = orderPayService.addTipsWithAliPay(Long.parseLong(capitalLogId),trade_no);
                            if(ret==1){
                                logger.info("app支付宝付小费成功.......");
                            }
                            else{
                                logger.info("app支付宝付小费失败.......");
                            }


                            //调试打印log
                            AlipayCoreApp.logResult("notify_url success!", "notify_url");

                        }
                        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        out.print("success");//请不要修改或删除

                        //调试打印log
                        //AlipayCore.logResult("notify_url success!","notify_url");
                    } else//验证签名失败
                    {
                        out.print("sign fail");
                    }
                } else//验证是否来自支付宝的通知失败
                {
                    out.print("response fail");
                }
            } else {
                out.print("no notify message");
            }

        } catch (Exception e) {
            logger.info("2");
        }
    }


    /**
     * App支付宝付商品费接口异步通知
     */
    @RequestMapping("buyGoodsAlipayNotifyCallBack")
    @ApiIgnore
    public void buyGoodsAlipayNotifyCallBack() {
        logger.info("app支付宝.....init");
        logger.info("app支付.............");
        PrintWriter out = null;
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        logger.info(requestParams + "requestParams..................");
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            logger.info("name............." + name);
            String[] values = (String[]) requestParams.get(name);
            logger.info("values............." + values);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            logger.info("直接输出......" + valueStr);
            params.put(name, valueStr);
        }
        logger.info("params............" + params);

        try {
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("trade_status.........." + trade_status);
            //异步通知ID
            String notify_id = request.getParameter("notify_id");
            logger.info("notify_id............." + notify_id);
            //sign
            String sign = request.getParameter("sign");
            logger.info("sign............." + sign);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            logger.info("支付宝app............0");
            if (notify_id != "" && notify_id != null) { ////判断接受的post通知中有无notify_id，如果有则是异步通知。
                if (AlipayNotifyApp.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                {
                    logger.info("支付宝app..............1");
                    if (AlipayNotifyApp.getSignVeryfy(params, sign))//使用支付宝公钥验签
                    {
                        logger.info("支付宝app................2");
                        //
                        if (trade_status.equalsIgnoreCase("trade_success")) {
                            logger.info("帮买app支付宝付商品费成功进入回调页面...........post" + new Date());


                            String capitalLogId= out_trade_no.replace("xf","");
                            int ret = orderPayService.updateOfBuyGoodsAliPayMoneySuccess(Long.parseLong(capitalLogId),trade_no);
                            if(ret==1){
                                logger.info("帮买app支付宝付商品费成功.......");
                            }
                            else{
                                logger.info("帮买app支付宝付商品费失败.......");
                            }
                            //调试打印log
                            AlipayCoreApp.logResult("notify_url success!", "notify_url");
                        }
                        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        out.print("success");//请不要修改或删除

                        //调试打印log
                        //AlipayCore.logResult("notify_url success!","notify_url");
                    } else//验证签名失败
                    {
                        out.print("sign fail");
                    }
                } else//验证是否来自支付宝的通知失败
                {
                    out.print("response fail");
                }
            } else {
                out.print("no notify message");
            }

        } catch (Exception e) {
            logger.info("2");
        }
    }

    /**
     * App支付宝支付排队加时费接口异步通知
     */
    @RequestMapping("lineupAddPayAlipayNotifyCallBack")
    @ApiIgnore
    public void lineupAddPayAlipayNotifyCallBack() {
        logger.info("app支付宝.....init");
        logger.info("app支付.............");
        PrintWriter out = null;
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        logger.info(requestParams + "requestParams..................");
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            logger.info("name............." + name);
            String[] values = (String[]) requestParams.get(name);
            logger.info("values............." + values);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            logger.info("直接输出......" + valueStr);
            params.put(name, valueStr);
        }
        logger.info("params............" + params);
        try {
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
            logger.info("trade_status.........." + trade_status);
            //异步通知ID
            String notify_id = request.getParameter("notify_id");
            logger.info("notify_id............." + notify_id);
            //sign
            String sign = request.getParameter("sign");
            logger.info("sign............." + sign);
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
            logger.info("支付宝app............0");
            if (notify_id != "" && notify_id != null) { ////判断接受的post通知中有无notify_id，如果有则是异步通知。
                if (AlipayNotifyApp.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
                {
                    logger.info("支付宝app..............1");
                    if (AlipayNotifyApp.getSignVeryfy(params, sign))//使用支付宝公钥验签
                    {
                        logger.info("支付宝app................2");
                        //
                        if (trade_status.equalsIgnoreCase("trade_success")) {
                            logger.info("代排队app支付宝支付加时费成功进入回调页面...........post" + new Date());
                            String capitalLogId= out_trade_no.replace("xf","");
                            int ret = orderPayService.updateOfLineupAddPayAliPayMoneySuccess(Long.parseLong(capitalLogId),trade_no);
                            if(ret==1){
                                logger.info("代排队app支付宝支付加时费成功.......");
                            }
                            else{
                                logger.info("代排队app支付宝支付加时费失败.......");
                            }
                            //调试打印log
                            AlipayCoreApp.logResult("notify_url success!", "notify_url");
                        }
                        //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                        out.print("success");//请不要修改或删除
                        //调试打印log
                        //AlipayCore.logResult("notify_url success!","notify_url");
                    }else{
                        //验证签名失败
                        out.print("sign fail");
                    }
                } else {
                    //验证是否来自支付宝的通知失败
                    out.print("response fail");
                }
            } else {
                out.print("no notify message");
            }

        } catch (Exception e) {
            logger.info("2");
        }
    }


    //    /**
    //     * 退款异步通知
    //     */
    //    /**
    //     * 支付宝异步通知
    //     */
    //    @RequestMapping(value="refundNotifyCallBack")
    //    public void refundNotifyCallBack(){
    //        logger.info("....................refundNotifyCallBack method is run......................");
    //        PrintWriter out=null;
    //        //获取支付宝POST过来反馈信息
    //        Map<String,String> params = new HashMap<String,String>();
    //        Map requestParams = request.getParameterMap();
    //        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
    //            String name = (String) iter.next();
    //            String[] values = (String[]) requestParams.get(name);
    //            String valueStr = "";
    //            for (int i = 0; i < values.length; i++) {
    //                valueStr = (i == values.length - 1) ? valueStr + values[i]
    //                        : valueStr + values[i] + ",";
    //                logger.info("*****************"+ valueStr +"********************");
    //            }
    //            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
    //            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
    //            params.put(name, valueStr);
    //        }
    //
    //        try {
    //            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
    //            //批次号
    //
    //            String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"),"UTF-8");
    //            logger.info("batch_no" + batch_no);
    //            //批量退款数据中转账成功的笔数
    //
    //
    //            String success_num = new String(request.getParameter("success_num").getBytes("ISO-8859-1"),"UTF-8");
    //
    //
    //            //批量退款数据中的详细信息
    //            String result_details = new String(request.getParameter("result_details").getBytes("ISO-8859-1"),"UTF-8");
    //            logger.info("result_details" + result_details);
    //            out=response.getWriter();
    //            if(AlipayNotify.verify(params)){//验证成功
    //                logger.info("*******************************AlipayNotify***********************");
    //                //////////////////////////////////////////////////////////////////////////////////////////
    //                //请在这里加上商户的业务逻辑程序代码
    //                RejectedGoods rejectedGoods = new RejectedGoods();
    //                //当同意退款时将退款批次号batch_no作为一个标志插入到退款表。然后根据batch_no查出退款记录，再根据退款记录里的商品id和订单id去修改订单和销量表
    //                rejectedGoods.setData7(batch_no);
    //                rejectedGoods.setIsAgree(1);
    //                int flag = rejectedGoodsService.update(request,rejectedGoods);
    //                logger.info("*******************************update rejectedGoods is run************************************");
    //                if(flag>1){
    //                    out.print("success");	//请不要修改或删除
    //                }else{
    //                    out.print("fail");
    //                }
    //
    //
    //            }else{//验证失败
    //                out.print("fail");
    //            }
    //
    //
    //        } catch (UnsupportedEncodingException e) {
    //            e.printStackTrace();
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
    //
    //
    //    }


}