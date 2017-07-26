package com.xgh.paotui.controller.task;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.xgh.util.DateUtil;
import com.xgh.util.StringUtil;
import org.json.simple.JSONObject;
import com.xgh.paotui.entity.DeliveryManState;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.services.IDeliveryManService;
import com.xgh.paotui.services.IDeliveryManStateService;
import com.xgh.paotui.services.IOpenCityService;
import com.xgh.paotui.services.IOrderService;
import com.xgh.util.MapUtil;
import com.xgh.util.PaotuiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScheduledTask {

    @Autowired
    protected IOpenCityService openCityService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IDeliveryManStateService deliveryManStateService;
    /**
     * 推送可以抢的订单给跑腿师傅
     */
    @Scheduled(cron = "0 10 * * * ? ")
    //@Scheduled(cron = "0/10 * * * * ? ")
   public void pushOrdersToDeliveryMan() {
         //System.out.println("每10分钟执行一次");
        Map<String, Object> openCityMap=new HashMap<String, Object>();
        List<OpenCity> openCitys= openCityService.getList(openCityMap);

        //推送抢单接口，使用消息队列
        JPushClient jpushClient = new JPushClient(PaotuiUtil.JPUSH_MASTER_SECRET, PaotuiUtil.JPUSH_APP_KEY, null, ClientConfig.getInstance());


        //按照城市推送
        for(OpenCity openCity:openCitys){
            Map<String, Object> orderMap=new HashMap<String, Object>();
            //等待接单
            orderMap.put("orderStatus",2);
            //所属城市
            orderMap.put("orderCity",openCity.getId());
            List<Map<String, Object>> orderInfos = orderService.getOrderInfoList(orderMap);

            //开工，而且可以抢单的跑客
            Map<String, Object> deliveryManStateMap=new HashMap<String, Object>();
            //开工
            deliveryManStateMap.put("workStatus",1);

            //可以接单
            deliveryManStateMap.put("gainNewOrder",1);

            //所属城市
            deliveryManStateMap.put("openCityId",openCity.getId());

            List<DeliveryManState>  deliveryManStates= deliveryManStateService.getList(deliveryManStateMap);


            for(Map<String, Object> orderInfo:orderInfos){
                for( DeliveryManState deliveryManState:deliveryManStates){
                    //计算距离
                    double distance = MapUtil.GetDistance((Double)orderInfo.get("latitude"),(Double)orderInfo.get("longitude"),deliveryManState.getNowLatitude(),deliveryManState.getNowLongitude());

                    if(distance < PaotuiUtil.DEFAULT_ORDER_DISTANCE){
                        //进行极光推送

                        Map<String, String> extras  = new JSONObject();
                        extras.put("orderId",String.valueOf(orderInfo.get("id")));
                        extras.put("distance",String.valueOf(distance));

                        extras.put("originalFreight",String.valueOf(orderInfo.get("originalFreight")));
                        extras.put("tips",StringUtil.convertNullToEmpty(orderInfo.get("tips")));

                        extras.put("bookingType",String.valueOf(orderInfo.get("bookingType")));
                        if((Integer)orderInfo.get("bookingType")==2){
                            Date bookingTime=(Date)orderInfo.get("bookingTime");
                            extras.put("bookingTime", DateUtil.getChineseDateTime(bookingTime));
                        }


                        String orderType=String.valueOf(orderInfo.get("orderType"));
                        if("1".equals(orderType)  || "2".equals(orderType) ){
                            //帮送或帮取
                            String addressA = "";
                            addressA += StringUtil.convertNullToEmpty(orderInfo.get("locationAddressA"));
                            addressA += StringUtil.convertNullToEmpty(orderInfo.get("locationAddressNameA"));
                            addressA += StringUtil.convertNullToEmpty(orderInfo.get("detailAddressA"));
                            extras.put("addressA", addressA);

                            String addressB = "";
                            addressB += StringUtil.convertNullToEmpty(orderInfo.get("locationAddressB"));
                            addressB += StringUtil.convertNullToEmpty(orderInfo.get("locationAddressNameB"));
                            addressB += StringUtil.convertNullToEmpty(orderInfo.get("detailAddressB"));
                            extras.put("addressB", addressB);

                            extras.put("remark", StringUtil.convertNullToEmpty(orderInfo.get("remark")));

                        }
                        else if("3".equals(orderType)){
                            int isNearBuy=(Integer)orderInfo.get("isNearBuy");
                            if(isNearBuy==1){
                                extras.put("addressA", "就近购买");
                            }
                            else{
                                String addressA = "";
                                addressA += StringUtil.convertNullToEmpty(orderInfo.get("locationAddressA"));
                                addressA += StringUtil.convertNullToEmpty(orderInfo.get("locationAddressNameA"));
                                addressA += StringUtil.convertNullToEmpty(orderInfo.get("detailAddressA"));
                                extras.put("addressA", addressA);
                            }

                            String addressB = "";
                            addressB += StringUtil.convertNullToEmpty(orderInfo.get("locationAddressB"));
                            addressB += StringUtil.convertNullToEmpty(orderInfo.get("locationAddressNameB"));
                            addressB += StringUtil.convertNullToEmpty(orderInfo.get("detailAddressB"));
                            extras.put("addressB", addressB);


                            extras.put("buyRequire", StringUtil.convertNullToEmpty(orderInfo.get("buyRequire")));

                            int isKonwPrice=(Integer)orderInfo.get("isKonwPrice");
                            extras.put("isKonwPrice", StringUtil.convertNullToEmpty(orderInfo.get("isKonwPrice")));
                            if(isKonwPrice==1){
                                extras.put("buyPrice", StringUtil.convertNullToEmpty(orderInfo.get("buyPrice")));
                            }


                        }
                        System.out.println("推送的可抢订单信息："+extras.toString());
                        //极光推送
//                        Message message=Message.newBuilder()
//                                .setTitle("抢单通知")
//                                .addExtras(extras)
//                                .setMsgContent("")
//                                .build();
//
//
//
//                        // For push, all you need do is to build PushPayload object.
//                        PushPayload payload = PushPayload.newBuilder()
//                                .setPlatform(Platform.all())
//                                //.setAudience(Audience.alias("d"+String.valueOf(deliveryManState.getDeliverManId())))
//                                .setAudience(Audience.alias("10000000377"))
//                               .setMessage(message)
//                                .build();
//
//                        try {
//                            PushResult result = jpushClient.sendPush(payload);
//
//
//                        } catch (APIConnectionException e) {
//                            e.printStackTrace();
//
//                        } catch (APIRequestException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }
        }



    }
}
