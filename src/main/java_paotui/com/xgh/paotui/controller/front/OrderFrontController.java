package com.xgh.paotui.controller.front;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.entity.DeliveryMan;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.entity.Order;
import com.xgh.paotui.services.IDeliveryManService;
import com.xgh.paotui.services.IOpenCityService;
import com.xgh.paotui.services.IOrderService;
import com.xgh.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(value="订单API")
@RequestMapping(value = "paotui/front/v1/order/")
public class OrderFrontController extends BaseController{

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(OrderFrontController.class);

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IDeliveryManService deliveryManService;

    @Autowired
    protected IOpenCityService openCityService;

    @Autowired
    protected BaseService baseService;

    @ResponseBody
    @RequestMapping(value = "getOrderDeliveryMan", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取接单师傅信息", httpMethod = "GET", notes = "APP跑客端")
    public String getOrderDeliveryMan(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        DeliveryMan deliveryMan= deliveryManService.get(deliveryManId);
        if (deliveryMan!=null&&deliveryMan.getId()>0){
            map.put("loginName",deliveryMan.getLoginName());
            map.put("realName",deliveryMan.getRealName());
            map.put("sex",deliveryMan.getSex());
            map.put("birthday",deliveryMan.getBirthday());
            map.put("mobilePhone",StringUtil.convertNullToEmpty(deliveryMan.getMobilePhone()));
            map.put("balance",deliveryMan.getBalance());
            map.put("starLevel",deliveryMan.getStarLevel());
            String belongType = StringUtil.convertNullToEmpty(deliveryMan.getBelongType());
            map.put("belongType", DictUtils.getDictLabel(belongType,"paotui_belong_type",""));
            long belongCityId = deliveryMan.getBelongCityId();
            OpenCity openCity = openCityService.get(belongCityId);
            if (openCity!=null&&openCity.getId()>0){
                map.put("cityName",openCity.getCityName());
            }
            map.put("getOrderCount",deliveryMan.getGetOrderCount());
            map.put("praiseRate",deliveryMan.getPraiseRate());
            if (deliveryMan.getQrCode()!=null&&deliveryMan.getQrCode()!=""){
                String qrCode = ConstantUtil.SERVER_URL+deliveryMan.getQrCode();
                map.put("qrCode",qrCode);
            }else {
                map.put("qrCode","");
            }
            if (deliveryMan.getHeadImage()!=null&&deliveryMan.getHeadImage()!=""){
                String headImage = ConstantUtil.SERVER_URL+deliveryMan.getHeadImage();
                map.put("headImage",headImage);
            }else {
                map.put("headImage","");
            }
            resultMap = getResultMap("1","获取接单师傅信息成功",map);
        }else {
            resultMap = getResultMap("0","获取接单师傅信息失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getOrderDetail", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "订单详情", httpMethod = "GET", notes = "APP端使用")
    public String getOrderDetail(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") String orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        resultMap.put("orderId",orderId);
        List<Map<String,Object>> myOrderMap = orderService.getOrderDetail(resultMap);
        if (myOrderMap!=null&&myOrderMap.size()>0){
            for (Map<String, Object> myOrderMap_ : myOrderMap) {
                //订单类型
                String orderType = StringUtil.convertNullToEmpty(myOrderMap_.get("orderType"));
                map.put("orderTypeId",orderType);
                map.put("orderTypeName", DictUtils.getDictLabel(orderType, "paotui_order_type", ""));
                //订单编号
                map.put("orderNo", StringUtil.convertNullToEmpty(myOrderMap_.get("orderNo")));
                //订单预约类型：1、立即订单；2：预约订单
                String bookingType = StringUtil.convertNullToEmpty(myOrderMap_.get("bookingType"));
                map.put("bookingType",bookingType);
                map.put("bookingTypeName", DictUtils.getDictLabel(bookingType, "paotui_booking_type", ""));
                if ("2".equals(bookingType)) {
                    //预约时间
                    Date bookingTime = (Date) myOrderMap_.get("bookingTime");
                    map.put("bookingTime", DateUtil.getChineseDateTime(bookingTime));
                }
                //订单状态
                String orderStatus = StringUtil.convertNullToEmpty(myOrderMap_.get("orderStatus"));
                map.put("orderStatus",orderStatus);
                map.put("orderStatusName", DictUtils.getDictLabel(orderStatus, "paotui_order_state", ""));
                if (!"1".equals(orderStatus)&&!"2".equals(orderStatus)){
                    //接单人id
                    map.put("deliveryManId", StringUtil.convertNullToEmpty(myOrderMap_.get("deliveryManId")));
                    //收货验证码
                    map.put("smsCode", StringUtil.convertNullToEmpty(myOrderMap_.get("smsCode")));
                }
                //原始跑腿费
                map.put("originalFreight", StringUtil.convertNullToEmpty(myOrderMap_.get("originalFreight")));
                if (!"1".equals(orderStatus)) {
                    //实际跑腿费
                    map.put("actualFreight", StringUtil.convertNullToEmpty(myOrderMap_.get("actualFreight")));
                    //是否使用优惠券：1、使用；0：未使用
                    String isUseCoupons = StringUtil.convertNullToEmpty(myOrderMap_.get("isUseCoupons"));
                    map.put("isUseCouponsId",isUseCoupons);
                    map.put("isUseCouponsName", DictUtils.getDictLabel(isUseCoupons, "paotui_is_use_coupons", ""));
                    if ("1".equals(isUseCoupons)) {
                        //优惠券优惠金额
                        map.put("couponsMoney", StringUtil.convertNullToEmpty(myOrderMap_.get("couponsMoney")));
                    }
                    //小费
                    map.put("tips", StringUtil.convertNullToEmpty(myOrderMap_.get("tips")));
                }
                //下单时间
                map.put("createTime", StringUtil.convertNullToEmpty(myOrderMap_.get("createTime")));
                if ("5".equals(orderStatus)){
                    //签收时间
                    map.put("signTime", StringUtil.convertNullToEmpty(myOrderMap_.get("signTime")));
                    //订单总时间
                    map.put("totalTime", StringUtil.convertNullToEmpty(myOrderMap_.get("totalTime")));
                }
                /*帮送、帮取*/
                if ("1".equals(orderType) || "2".equals(orderType)) {
                    String addressA = "";
                    addressA += StringUtil.convertNullToEmpty(myOrderMap_.get("locationAddressA"));
                    addressA += StringUtil.convertNullToEmpty(myOrderMap_.get("locationAddressNameA"));
                    addressA += StringUtil.convertNullToEmpty(myOrderMap_.get("detailAddressA"));
                    map.put("addressA", addressA);
                    map.put("linkmanNameA", StringUtil.convertNullToEmpty(myOrderMap_.get("linkmanNameA")));
                    map.put("mobilePhoneA", StringUtil.convertNullToEmpty(myOrderMap_.get("mobilePhoneA")));
                    String addressB = "";
                    addressB += StringUtil.convertNullToEmpty(myOrderMap_.get("locationAddressB"));
                    addressB += StringUtil.convertNullToEmpty(myOrderMap_.get("locationAddressNameB"));
                    addressB += StringUtil.convertNullToEmpty(myOrderMap_.get("detailAddressB"));
                    map.put("addressB", addressB);
                    map.put("linkmanNameB", StringUtil.convertNullToEmpty(myOrderMap_.get("linkmanNameB")));
                    map.put("mobilePhoneB", StringUtil.convertNullToEmpty(myOrderMap_.get("mobilePhoneB")));
                    //物品类型
                    map.put("goodsTypeName", StringUtil.convertNullToEmpty(myOrderMap_.get("goodsTypeName")));
                    //特殊要求
                    map.put("specialRequire", StringUtil.convertNullToEmpty(myOrderMap_.get("specialRequire")));
                }
                /*帮买*/
                else if ("3".equals(orderType)) {
                    String isNearBuy = StringUtil.convertNullToEmpty(myOrderMap_.get("isNearBuy"));
                    if ("2".equals(isNearBuy)) {
                        String addressA = "";
                        addressA += StringUtil.convertNullToEmpty(myOrderMap_.get("locationAddressA"));
                        addressA += StringUtil.convertNullToEmpty(myOrderMap_.get("locationAddressNameA"));
                        addressA += StringUtil.convertNullToEmpty(myOrderMap_.get("detailAddressA"));
                        map.put("addressA", addressA);
                    } else {
                        map.put("addressA", DictUtils.getDictLabel(isNearBuy, "paotui_is_near_buy", ""));
                    }
                    String addressB = "";
                    addressB += StringUtil.convertNullToEmpty(myOrderMap_.get("locationAddressB"));
                    addressB += StringUtil.convertNullToEmpty(myOrderMap_.get("locationAddressNameB"));
                    addressB += StringUtil.convertNullToEmpty(myOrderMap_.get("detailAddressB"));
                    map.put("addressB", addressB);
                    map.put("linkmanNameB", StringUtil.convertNullToEmpty(myOrderMap_.get("linkmanNameB")));
                    map.put("mobilePhoneB", StringUtil.convertNullToEmpty(myOrderMap_.get("mobilePhoneB")));
                    //购买要求
                    map.put("buyRequire", StringUtil.convertNullToEmpty(myOrderMap_.get("buyRequire")));
                    //商是否知道价格
                    String isKonwPrice = StringUtil.convertNullToEmpty(myOrderMap_.get("isKonwPrice"));
                    if ("1".equals(isKonwPrice)) {
                        //商品价格
                        map.put("buyPrice", StringUtil.convertNullToEmpty(myOrderMap_.get("buyPrice")));
                    } else {
                        map.put("isKonwPrice", DictUtils.getDictLabel(isKonwPrice, "paotui_is_konw_price", ""));
                    }
                }
                /*带排队*/
                else if ("4".equals(orderType)) {
                    String address = "";
                    address += StringUtil.convertNullToEmpty(myOrderMap_.get("lineupLocationAddress"));
                    address += StringUtil.convertNullToEmpty(myOrderMap_.get("lineupLocationAddressName"));
                    address += StringUtil.convertNullToEmpty(myOrderMap_.get("lineupDetailAddress"));
                    map.put("address", address);
                    //联系电话
                    map.put("lineupPhone", StringUtil.convertNullToEmpty(myOrderMap_.get("lineupPhone")));
                    // 排队要求
                    map.put("lineupRequire", StringUtil.convertNullToEmpty(myOrderMap_.get("lineupRequire")));
                    // 排队时长
                    map.put("lineupDuration", StringUtil.convertNullToEmpty(myOrderMap_.get("lineupDuration")));
                }
            }
            resultMap = getResultMap("1","获取订单详情成功",map);
        }else {
            resultMap = getResultMap("0","获取订单详情失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "删除订单", httpMethod = "POST", notes = "APP端使用")
    public String deleteOrder(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap=new HashMap<String, Object>();
        Order order = orderService.get(orderId);
        if (order!=null&&order.getId()>0){
            if (order.getOrderStatus()==5||order.getOrderStatus()==9){
                order.setStatus(2);
                int flag = orderService.update(order);
                if (flag>0){
                    resultMap=getResultMap("1","删除订单成功");
                }else {
                    resultMap=getResultMap("0","删除订单失败");
                }
            }else {
                resultMap=getResultMap("0","该订单不能删除");
            }
        }else {
            resultMap=getResultMap("0","订单不存在");
        }
        return JSONUtil.getJson(resultMap);
    }

}
