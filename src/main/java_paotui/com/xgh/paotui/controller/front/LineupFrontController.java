package com.xgh.paotui.controller.front;


import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.alipayApp.config.AlipayConfigApp;
import com.xgh.paotui.alipayApp.util.AlipayCoreApp;
import com.xgh.paotui.dao.IOrderGoodsDao;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.services.*;
import com.xgh.paotui.weixin.pay.WeiChartConfig;
import com.xgh.paotui.weixin.pay.WeiChartUtil;
import com.xgh.util.DictUtils;
import com.xgh.util.JSONUtil;
import com.xgh.util.PaotuiUtil;
import com.xgh.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lcp on 2017/4/1.
 */
@Controller
@Api(value="代排队API")
@RequestMapping(value = "paotui/front/v1/lineup/")
public class LineupFrontController extends BaseController {

    @Autowired
    protected IErrandsFeeService errandsFeeService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderGoodsDao orderGoodsDao;

    @Autowired
    protected IOrderPositionPathService orderPositionPathService;

    @Autowired
    protected ICustomerService customerService;

    @Autowired
    protected IOrderLineupService orderLineupService;

    @Autowired
    protected IOpenCityService openCityService;

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;

    @Autowired
    protected IOrderPayService orderPayService;

    @ApiParam
    protected ICapitalLogService capitalLogService;


    private static Logger logger = Logger.getLogger(LineupFrontController.class);

    @ResponseBody
    @RequestMapping(value = "addLineupOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "提交代排队订单信息", httpMethod = "POST", notes = "APP端使用")
    public String addBuyOrder(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "下单工具(2.android 3.ios)") @RequestParam(value = "shippingFrom") int shippingFrom,
            @ApiParam(value = "下单城市id") @RequestParam(value = "orderCity") long orderCity,
            @ApiParam(value = "原始跑腿费") @RequestParam(value = "originalFreight") BigDecimal originalFreight,
            @ApiParam(value = "预约类型(1.立即订单 2.预约订单)") @RequestParam(value = "bookingType") int bookingType,
            @ApiParam(value = "预约时间") @RequestParam(required = false, value = "bookingTime") String bookingTime,
            @ApiParam(value = "排队地点定位地址") @RequestParam(value = "locationAddress") String locationAddress,
            @ApiParam(value = "排队地点定位地址概述") @RequestParam(value = "locationAddressName") String locationAddressName,
            @ApiParam(value = "排队地点详细地址") @RequestParam(value = "detailAddress") String detailAddress,
            @ApiParam(value = "排队地经度") @RequestParam(value = "longitude") Double longitude,
            @ApiParam(value = "排队地纬度") @RequestParam(value = "latitude") Double latitude,
            @ApiParam(value = "排队要求") @RequestParam(required = false, value = "lineupRequire") String lineupRequire,
            @ApiParam(value = "排队时长") @RequestParam(value = "lineupDuration") int lineupDuration,
            @ApiParam(value = "联系电话") @RequestParam(value = "lineupPhone") String lineupPhone) {
        long customerId=getCustomerIdByToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Order order = new Order();
        //订单类型
        order.setOrderType(4);
        //订单编号
        String orderNo = orderService.updateAndGetOrderNoOfOpenCity(orderCity);
        order.setOrderNo(orderNo);

        Customer customer = customerService.get(customerId);
        order.setCustomerId(customerId);
        order.setCustomerName(customer.getRealName());
        order.setCustomerPhone(customer.getLoginName());
        order.setShippingFrom(shippingFrom);
        order.setLongitude(longitude);
        order.setLatitude(latitude);
        order.setOriginalFreight(originalFreight);
        //下单城市信息
        OpenCity openCity = openCityService.get(orderCity);
        order.setOrderCity(orderCity);
        order.setOrderCityName(openCity.getCityName());
        order.setBookingType(bookingType);
        if (bookingType == 2) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setBookingTime(sdf.parse(bookingTime));
            } catch (ParseException p) {
                return JSONUtil.getJson("时间格式转换错误,时间输入不正确");
            }
        }
        order.setCreateTime(new Date());
        order.setOrderStatus(1);
        order.setEvaluationStatus(3);
        order.setStatus(1);
        OrderLineup orderLineup = new OrderLineup();
        orderLineup.setLocationAddress(locationAddress);
        orderLineup.setLocationAddressName(locationAddressName);
        orderLineup.setDetailAddress(detailAddress);
        orderLineup.setLongitude(longitude);
        orderLineup.setLatitude(latitude);
        orderLineup.setLineupRequire(lineupRequire);
        orderLineup.setLineupDuration(lineupDuration);
        orderLineup.setLineupPhone(lineupPhone);
        orderLineup.setLineupRealDuration(lineupDuration);
        orderLineup.setLineupRealMoney(originalFreight);
        orderLineup.setStatus(1);
        Map<String, Object> lineUpMap = orderService.addLineupOrder(order, orderLineup);
        return JSONUtil.getJson(lineUpMap);
    }

    @ResponseBody
    @RequestMapping(value = "getLineupOrderState", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取排队地位置,跑客位置", httpMethod = "GET", notes = "APP端使用")
    public String getLineupOrderState(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order = orderService.get(orderId);
        if (order!=null&&order.getId()>0) {
            if (order.getOrderType()==4){
                Map<String, Object> map = new HashMap<String, Object>();
                OrderLineup orderLineup = orderLineupService.getLineup(orderId);
                if (orderLineup != null && orderLineup.getId() > 0) {
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    //排队地经/纬度
                    String longitude = StringUtil.convertNullToEmpty(orderLineup.getLongitude());
                    String latitude = StringUtil.convertNullToEmpty(orderLineup.getLatitude());
                    map1.put("longitude", longitude);
                    map1.put("latitude", latitude);
                    map.put("LineupReceiveAddress", map1);
                } else {
                    map.put("LineupReceiveAddress", "没有获取到排队地位置");
                }
                long deliveryManId = order.getDeliveryManId();
                int orderStatus = order.getOrderStatus();
                if (!"".equals(deliveryManId)&&deliveryManId>0&&orderStatus !=5) {
                    DeliveryManState deliveryManState = deliveryManStateService.get(deliveryManId);
                    if (deliveryManState != null && deliveryManState.getId() > 0) {
                        Double deliveryManLongitude = deliveryManState.getNowLongitude();
                        Double deliveryManLatitude = deliveryManState.getNowLatitude();
                        Map<String, Object> map2 = new HashMap<String, Object>();
                        map2.put("longitude", deliveryManLongitude);
                        map2.put("latitude", deliveryManLatitude);
                        map.put("deliveryManAddress", map2);
                    } else {
                        map.put("deliveryManAddress", "没有获取到跑客位置");
                    }
                }
                resultMap = getResultMap("1", "获取位置成功", map);
            }else {
                resultMap = getResultMap("-1", "订单不是代排队订单");
            }
        }else {
            resultMap = getResultMap("0", "获取订单失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getTimeAndFreight", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取排队时长，计算跑腿费", httpMethod = "POST", notes = "APP端使用")
    public String getTimeAndFreight(
            @ApiParam(value = "排队时长") @RequestParam(value = "lineupDuration") int lineupDuration,
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") long openCityId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        //跑腿费
        BigDecimal freight;
        //获取跑腿费收费规则
        resultMap.put("openCityId", openCityId);
        List<ErrandsFee> errandsFee = errandsFeeService.getList(resultMap);
        if (errandsFee != null && errandsFee.size() > 0) {
            ErrandsFee errandsFee_ = errandsFee.get(0);
            //排队起步时间内费用
            freight = errandsFee_.getQueueStartFee();
            //排队起步时间之外的费用
            if (lineupDuration > errandsFee_.getQueueStartTime()) {
                //起步时间以外的剩余时间
                int theRestTime = lineupDuration - errandsFee_.getQueueStartTime();
                int doubleTime = theRestTime % errandsFee_.getQueueExceedTime() == 0 ? theRestTime / errandsFee_.getQueueExceedTime() : theRestTime / errandsFee_.getQueueExceedTime() + 1;
                freight = freight.add(errandsFee_.getQueueExceedFee().multiply(new BigDecimal(doubleTime)));
            }
            map.put("freight", freight);
            resultMap = getResultMap("1", "获取跑腿费成功", map);
        } else {
            resultMap = getResultMap("0", "获取跑腿费参数失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getLineupExceedFee", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "计算加时费用", httpMethod = "POST", notes = "APP端使用")
    public String getLineupExceedFee(
            @ApiParam(value = "加时时长") @RequestParam(value = "addPayDuration") int addPayDuration,
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") long openCityId){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        BigDecimal freight;
        resultMap.put("openCityId", openCityId);
        List<ErrandsFee> errandsFee = errandsFeeService.getList(resultMap);
        if (errandsFee != null && errandsFee.size() > 0) {
            ErrandsFee errandsFee_ = errandsFee.get(0);
            int DoubleTime =addPayDuration % errandsFee_.getQueueExceedTime() == 0 ?addPayDuration / errandsFee_.getQueueExceedTime() : addPayDuration / errandsFee_.getQueueStartTime() + 1;
            freight = errandsFee_.getQueueExceedFee().multiply(new BigDecimal(DoubleTime));
            map.put("freight", freight);
            resultMap = getResultMap("1", "获取加时费用成功", map);
        }else {
            resultMap = getResultMap("0", "没有获取到费用规则");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getLineupAddPayMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取排队加时是否支付费用", httpMethod = "POST", notes = "APP端使用")
    public String getLineupAddPayMoney(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        OrderLineup orderLineup=orderLineupService.getLineup(orderId);
        if (orderLineup != null && orderLineup.getId() > 0) {
            //排队加时时长
            int addPayDuration = orderLineup.getAddPayDuration();
            if (addPayDuration > 0) {
                //排队加时支付的金额
                String addPayMoney = StringUtil.convertNullToEmpty(orderLineup.getAddPayMoney());
                if (addPayMoney != null && addPayMoney != "") {
                    map.put("isAddPay", "已支付排队加时费");
                    map.put("addPayMoney", addPayMoney);
                    map.put("addPayDuration", addPayDuration);
                } else {
                    map.put("isAddPay", "未支付排队加时费");
                }
            } else {
                map.put("isAddTime", "未加时");
            }
            resultMap = getResultMap("1", "获取排队加时是否已支付成功", map);
        } else {
            resultMap = getResultMap("0", "没有获取到订单");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "lineupAlipayMoneyApp", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "代排队支付宝支付", httpMethod = "POST", notes = "APP端使用")
    public String lineupAlipayMoneyApp(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="是否使用优惠券1、使用；0：未使用") @RequestParam(value = "isUseCoupons") int isUseCoupons,
            @ApiParam(value="优惠券id") @RequestParam(required = false,value = "couponId") long couponId,
            @ApiParam(value="还需支付金额") @RequestParam(value = "payMoney") BigDecimal payMoney){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        if(isUseCoupons==0){
            couponId=0;
        }
        int ret=orderPayService.updateOfLineupAliPayMoney( customerId, orderId, isUseCoupons, couponId, payMoney);
        if(ret==1){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",orderId);
            map.put("orderStatus",1);//等待支付

            List<Map<String, Object>> orderMaps=orderService.getOrderInfoList(map);
            if(orderMaps!=null && orderMaps.size()>0){
                Map<String, Object> orderMap=orderMaps.get(0);
                String body="还需支付的跑腿费";
                BigDecimal total_fee=new BigDecimal(StringUtil.convertNullToEmpty(orderMap.get("actualFreight")));
                String orderNo=String.valueOf(orderMap.get("orderNo"));
                String[] parameters = {
                        "partner=\"" + AlipayConfigApp.partner + "\"",
                        "seller_id=\"" + AlipayConfigApp.partner + "\"",
                        "out_trade_no=\"" + orderNo + "\"",
                        "subject=\"跑腿放上去\"",
                        "body=\"" + body + "\"",
                        "total_fee=\"" + total_fee + "\"",
                        "notify_url=\"http://paotui.fangshangqu.com/paotui/front/v1/Alipay/lineupOrderAliPayNotifyCallBack.htm\"",
                        "service=\"mobile.securitypay.pay\"",
                        "payment_type=\"1\"",
                        "input_charset=\"utf-8\""
                };
                String result = AlipayCoreApp.signAllString(parameters);
                logger.info("result.........." + result);
                resultMap=getResultMap("1","代排队支付宝付款信息串成功！",result);
            }else{
                resultMap=getResultMap("0","获取订单信息失败");
            }
        }else{
            resultMap = getResultMap("0","修改订单优惠劵使用状态失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "lineupWeixinMoneyApp", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "代排队微信支付跑腿费", httpMethod = "POST", notes = "openid不为空时，表示公众号支付；为空时，表示APP端支付")
    public String lineupWeixinMoneyApp(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="是否使用优惠券1、使用；0：未使用") @RequestParam(value = "isUseCoupons") int isUseCoupons,
            @ApiParam(value="优惠券id") @RequestParam(required = false,value = "couponId") long couponId,
            @ApiParam(value="还需支付金额") @RequestParam(value = "payMoney") BigDecimal payMoney,
            @ApiParam(value = "交易类型，取值为：JSAPI或APP") @RequestParam(value = "trade_type") String trade_type) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Customer customer = customerService.get(customerId);
        if(isUseCoupons==0){
            couponId=0;
        }
        //更新微信支付订单状态
        int ret=orderPayService.updateOfLineupWeixinPayMoney( customerId, orderId, isUseCoupons, couponId, payMoney);
        if(ret==1){
            Order order=orderService.get(orderId);
            if (order!=null&&order.getId()>0){
                String openid="";
                if("JSAPI".equals(trade_type)){
                    openid=customer.getToken();
                }
                /* 添加资金日志 */
                CapitalLog capitalLog=new CapitalLog();
                capitalLog.setAccountType(1);
                capitalLog.setAccountId(customerId);
                capitalLog.setLoginName(customer.getLoginName());
                capitalLog.setRealName(customer.getRealName());
                capitalLog.setType(order.getOrderType());
                capitalLog.setOrderId(orderId);
                capitalLog.setOrderNo(order.getOrderNo());
                capitalLog.setTradeFrom(2);
                capitalLog.setCapitalChangeReason("微信支付跑腿费");
                capitalLog.setChangeMoney(payMoney);
                capitalLog.setCreateDate(new Date());
                capitalLog.setStatus(1);
                capitalLogService.add(capitalLog);
                String total_fee=String.valueOf(order.getActualFreight());
                String notify_url="http://paotui.fangshangqu.com/paotui/front/v1/WeixinPay/lineupOrderWeixinNotifyCallBack.htm";
                Map<String, String> weixinPayMap= WeiChartUtil.getPreyId(order.getOrderNo(),"跑腿费",WeiChartUtil.changeToFen(total_fee),notify_url,openid);
                if(weixinPayMap!=null){
                    String retSign = weixinPayMap.get("sign");
                    weixinPayMap.remove("sign");
                    String rightSing = WeiChartUtil.getSign(weixinPayMap);
                    if(rightSing.equals(retSign)){
                        if("JSAPI".equals(trade_type)){
                            Map<String, String> reqMap = new HashMap<String, String>();
                            reqMap.put("appId", WeiChartConfig.AppId);
                            reqMap.put("timeStamp", String.valueOf(new Date().getTime()));
                            reqMap.put("nonceStr", WeiChartUtil.getRandomString());
                            reqMap.put("package", "prepay_id="+weixinPayMap.get("prepay_id"));
                            reqMap.put("signType", "MD5");
                            reqMap.put("sign", WeiChartUtil.getSign(reqMap));
                            resultMap = getResultMap("1","返回微信付款信息串成功！",reqMap);
                        } else{
                            resultMap = getResultMap("1","返回微信付款信息串成功！",weixinPayMap);
                        }
                    }else{
                        resultMap=getResultMap("0","返回帮买微信付款信息串失败！",weixinPayMap);
                    }
                }else{
                    resultMap=getResultMap("0","生成预支付交易单失败");
                }
            }else {
                resultMap=getResultMap("0","获取订单失败");
            }
        }else{
            resultMap = getResultMap("0","修改订单优惠劵使用状态失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "lineupAddPayAlipayMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "代排队支付宝支付加时费", httpMethod = "POST", notes = "帮买支付宝支付，返回支付宝付款信息串")
    public String lineupAddPayAlipayMoney(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="加时时长") @RequestParam(value = "addPayDuration") int addPayDuration,
            @ApiParam(value="加时费") @RequestParam(value = "addPayMoney") BigDecimal addPayMoney){
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Customer customer = customerService.get(customerId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",orderId);
        map.put("customerId",customerId);
        List<Map<String, Object>> orderMaps=orderService.getOrderInfoList(map);
        if(orderMaps!=null && orderMaps.size()>0) {
            Map<String, Object> orderMap = orderMaps.get(0);
            String addPayTransactionId =StringUtil.convertNullToEmpty(orderMap.get("addPayTransactionId"));
            if(StringUtil.isEmpty(addPayTransactionId)){
                OrderLineup orderLineup = orderLineupService.getLineup(orderId);
                orderLineup.setAddPayDuration(addPayDuration);
                orderLineup.setLineupRealDuration(orderLineup.getLineupDuration()+addPayDuration);
                orderLineupService.update(orderLineup);
                CapitalLog capitalLog = new CapitalLog();
                capitalLog.setAccountType(1);
                capitalLog.setAccountId(customer.getId());
                capitalLog.setLoginName(customer.getLoginName());
                capitalLog.setRealName(customer.getRealName());
                capitalLog.setType(4);
                capitalLog.setOrderId(orderId);
                capitalLog.setOrderNo((String)orderMap.get("orderNo"));
                capitalLog.setTradeFrom(3);
                capitalLog.setCapitalChangeReason("支付排队加时费");
                capitalLog.setChangeMoney(addPayMoney);
                capitalLog.setCreateDate(new Date());
                capitalLog.setStatus(1);
                capitalLogService.add(capitalLog);
                String[] parameters = {
                        "partner=\"" + AlipayConfigApp.partner + "\"",
                        "seller_id=\"" + AlipayConfigApp.partner + "\"",
                        "out_trade_no=\"" + "gd"+capitalLog.getId() + "\"",
                        "subject=\"跑腿放上去\"",
                        "body=\"排队加时费\"",
                        "total_fee=\"" + addPayMoney + "\"",
                        "notify_url=\"http://paotui.fangshangqu.com/paotui/front/v1/Alipay/lineupAddPayAlipayNotifyCallBack.htm\"",
                        "service=\"mobile.securitypay.pay\"",
                        "payment_type=\"1\"",
                        "input_charset=\"utf-8\""
                };
                String result = AlipayCoreApp.signAllString(parameters);
                logger.info("result.........." + result);
                resultMap=getResultMap("1","生成支付宝支付加时费信息串成功！",result);
            }else{
                resultMap=getResultMap("0","加时费已支付，生成支付宝支付加时费信息串失败！");
            }
        }else{
            resultMap=getResultMap("-1","订单不存在！");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "lineupAddPayWeixinPayMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "代排队微信支付加时费", httpMethod = "POST", notes = "app、微信通用")
    public String lineupAddPayWeixinPayMoney(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="加时时长") @RequestParam(value = "addPayDuration") int addPayDuration,
            @ApiParam(value="加时费") @RequestParam(value = "addPayMoney") BigDecimal addPayMoney,
            @ApiParam(value = "交易类型，取值为：JSAPI或APP") @RequestParam(value = "trade_type") String trade_type) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Customer customer = customerService.get(customerId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",orderId);
        map.put("customerId",customerId);
        List<Map<String, Object>> orderMaps=orderService.getOrderInfoList(map);
        if(orderMaps!=null && orderMaps.size()>0) {
            Map<String, Object> orderMap = orderMaps.get(0);
            String addPayTransactionId =(String)orderMap.get("addPayTransactionId");
            if(StringUtil.isEmpty(addPayTransactionId) ){
                OrderLineup orderLineup = orderLineupService.getLineup(orderId);
                orderLineup.setAddPayDuration(addPayDuration);
                orderLineup.setLineupRealDuration(orderLineup.getLineupDuration()+addPayDuration);
                orderLineupService.update(orderLineup);
                CapitalLog capitalLog = new CapitalLog();
                capitalLog.setAccountType(1);
                capitalLog.setAccountId(customer.getId());
                capitalLog.setLoginName(customer.getLoginName());
                capitalLog.setRealName(customer.getRealName());
                capitalLog.setType(4);
                capitalLog.setOrderId(orderId);
                capitalLog.setOrderNo((String)orderMap.get("orderNo"));
                capitalLog.setTradeFrom(2);
                capitalLog.setCapitalChangeReason("支付排队加时费");
                capitalLog.setChangeMoney(addPayMoney);
                capitalLog.setCreateDate(new Date());
                capitalLog.setStatus(1);
                capitalLogService.save(capitalLog);
                String total_fee=String.valueOf(addPayMoney);
                String goodsBody="排队加时费";
                String openid="";
                if("JSAPI".equals(trade_type)){
                    openid=customer.getToken();
                }
                String notify_url= PaotuiUtil.PAOTUI_URL+"paotui/front/v1/WeixinPay/lineupAddPayWeixinNotifyCallBack.do";
                Map<String, String> weixinPayMap= WeiChartUtil.getPreyId("gd"+capitalLog.getId(),goodsBody,WeiChartUtil.changeToFen(total_fee), notify_url,openid);
                if(weixinPayMap!=null){
                    String retSign = weixinPayMap.get("sign");
                    weixinPayMap.remove("sign");
                    String rightSing = WeiChartUtil.getSign(weixinPayMap);
                    if(rightSing.equals(retSign)){
                        resultMap=getResultMap("1","返回代排队微信支付加时费信息串成功！",weixinPayMap);
                    }else{
                        resultMap=getResultMap("0","返回代排队微信支付加时费信息串失败！");
                    }
                }else{
                    resultMap=getResultMap("0","返回代排队微信支付加时费信息串失败！");
                }
            }else{
                resultMap=getResultMap("0","排队加时费已支付，生成代排队微信支付加时费信息串失败！");
            }
        }else{
            resultMap=getResultMap("0","订单不存在！");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "checkCancelBuyOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "检查代排队是否可以直接取消订单", httpMethod = "POST", notes = "APP端使用。resultData 0：订单不能取消，1：可以直接取消；2：取消订单将扣除5元跑腿费")
    public String checkCancelBuyOrder(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Order order = orderService.get(orderId);
        if(order!=null&&order.getId()>0){
            if(order.getCustomerId()!=customerId){
                if(order.getOrderStatus()==1 || order.getOrderStatus()==2){
                    resultMap= getResultMap("1","订单可以直接取消");
                }
                //如果跑客没有到达，可以直接取消订单
                else if(order.getOrderStatus()==3){
                    if(order.getActionStep()< 2){
                        resultMap = getResultMap("1","订单可以直接取消");
                    }
                    //跑客出发了，没有开始计时排队，取消订单要扣费
                    else if(order.getActionStep()<4){
                        resultMap = getResultMap("2","取消订单将扣除5元跑腿费");
                    }
                    //跑客已开始排队，不能取消
                    else{
                        resultMap = getResultMap("0","订单不能取消");
                    }
                }
            }else {
                resultMap = getResultMap("-3","当前用户与下单用户不一致");
            }
        }else {
            resultMap = getResultMap("-2","获取订单失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "cancelLineupOrderWithReason", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮买填写取消原因后，取消订单", httpMethod = "POST", notes = "APP端使用")
    public String cancelLineupOrderWithReason(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "取消原因值") @RequestParam(required = false,value = "cancelReasonValue") String cancelReasonValue,
            @ApiParam(value = "填写的取消原因") @RequestParam(required = false,value = "cancelReasonOfInput") String cancelReasonOfInput){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        String cancelReason= DictUtils.getDictLabel(cancelReasonValue,"paotui_cancel_reason","");
        cancelReason+= StringUtil.convertNullToEmpty(cancelReasonOfInput);
        int flag= orderPayService.updateOfCancelLineupOrder(orderId,cancelReason);
        if(flag>0){
            resultMap = getResultMap("1","订单取消成功，如已支付系统将在5个工作日内退款");
        }else{
            resultMap = getResultMap("0","订单取消失败");
        }
        return JSONUtil.getJson(resultMap);
    }

}