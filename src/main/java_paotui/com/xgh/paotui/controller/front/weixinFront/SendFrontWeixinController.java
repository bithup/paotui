package com.xgh.paotui.controller.front.weixinFront;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.dao.IOrderGoodsDao;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.services.*;
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
@Api(value="微信端帮送/帮取API")
@RequestMapping(value = "paotui/front/weixinFront/v1/send/")
public class SendFrontWeixinController extends BaseController {

    private static Logger logger = Logger.getLogger(SendFrontWeixinController.class);

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderGoodsDao orderGoodsDao;

    @Autowired
    protected IOrderPositionPathService orderPositionPathService;

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;

    @Autowired
    protected IErrandsFeeService errandsFeeService;

    @Autowired
    protected IOpenCityService openCityService;

    @Autowired
    protected ICustomerService customerService;

    @ResponseBody
    @RequestMapping(value = "addSendOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "提交帮送/帮取订单", httpMethod = "POST", notes = "APP端使用")
    public String addSendOrder(
            @ApiParam(value = "订单类型：1、帮送；2、帮取")@RequestParam(value = "orderType") int orderType,
            @ApiParam(value="用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value = "备注留言")@RequestParam(required = false,value = "remark")String remark,
            @ApiParam(value = "下单城市id")@RequestParam(value = "orderCity")long orderCity,
            @ApiParam(value = "订单预约类型：1、立即订单；2：预约订单")@RequestParam(value = "bookingType")int bookingType,
            @ApiParam(value = "预约时间")@RequestParam(required = false,value = "bookingTime")String bookingTime,
            @ApiParam(value = "原始跑腿费")@RequestParam(value = "originalFreight")BigDecimal originalFreight,
            @ApiParam(value = "特殊要求")@RequestParam(value = "specialRequire")String specialRequire,
            @ApiParam(value = "发货地/取货地定位地址")@RequestParam(value = "locationAddressA")String locationAddressA,
            @ApiParam(value = "发货地/取货地定位地址概述")@RequestParam(value = "locationAddressNameA")String locationAddressNameA,
            @ApiParam(value = "发货地/取货地详细地址")@RequestParam(value = "detailAddressA")String detailAddressA,
            @ApiParam(value = "发货地/取货地经度")@RequestParam(value = "longitudeA")Double longitudeA,
            @ApiParam(value = "发货地/取货地纬度")@RequestParam(value = "latitudeA")Double latitudeA,
            @ApiParam(value = "发货人手机号")@RequestParam(value = "mobilePhoneA") String mobilePhoneA,
            @ApiParam(value = "发货联系人姓名")@RequestParam(value = "linkmanNameA")String linkmanNameA,
            @ApiParam(value = "收货地定位地址")@RequestParam(value = "locationAddressB")String locationAddressB,
            @ApiParam(value = "收货地定位地址概述")@RequestParam(value = "locationAddressNameB")String locationAddressNameB,
            @ApiParam(value = "收货地详细地址")@RequestParam(value = "detailAddressB")String detailAddressB,
            @ApiParam(value = "收货地经度")@RequestParam(value = "longitudeB") Double longitudeB,
            @ApiParam(value = "收货地纬度")@RequestParam(value = "latitudeB")Double latitudeB,
            @ApiParam(value = "收货人联系电话")@RequestParam(value = "mobilePhoneB")String mobilePhoneB,
            @ApiParam(value = "收货人姓名")@RequestParam(value = "linkmanNameB")String linkmanNameB,
            @ApiParam(value = "货物类型")@RequestParam(value = "goodsTypeId")int goodsTypeId,
            @ApiParam(value = "货物类型名称")@RequestParam(value = "goodsTypeName")String goodsTypeName,
            @ApiParam(value = "订单总里程")@RequestParam(value = "totalDistance")BigDecimal totalDistance
    ){
        //腾讯地图经纬度转换成百度地图经纬度
        String longitudeAlatitudeA = MapUtil.map_tx2bd(latitudeA,longitudeA);
        String[] array= new String[10];
        array = longitudeAlatitudeA.split(",");
        latitudeA = Double.parseDouble(array[0]);
        longitudeA = Double.parseDouble(array[1]);
        String longitudeBlatitudeB = MapUtil.map_tx2bd(latitudeB,longitudeB);
        String[] array_= new String[10];
        array_ = longitudeBlatitudeB.split(",");
        latitudeB = Double.parseDouble(array_[0]);
        longitudeB = Double.parseDouble(array_[1]);

        long customerId=getCustomerIdByToken(token);
        if(customerId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Order order=new Order();
        order.setOrderType(orderType);
        String orderNo = orderService.updateAndGetOrderNoOfOpenCity(orderCity);
        order.setOrderNo(orderNo);
        Customer customer = customerService.get(customerId);
        order.setCustomerId(customerId);
        order.setCustomerName(customer.getRealName());
        order.setCustomerPhone(customer.getLoginName());
        order.setShippingFrom(1);
        order.setLongitude(longitudeA);
        order.setLatitude(latitudeA);
        order.setRemark(remark);
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
        order.setSpecialRequire(specialRequire);
        order.setCreateTime(new Date());
        order.setOrderStatus(1);
        order.setStatus(1);
        OrderGoods orderGoods=new OrderGoods();
        orderGoods.setLocationAddressA(locationAddressA);
        orderGoods.setLocationAddressNameA(locationAddressNameA);
        orderGoods.setDetailAddressA(detailAddressA);
        orderGoods.setLongitudeA(longitudeA);
        orderGoods.setLatitudeA(latitudeA);
        orderGoods.setMobilePhoneA(mobilePhoneA);
        orderGoods.setLinkmanNameA(linkmanNameA);
        orderGoods.setLocationAddressB(locationAddressB);
        orderGoods.setLocationAddressNameB(locationAddressNameB);
        orderGoods.setDetailAddressB(detailAddressB);
        orderGoods.setLongitudeB(longitudeB);
        orderGoods.setLatitudeB(latitudeB);
        orderGoods.setMobilePhoneB(mobilePhoneB);
        orderGoods.setLinkmanNameB(linkmanNameB);
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
    @RequestMapping(value = "getDistanseAndFreight", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获得帮送/帮取路径距离和跑腿费", httpMethod = "POST", notes = "微信端")
    public String getDistanseAndFreight(
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") int openCityId,
            @ApiParam(value = "起点经度") @RequestParam(value = "longitudeA") String longitudeA,
            @ApiParam(value = "起点纬度") @RequestParam(value = "latitudeA") String latitudeA,
            @ApiParam(value = "终点经度") @RequestParam(value = "longitudeB") String longitudeB,
            @ApiParam(value = "终点纬度") @RequestParam(value = "latitudeB") String latitudeB) {
        //参看：http://lbsyun.baidu.com/index.php?title=webapi/route-matrix-api-v2
        Map<String, Object> resultMap=new HashMap<String, Object>();
        Map<String, Object> map=new HashMap<String, Object>();

        //测试数据
        longitudeA="113.6020256253";
        latitudeA="34.7960473651";
        longitudeB="113.729609701";
        latitudeB="34.7722286953";
        StringBuffer url = new StringBuffer("http://api.map.baidu.com/routematrix/v2/driving?output=json&ak="+ PaotuiUtil.BAIDU_APP_KEY);
        url.append("&origins=");
        //坐标转换，腾讯地图转换成百度地图坐标
        url.append(MapUtil.map_tx2bd(Double.parseDouble(latitudeA),Double.parseDouble(longitudeA)));
        url.append("&destinations=");
        url.append(MapUtil.map_tx2bd(Double.parseDouble(latitudeB),Double.parseDouble(longitudeB)));
        String mapJson = MapUtil.loadBaiduMapJSON(url.toString());
        //计算距离
        int distance=MapUtil.getRealDistanceOfBaiduMap(mapJson);
        if(distance==-1){
            logger.info("获取百度地图两个坐标间的路径距离失败！");
            resultMap= this.getResultMap("0","获取百度地图两个坐标间的路径距离失败！");
        }else{
            Map<String, Object> map1=new HashMap<String, Object>();
            map1.put("openCityId",openCityId);
            List<ErrandsFee> errandsFees = errandsFeeService.getList(map1);
            if(errandsFees!=null&&errandsFees.size()>0){
                ErrandsFee errandsFee=errandsFees.get(0);
                //计算跑腿费
                BigDecimal freight=errandsFee.getStartFee();
                //起步距离之外
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
                map.put("distance",distance);
                map.put("freight",freight);
                resultMap=getResultMap("1","获得帮送/帮取路径距离和跑腿费成功",map);
            }else{
                resultMap=this.getResultMap("0","获取跑腿费参数失败");
            }
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getSendOrderState", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取发货/取货地位置,收货位置,跑客位置,订单轨迹", httpMethod = "GET", notes = "APP端使用")
    public String getSendOrderState(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order = orderService.get(orderId);
        if (order!=null&&order.getId()>0){
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
            if (!"".equals(deliveryManId)&&deliveryManId>0&&orderStatus !=5){
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
            resultMap = getResultMap("0","获取订单失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getSendHistoryAddress", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "帮送、帮取历史地址，目前返回5个地址", httpMethod = "GET", notes = "帮送、帮取历史地址，目前返回5个地址")
    public String getSendHistoryAddress(
            @ApiParam(value="用户身份标识") @RequestParam(value = "token") String token,
            @ApiParam(value="订单类型，1：帮送；2：帮取") @RequestParam(value = "orderType") int orderType,
            @ApiParam(value="地址类型，1：发货地；2：收货地") @RequestParam(value = "addressType") int addressType
    ) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        long customerId=getCustomerIdByToken(token);
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
                Map<String, Object> map1= new  HashMap<String, Object>();
                map1.put("locationAddress",data.get("locationAddressA"));
                map1.put("locationAddressName",data.get("locationAddressNameA"));
                map1.put("detailAddress",data.get("detailAddressA"));
                map1.put("longitude",data.get("longitudeA"));
                map1.put("latitude",data.get("latitudeA"));
                map1.put("mobilePhone",data.get("mobilePhoneA"));
                map1.put("linkmanName",data.get("linkmanNameA"));
                addressMap.put(String.valueOf(data.get("longitudeA"))+data.get("latitudeA"),map1);
            }
        } else{
            for(Map<String, Object> data:orderInfos){
                Map<String, Object> map1= new  HashMap<String, Object>();
                map1.put("locationAddress",data.get("locationAddressB"));
                map1.put("locationAddressName",data.get("locationAddressNameB"));
                map1.put("detailAddress",data.get("detailAddressB"));
                map1.put("longitude",data.get("longitudeB"));
                map1.put("latitude",data.get("latitudeB"));
                map1.put("mobilePhone",data.get("mobilePhoneB"));
                map1.put("linkmanName",data.get("linkmanNameB"));
                addressMap.put(String.valueOf(data.get("longitudeB"))+data.get("latitudeB"),map1);
            }
        }
        List<Map<String, Object>>addressMapList =new ArrayList<Map<String, Object>>();
        int index=0;
        //遍历map中的值
        for (Object data : addressMap.values()) {
            if(index<5){
                addressMapList.add((Map<String, Object>)data);
                index++;
            }
        }
        resultMap = getResultMap("1", "获取历史地址成功", addressMapList);
        return JSONUtil.getJson(resultMap);
    }
}
