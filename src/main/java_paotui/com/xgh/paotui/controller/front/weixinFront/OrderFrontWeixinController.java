package com.xgh.paotui.controller.front.weixinFront;

import com.alibaba.fastjson.JSONObject;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.*;
import com.xgh.paotui.services.*;
import com.xgh.paotui.weixin.AccessToken;
import com.xgh.paotui.weixin.WeixinHttpUtil;
import com.xgh.util.ConstantUtil;
import com.xgh.util.DictUtils;
import com.xgh.util.JSONUtil;
import com.xgh.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Api(value="微信端订单API")
@RequestMapping(value = "paotui/front/weixinFront/v1/order/")
public class OrderFrontWeixinController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(OrderFrontWeixinController.class);

    @Autowired
    protected ICustomerService customerService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderActionService orderActionService;

    @Autowired
    protected IDeliveryManService deliveryManService;

    @Autowired
    protected IOpenCityService openCityService;

    @ResponseBody
    @RequestMapping(value = "getOrderDeliveryMan", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取接单师傅信息", httpMethod = "GET", notes = "微信端")
    public String getOrderDeliveryMan(
            @ApiParam(value = "跑客身份标识") @RequestParam(value = "token") String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long deliveryManId=getDeliveryManIdByAppToken(token);
        if(deliveryManId==-1){
            return JSONUtil.getJson(getResultMap("-1","身份认证失败"));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        DeliveryMan deliveryMan = deliveryManService.get(deliveryManId);
        if (deliveryMan != null && deliveryMan.getId() > 0) {
            map.put("loginName", deliveryMan.getLoginName());
            map.put("realName", deliveryMan.getRealName());
            map.put("sex", deliveryMan.getSex());
            map.put("birthday", deliveryMan.getBirthday());
            map.put("mobilePhone", StringUtil.convertNullToEmpty(deliveryMan.getMobilePhone()));
            map.put("balance", deliveryMan.getBalance());
            map.put("starLevel", deliveryMan.getStarLevel());
            String belongType = StringUtil.convertNullToEmpty(deliveryMan.getBelongType());
            map.put("belongType", DictUtils.getDictLabel(belongType, "paotui_belong_type", ""));
            long belongCityId = deliveryMan.getBelongCityId();
            OpenCity openCity = openCityService.get(belongCityId);
            if (openCity != null && openCity.getId() > 0) {
                map.put("cityName", openCity.getCityName());
            }
            map.put("getOrderCount", deliveryMan.getGetOrderCount());
            map.put("praiseRate", deliveryMan.getPraiseRate());
            if (deliveryMan.getQrCode() != null && deliveryMan.getQrCode() != "") {
                String qrCode = ConstantUtil.SERVER_URL + deliveryMan.getQrCode();
                map.put("qrCode", qrCode);
            } else {
                map.put("qrCode", "");
            }
            if (deliveryMan.getHeadImage() != null && deliveryMan.getHeadImage() != "") {
                String headImage = ConstantUtil.SERVER_URL + deliveryMan.getHeadImage();
                map.put("headImage", headImage);
            } else {
                map.put("headImage", "");
            }
            resultMap = getResultMap("1", "获取接单师傅信息成功", map);
        } else {
            resultMap = getResultMap("0", "获取接单师傅信息失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getOrderStatusDescription", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "获取订单状态描述", httpMethod = "GET", notes = "微信端")
    public String getOrderStatusDescription(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        resultMap.put("orderId", orderId);
        List<OrderAction> orderActionServiceList = orderActionService.getList(resultMap);
        if (orderActionServiceList != null && orderActionServiceList.size() > 0) {
            for (OrderAction orderActionServiceList_ : orderActionServiceList) {
                String actionName = StringUtil.convertNullToEmpty(orderActionServiceList_.getActionName());
                Date actionTime = orderActionServiceList_.getActionTime();
                String actionImage = orderActionServiceList_.getActionImage();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("actionName",actionName);
                map.put("actionTime",actionTime);
                if (!"".equals(actionImage)) {
                    String actionImage_ = ConstantUtil.SERVER_URL+actionImage;
                    map.put("actionImage", actionImage_);
                }
                mapList.add(map);
            }
            resultMap = getResultMap("1", "获取订单状态描述成功",mapList);
        } else {
            resultMap = getResultMap("0", "获取订单状态描述失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "getOrderEndWeixinPush", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "订单完成微信推送接口", httpMethod = "GET", notes = "微信端")
    public String getOrderEndWeixinPush(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order= orderService.get(orderId);
        if(order!=null&&order.getId()>0){

            Customer customer = customerService.get(order.getCustomerId());
            if(StringUtils.isEmpty(customer.getToken())){
                resultMap = getResultMap("0","订单完成微信推送失败,用户没有绑定本微信公众平台");
                return JSONUtil.getJson(resultMap);
            }

            CloseableHttpClient httpClient = WeixinHttpUtil.getHTTPSClient();
            String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
            url+= AccessToken.getInstance().getAccessToken();
            //推送消息
            String pushJson=this.getOrderFinishJson(order,customer.getToken());
            String result = WeixinHttpUtil.postJson(httpClient,url,pushJson);
            JSONObject resultJson=  JSONObject.parseObject(result);
            if(resultJson.getInteger("errcode")==0){
                resultMap = getResultMap("1","订单完成微信推送成功",resultJson);
            }else{
                resultMap = getResultMap("0","订单完成微信推送失败",resultJson);
            }
        }else{
            resultMap = getResultMap("0","订单完成微信推送失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    public String getOrderFinishJson(Order order,String openid){

        JSONObject pushJson= new JSONObject();
        pushJson.put("touser",openid);
        pushJson.put("template_id","eYeTv2LF586ODsI_tnsBemHK3W17NV39QKr1DSteY3M");
        pushJson.put("url","http://www.baidu.com");

        JSONObject dataJson= new JSONObject();

        JSONObject dataJson1= new JSONObject();
        dataJson1.put("value","尊敬的客户，您的订单已完成。");
        dataJson1.put("color","#173177");
        dataJson.put("first",dataJson1);

        JSONObject dataJson2= new JSONObject();
        dataJson2.put("value",DictUtils.getDictLabel(String.valueOf(order.getOrderType()),"paotui_order_type",""));
        dataJson2.put("color","#173177");
        dataJson.put("keyword1",dataJson2);

        JSONObject dataJson3= new JSONObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dataJson3.put("value",simpleDateFormat.format(order.getSignTime()));
        dataJson3.put("color","#173177");
        dataJson.put("keyword2",dataJson3);

        JSONObject remark= new JSONObject();
        remark.put("value","感谢您对我们的支持和理解，期待再次为您服务，祝您生活愉快！");
        remark.put("color","#173177");
        dataJson.put("remark",remark);

        pushJson.put("data",dataJson);

        return pushJson.toString();
    }

    @ResponseBody
    @RequestMapping(value = "getOrderRobSuccessPush", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "抢单成功微信推送接口", httpMethod = "GET", notes = "微信端")
    public String getOrderRobSuccessPush(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order= orderService.get(orderId);
        if(order!=null&&order.getId()>0){

            Customer customer = customerService.get(order.getCustomerId());
            if(StringUtils.isEmpty(customer.getToken())){
                resultMap = getResultMap("0","抢单成功微信推送失败,用户没有绑定本微信公众平台");
                return JSONUtil.getJson(resultMap);
            }

            CloseableHttpClient httpClient = WeixinHttpUtil.getHTTPSClient();
            String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
            url+= AccessToken.getInstance().getAccessToken();
            //推送消息
            String pushJson=this.getOrderRobSuccessJson(order,customer.getToken());
            String result = WeixinHttpUtil.postJson(httpClient,url,pushJson);
            JSONObject resultJson=  JSONObject.parseObject(result);
            if(resultJson.getInteger("errcode")==0){
                resultMap = getResultMap("1","抢单成功微信推送成功",resultJson);
            }
            else{
                resultMap = getResultMap("0","抢单成功微信推送失败",resultJson);
            }

        }else{
            resultMap = getResultMap("0","抢单成功微信推送失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    public String getOrderRobSuccessJson(Order order,String openid){

        JSONObject pushJson= new JSONObject();
        pushJson.put("touser",openid);
        pushJson.put("template_id","evYtOnS-exUOIhNC5sUmFudzXR18ZdFNpifReP1B2jI");
        pushJson.put("url","http://www.baidu.com");

        JSONObject dataJson= new JSONObject();

        JSONObject dataJson1= new JSONObject();
        dataJson1.put("value","您好，您的订单已经被抢单成功，请耐心等待。");
        dataJson1.put("color","#173177");
        dataJson.put("first",dataJson1);

        JSONObject dataJson2= new JSONObject();
        dataJson2.put("value",order.getOrderNo());
        dataJson2.put("color","#173177");
        dataJson.put("keyword1",dataJson2);

        JSONObject dataJson3= new JSONObject();
        dataJson3.put("value",DictUtils.getDictLabel(String.valueOf(order.getOrderType()),"paotui_order_type",""));
        dataJson3.put("color","#173177");
        dataJson.put("keyword2",dataJson3);

        //配送地址
        JSONObject dataJson4= new JSONObject();
        dataJson4.put("value","");
        dataJson4.put("color","#173177");
        dataJson.put("keyword3",dataJson4);

        //配送人
        JSONObject dataJson5= new JSONObject();
        dataJson5.put("value",order.getDeliveryManName());
        dataJson5.put("color","#173177");
        dataJson.put("keyword4",dataJson5);

        JSONObject dataJson6= new JSONObject();
        dataJson6.put("value",order.getDeliveryManPhone());
        dataJson6.put("color","#173177");
        dataJson.put("keyword5",dataJson6);

        JSONObject remark= new JSONObject();
        remark.put("value","感谢您对我们的支持和理解，期待再次为您服务，祝您生活愉快！");
        remark.put("color","#173177");
        dataJson.put("remark",remark);

        pushJson.put("data",dataJson);

        return pushJson.toString();
    }

    @ResponseBody
    @RequestMapping(value = "getCancelOrderWeixinPush", method = RequestMethod.GET, produces = {"application/json; charset=utf-8"})
    @ApiOperation(value = "订单取消微信推送接口", httpMethod = "GET", notes = "微信端")
    public String getCancelOrderWeixinPush(
            @ApiParam(value = "订单id") @RequestParam(value = "orderId") long orderId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order= orderService.get(orderId);
        if(order!=null&&order.getId()>0){
            Customer customer = customerService.get(order.getCustomerId());
            if(StringUtils.isEmpty(customer.getToken())){
                resultMap = getResultMap("0","订单取消微信推送失败,用户没有绑定本微信公众平台");
                return JSONUtil.getJson(resultMap);
            }
            CloseableHttpClient httpClient = WeixinHttpUtil.getHTTPSClient();
            String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
            url+= AccessToken.getInstance().getAccessToken();
            //推送消息
            String pushJson=this.getCancelOrderFinishJson(order,customer.getToken());
            String result = WeixinHttpUtil.postJson(httpClient,url,pushJson);
            JSONObject resultJson=  JSONObject.parseObject(result);
            if(resultJson.getInteger("errcode")==0){
                resultMap = getResultMap("1","订单取消微信推送成功",resultJson);
            }else{
                resultMap = getResultMap("0","订单取消微信推送失败",resultJson);
            }
        }else{
            resultMap = getResultMap("0","订单取消微信推送失败");
        }
        return JSONUtil.getJson(resultMap);
    }

    public String getCancelOrderFinishJson(Order order,String openid){
        JSONObject pushJson= new JSONObject();
        pushJson.put("touser",openid);
        pushJson.put("template_id","4OpZMwX4LYukunhY2W6JEKkK-GfnWC3nhWOtci5wivA");
        pushJson.put("url","http://www.baidu.com");

        JSONObject dataJson= new JSONObject();

        JSONObject dataJson1= new JSONObject();
        String orderType = DictUtils.getDictLabel(String.valueOf(order.getOrderType()),"paotui_order_type","");
        dataJson1.put("value","尊敬的客户，您的"+orderType+"订单已取消。");
        dataJson1.put("color","#173177");
        dataJson.put("first",dataJson1);

        JSONObject dataJson2= new JSONObject();
        dataJson2.put("value",order.getOrderNo());
        dataJson2.put("color","#173177");
        dataJson.put("keyword1",dataJson2);

        JSONObject dataJson3= new JSONObject();
        dataJson3.put("value",String.valueOf(order.getActualFreight()));
        dataJson3.put("color","#173177");
        dataJson.put("keyword2",dataJson3);

        JSONObject dataJson4= new JSONObject();
        dataJson4.put("value",order.getData3());
        dataJson4.put("color","#173177");
        dataJson.put("keyword3",dataJson4);

        JSONObject remark= new JSONObject();
        remark.put("value","感谢您对我们的支持和理解，期待再次为您服务，祝您生活愉快！");
        remark.put("color","#173177");
        dataJson.put("remark",remark);

        pushJson.put("data",dataJson);

        return pushJson.toString();
    }
}