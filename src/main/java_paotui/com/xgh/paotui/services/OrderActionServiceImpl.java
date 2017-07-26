package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.dao.*;
import com.xgh.paotui.entity.*;
import com.xgh.util.ShortMessageUtil;
import com.xgh.util.ValidationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tian on 2017/2/23.
 */
@Service("orderActionService")
public class OrderActionServiceImpl  extends BaseService implements IOrderActionService {

    @Autowired
    protected IOrderDao orderDao;

    @Autowired
    protected IOrderLineupDao orderLineupDao;

    @Autowired
    protected IOrderActionDao orderActionDao;

    @Autowired
    protected IDeliveryManDao deliveryManDao;

    @Autowired
    protected IDeliveryManStateDao deliveryManStateDao;

    @Autowired
    protected IFeeAllocationDao feeAllocationDao;

    @Autowired
    protected ICapitalLogDao capitalLogDao;

    public int add(OrderAction orderAction){
        return orderActionDao.add(orderAction);
    }

    public int update(OrderAction orderAction) {
        return orderActionDao.update(orderAction);
    }

    /**
     * 保存主题
     *
     * @param request
     * @param orderAction
     * @return
     */
    public int save(HttpServletRequest request, OrderAction orderAction) {

        //此处认为不为空，进行操作，是否为空的判断在controller中进行
        if (orderAction != null && orderAction.getId() < 1) {
            //该处认为是添加操作
            Date date = new Date();

            orderAction.setStatus(1);
            int ret=orderActionDao.add(orderAction);
            return ret;
        } else {
            OrderAction orderAction2 = get(orderAction.getId());

            return update(orderAction2);
        }
    }

    public int delete(long id) {
        return orderActionDao.deleteById(id);
    }

    public OrderAction get(long id) {

        OrderAction orderAction = orderActionDao.get(id);

        return orderAction;
    }


    public List<OrderAction> getList(Map<String, Object> map) {
        return orderActionDao.getList(map);
    }

    public Map<String, Object> getGridList(HttpServletRequest request) {

        String page = request.getParameter("page");
        String pagesize = request.getParameter("pagesize");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("instId", getCurrentInstId(request));
        map.put("unitId", getCurrentUnitId(request));
        map.put("type", request.getParameter("type"));
        map.put("page", Integer.parseInt(page));
        map.put("pagesize", Integer.parseInt(pagesize));

        Map<String, Object> gridMap = new HashMap<String, Object>();

//        List<OrderAction> dataList = orderActionDao.getListPage(map);
//        if (dataList == null) {
//            dataList = new ArrayList<OrderAction>();
//        }

        List<Map<String, Object>> dataList = orderActionDao.getListMapPage(map);

        gridMap.put("Rows", dataList);
        gridMap.put("Total", orderActionDao.getRows(map));
        return gridMap;
    }


    /**
     * 更新帮送订单操作轨迹
     * @param orderId
     * @param actionStep
     * @param orderAction
     */
    public int updateSendOrderActionStep(long deliveryManId,long orderId,int actionStep,OrderAction orderAction){
        //在执行动作之前先获取到进行中动作把显示标志修改成不显示，然后再执行动作
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("orderId",orderId);
        map.put("actionType",2);
        List<OrderAction> orderActionsList=orderActionDao.getList(map);
        for(OrderAction orderActionsList_:orderActionsList){
            orderActionsList_.setShowFlag(2);
            orderActionDao.update(orderActionsList_);
        }

        Order order = orderDao.get(orderId);
        //先执行联系下单人动作，再执行前往取货地动作
        if(actionStep==1){
            int step1 = orderActionDao.add(orderAction);
            if (step1>0){
                if(order.getBookingType()==1){
                    orderAction.setActionName("跑客正在前往取货地的路上");
                }else{
                    orderAction.setActionName("跑客将在预约时间前往取货地");
                }
                orderAction.setActionType(2);
                orderActionDao.add(orderAction);
            }
        }
        //执行到达取货地动作，然后把订单id存入跑客接单状态表中
        else if (actionStep==2){
            orderActionDao.add(orderAction);
            Map<String, Object> map2=new HashMap<String, Object>();
            map2.put("deliverManId",deliveryManId);
            List<DeliveryManState> deliveryManStateList = deliveryManStateDao.getList(map2);
            DeliveryManState deliveryManState = deliveryManStateList.get(0);
            String data3 = deliveryManState.getData3();
            if (data3!=null&&!"".equals(data3)){
                deliveryManState.setData3(data3+","+String.valueOf(orderId));
            }else {
                deliveryManState.setData3(String.valueOf(orderId));
            }
            deliveryManStateDao.update(deliveryManState);
        }
        //先执行已取货动作，然后会把收货验证码添加到订单信息中
        else if(actionStep==4){
            orderActionDao.add(orderAction);
            //发送短信验证码
            String code = ValidationCode.getSecurityCode(4,3);
            String content = "您的收货验证码为："+code+"。请在收到货时出示！";
            int flag = ShortMessageUtil.sendMessage(order.getCustomerPhone(),content);
            if(flag<1){
                //验证码发送失败,只再重新发送一次
                ShortMessageUtil.sendMessage(order.getCustomerPhone(),content);
            }
            order.setSmsCode(code);
        }
        //先执行联系收货人动作，再执行前往收货地动作
        else if(actionStep==5){
            int step5 = orderActionDao.add(orderAction);
            if (step5>0){
                orderAction.setActionName("跑客正在前往收货地的路上");
                orderAction.setActionType(2);
                orderActionDao.add(orderAction);
            }
        }
        //执行到达收货地动作
        else if(actionStep==6){
            orderActionDao.add(orderAction);
            order.setOrderStatus(4);
        }
        //修改订单状态、跑客当前操作步骤
        order.setActionStep(actionStep);
        return orderDao.update(order);
    }

    /**
     * 更新帮送、帮取验证码验证订单操作
     * @param order
     * @param orderAction
     * @param deliveryMan
     * @param capitalLog
     */
    public int updateSendSmsCodeOrderAction(long deliveryManId,Order order, OrderAction orderAction, DeliveryMan deliveryMan,CapitalLog capitalLog){
        int ret = 0;
        if (order!=null&&order.getId()>0){
            /*执行输入验证码动作*/
            orderActionDao.add(orderAction);
            /*改变订单状态*/
            orderDao.update(order);
            /*跑客获取佣金*/
            deliveryManDao.update(deliveryMan);
            /*添加资金日志*/
            capitalLogDao.add(capitalLog);
            /*把当前订单id从跑客接单状态表中去除*/
            String orderId = String.valueOf(order.getId());
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("deliverManId",deliveryManId);
            List<DeliveryManState> deliveryManStateList = deliveryManStateDao.getList(map);
            DeliveryManState deliveryManState = deliveryManStateList.get(0);
            String data3 = deliveryManState.getData3();
            String data3_ = null;
            String[] orderIdList = data3.split(",");
            for (int i=0; i<orderIdList.length;i++){
                if (!orderId.equals(orderIdList[i])){
                    if (data3_ !=null&&!"".equals(data3_)){
                        data3_ = data3_+","+orderIdList[i];
                    }else {
                        data3_ = orderIdList[i];
                    }
                }
            }
            deliveryManState.setData3(data3_);
            deliveryManStateDao.update(deliveryManState);
            ret = 1;
        }
        return ret;
    }

    /**
     * 更新帮买订单操作动作
     * @param orderId
     * @param actionStep
     * @param orderAction
     */
    public int updateBuyOrderActionStep(long deliveryManId,long orderId,int actionStep,OrderAction orderAction){
        //在执行动作之前先获取到进行中动作把显示标志修改成不显示，然后再执行动作
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("orderId",orderId);
        map.put("actionType",2);
        List<OrderAction> orderActionsList=orderActionDao.getList(map);
        for(OrderAction orderActionsList_:orderActionsList){
            orderActionsList_.setShowFlag(2);
            orderActionDao.update(orderActionsList_);
        }
        Order order = orderDao.get(orderId);
        //先执行联系下单人动作，再执行前往购买地动作
        if(actionStep==1){
            int step1 = orderActionDao.add(orderAction);
            if (step1>0){
                if(order.getBookingType()==1){
                    orderAction.setActionName("跑客正在前往购买地的路上");
                }else{
                    orderAction.setActionName("跑客将在预约时间前往购买地");
                }
                orderAction.setActionType(2);
                orderAction.setActionTime(new Date());
                orderActionDao.add(orderAction);
            }
        }
        //执行到达购买地动作，然后把订单id存入跑客接单状态表中
        else if (actionStep==2){
            orderActionDao.add(orderAction);
            Map<String, Object> map2=new HashMap<String, Object>();
            map2.put("deliverManId",deliveryManId);
            List<DeliveryManState> deliveryManStateList = deliveryManStateDao.getList(map2);
            DeliveryManState deliveryManState = deliveryManStateList.get(0);
            String data3 = deliveryManState.getData3();
            if (data3!=null&&!"".equals(data3)){
                deliveryManState.setData3(data3+","+String.valueOf(orderId));
            }else {
                deliveryManState.setData3(String.valueOf(orderId));
            }
            deliveryManStateDao.update(deliveryManState);
        }
        //先执行购买完成动作，然后会把收货验证码添加到订单信息中
        else if(actionStep==4){
            int step4 = orderActionDao.add(orderAction);
            if (step4>0){
                String code = ValidationCode.getSecurityCode(4,3);
                String content = "您的收货验证码为："+code+"。请在收到货时出示！";
                int flag = ShortMessageUtil.sendMessage(order.getCustomerPhone(),content);
                if(flag<1){
                    ShortMessageUtil.sendMessage(order.getCustomerPhone(),content);
                }
                order.setSmsCode(code);
            }
        }
        //先执行联系收货人动作，再执行前往收货地动作
        else if(actionStep==5){
            int step5 = orderActionDao.add(orderAction);
            if (step5>0){
                orderAction.setActionName("跑客正在前往收货地的路上");
                orderAction.setActionType(2);
                orderAction.setActionTime(new Date());
                orderActionDao.add(orderAction);
            }
        }
        //执行到达收货地动作
        else if(actionStep==6){
            orderActionDao.add(orderAction);
            order.setOrderStatus(4);
        }
        //修改跑客当前操作步骤、订单状态
        order.setActionStep(actionStep);
        int flag = orderDao.update(order);
        return flag;
    }

    /**
     * 更新帮买验证码验证订单操作
     * @param order
     * @param orderAction
     * @param deliveryMan
     * @param capitalLog
     */
    public int updateBuySmsCodeOrderAction(long deliveryManId,Order order, OrderAction orderAction, DeliveryMan deliveryMan,CapitalLog capitalLog){
        int ret = 0;
        if (order!=null&&order.getId()>0){
            /*执行输入验证码动作*/
            orderActionDao.add(orderAction);
            /*改变订单状态*/
            orderDao.update(order);
            /*跑客获取佣金*/
            deliveryManDao.update(deliveryMan);
            /*添加资金日志*/
            capitalLogDao.add(capitalLog);
            /*订单完成把当前订单id从跑客接单状态表中去除*/
            String orderId = String.valueOf(order.getId());
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("deliverManId",deliveryManId);
            List<DeliveryManState> deliveryManStateList = deliveryManStateDao.getList(map);
            DeliveryManState deliveryManState = deliveryManStateList.get(0);
            String data3 = deliveryManState.getData3();
            String data3_ = null;
            String[] orderIdList = data3.split(",");
            for (int i=0; i<orderIdList.length;i++){
                if (!orderId.equals(orderIdList[i])){
                    if (data3_ !=null&&!"".equals(data3_)){
                        data3_ = data3_+","+orderIdList[i];
                    }else {
                        data3_ = orderIdList[i];
                    }
                }
            }
            deliveryManState.setData3(data3_);
            deliveryManStateDao.update(deliveryManState);
            ret = 1;
        }
        return ret;
    }

    /**
     * 更新代排队订单操作动作
     * @param orderId
     * @param actionStep
     * @param orderAction
     */
    public int updateLineupOrderActionStep(long deliveryManId,long orderId,int actionStep,OrderAction orderAction){
        //在执行动作之前先获取到进行中动作把显示标志修改成不显示，然后再执行动作
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("orderId",orderId);
        map.put("actionType",2);
        List<OrderAction> orderActionsList=orderActionDao.getList(map);
        for(OrderAction orderActionsList_:orderActionsList){
            orderActionsList_.setShowFlag(2);
            orderActionDao.update(orderActionsList_);
        }
        Order order = orderDao.get(orderId);
        OrderLineup orderLineup = orderLineupDao.getLineup(orderId);
        //先执行联系下单人动作，再执行前往排队地动作
        if(actionStep==1){
            int step1 = orderActionDao.add(orderAction);
            if (step1>0){
                if(order.getBookingType()==1){
                    orderAction.setActionName("跑客正在前往排队地的路上");
                }else{
                    orderAction.setActionName("跑客将在预约时间前往排队地");
                }
                orderAction.setActionType(2);
                orderActionDao.add(orderAction);
            }
        }
        //执行到达排队地动作
        else if (actionStep==2){
            orderActionDao.add(orderAction);
        }
        //先执行开始计时动作，然后会把验证码添加到订单信息中
        else if(actionStep==4){
            int step4 = orderActionDao.add(orderAction);
            if (step4>0){
                String code = ValidationCode.getSecurityCode(4,3);
                String content = "您的验证码为："+code+"。请在排队完成时出示！";
                int flag = ShortMessageUtil.sendMessage(order.getCustomerPhone(),content);
                if(flag<1){
                    ShortMessageUtil.sendMessage(order.getCustomerPhone(),content);
                }
                order.setSmsCode(code);
                orderLineup.setLineupBeginTime(new Date());
            }
        }
        //执行结束计时动作
        else if(actionStep==5){
            orderActionDao.add(orderAction);
            order.setOrderStatus(4);
            orderLineup.setLineupEndTime(new Date());
        }
        //修改跑客当前操作步骤、订单状态
        order.setActionStep(actionStep);
        int flag = orderDao.update(order);
        if (flag>0){
            orderLineupDao.update(orderLineup);
        }
        return flag;
    }

    /**
     * 更新代排队验证码验证订单操作
     * @param order
     * @param orderAction
     * @param deliveryMan
     * @param capitalLog
     */
    public int updateLineupSmsCodeOrderAction(Order order, OrderAction orderAction, DeliveryMan deliveryMan,CapitalLog capitalLog){
        int ret = 0;
        if (order!=null&&order.getId()>0){
            /*执行输入验证码动作*/
            orderActionDao.add(orderAction);
            /*改变订单状态*/
            orderDao.update(order);
            /*跑客获取佣金*/
            deliveryManDao.update(deliveryMan);
            /*添加资金日志*/
            capitalLogDao.add(capitalLog);
            ret = 1;
        }
        return ret;
    }

}