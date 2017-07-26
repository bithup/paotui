package com.xgh.paotui.controller.front.weixinFront;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.services.*;
import com.xgh.util.JSONUtil;
import com.xgh.util.StringUtil;
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
 * Created by Administrator on 2017/4/13 0013.
 */
@Controller
@Api(value="微信端代排队API")
@RequestMapping(value = "paotui/front/weixinFront/v1/lineup/")
public class LineupFrontWeixinController extends BaseController {
    @Autowired protected IOrderService orderService;

    @Autowired protected IOpenCityService openCityService;

    @Autowired protected ICustomerService customerService;

    @Autowired protected IOrderLineupService orderLineupService;

    @Autowired protected IDeliveryManStateService deliveryManStateService;

    @Autowired protected IErrandsFeeService errandsFeeService;

    @ResponseBody
    @RequestMapping(value = "addLineupOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "提交代排队订单信息", httpMethod = "POST", notes = "微信端")
    public String addBuyOrder(
            @ApiParam(value="用户身份标识") @RequestParam(value = "token") String token,
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
        Order order=new Order();
        order.setOrderType(4);
        String orderNo = orderService.updateAndGetOrderNoOfOpenCity(orderCity);
        order.setOrderNo(orderNo);
        Customer customer = customerService.get(customerId);
        order.setCustomerId(customerId);
        order.setCustomerName(customer.getRealName());
        order.setCustomerPhone(customer.getLoginName());
        order.setShippingFrom(1);
        order.setLongitude(longitude);
        order.setLatitude(latitude);
        order.setOriginalFreight(originalFreight);
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
        orderLineup.setStatus(1);
        Map<String, Object> lineUpMap = orderService.addLineupOrder(order, orderLineup);
        return JSONUtil.getJson(lineUpMap);
    }

    @ResponseBody
    @RequestMapping(value = "getLineupOrderState", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取排队地位置,跑客位置", httpMethod = "GET", notes = "微信端")
    public String getLineupOrderState(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order = orderService.get(orderId);
        if (order!=null&&order.getId()>0){
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
                }else {
                    map.put("deliveryManAddress", "没有获取到跑客位置");
                }
            }
            resultMap = getResultMap("1", "获取位置成功", map);
        }else {
            resultMap = getResultMap("0", "获取订单失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getDistenceAndFreight", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取排队跑腿费", httpMethod = "POST", notes = "微信端")
        public String getDistenceAndFreight(
            @ApiParam(value = "开通城市id") @RequestParam(value = "openCityId") int openCityId,
            @ApiParam(value = "排队时长") @RequestParam(value = "lineupDuration") int lineupDuration
    ){
        Map<String, Object> resultMap=new HashMap<String, Object>();
        Map<String, Object> map=new HashMap<String, Object>();
        /*跑腿费*/
        BigDecimal freight;
       /*获取跑腿费收费规则*/
        resultMap.put("openCityId", openCityId);
        List<ErrandsFee> errandsFee = errandsFeeService.getList(resultMap);
        if (errandsFee != null && errandsFee.size() > 0) {
            ErrandsFee errandsFee_ = errandsFee.get(0);
            /*排队起步时间内费用*/
            freight = errandsFee_.getQueueStartFee();
            /*排队起步时间之外的费用*/
            if (lineupDuration > errandsFee_.getQueueStartTime()) {
                /*起步时间以外的剩余时间*/
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
}
