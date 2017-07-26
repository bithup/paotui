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
import com.xgh.util.*;
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
import java.util.*;

@Controller
@Api(value="帮买API")
@RequestMapping(value = "paotui/front/v1/buy/")
public class BuyFrontController extends BaseController {

    private static Logger logger = Logger.getLogger(BuyFrontController.class);

    @Autowired
    protected IErrandsFeeService errandsFeeService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderGoodsDao orderGoodsDao;

    @Autowired
    protected IOrderPayService orderPayService;

    @Autowired
    protected IOrderPositionPathService orderPositionPathService;

    @Autowired
    protected ICustomerService customerService;

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;

    @Autowired
    protected IOpenCityService openCityService;

    @Autowired
    protected ICapitalLogService capitalLogService;

    @ResponseBody
    @RequestMapping(value = "addBuyOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "提交帮买订单", httpMethod = "POST", notes = "APP端使用")
    public String addBuyOrder(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "下单工具(2.android 3.ios)") @RequestParam(value = "shippingFrom") int shippingFrom,
            @ApiParam(value = "下单城市id") @RequestParam(value = "orderCity") long orderCity,
            @ApiParam(value = "预约类型(1.立即订单 2.预约订单)") @RequestParam(value = "bookingType") int bookingType,
            @ApiParam(value = "预约时间") @RequestParam(required = false,value = "bookingTime") String bookingTime,
            @ApiParam(value = "原始跑腿费") @RequestParam(value = "originalFreight") BigDecimal originalFreight,
            @ApiParam(value = "特殊要求") @RequestParam(required = false,value = "specialRequire") String specialRequire,
            @ApiParam(value = "帮买地定位地址") @RequestParam(required = false,value = "locationAddressA") String locationAddressA,
            @ApiParam(value = "帮买地定位地址概述") @RequestParam(required = false,value = "locationAddressNameA") String locationAddressNameA,
            @ApiParam(value = "帮买地详细地址(楼层/门牌号)") @RequestParam(required = false,value = "detailAddressA") String detailAddressA,
            @ApiParam(value = "帮买地经度") @RequestParam(required = false,value = "longitudeA") Double longitudeA,
            @ApiParam(value = "帮买地纬度") @RequestParam(required = false,value = "latitudeA") Double latitudeA,
            @ApiParam(value = "收货地定位地址") @RequestParam(value = "locationAddressB") String locationAddressB,
            @ApiParam(value = "收货地定位地址概述") @RequestParam(value = "locationAddressNameB") String locationAddressNameB,
            @ApiParam(value = "收货地详细地址(楼层/门牌号)") @RequestParam(value = "detailAddressB") String detailAddressB,
            @ApiParam(value = "收货地经度") @RequestParam(value = "longitudeB") Double longitudeB,
            @ApiParam(value = "收货地纬度") @RequestParam(value = "latitudeB") Double latitudeB,
            @ApiParam(value = "收货人姓名") @RequestParam(value = "linkmanNameB") String linkmanNameB,
            @ApiParam(value = "收货人电话") @RequestParam(value = "mobilePhoneB") String mobilePhoneB,
            @ApiParam(value = "帮买联系电话") @RequestParam(value = "buyLinkPhone") String buyLinkPhone ,
            @ApiParam(value = "是否知道价格；1、知道价格；2、不知道价格") @RequestParam(value = "isKonwPrice") int isKonwPrice,
            @ApiParam(value = "商品价格") @RequestParam(required = false,value = "buyPrice") BigDecimal buyPrice ,
            @ApiParam(value = "是否就近购买;1、收货地附近就近购买；2、指定购买地") @RequestParam(value = "isNearBuy")int isNearBuy ,
            @ApiParam(value = "购买要求") @RequestParam(value = "buyRequire") String buyRequire,
            @ApiParam(value = "订单总里程") @RequestParam(value = "totalDistance") BigDecimal totalDistance){

        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }

        Order order= new Order();
        //订单类型
        order.setOrderType(3);
        //订单编号
        String  orderNo = orderService.updateAndGetOrderNoOfOpenCity(orderCity);
        order.setOrderNo(orderNo);
        //下单人信息
        Customer customer = customerService.get(customerId);
        order.setCustomerId(customerId);
        order.setCustomerName(customer.getRealName());
        order.setCustomerPhone(customer.getLoginName());
        order.setShippingFrom(shippingFrom);
        if (isNearBuy==1){
            order.setLongitude(longitudeB);
            order.setLatitude(latitudeB);
        }else if (isNearBuy==2){
            order.setLongitude(longitudeA);
            order.setLatitude(latitudeA);
        }
        //下单城市信息
        OpenCity openCity = openCityService.get(orderCity);
        order.setOrderCity(orderCity);
        order.setOrderCityName(openCity.getCityName());
        order.setBookingType(bookingType);
        if (bookingType==2){
            try{
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setBookingTime(sdf.parse(bookingTime));
            }catch (ParseException p){
                return JSONUtil.getJson("时间格式转换错误,时间输入不正确");
            }
        }
        order.setOriginalFreight(originalFreight);
        order.setCreateTime(new Date());
        order.setSpecialRequire(specialRequire);
        order.setOrderStatus(1);
        order.setEvaluationStatus(3);
        order.setStatus(1);
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setLocationAddressB(locationAddressB);
        orderGoods.setLocationAddressNameB(locationAddressNameB);
        orderGoods.setDetailAddressB(detailAddressB);
        orderGoods.setLongitudeB(longitudeB);
        orderGoods.setLatitudeB(latitudeB);
        orderGoods.setLinkmanNameB(linkmanNameB);
        orderGoods.setMobilePhoneB(mobilePhoneB);
        orderGoods.setIsNearBuy(isNearBuy);
        if (isNearBuy==2){
            orderGoods.setLocationAddressA(locationAddressA);
            orderGoods.setLocationAddressNameA(locationAddressNameA);
            orderGoods.setDetailAddressA(detailAddressA);
            orderGoods.setLongitudeA(longitudeA);
            orderGoods.setLatitudeA(latitudeA);
        }
        orderGoods.setIsKonwPrice(isKonwPrice);
        if (isKonwPrice==1){
            orderGoods.setBuyPrice(buyPrice);
        }
        orderGoods.setBuyLinkPhone(buyLinkPhone);
        orderGoods.setBuyRequire(buyRequire);
        orderGoods.setTotalDistance(totalDistance);
        orderGoods.setStatus(1);
        Map<String,Object> buyOrderMap= orderService.addBuyOrder(order,orderGoods);
        return JSONUtil.getJson(buyOrderMap);
    }

    @ResponseBody
    @RequestMapping(value = "getDistanseAndFreight", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取帮买两地距离和跑腿费", httpMethod = "POST", notes = "APP端使用")
    public String getDistanseAndFreight(
            @ApiParam(value = "地图类型，1：百度，2：腾讯") @RequestParam(value = "mapType") String mapType,
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") int openCityId,
            @ApiParam(value = "是否就近购买(1,收货地附近就近购买 2,指定购买地)") @RequestParam(value = "isNearBuy") int isNearBuy,
            @ApiParam(value = "购买地经度") @RequestParam(required = false,value = "longitudeA") String longitudeA,
            @ApiParam(value = "购买地纬度") @RequestParam(required = false,value = "latitudeA") String latitudeA,
            @ApiParam(value = "收货地经度") @RequestParam(value = "longitudeB") String longitudeB,
            @ApiParam(value = "收货地纬度") @RequestParam(value = "latitudeB") String latitudeB) {
        //参看：http://lbsyun.baidu.com/index.php?title=webapi/route-matrix-api-v2
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        //距离
        int distance;
        //跑腿费
        BigDecimal freight;
        //获取跑腿费收费规则
        resultMap.put("openCityId",openCityId);
        List<ErrandsFee> errandsFee = errandsFeeService.getList(resultMap);
        if(errandsFee!=null&&errandsFee.size()>0) {
            if (isNearBuy==1){
                map.put("distance","附近购买");
                map.put("freight",errandsFee.get(0).getStartFee());
            }else {
                //测试数据
                longitudeA="40.45";
                latitudeA="116.34";
                longitudeB="40.34";
                latitudeB="116.45";
                StringBuffer url = new StringBuffer("http://api.map.baidu.com/routematrix/v2/driving?output=json&ak="+ PaotuiUtil.BAIDU_APP_KEY);
                url.append("&origins=");
                if ("1".equals(mapType)) {
                    url.append(latitudeA);
                    url.append(",");
                    url.append(longitudeA);
                } else {
                    url.append(MapUtil.map_tx2bd(Double.parseDouble(latitudeA),Double.parseDouble(longitudeA)));
                }
                url.append("&destinations=");
                if ("1".equals(mapType)) {
                    url.append(latitudeB);
                    url.append(",");
                    url.append(longitudeB);
                } else {
                    url.append(MapUtil.map_tx2bd(Double.parseDouble(latitudeB),Double.parseDouble(longitudeB)));
                }
                String mapJson = MapUtil.loadBaiduMapJSON(url.toString());
                //计算距离
                distance=MapUtil.getRealDistanceOfBaiduMap(mapJson);
                if(distance==-1){
                    logger.info("获取百度地图两个坐标间的路径距离失败！");
                } else {
                    ErrandsFee errandsFee_ = errandsFee.get(0);
                    //起步价
                    freight=errandsFee_.getStartFee();
                    //起步价之外
                    if(distance>errandsFee_.getStartMileage()*1000){
                        int diffDistance = distance-errandsFee_.getStartMileage()*1000;
                        BigDecimal diffDecimal = (new BigDecimal(diffDistance)).divide(new BigDecimal(errandsFee_.getExceedMileage()*1000),0,BigDecimal.ROUND_UP);
                        freight = freight.add(errandsFee_.getExceedFee().multiply(diffDecimal));
                    }
                    //夜间时加收跑腿费
                    Calendar now = Calendar.getInstance();
                    int hour= now.get(Calendar.HOUR_OF_DAY);
                    if(hour>=Integer.parseInt(errandsFee_.getNightStartTime()) || hour<=Integer.parseInt(errandsFee_.getNightEndTime())){
                        freight=freight.add(errandsFee_.getNightAddFee());
                    }
                    map.put("distance",distance);
                    map.put("freight",freight);
                }
            }
            resultMap = getResultMap("1","获取帮买两地距离和跑腿费成功",map);
        }else{
            resultMap = getResultMap("0","获取跑腿费参数失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getBuyOrderState", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取购买地位置,收货地位置,跑客位置,订单轨迹", httpMethod = "GET", notes = "APP端使用")
    public String getBuyOrderState(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        Order order = orderService.get(orderId);
        if (order!=null&&order.getId()>0){
            if (order.getOrderType()==3){
                OrderGoods orderGoods = orderGoodsDao.getOrderGoods(orderId);
                if (orderGoods != null && orderGoods.getId() > 0) {
                    String isNearBuy = StringUtil.convertNullToEmpty(orderGoods.getIsNearBuy());
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    if (isNearBuy == "2") {
                        //帮买地经/纬度
                        String longitudeA = StringUtil.convertNullToEmpty(orderGoods.getLongitudeA());
                        String latitudeA = StringUtil.convertNullToEmpty(orderGoods.getLatitudeA());
                        map1.put("longitudeA", longitudeA);
                        map1.put("latitudeA", latitudeA);
                    } else {
                        map1.put("longitudeAlatitudeA", "收货地附近就近购买");
                    }
                    //收货地经/纬度
                    String longitudeB = StringUtil.convertNullToEmpty(orderGoods.getLongitudeB());
                    String latitudeB = StringUtil.convertNullToEmpty(orderGoods.getLatitudeB());
                    map1.put("longitudeB", longitudeB);
                    map1.put("latitudeB", latitudeB);
                    map.put("buyAddress", map1);
                } else {
                    map.put("buyAddress", "没有获取到购买地、收货地经/纬度");
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
                        map.put("deliveryManAddress", "没有获取到跑客经/纬度");
                    }
                }
                List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                resultMap.put("orderId", orderId);
                List<OrderPositionPath> orderPositionPathList = orderPositionPathService.getOrderState(resultMap);
                if (orderPositionPathList != null && orderPositionPathList.size() > 0) {
                    for (OrderPositionPath orderPositionPathList_ : orderPositionPathList) {
                        String orderLongitude = StringUtil.convertNullToEmpty(orderPositionPathList_.getLatitude());
                        String orderLatitude = StringUtil.convertNullToEmpty(orderPositionPathList_.getLatitude());
                        Map<String, Object> map3 = new HashMap<String, Object>();
                        map3.put("longitude", orderLongitude);
                        map3.put("latitude", orderLatitude);
                        mapList.add(map3);
                    }
                    //订单轨迹
                    map.put("orderAddress", mapList);
                } else {
                    map.put("orderAddress", "没有获取到订单轨迹");
                }
                resultMap = getResultMap("1", "获取位置/轨迹成功", map);
            }else {
                resultMap = getResultMap("-1", "订单不是帮买订单");
            }
        }else {
            resultMap = getResultMap("0", "获取订单失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getBuyHistoryAddress", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮买历史地址，目前返回5个地址", httpMethod = "GET", notes = "帮买历史地址，目前返回5个地址")
    public String getBuyHistoryAddress(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="地址类型，1：购买地；2：收货地") @RequestParam(value = "addressType") int addressType
    ) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> retMap=new HashMap<String, Object>();
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("customerId",customerId);
        map.put("orderType",3);
        List<Map<String, Object>> orderInfos = orderService.getOrderInfoList(map);

        //根据经纬度，通过map合并历史地址
        Map<String, Object> addressMap= new LinkedHashMap<String, Object>();
        if(addressType==1){
            for(Map<String, Object> data:orderInfos){
                Integer isNearBuy =(Integer)data.get("isNearBuy");
                if(isNearBuy==null || isNearBuy==1){
                    continue;
                }

                Map<String, Object> dataTemp= new  HashMap<String, Object>();
                dataTemp.put("locationAddress",data.get("locationAddressA"));
                dataTemp.put("locationAddressName",data.get("locationAddressNameA"));
                dataTemp.put("detailAddress",data.get("detailAddressA"));
                dataTemp.put("longitude",data.get("longitudeA"));
                dataTemp.put("latitude",data.get("latitudeA"));
                dataTemp.put("mobilePhone",data.get("mobilePhoneA"));
                dataTemp.put("linkmanName",data.get("linkmanNameA"));
                addressMap.put(String.valueOf(data.get("longitudeA"))+data.get("latitudeA"),dataTemp);

            }
        } else{
            for(Map<String, Object> data:orderInfos){
                Map<String, Object> dataTemp= new  HashMap<String, Object>();
                dataTemp.put("locationAddress",data.get("locationAddressB"));
                dataTemp.put("locationAddressName",data.get("locationAddressNameB"));
                dataTemp.put("detailAddress",data.get("detailAddressB"));
                dataTemp.put("longitude",data.get("longitudeB"));
                dataTemp.put("latitude",data.get("latitudeB"));
                dataTemp.put("mobilePhone",data.get("mobilePhoneB"));
                dataTemp.put("linkmanName",data.get("linkmanNameB"));
                addressMap.put(String.valueOf(data.get("longitudeB"))+data.get("latitudeB"),dataTemp);
            }
        }
        List<Map<String, Object>>  addressMapList =new ArrayList<Map<String, Object>>();
        int index=0;
        //遍历map中的值
        for (Object data : addressMap.values()) {
            if(index<5){
                addressMapList.add((Map<String, Object>)data);
                index++;
            }
        }
        return JSONUtil.getJson(getResultMap("1","获取历史地址成功！",addressMapList));
    }

    @ResponseBody
    @RequestMapping(value = "buyAlipayMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮买支付宝支付跑腿费(可能包含商品费)", httpMethod = "POST", notes = "帮买支付宝支付，返回支付宝付款信息串")
    public String buyAlipayPayMoney(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="是否使用优惠券1、使用；0：未使用") @RequestParam(value = "isUseCoupons") int isUseCoupons,
            @ApiParam(value="优惠券id") @RequestParam(required = false,value = "couponId") String couponId,
            @ApiParam(value="还需支付金额") @RequestParam(value = "payMoney") BigDecimal payMoney
    ) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> retMap=new HashMap<String, Object>();
        long couponIdL=0;
        //使用优惠券
        if(isUseCoupons==1){
            couponIdL=Long.parseLong(couponId);
        }
        //更新支付宝支付订单状态
        int ret=orderPayService.updateOfBuyAliPayMoney( customerId, orderId, isUseCoupons, couponIdL, payMoney);
        if(ret==1){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",orderId);
            map.put("orderStatus",1);//等待支付

            List<Map<String, Object>> orderMaps=orderService.getOrderInfoList(map);
            if(orderMaps!=null && orderMaps.size()>0){
                Map<String, Object> orderMap=orderMaps.get(0);
                String body="还需支付的跑腿费";
                //支付金额
                BigDecimal total_fee=payMoney;
                String orderNo=String.valueOf(orderMap.get("orderNo"));
                int orderType=(Integer)orderMap.get("orderType");
                if(orderType==3){
                    //帮买
                    int isKonwPrice=(Integer)orderMap.get("isKonwPrice");
                    if(isKonwPrice==1){
                        body="还需支付的跑腿费和商品费";
                    }
                }
                String[] parameters = {
                        "partner=\"" + AlipayConfigApp.partner + "\"",
                        "seller_id=\"" + AlipayConfigApp.partner + "\"",
                        "out_trade_no=\"" + orderNo + "\"",
                        "subject=\"跑腿放上去\"",
                        "body=\"" + body + "\"",
                        "total_fee=\"" + total_fee + "\"",
                        "notify_url=\"http://paotui.fangshangqu.com/paotui/front/v1/Alipay/buyOrderAliPayNotifyCallBack.htm\"",
                        "service=\"mobile.securitypay.pay\"",
                        "payment_type=\"1\"",
                        "input_charset=\"utf-8\""
                };
                String result = AlipayCoreApp.signAllString(parameters);
                logger.info("result.........." + result);
                retMap=getResultMap("1","返回帮买支付宝付款信息串成功！",result);
            } else{
                retMap=this.getResultMap("0","返回帮买支付宝付款信息串失败！");
            }
        } else{
            //订单不存在
            retMap=this.getResultMap("0","返回帮买支付宝付款信息串失败！");
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "buyWeixinPayMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮买微信支付跑腿费(可能包含商品费)", httpMethod = "POST", notes = "app、微信通用")
    public String buyWeixinMoneyApp(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="是否使用优惠券1、使用；0：未使用") @RequestParam(value = "isUseCoupons") int isUseCoupons,
            @ApiParam(value="优惠券id") @RequestParam(required = false,value = "couponId") String couponId,
            @ApiParam(value="还需支付金额") @RequestParam(value = "payMoney") BigDecimal payMoney,
            @ApiParam(value = "交易类型，取值为：JSAPI或APP") @RequestParam(value = "trade_type") String trade_type

    ) {
        long customerId=getCustomerIdByToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Customer customer = customerService.get(customerId);

        Map<String, Object> retMap=new HashMap<String, Object>();
        long couponIdL=0;
        //使用优惠券
        if(isUseCoupons==1){
            couponIdL=Long.parseLong(couponId);
        }
        //更新微信付订单状态
        int ret=orderPayService.updateOfBuyWeixinPayMoney( customerId, orderId, isUseCoupons, couponIdL, payMoney);
        if(ret==1){

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",orderId);
            map.put("orderStatus",1);//等待支付
            List<Map<String, Object>> orderMaps=orderService.getOrderInfoList(map);
            if(orderMaps!=null && orderMaps.size()>0) {
                Map<String, Object> orderMap = orderMaps.get(0);
                int orderType=(Integer)orderMap.get("orderType");
                String goodsBody="跑腿费";

                //帮买
                int isKonwPrice=(Integer)orderMap.get("isKonwPrice");
                if(isKonwPrice==1){
                    goodsBody="还需支付的跑腿费和商品费";
                }

                String orderNo=String.valueOf(orderMap.get("orderNo"));

                String openid="";
                if("JSAPI".equals(trade_type)){
                    openid=customer.getToken();
                }

                //支付金额
                String total_fee=String.valueOf(payMoney);



                /* 资金日志流表  */
                CapitalLog capitalLog=new CapitalLog();
                capitalLog.setAccountType(1);
                capitalLog.setAccountId((Long)orderMap.get("customerId"));
                capitalLog.setChangeMoney(payMoney);
                if(isKonwPrice==1){


                    capitalLog.setCapitalChangeReason("提交到微信支付跑腿费和商品费...");
                    capitalLog.setRemark("跑腿费和商品费");
                }
                else{
                    capitalLog.setCapitalChangeReason("提交到微信支付跑腿费...");
                    capitalLog.setRemark("跑腿费");
                }
                capitalLog.setLoginName(String.valueOf(orderMap.get("customerPhone")));
                capitalLog.setRealName(String.valueOf(orderMap.get("customerName")));
                //订单类别
                capitalLog.setType((Integer)orderMap.get("orderType"));
                capitalLog.setOrderId((Long)orderMap.get("id"));
                capitalLog.setOrderNo(String.valueOf(orderMap.get("orderNo")));
                capitalLog.setTradeFrom(2);
                capitalLog.setCreateDate(new Date());
                capitalLog.setStatus(1);
                capitalLogService.add(capitalLog);

                 /* 生成帮买微信付款信息串 */
                String notify_url=PaotuiUtil.PAOTUI_URL+"paotui/front/v1/WeixinPay/buyOrderWeixinNotifyCallBack.htm";
                Map<String, String> weixinPayMap= WeiChartUtil.getPreyId("pt"+String.valueOf(capitalLog.getId()),goodsBody,WeiChartUtil.changeToFen(total_fee), notify_url,openid);
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
                            retMap=getResultMap("1","返回微信付款信息串成功！",reqMap);
                        }
                        else{
                            retMap=getResultMap("1","返回微信付款信息串成功！",weixinPayMap);
                        }

                    } else{
                        retMap=getResultMap("0","返回帮买微信付款信息串失败！",weixinPayMap);
                    }

                }else{
                    retMap=this.getResultMap("0","返回帮买微信付款信息串失败！");
                }
            } else{
                retMap=this.getResultMap("0","返回帮买微信付款信息串失败！");
            }
        } else if(ret==0)
        {
            retMap=this.getResultMap("0","返回帮买微信付款信息串失败！");
        }

        else if(ret==3)
        {
            retMap=this.getResultMap("0","优惠券不可用！");
        }
        else if(ret==4)
        {
            retMap=this.getResultMap("0","订单不存在！");
        }
        else if(ret==5)
        {
            retMap=this.getResultMap("0","支付金额不正确！");
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "buyGoodsAlipayMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮买支付宝支付商品费", httpMethod = "POST", notes = "帮买支付宝支付，返回支付宝付款信息串")
    public String buyGoodsAlipayMoney(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="商品费") @RequestParam(value = "goodsMoney") BigDecimal goodsMoney
    ) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> retMap=new HashMap<String, Object>();
        Customer customer = customerService.get(customerId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",orderId);
        map.put("customerId",customerId);
        List<Map<String, Object>> orderMaps=orderService.getOrderInfoList(map);
        if(orderMaps!=null && orderMaps.size()>0) {
            Map<String, Object> orderMap = orderMaps.get(0);
            String addPayTransactionId =(String)orderMap.get("addPayTransactionId");
            int orderType =(Integer)orderMap.get("orderType");
            if(orderType!=3){
                retMap=getResultMap("0","该订单不是帮买订单！");
                return JSONUtil.getJson(retMap);
            }
            if(StringUtil.isEmpty(addPayTransactionId) ){
                //更新资金日志表
                CapitalLog capitalLog = new CapitalLog();
                capitalLog.setAccountId(customer.getId());
                capitalLog.setAccountType(1);//客户帐号
                capitalLog.setLoginName(customer.getLoginName());
                capitalLog.setRealName(customer.getRealName());
                //充值
                capitalLog.setOrderId(orderId);
                capitalLog.setOrderNo((String)orderMap.get("orderNo"));
                capitalLog.setType(3);
                capitalLog.setTradeFrom(3);//支付宝
                capitalLog.setCapitalChangeReason("提交到支付宝付商品费...");
                capitalLog.setChangeMoney(goodsMoney);
                capitalLog.setCreateDate(new Date());
                capitalLog.setStatus(1);
                capitalLogService.save(capitalLog);

                String[] parameters = {
                        "partner=\"" + AlipayConfigApp.partner + "\"",
                        "seller_id=\"" + AlipayConfigApp.partner + "\"",
                        "out_trade_no=\"" + "gd"+capitalLog.getId() + "\"",
                        "subject=\"跑腿放上去\"",
                        "body=\"商品费\"",
                        "total_fee=\"" + goodsMoney + "\"",
                        "notify_url=\"http://paotui.fangshangqu.com/paotui/front/v1/Alipay/buyGoodsAlipayNotifyCallBack.htm\"",
                        "service=\"mobile.securitypay.pay\"",
                        "payment_type=\"1\"",
                        "input_charset=\"utf-8\""
                };
                String result = AlipayCoreApp.signAllString(parameters);
                logger.info("result.........." + result);
                retMap=getResultMap("1","生成支付宝付商品费信息串成功！",result);
            } else{
                retMap=getResultMap("0","商品已支付，生成支付宝付商品费信息串失败！");
            }
        } else{
            retMap=getResultMap("0","订单不存在！");
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "buyGoodsWeixinPayMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮买微信支付商品费", httpMethod = "POST", notes = "app、微信通用")
    public String buyGoodsWeixinPayMoney(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value="商品费") @RequestParam(value = "goodsMoney") BigDecimal goodsMoney,
            @ApiParam(value = "交易类型，取值为：JSAPI或APP") @RequestParam(value = "trade_type") String trade_type
    ) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> retMap=new HashMap<String, Object>();
        Customer customer = customerService.get(customerId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",orderId);
        map.put("customerId",customerId);
        List<Map<String, Object>> orderMaps=orderService.getOrderInfoList(map);

        if(orderMaps!=null && orderMaps.size()>0) {
            Map<String, Object> orderMap = orderMaps.get(0);
            String addPayTransactionId =(String)orderMap.get("addPayTransactionId");
            int orderType =(Integer)orderMap.get("orderType");
            if(orderType!=3){
                retMap=getResultMap("0","该订单不是帮买订单！");
                return JSONUtil.getJson(retMap);
            }
            if(StringUtil.isEmpty(addPayTransactionId) ){
                //更新资金日志表
                CapitalLog capitalLog = new CapitalLog();
                capitalLog.setAccountId(customer.getId());
                capitalLog.setAccountType(1);//客户帐号
                capitalLog.setAccountId(customer.getId());
                capitalLog.setLoginName(customer.getLoginName());
                capitalLog.setRealName(customer.getRealName());
                //支付商品费
                capitalLog.setOrderId(orderId);
                capitalLog.setOrderNo((String)orderMap.get("orderNo"));
                capitalLog.setType(3);
                capitalLog.setTradeFrom(2);//微信
                capitalLog.setCapitalChangeReason("提交到微信付商品费...");
                capitalLog.setChangeMoney(goodsMoney);
                capitalLog.setCreateDate(new Date());
                capitalLog.setStatus(1);
                capitalLogService.save(capitalLog);

                String openid="";
                if("JSAPI".equals(trade_type)){
                    openid=customer.getToken();
                }

                //支付金额
                String total_fee=String.valueOf(goodsMoney);
                String goodsBody="商品费";
                String notify_url=PaotuiUtil.PAOTUI_URL+"paotui/front/v1/WeixinPay/buyGoodsWeixinNotifyCallBack.htm";
                Map<String, String> weixinPayMap= WeiChartUtil.getPreyId("gd"+capitalLog.getId(),goodsBody,WeiChartUtil.changeToFen(total_fee), notify_url,openid);
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
                            retMap=getResultMap("1","返回微信付商品费信息串成功！",reqMap);
                        }
                        else{
                            retMap=getResultMap("1","返回微信付商品费信息串成功！",weixinPayMap);
                        }


                    } else{
                        retMap=getResultMap("0","返回帮买微信付商品费信息串失败！");
                    }
                }else{
                    retMap=this.getResultMap("0","返回帮买微信付商品费信息串失败！");
                }
            } else{
                retMap=getResultMap("0","商品已支付，生成帮买微信付商品费信息串失败！");
            }
        } else{
            retMap=getResultMap("0","订单不存在！");
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "checkCancelBuyOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "检查帮买是否可以直接取消订单", httpMethod = "POST", notes = "APP端")
    public String checkCancelBuyOrder(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId
    ) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order = orderService.get(orderId);
        if(order!=null&&order.getId()>0){
            if(order.getCustomerId()==customerId){
                //等待支付和等待接单，可以直接取消订单
                if(order.getOrderStatus()==1  || order.getOrderStatus()==2){
                    resultMap= getResultMap("1","可以直接取消订单");
                }
                //进行中时
                else if(order.getOrderStatus()==3){
                    if(order.getActionStep()< 2){
                        //如果跑客没有到达，可以直接取消订单
                       resultMap = getResultMap("1","可以直接取消订单");
                    }else if(order.getActionStep()< 4){
                        resultMap = getResultMap("2","取消订单将扣除一定的费用");
                    }else{
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
    @RequestMapping(value = "cancelSendOrderWithReason", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮买填写取消原因后，取消订单", httpMethod = "POST", notes = "APP端使用")
    public String cancelSendOrderWithReason(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId,
            @ApiParam(value = "取消原因值") @RequestParam(required = false,value = "cancelReasonValue") String cancelReasonValue,
            @ApiParam(value = "填写的取消原因") @RequestParam(required = false,value = "cancelReasonOfInput") String cancelReasonOfInput
    ) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        String cancelReason= DictUtils.getDictLabel(cancelReasonValue,"paotui_cancel_reason","");
        cancelReason+= StringUtil.convertNullToEmpty(cancelReasonOfInput);
        int ret= orderPayService.updateOfCancelBuyOrder(orderId,cancelReason);
        if(ret==1){
            return JSONUtil.getJson(getResultMap("1","订单取消成功，系统将在5个工作日内退款。"));
        } else{
            return JSONUtil.getJson(getResultMap("0","订单取消失败"));
        }
    }

}

