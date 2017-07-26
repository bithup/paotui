package com.xgh.paotui.controller.front;

import com.alibaba.fastjson.JSONObject;
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

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Tian on 2017/3/13.
 */
@Controller
@Api(value="帮送/帮取API")
@RequestMapping(value = "paotui/front/v1/send/")
public class SendFrontController extends BaseController {

    private static Logger logger = Logger.getLogger(SendFrontController.class);

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
    protected IOpenCityService openCityService;


    @Autowired
    protected IOrderPayService orderPayService;

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;


    @Autowired
    protected ICapitalLogService capitalLogService;


    @ResponseBody
    @RequestMapping(value = "getDistanseAndFreight", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获得帮送两个坐标间的路径距离和跑腿费", httpMethod = "POST", notes = "获得帮送两个坐标间的路径距离和跑腿费")
    public String getDistanseAndFreight(
            @ApiParam(value = "地图类型，1：百度，2：腾讯") @RequestParam(value = "mapType") String mapType,
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") int openCityId,
            @ApiParam(value = "起点经度") @RequestParam(value = "longitudeA") String longitudeA,
            @ApiParam(value = "起点纬度") @RequestParam(value = "latitudeA") String latitudeA,
            @ApiParam(value = "终点经度") @RequestParam(value = "longitudeB") String longitudeB,
            @ApiParam(value = "终点纬度") @RequestParam(value = "latitudeB") String latitudeB,
            HttpServletRequest request) {
        //参看：http://lbsyun.baidu.com/index.php?title=webapi/route-matrix-api-v2
        Map<String, Object> retMap=new HashMap<String, Object>();
        JSONObject dataJson = new JSONObject();
        StringBuffer url = new StringBuffer("http://api.map.baidu.com/routematrix/v2/driving?output=json&ak="+ PaotuiUtil.BAIDU_APP_KEY);
        url.append("&origins=");
        if ("1".equals(mapType)) {
            url.append(latitudeA);
            url.append(",");
            url.append(longitudeA);
        } else {
            url.append(MapUtil.map_tx2bd( Double.parseDouble(latitudeA),Double.parseDouble(longitudeA)));
        }
        url.append("&destinations=");
        if ("1".equals(mapType)) {
            url.append(latitudeB);
            url.append(",");
            url.append(longitudeB);
        } else {
            url.append(MapUtil.map_tx2bd( Double.parseDouble(latitudeB),Double.parseDouble(longitudeB)));
        }
        String mapJson = MapUtil.loadBaiduMapJSON(url.toString());
        //计算距离
        int distance=MapUtil.getRealDistanceOfBaiduMap(mapJson);
        if(distance==-1){
            logger.info("获取百度地图两个坐标间的路径距离失败！");
        }
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("openCityId",openCityId);
        List<ErrandsFee>  errandsFees = errandsFeeService.getList(map);
        if(errandsFees.size()>0){
            dataJson.put("distance",distance);
            ErrandsFee errandsFee=errandsFees.get(0);
            //计算跑腿费
            //默认起步价格之内
            BigDecimal freight=errandsFee.getStartFee();
            //起步价格之外
            if(distance>errandsFee.getStartMileage()*1000){
                int diffDistance=distance-errandsFee.getStartMileage()*1000;
                BigDecimal diffDecimal=(new BigDecimal(diffDistance)).divide(new BigDecimal(errandsFee.getExceedMileage()*1000),0,BigDecimal.ROUND_UP);
                freight=freight.add(errandsFee.getExceedFee().multiply(diffDecimal));
            }
            //如果是夜间，增加跑腿费
            Calendar now = Calendar.getInstance();
            int hour=now.get(Calendar.HOUR_OF_DAY);
            if(hour>=Integer.parseInt(errandsFee.getNightStartTime()) || hour<=Integer.parseInt(errandsFee.getNightEndTime())){
                freight=freight.add(errandsFee.getNightAddFee());
            }
            dataJson.put("freight",freight);
            retMap=getResultMap("1","成功！",dataJson);
        }
        else{
            retMap=this.getResultMap("0","获取跑腿费参数失败！");
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "getNextOrderNo", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取开通城市的下一个订单流水号", httpMethod = "POST", notes = "获取开通城市的下一个订单流水号")
    public String getNextOrderNo(
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") int openCityId) {
        Map<String, Object> retMap=new HashMap<String, Object>();
        JSONObject dataJson = new JSONObject();
        String  nextOrderNo=orderService.updateAndGetOrderNoOfOpenCity(openCityId);
        if("-1".equals(nextOrderNo)){
            retMap=getResultMap("-1","开通城市不存在！");
        }else{
            dataJson.put("nextOrderNo",nextOrderNo);
            retMap=getResultMap("1","成功！",dataJson);
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "addSendOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "提交帮送/帮取订单", httpMethod = "POST", notes = "APP端使用")
    public String addSendOrder(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "订单类型(1.帮送 2.帮取)") @RequestParam(value = "orderType") int orderType,
            @ApiParam(value = "下单工具(2.android 3.ios)") @RequestParam(value = "shippingFrom") int shippingFrom,
            @ApiParam(value = "备注留言") @RequestParam(required = false,value = "remark") String remark,
            @ApiParam(value = "下单城市id") @RequestParam(value = "orderCity") long orderCity,
            @ApiParam(value = "预约类型(1.立即订单 2.预约订单)") @RequestParam(value = "bookingType") int bookingType,
            @ApiParam(value = "预约时间") @RequestParam(required = false,value = "bookingTime") String bookingTime,
            @ApiParam(value = "原始跑腿费") @RequestParam(value = "originalFreight") BigDecimal originalFreight,
            @ApiParam(value = "特殊要求") @RequestParam(required = false,value = "specialRequire") String specialRequire,
            @ApiParam(value = "发货地/取货地定位地址") @RequestParam(value = "locationAddressA") String locationAddressA,
            @ApiParam(value = "发货地/取货地定位地址概述") @RequestParam(value = "locationAddressNameA") String locationAddressNameA,
            @ApiParam(value = "发货地/取货地详细地址(楼层/门牌号)") @RequestParam(value = "detailAddressA") String detailAddressA,
            @ApiParam(value = "发货地/取货地经度") @RequestParam(value = "longitudeA") Double longitudeA,
            @ApiParam(value = "发货地/取货地纬度") @RequestParam(value = "latitudeA") Double latitudeA,
            @ApiParam(value = "收货地定位地址") @RequestParam(value = "locationAddressB") String locationAddressB,
            @ApiParam(value = "收货地定位地址概述") @RequestParam(value = "locationAddressNameB") String locationAddressNameB,
            @ApiParam(value = "收货地详细地址(楼层/门牌号)") @RequestParam(value = "detailAddressB") String detailAddressB,
            @ApiParam(value = "收货地经度") @RequestParam(value = "longitudeB") Double longitudeB,
            @ApiParam(value = "收货地纬度") @RequestParam(value = "latitudeB") Double latitudeB,
            @ApiParam(value = "发货人姓名") @RequestParam(value = "linkmanNameA") String linkmanNameA,
            @ApiParam(value = "发货人电话") @RequestParam(value = "mobilePhoneA") String mobilePhoneA,
            @ApiParam(value = "收货人姓名") @RequestParam(value = "linkmanNameB") String linkmanNameB,
            @ApiParam(value = "收货人电话") @RequestParam(value = "mobilePhoneB") String mobilePhoneB,
            @ApiParam(value = "货物类型") @RequestParam(value = "goodsTypeId") int goodsTypeId,
            @ApiParam(value = "货物名称") @RequestParam(value = "goodsTypeName") String goodsTypeName,
            @ApiParam(value = "订单总里程") @RequestParam(value = "totalDistance") BigDecimal totalDistance
    ){
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Order order= new Order();
        order.setOrderType(orderType);
        //订单编号
        String orderNo = orderService.updateAndGetOrderNoOfOpenCity(orderCity);
        order.setOrderNo(orderNo);
        //下单人信息
        Customer customer = customerService.get(customerId);
        order.setCustomerId(customerId);
        order.setCustomerName(customer.getRealName());
        order.setCustomerPhone(customer.getLoginName());
        order.setShippingFrom(shippingFrom);
        order.setLongitude(longitudeA);
        order.setLatitude(latitudeA);
        order.setRemark(remark);
        //下单城市信息
        OpenCity openCity = openCityService.get(orderCity);
        order.setOrderCity(orderCity);
        order.setOrderCityName(openCity.getCityName());
        order.setOriginalFreight(originalFreight);
        order.setBookingType(bookingType);
        if (bookingType==2){
            try{
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                order.setBookingTime(sdf.parse(bookingTime));
            }catch (ParseException p){
                return JSONUtil.getJson("时间格式转换错误,时间输入不正确");
            }

        }
        order.setCreateTime(new Date());
        order.setSpecialRequire(specialRequire);
        order.setOrderStatus(1);
        order.setEvaluationStatus(3);
        order.setStatus(1);
        OrderGoods orderGoods = new OrderGoods();
        orderGoods.setLocationAddressA(locationAddressA);
        orderGoods.setLocationAddressNameA(locationAddressNameA);
        orderGoods.setDetailAddressA(detailAddressA);
        orderGoods.setLongitudeA(longitudeA);
        orderGoods.setLatitudeA(latitudeA);
        orderGoods.setLocationAddressB(locationAddressB);
        orderGoods.setLocationAddressNameB(locationAddressNameB);
        orderGoods.setDetailAddressB(detailAddressB);
        orderGoods.setLongitudeB(longitudeB);
        orderGoods.setLatitudeB(latitudeB);
        orderGoods.setLinkmanNameA(linkmanNameA);
        orderGoods.setMobilePhoneA(mobilePhoneA);
        orderGoods.setLinkmanNameB(linkmanNameB);
        orderGoods.setMobilePhoneB(mobilePhoneB);
        orderGoods.setGoodsTypeId(goodsTypeId);
        orderGoods.setGoodsTypeName(goodsTypeName);
        orderGoods.setTotalDistance(totalDistance);
        orderGoods.setStatus(1);
        Map<String,Object> sendOrderMap= orderService.addSendOrder(order,orderGoods);
        return JSONUtil.getJson(sendOrderMap);
    }

    @ResponseBody
    @RequestMapping(value = "getGoodsType", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取物品类型", httpMethod = "GET", notes = "APP端使用")
    public String getGoodsType() {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        List<Dict> goodsType = DictUtils.getDictList("paotui_goods_type");
        for (Dict goodsType_:goodsType){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("goodsTypeId",goodsType_.getId());
            map.put("goodsTypeName",goodsType_.getLabel());
            mapList.add(map);
        }
        return JSONUtil.getJson(mapList);
    }

    @ResponseBody
    @RequestMapping(value = "getSendOrderState", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取发货/取货地位置,收货位置,跑客位置,订单轨迹", httpMethod = "GET", notes = "APP端使用")
    public String getSendOrderState(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order = orderService.get(orderId);
        if (order!=null&&order.getId()>0){
            if (order.getOrderType()==1||order.getOrderType()==2){
                Map<String, Object> map = new HashMap<String, Object>();
                OrderGoods orderGoods = orderGoodsDao.getOrderGoods(orderId);
                if (orderGoods!=null&&orderGoods.getId()>0){
                    //发货/取货地经/纬度
                    String longitudeA = StringUtil.convertNullToEmpty(orderGoods.getLongitudeA());
                    String latitudeA = StringUtil.convertNullToEmpty(orderGoods.getLatitudeA());
                    //收货地经/纬度
                    String longitudeB = StringUtil.convertNullToEmpty(orderGoods.getLongitudeB());
                    String latitudeB = StringUtil.convertNullToEmpty(orderGoods.getLatitudeB());
                    Map<String, Object> map1 = new HashMap<String, Object>();
                    map1.put("longitudeA",longitudeA);
                    map1.put("latitudeA",latitudeA);
                    map1.put("longitudeB",longitudeB);
                    map1.put("latitudeB",latitudeB);
                    map.put("sendTakeReceiveAddress",map1);
                }else {
                    map.put("sendTakeReceiveAddress","没有获取到发货/取货地位置");
                }
                long deliveryManId = order.getDeliveryManId();
                int orderStatus = order.getOrderStatus();
                //订单状态完成时，不显示跑客位置
                if (!"".equals(deliveryManId)&&deliveryManId>0&&orderStatus !=5) {
                    DeliveryManState deliveryManState = deliveryManStateService.get(deliveryManId);
                    if (deliveryManState!=null&&deliveryManState.getId()>0){
                        //跑客当前所在位置经纬度
                        Double deliveryManLongitude = deliveryManState.getNowLongitude();
                        Double deliveryManLatitude = deliveryManState.getNowLatitude();
                        Map<String, Object> map2 = new HashMap<String, Object>();
                        map2.put("longitude",deliveryManLongitude);
                        map2.put("latitude",deliveryManLatitude);
                        map.put("deliveryManAddress",map2);
                    }else {
                        map.put("deliveryManAddress","没有获取到跑客位置");
                    }
                }
                List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                resultMap.put("orderId",orderId);
                List<OrderPositionPath> orderPositionPathList = orderPositionPathService.getOrderState(resultMap);
                if (orderPositionPathList!=null&&orderPositionPathList.size()>0){
                    for (OrderPositionPath orderPositionPathList_:orderPositionPathList){
                        String orderLongitude = StringUtil.convertNullToEmpty(orderPositionPathList_.getLatitude());
                        String orderLatitude = StringUtil.convertNullToEmpty(orderPositionPathList_.getLatitude());
                        Map<String, Object> map3 = new HashMap<String, Object>();
                        map3.put("longitude",orderLongitude);
                        map3.put("latitude",orderLatitude);
                        mapList.add(map3);
                    }
                    map.put("orderAddress",mapList);
                }else {
                    map.put("orderAddress","没有获取到订单轨迹");
                }
                resultMap = getResultMap("1","获取位置/轨迹成功",map);
            }else {
                resultMap = getResultMap("-1","订单不是帮送/帮取订单");
            }
        }else {
            resultMap = getResultMap("0","获取订单失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "checkCancelSendOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "检查帮送/帮取是否可以直接取消订单", httpMethod = "POST", notes = "APP端使用。resultData 0：订单不能取消，1：可以直接取消；2：取消订单将扣除5元跑腿费")
    public String checkCancelSendOrder(
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
                //等待支付和等待接单，可以直接取消订单
                if(order.getOrderStatus()==1 || order.getOrderStatus()==2){
                    resultMap = getResultMap("1","可以直接取消订单");
                }
                //进行中时
                else if(order.getOrderStatus()==3){
                    if(order.getActionStep()< 2){
                        //如果跑客没有到达，可以直接取消订单
                        resultMap = getResultMap("1","可以直接取消订单");
                    }else if (order.getActionStep()<4){
                        resultMap = getResultMap("2","取消订单将扣除一定的费用");
                    }else {
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
    @ApiOperation(value = "帮送/帮取填写取消原因后，取消订单", httpMethod = "POST", notes = "APP端使用")
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
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String cancelReason=DictUtils.getDictLabel(cancelReasonValue,"paotui_cancel_reason","");
        cancelReason+= StringUtil.convertNullToEmpty(cancelReasonOfInput);
        int flag= orderPayService.updateOfCancelOrder(orderId,cancelReason);
        if(flag>1){
            resultMap = getResultMap("1","订单取消成功");
        }else if (flag==-1){
            resultMap = getResultMap("-1","订单不能取消");
        }else {
            resultMap = getResultMap("0","订单取消失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "sendAlipayMoneyApp", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮送、帮取支付宝支付跑腿费", httpMethod = "POST", notes = "帮送、帮取支付宝支付，返回支付宝付款信息串")
    public String sendAlipayMoneyApp(
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
        int ret=orderPayService.updateOfSendAliPayMoney( customerId, orderId, isUseCoupons, couponIdL, payMoney);
        if(ret==1){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id",orderId);
            map.put("orderStatus",1);//等待支付
            List<Map<String, Object>> orderMaps=orderService.getOrderInfoList(map);
            if(orderMaps!=null && orderMaps.size()>0){
                Map<String, Object> orderMap=orderMaps.get(0);
                String body="还需支付的跑腿费";
                //支付金额
                BigDecimal total_fee=new BigDecimal(String.valueOf(orderMap.get("actualFreight")));
                String orderNo=String.valueOf(orderMap.get("orderNo"));
                String[] parameters = {
                        "partner=\"" + AlipayConfigApp.partner + "\"",
                        "seller_id=\"" + AlipayConfigApp.partner + "\"",
                        "out_trade_no=\"" + orderNo + "\"",
                        "subject=\"跑腿放上去\"",
                        "body=\"" + body + "\"",
                        "total_fee=\"" + total_fee + "\"",
                        "notify_url=\"http://paotui.fangshangqu.com/paotui/front/v1/Alipay/sendOrderAliPayNotifyCallBack.htm\"",
                        "service=\"mobile.securitypay.pay\"",
                        "payment_type=\"1\"",
                        "input_charset=\"utf-8\""
                };
                String result = AlipayCoreApp.signAllString(parameters);
                logger.info("result.........." + result);
                retMap=getResultMap("1","返回帮送、帮取支支付宝付款信息串成功！",result);
            }else{
                retMap=getResultMap("0","返回帮送、帮取支支付宝付款信息串失败！");
            }
        } else{
            //订单不存在
            retMap=getResultMap("0","返回帮送、帮取支支付宝付款信息串失败！");
        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "sendWeixinMoney", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮送、帮取微信支付跑腿费，返回微信付款信息串", httpMethod = "POST", notes = "app、微信通用")
    public String sendWeixinMoney(
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
        //更新微信支付订单状态
        int ret=orderPayService.updateOfSendWeixinPayMoney( customerId, orderId, isUseCoupons, couponIdL, payMoney);
        if(ret==1){
            Order order=orderService.get(orderId);
            //支付金额
            String total_fee=String.valueOf(order.getActualFreight());


                  /* 资金日志流表  */
            CapitalLog capitalLog=new CapitalLog();
            capitalLog.setAccountType(1);
            capitalLog.setAccountId(order.getCustomerId());
            capitalLog.setChangeMoney(payMoney);
            capitalLog.setCapitalChangeReason("提交到微信支付跑腿费...");
            capitalLog.setRemark("跑腿费");

            capitalLog.setLoginName(order.getCustomerPhone());
            capitalLog.setRealName(order.getCustomerName());
            //订单类别
            capitalLog.setType(order.getOrderType());
            capitalLog.setOrderId(orderId);
            capitalLog.setOrderNo(order.getOrderNo());
            capitalLog.setTradeFrom(2);
            capitalLog.setCreateDate(new Date());
            capitalLog.setStatus(1);
            capitalLogService.add(capitalLog);


            String notify_url=PaotuiUtil.PAOTUI_URL+"paotui/front/v1/WeixinPay/sendOrderWeixinNotifyCallBack.do";

            String openid="";
            if("JSAPI".equals(trade_type)){
                openid=customer.getToken();
            }

            Map<String, String> weixinPayMap=WeiChartUtil.getPreyId("pt"+String.valueOf(capitalLog.getId()),"跑腿费",WeiChartUtil.changeToFen(total_fee),notify_url,openid);

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
                }else{
                retMap=getResultMap("0","更新订单支付状态失败！",weixinPayMap);
            }

            }else{
                retMap=this.getResultMap("0","生成预支付交易单失败！");
            }
        } else{
            //订单不存在
            retMap=this.getResultMap("0","更新订单支付状态失败！");

        }
        return JSONUtil.getJson(retMap);
    }

    @ResponseBody
    @RequestMapping(value = "getSendHistoryAddress", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮送、帮取历史地址，目前返回5个地址", httpMethod = "GET", notes = "帮送、帮取历史地址，目前返回5个地址")
    public String getSendHistoryAddress(
            @ApiParam(value = "用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单类型，1：帮送；2：帮取") @RequestParam(value = "orderType") int orderType,
            @ApiParam(value="地址类型，1：发货地；2：收货地") @RequestParam(value = "addressType") int addressType
    ) {
        long customerId=getCustomerIdByAppToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }

        Map<String, Object> map=new HashMap<String, Object>();
        map.put("customerId",customerId);
        map.put("orderType",orderType);
        List<Map<String, Object>>  orderInfos = orderService.getOrderInfoList(map);

        //根据经纬度，通过map合并历史地址
        Map<String, Object> addressMap= new LinkedHashMap<String, Object>();
        if(addressType==1){
            for(Map<String, Object> data:orderInfos){
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


}
