package com.xgh.weixin;

import com.xgh.paotui.entity.DeliverManMarket;
import com.xgh.paotui.services.IDeliveryManMarketService;
import com.xgh.paotui.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信xml消息处理流程逻辑类
 *
 */
public class WechatProcess {

    /**
     * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
     * @param xml 接收到的微信数据
     * @return  最终的解析结果（xml格式数据）
     */
    public static String processWechatMag(String xml){
        /** 解析xml数据 */
        ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

        String result = "";
        if("text".endsWith(xmlEntity.getMsgType())){

        }
        else if("event".endsWith(xmlEntity.getMsgType())){

            /** 用户未关注时，进行关注后的事件推送 */
            if("subscribe".equals(xmlEntity.getEvent())){
                //
                String OpenID=xmlEntity.getFromUserName();
                System.out.println("OpenID:"+OpenID);
                String EventKey=xmlEntity.getEventKey();
                System.out.println("EventKey:"+EventKey);
                String id=EventKey.replace("qrscene_","");

                WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
                IDeliveryManMarketService deliveryManMarketService=(IDeliveryManMarketService)applicationContext.getBean("deliveryManMarketService");

                Map<String, Object> map= new HashMap<String, Object>();
                map.put("openId",OpenID);
                List<DeliverManMarket>  deliverManMarkets= deliveryManMarketService.getList(map);
                if(deliverManMarkets.size()==0){
                    DeliverManMarket deliverManMarket=new DeliverManMarket();
                    deliverManMarket.setDeliveryManId(Long.parseLong(id));
                    deliverManMarket.setOpenId(OpenID);
                    deliverManMarket.setMarketTime(new Date());
                    deliverManMarket.setStatus(1);
                    deliveryManMarketService.add(deliverManMarket);

                }


            }

        }

        /**
         * */
        //result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);

        return result;
    }
}