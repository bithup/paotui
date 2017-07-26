package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.dao.*;
import com.xgh.paotui.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("deliveryManStateService")
public class DeliveryManStateServiceImpl extends BaseService implements IDeliveryManStateService{

    @Autowired
    protected IDeliveryManDao deliveryManDao;

    @Autowired
    protected IDeliveryManStateDao deliveryManStateDao;

    @Autowired
    protected IOpenCityDao openCityDao;

    @Autowired
    protected IOrderDao orderDao;

    @Autowired IOrderPositionPathDao orderPositionPathDao;

    @Autowired
    protected IOrderTaskSettingDao orderTaskSettingDao;

    public DeliveryManState get(Long id){
        return deliveryManStateDao.get(id);
    }

    public int save(HttpServletRequest request,DeliveryManState deliveryManState){
        return  deliveryManStateDao.add(deliveryManState);
    }

    public int update(DeliveryManState deliveryManState){
        return deliveryManStateDao.update(deliveryManState);
    }

    public List<DeliveryManState> getList(Map<String, Object> map){
        return deliveryManStateDao.getList(map);
    }

    public int addDeliveryManState(DeliveryManState deliveryManState){
        int flag = deliveryManStateDao.update(deliveryManState);
        if (flag>0){
            String data3 = deliveryManState.getData3();
            if (data3!=null&&!"".equals(data3)){
                String[] orderIdList = data3.split(",");
                for (int i=0;i<orderIdList.length;i++){
                    String orderId = orderIdList[i];
                    OrderPositionPath orderPositionPath = new OrderPositionPath();
                    orderPositionPath.setOrderId(Long.parseLong(orderId));
                    orderPositionPath.setLongitude(deliveryManState.getNowLongitude());
                    orderPositionPath.setLatitude(deliveryManState.getNowLatitude());
                    orderPositionPath.setCreateDate(new Date());
                    orderPositionPath.setStatus(1);
                    orderPositionPathDao.add(orderPositionPath);
                }
            }
        }
        return flag;
    }

    /**
     * 返回跑客是否可以接新单
     * @param deliveryManId
     * @return 1：可以接单；2：有任务不能接单
     */
    public int getGainNewOrder(Long deliveryManId){
        int ret=2;
        int maxTaskNum=1;//最大抢单数默认1个订单
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("deliveryManId",deliveryManId);
        //3、进行中；4、待收货
        map.put("orderStatusList","3,4");
        List<Order>  orders=orderDao.getList(map);
        //当前在任务的订单
        int nowTaskOrder=0;
        for(Order data:orders){
            if(1==data.getBookingType()){
                //立即订单
                nowTaskOrder=+1;
            } else{
                //预约订单 3、进行中；4、待收货
                if(data.getOrderStatus()==3 || data.getOrderStatus()==4){
                    nowTaskOrder=+1;
                }
            }
        }
        DeliveryMan deliveryMan= deliveryManDao.get(deliveryManId);
        Map<String, Object> orderTaskSettingMap=new HashMap<String, Object>();
        orderTaskSettingMap.put("openCityId",deliveryMan.getBelongCityId());
        orderTaskSettingMap.put("level",deliveryMan.getStarLevel());
        List<OrderTaskSetting> orderTaskSettings = orderTaskSettingDao.getList(orderTaskSettingMap);
        if(orderTaskSettings.size()>0){
            OrderTaskSetting orderTaskSetting=orderTaskSettings.get(0);
            if(1==deliveryMan.getBelongType()){
            //众包员工
                maxTaskNum=   orderTaskSetting.getCrowdsourcingOrderNum();
            }
            else{
                //公司员工
                maxTaskNum=   orderTaskSetting.getStaffOrderNum();
            }
        }
        if(nowTaskOrder<maxTaskNum){
            ret=1;//可以接新单
        }
        return ret;
    }

    /**
     * 跑客抢单
     * @param deliveryManId:跑客id
     * @param orderId:订单id
     * @return 1：抢单成功；2：抢单失败  3：订单被别人抢了 4:订单被自己抢过了
     */
    public int updateOfGainNewOrder(long deliveryManId, long orderId){
        int ret;
        Order order = orderDao.get(orderId);
        DeliveryMan deliveryMan = deliveryManDao.get(deliveryManId);
        if(order.getOrderStatus()!=2){
            if (order.getOrderStatus()==1){
                //订单未支付
                ret=2;
            }else if(order.getDeliveryManId().equals(deliveryManId)){
                //订单被自己抢过了
                ret=3;
            } else{
                //订单被别人抢了
                ret=4;
            }
        }else {
            //状态设置为进行中
            order.setOrderStatus(3);
            order.setDeliveryManId(deliveryManId);
            order.setDeliveryManName(deliveryMan.getRealName());
            order.setDeliveryManPhone(deliveryMan.getLoginName());
            order.setDeliveryManHeadImage(deliveryMan.getHeadImage());
            order.setDeliveryOrderTime(new Date());
            ret=orderDao.update(order);
            if(ret==1){
                //新增操作步骤
                OrderAction orderAction =new OrderAction();
                orderAction.setActionName("跑客抢单成功");
                orderAction.setOrderId(orderId);
                orderAction.setActionType(1);//完成动作
                orderAction.setShowFlag(1);
                orderAction.setActionTime(new Date());
                //抢单后判断能不能继续接单
                int gainNewOrder = this.getGainNewOrder(deliveryManId);
                DeliveryManState  deliveryManState= deliveryManStateDao.get(deliveryManId);
                //设置是否可以再接单
                deliveryManState.setGainNewOrder(gainNewOrder);
                deliveryManState.setStateUpdateTime(new Date());
                deliveryManStateDao.update(deliveryManState);
            }
        }
        return ret;
    }
}