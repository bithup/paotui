package com.xgh.paotui.controller.front;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.alipayApp.config.AlipayConfigApp;
import com.xgh.paotui.alipayApp.util.AlipayCoreApp;
import com.xgh.paotui.entity.CapitalLog;
import com.xgh.paotui.entity.Customer;
import com.xgh.paotui.entity.DeliveryManState;
import com.xgh.paotui.services.*;
import com.xgh.paotui.weixin.pay.WeiChartConfig;
import com.xgh.paotui.weixin.pay.WeiChartUtil;
import com.xgh.util.JSONUtil;
import com.xgh.util.MapUtil;
import com.xgh.util.PaotuiUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

@Controller
@Api(value="用户端通用接口API")
@RequestMapping(value = "paotui/front/v1/common/")
public class ComnonFrontController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(ComnonFrontController.class);

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderPayService orderPayService;

    @Autowired
    protected ICustomerService customerService;

    @Autowired
    protected ICapitalLogService capitalLogService;

    @ResponseBody
    @RequestMapping(value = "getNearbyDeliveryManInfo", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获得附近跑客数量及坐标", httpMethod = "GET", notes = "获得附近跑客数量及坐标")
    public String getNearbyDeliveryManInfo(
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") int openCityId,
            @ApiParam(value = "当前经度") @RequestParam(value = "longitude") String longitude,
            @ApiParam(value = "当前纬度") @RequestParam(value = "latitude") String latitude) {

        Map<String, Object> retMap=new HashMap<String, Object>();

        Map<String, Object> map=new HashMap<String, Object>();
        map.put("openCityId",openCityId);
        //刚开始人员少的情况下，显示手工的跑腿师傅，后续只显示开工的师傅
        //map.put("workStatus",1);
        List<DeliveryManState>  deliveryManStates=deliveryManStateService.getList(map);
        //附近的跑腿师傅
        List<Map<String, Object>>  nearbyDeliveryManMaps=new ArrayList<Map<String, Object>>();
        for(int i=0;i<deliveryManStates.size();i++){
            DeliveryManState data=deliveryManStates.get(i);
            double distance=MapUtil.GetDistance(Double.parseDouble(latitude),Double.parseDouble(longitude),data.getNowLatitude(),data.getNowLongitude());
            //暂时计算10000米内的跑腿师傅
            if(distance<10000){
                Map<String, Object> nearbyDeliveryManMap=new HashMap<String, Object>();
                nearbyDeliveryManMap.put("deliverManId",data.getDeliverManId());
                nearbyDeliveryManMap.put("nowLatitude",data.getNowLatitude());
                nearbyDeliveryManMap.put("nowLongitude",data.getNowLongitude());
                nearbyDeliveryManMaps.add(nearbyDeliveryManMap);
            }
        }
        retMap.put("nearbyDeliveryManCount",nearbyDeliveryManMaps.size()+5);
        retMap.put("nearbyDeliveryManMaps",nearbyDeliveryManMaps);
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "balancePayMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮送、帮取、帮买、代排队余额支付跑腿费", httpMethod = "POST", notes = "微信、app通用。resultData返回支付失败原因:2：余额不足；3：优惠券不能用；4：订单不存在；5：支付失败")
    public String balancePayMoney(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="是否使用优惠券1、使用；0：未使用") @RequestParam(value = "isUseCoupons") int isUseCoupons,
            @ApiParam(value="优惠券id") @RequestParam(required = false,value = "couponId") String couponId,
            @ApiParam(value="还需支付金额") @RequestParam(value = "payMoney") BigDecimal payMoney
    ) {
        long customerId=getCustomerIdByToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long couponIdL=0;
        //使用优惠券
        if(isUseCoupons==1){
            couponIdL=Long.parseLong(couponId);
        }
        int ret=orderPayService.updateOfBalancePayMoney( customerId, orderId, isUseCoupons, couponIdL, payMoney);
        if(ret==1){
            resultMap = getResultMap("1","支付成功");
            //推送抢单接口
            JPushClient jpushClient = new JPushClient(PaotuiUtil.JPUSH_MASTER_SECRET, PaotuiUtil.JPUSH_APP_KEY, null, ClientConfig.getInstance());

            // For push, all you need do is to build PushPayload object.
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(Audience.alias("c"+String.valueOf(customerId)))
                    .setNotification(Notification.alert("支付成功"))
                    .build();

            try {
                PushResult result = jpushClient.sendPush(payload);
                logger.info("Got result - " + result);

            } catch (APIConnectionException e) {
                logger.error("Connection error, should retry later", e);

            } catch (APIRequestException e) {
                logger.info("HTTP Status: " + e.getStatus());
                logger.info("Error Code: " + e.getErrorCode());
                logger.info("Error Message: " + e.getErrorMessage());
            }
        }
        else if(ret==2){
            resultMap = getResultMap("0","余额不足，支付失败",ret);
        }
        else if(ret==3){
            resultMap = getResultMap("0","优惠券无效，支付失败",ret);
        }
        else{
            resultMap = getResultMap("0","支付失败",ret);
        }

        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "addBalanceWithAliPay", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "支付宝充值，返回支付宝付款信息串", httpMethod = "POST", notes = "支付宝充值，返回支付宝付款信息串")
    public String addBalanceWithAliPay(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "充值金额") @RequestParam(value = "money") String money,
            @ApiParam(value = "app版本,不同版本可能有不同充值优惠策略，当前版本：1") @RequestParam(value = "appVersion") String appVersion
    ) {
        Map<String, Object> retMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Customer customer = customerService.get(customerId);
        //更新资金日志表
        CapitalLog capitalLog = new CapitalLog();
        capitalLog.setAccountType(1);//客户帐号
        capitalLog.setAccountId(customer.getId());
        capitalLog.setLoginName(customer.getLoginName());
        capitalLog.setRealName(customer.getRealName());
        //充值
        capitalLog.setType(7);
        capitalLog.setTradeFrom(3);//支付宝
        capitalLog.setCapitalChangeReason("提交到支付宝充值...");
        capitalLog.setChangeMoney(new BigDecimal(money));
        capitalLog.setCreateDate(new Date());
        capitalLog.setStatus(1);
        //备注字段放置app版本号
        capitalLog.setRemark(appVersion);
        capitalLogService.save(capitalLog);
        String[] parameters = {
                "partner=\"" + AlipayConfigApp.partner + "\"",
                "seller_id=\"" + AlipayConfigApp.partner + "\"",
                "out_trade_no=\"" + "cz"+capitalLog.getId() + "\"",
                "subject=\"跑腿放上去\"",
                "body=\"3333\"",
                "total_fee=\"" + money + "\"",
                "notify_url=\"http://paotui.fangshangqu.com/paotui/front/v1/Alipay/addBalanceAliPayNotifyCallBack.htm\"",
                "service=\"mobile.securitypay.pay\"",
                "payment_type=\"1\"",
                "input_charset=\"utf-8\""
        };
        String result = AlipayCoreApp.signAllString(parameters);
        logger.info("result.........." + result);

        retMap=getResultMap("1","生成支付宝充值信息串成功！",result);

        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "addBalanceWithWeixinPay", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "微信充值", httpMethod = "POST", notes = "app、微信通用")
    public String addBalanceWithWeixinPay(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "充值金额") @RequestParam(value = "money") String money,
            @ApiParam(value = "交易类型，取值为：JSAPI或APP") @RequestParam(value = "trade_type") String trade_type,
            @ApiParam(value = "app版本,不同版本可能有不同充值优惠策略,当前版本：1") @RequestParam(value = "appVersion") String appVersion
    ) {
        Map<String, Object> retMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Customer customer = customerService.get(customerId);
        //更新资金日志表
        CapitalLog capitalLog = new CapitalLog();
        capitalLog.setAccountType(1);//客户帐号
        capitalLog.setAccountId(customer.getId());
        capitalLog.setLoginName(customer.getLoginName());
        capitalLog.setRealName(customer.getRealName());
        //充值
        capitalLog.setType(7);
        capitalLog.setTradeFrom(2);//微信
        capitalLog.setCapitalChangeReason("提交到微信充值...");
        capitalLog.setChangeMoney(new BigDecimal(money));
        capitalLog.setCreateDate(new Date());
        capitalLog.setStatus(1);
        //备注字段放置app版本号
        capitalLog.setRemark(appVersion);
        capitalLogService.save(capitalLog);

        String openid="";
        if("JSAPI".equals(trade_type)){
            openid=customer.getToken();
        }
        //支付金额
        String total_fee=String.valueOf(money);
        String goodsBody="test";
        String notify_url=PaotuiUtil.PAOTUI_URL+"paotui/front/v1/WeixinPay/addBalanceWithWeixinPayNotifyCallBack.do";
        Map<String, String> weixinPayMap= WeiChartUtil.getPreyId("cz"+capitalLog.getId(),goodsBody,WeiChartUtil.changeToFen(total_fee), notify_url,openid);
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
                    retMap=getResultMap("1","返回微信充值信息串成功！",reqMap);
                }
                else{
                    retMap=getResultMap("1","返回微信充值信息串成功！",weixinPayMap);
                }

            } else{
                retMap=getResultMap("0","返回微信充值信息串失败！");
            }
        }else{
            retMap=this.getResultMap("0","返回微信充值信息串失败！");
        }
        return JSONUtil.getJson(retMap);
    }


    @ResponseBody
    @RequestMapping(value = "addTipsWithAliPay", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "支付宝付小费，返回支付宝付款信息串", httpMethod = "POST", notes = "支付宝付小费，返回支付宝付款信息串")
    public String addTipsWithAliPay(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "小费金额") @RequestParam(value = "money") String money,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId
    ) {
        Map<String, Object> retMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Customer customer = customerService.get(customerId);
        //更新资金日志表
        CapitalLog capitalLog = new CapitalLog();
        capitalLog.setAccountType(1);//客户帐号
        capitalLog.setAccountId(customer.getId());
        capitalLog.setLoginName(customer.getLoginName());
        capitalLog.setRealName(customer.getRealName());
        //充值
        capitalLog.setOrderId(orderId);
//        capitalLog.setType(orderType);
        capitalLog.setTradeFrom(3);//支付宝
        capitalLog.setCapitalChangeReason("提交到支付宝付小费...");
        capitalLog.setChangeMoney(new BigDecimal(money));
        capitalLog.setCreateDate(new Date());
        capitalLog.setStatus(1);
        capitalLogService.save(capitalLog);
        String[] parameters = {
                "partner=\"" + AlipayConfigApp.partner + "\"",
                "seller_id=\"" + AlipayConfigApp.partner + "\"",
                "out_trade_no=\"" + "xf"+capitalLog.getId() + "\"",
                "subject=\"跑腿放上去\"",
                "body=\"3333\"",
                "total_fee=\"" + money + "\"",
                "notify_url=\"http://paotui.fangshangqu.com/paotui/front/v1/Alipay/addTipsAliPayNotifyCallBack.htm\"",
                "service=\"mobile.securitypay.pay\"",
                "payment_type=\"1\"",
                "input_charset=\"utf-8\""
        };
        String result = AlipayCoreApp.signAllString(parameters);
        logger.info("result.........." + result);
        retMap=getResultMap("1","生成支付宝付小费信息串成功！",result);
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "addTipsWithWeixinPay", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "微信付小费", httpMethod = "POST", notes = "app、微信通用")
    public String addTipsWithWeixinPay(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "小费金额") @RequestParam(value = "money") String money,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "交易类型，取值为：JSAPI或APP") @RequestParam(value = "trade_type") String trade_type
    ) {
        Map<String, Object> retMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Customer customer = customerService.get(customerId);
        //更新资金日志表
        CapitalLog capitalLog = new CapitalLog();
        capitalLog.setAccountType(1);//客户帐号
        capitalLog.setAccountId(customer.getId());
        capitalLog.setLoginName(customer.getLoginName());
        capitalLog.setRealName(customer.getRealName());
        //充值
        capitalLog.setOrderId(orderId);
//        capitalLog.setType(orderType);
        capitalLog.setTradeFrom(2);//微信
        capitalLog.setCapitalChangeReason("提交到微信付小费...");
        capitalLog.setRemark("小费");
        capitalLog.setChangeMoney(new BigDecimal(money));
        capitalLog.setCreateDate(new Date());
        capitalLog.setStatus(1);
        capitalLogService.save(capitalLog);

        String openid="";
        if("JSAPI".equals(trade_type)){
            openid=customer.getToken();
        }

        //支付金额
        String total_fee=String.valueOf(money);
        String goodsBody="付小费";
        String notify_url=PaotuiUtil.PAOTUI_URL+"paotui/front/v1/WeixinPay/addTipsWeixinPayNotifyCallBack.do";
        Map<String, String> weixinPayMap = WeiChartUtil.getPreyId("xf"+capitalLog.getId(),goodsBody,WeiChartUtil.changeToFen(total_fee), notify_url,openid);
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
                    retMap=getResultMap("1","返回微信付小费信息串成功！",reqMap);
                }
                else{
                    retMap=getResultMap("1","返回微信付小费信息串成功！",weixinPayMap);
                }
            } else{
                retMap=getResultMap("0","返回微信付小费信息串失败！");
            }
        }else{
            retMap=this.getResultMap("0","返回微信付小费信息串失败！");
        }
        return JSONUtil.getJson(retMap);
    }

}
