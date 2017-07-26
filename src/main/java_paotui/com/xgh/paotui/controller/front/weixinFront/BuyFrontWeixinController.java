package com.xgh.paotui.controller.front.weixinFront;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.dao.IOrderGoodsDao;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.services.*;
import com.xgh.util.JSONUtil;
import com.xgh.util.MapUtil;
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
import java.util.*;

@Controller
@Api(value="微信端帮买API")
@RequestMapping(value = "paotui/front/weixinFront/v1/buy/")
public class BuyFrontWeixinController extends BaseController {

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;

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

    private static Logger logger = Logger.getLogger(com.xgh.paotui.controller.front.weixinFront.BuyFrontWeixinController.class);

    @ResponseBody
    @RequestMapping(value = "getDistanseAndFreight", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取帮买两地距离和跑腿费", httpMethod = "POST", notes = "APP端使用")
    public String getDistanseAndFreight(
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") int openCityId,
            @ApiParam(value = "是否就近购买(1,收货地附近就近购买 2,指定购买地)") @RequestParam(value = "isNearBuy") int isNearBuy,
            @ApiParam(value = "购买地经度") @RequestParam(required = false,value = "longitudeA") String longitudeA,
            @ApiParam(value = "购买地纬度") @RequestParam(required = false,value = "latitudeA") String latitudeA,
            @ApiParam(value = "收货地经度") @RequestParam(value = "longitudeB") String longitudeB,
            @ApiParam(value = "收货地纬度") @RequestParam(value = "latitudeB") String latitudeB) {
        //参看：http://lbsyun.baidu.com/index.php?title=webapi/route-matrix-api-v2
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //距离
        int distance;
        //跑腿费
        BigDecimal freight;
        //获取跑腿费收费规则
        Map<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("openCityId",openCityId);
        List<ErrandsFee> errandsFee = errandsFeeService.getList(hashMap);
        Map<String, Object> map = new HashMap<String, Object>();
        if(errandsFee!=null&&errandsFee.size()>0) {
            if (isNearBuy==1){
                map.put("distance","附近购买");
                map.put("freight",errandsFee.get(0).getStartFee());
            }else {
                //测试数据
                longitudeA="113.610996051908";
                latitudeA="34.80975776199919";
                longitudeB="113.63543166856056";
                latitudeB="34.83836170438617";

                StringBuffer url = new StringBuffer("http://api.map.baidu.com/routematrix/v2/driving?output=json&ak="+ PaotuiUtil.BAIDU_APP_KEY);
                url.append("&origins=");
                url.append(MapUtil.map_tx2bd(Double.parseDouble(latitudeA),Double.parseDouble(longitudeA)));
                url.append("&destinations=");
                url.append(MapUtil.map_tx2bd(Double.parseDouble(latitudeB),Double.parseDouble(longitudeB)));
                String mapJson = MapUtil.loadBaiduMapJSON(url.toString());
                //计算距离
                distance=MapUtil.getRealDistanceOfBaiduMap(mapJson);
                if(distance==-1){
                    logger.info("获取百度地图两个坐标间的路径距离失败！");
                    resultMap = this.getResultMap("0", "获取百度地图两个坐标间的路径距离失败！");
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
    @RequestMapping(value = "addBuyOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "提交帮买订单", httpMethod = "POST", notes = "APP端使用")
    public String addBuyOrder(
           @ApiParam(value="用户身份标识") @RequestParam(value = "token") String token,
           @ApiParam(value = "下单城市id")@RequestParam(value = "orderCity")long orderCity,
           @ApiParam(value = "订单预约类型：1、立即订单；2：预约订单")@RequestParam(value = "bookingType")int bookingType,
           @ApiParam(value = "预约时间")@RequestParam(required = false,value = "bookingTime")String bookingTime,
           @ApiParam(value = "原始跑腿费")@RequestParam(value = "originalFreight")BigDecimal originalFreight,
           @ApiParam(value = "特殊要求")@RequestParam(value = "specialRequire")String specialRequire,
           @ApiParam(value = "购买地定位地址")@RequestParam(required = false,value = "locationAddressA")String locationAddressA,
           @ApiParam(value = "购买地定位地址概述")@RequestParam(required = false,value = "locationAddressNameA")String locationAddressNameA,
           @ApiParam(value = "购买地详细地址")@RequestParam(required = false,value = "detailAddressA") String detailAddressA,
           @ApiParam(value = "购买地经度")@RequestParam(required = false,value = "longitudeA")Double longitudeA,
           @ApiParam(value = "购买地纬度")@RequestParam(required = false,value = "latitudeA")Double latitudeA,
           @ApiParam(value = "收货地定位地址")@RequestParam(value = "locationAddressB")String locationAddressB,
           @ApiParam(value = "收货地定位地址概述")@RequestParam(value = "locationAddressNameB")String locationAddressNameB,
           @ApiParam(value = "收货地详细地址")@RequestParam(value = "detailAddressB")String detailAddressB,
           @ApiParam(value = "收货地经度")@RequestParam(value = "longitudeB")Double longitudeB,
           @ApiParam(value = "收货地纬度")@RequestParam(value = "latitudeB")Double latitudeB,
           @ApiParam(value = "收货人联系电话")@RequestParam(value = "mobilePhoneB")String mobilePhoneB,
           @ApiParam(value = "收货人姓名")@RequestParam(value = "linkmanNameB")String linkmanNameB,
           @ApiParam(value = "购买要求")@RequestParam(value = "buyRequire")String buyRequire,
           @ApiParam(value = "帮买联系电话")@RequestParam(value = "buyLinkPhone")String buyLinkPhone,
           @ApiParam(value = "是否知道价格；1、知道价格；2、不知道价格")@RequestParam(value = "isKonwPrice")int isKonwPrice,
           @ApiParam(value = "商品价格")@RequestParam(required = false,value = "buyPrice")BigDecimal buyPrice,
           @ApiParam(value = "是否就近购买")@RequestParam(value = "isNearBuy")int isNearBuy,
           @ApiParam(value = "订单总里程")@RequestParam(value = "totalDistance")BigDecimal totalDistance
    ){
        //腾讯地图经纬度转换成百度地图经纬度
        String longitudeAlatitudeA = MapUtil.map_tx2bd(latitudeA,longitudeA);
        String[] array;
        array = longitudeAlatitudeA.split(",");
        latitudeA = Double.parseDouble(array[0]);
        longitudeA = Double.parseDouble(array[1]);
        String longitudeBlatitudeB = MapUtil.map_tx2bd(latitudeB,longitudeB);
        String[] array_;
        array_ = longitudeBlatitudeB.split(",");
        latitudeB = Double.parseDouble(array_[0]);
        longitudeB = Double.parseDouble(array_[1]);

        long customerId=getCustomerIdByToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Order order=new Order();
        order.setOrderType(3);
        String orderNo = orderService.updateAndGetOrderNoOfOpenCity(orderCity);
        order.setOrderNo(orderNo);
        Customer customer=customerService.get(customerId);
        order.setCustomerId(customerId);
        order.setCustomerName(customer.getRealName());
        order.setCustomerPhone(customer.getLoginName());
        order.setShippingFrom(1);
        OpenCity openCity=openCityService.get(orderCity);
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
        if (isNearBuy==1){
            order.setLongitude(longitudeB);
            order.setLatitude(latitudeB);
        }else if (isNearBuy==2){
            order.setLongitude(longitudeA);
            order.setLatitude(latitudeA);
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
    @RequestMapping(value = "getBuyOrderState", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取购买地位置,收货地位置,跑客位置,订单轨迹", httpMethod = "GET", notes = "APP端使用")
    public String getBuyOrderState(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order = orderService.get(orderId);
        if (order!=null&&order.getId()>0){
            Map<String, Object> map = new HashMap<String, Object>();
            OrderGoods orderGoods = orderGoodsDao.get(orderId);
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
                    map.put("buyAddress", "收货地附近就近购买");
                }
                //收货地经/纬度
                String longitudeB = StringUtil.convertNullToEmpty(orderGoods.getLongitudeB());
                String latitudeB = StringUtil.convertNullToEmpty(orderGoods.getLatitudeB());
                map1.put("longitudeB", longitudeB);
                map1.put("latitudeB", latitudeB);
                map.put("buyReceiveAddress", map1);
            } else {
                map.put("buyReceiveAddress", "没有获取到购买地/收货地经/纬度");
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
                map.put("orderAddress", mapList);
            } else {
                map.put("orderAddress", "没有获取到订单轨迹");
            }
            resultMap = getResultMap("1", "获取位置/轨迹成功", map);
        }else {
            resultMap = getResultMap("0", "获取订单失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getBuyHistoryAddress", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮买历史地址，目前返回5个地址", httpMethod = "GET", notes = "帮买历史地址，目前返回5个地址")
    public String getBuyHistoryAddress(
            @ApiParam(value = "用户id") @RequestParam(value = "customerId") String customerId,
            @ApiParam(value="地址类型，1：购买地；2：收货地") @RequestParam(value = "addressType") int addressType
    ) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("customerId",customerId);
        map.put("orderType",3);
        List<Map<String, Object>> orderInfos = orderService.getOrderInfoList(map);
        //根据经纬度，通过map合并历史地址
        Map<String, Object> addressMap= new LinkedHashMap<String, Object>();
        if(addressType==1){
            for(Map<String, Object> data:orderInfos){
                Integer isNearBuy =(Integer)data.get("isNearBuy");
                if(isNearBuy!=null&&isNearBuy==2){
                    Map<String, Object> map_= new  HashMap<String, Object>();
                    map_.put("locationAddress",data.get("locationAddressA"));
                    map_.put("locationAddressName",data.get("locationAddressNameA"));
                    map_.put("detailAddress",data.get("detailAddressA"));
                    map_.put("longitude",data.get("longitudeA"));
                    map_.put("latitude",data.get("latitudeA"));
                    map_.put("mobilePhone",data.get("mobilePhoneA"));
                    map_.put("linkmanName",data.get("linkmanNameA"));
                    addressMap.put(String.valueOf(data.get("longitudeA"))+data.get("latitudeA"),map_);
                }
            }
        } else{
            for(Map<String, Object> data:orderInfos){
                Map<String, Object> map_= new  HashMap<String, Object>();
                map_.put("locationAddress",data.get("locationAddressB"));
                map_.put("locationAddressName",data.get("locationAddressNameB"));
                map_.put("detailAddress",data.get("detailAddressB"));
                map_.put("longitude",data.get("longitudeB"));
                map_.put("latitude",data.get("latitudeB"));
                map_.put("mobilePhone",data.get("mobilePhoneB"));
                map_.put("linkmanName",data.get("linkmanNameB"));
                addressMap.put(String.valueOf(data.get("longitudeB"))+data.get("latitudeB"),map_);
            }
        }
        List<Map<String, Object>>  mapList =new ArrayList<Map<String, Object>>();
        int index=0;
        //遍历map中的值
        for (Object data : addressMap.values()) {
            if(index<5){
                mapList.add((Map<String, Object>)data);
                index++;
            }
        }
        resultMap = getResultMap("1", "获取历史地址成功", mapList);
        return JSONUtil.getJson(resultMap);
    }

}

