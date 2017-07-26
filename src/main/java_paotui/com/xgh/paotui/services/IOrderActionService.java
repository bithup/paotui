package com.xgh.paotui.services;

import com.xgh.paotui.entity.CapitalLog;
import com.xgh.paotui.entity.DeliveryMan;
import com.xgh.paotui.entity.Order;
import com.xgh.paotui.entity.OrderAction;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Tian on 2017/2/23.
 */
public interface IOrderActionService {

    public int add(OrderAction orderAction);

    public int update(OrderAction orderAction);

    public int save(HttpServletRequest request, OrderAction rderAction);

    public int delete(long id);

    public OrderAction get(long id);

    public List<OrderAction> getList(Map<String, Object> map);

    public Map<String,Object> getGridList(HttpServletRequest request);

    /**
     * 更新帮送、帮取订单操作轨迹
     * @param deliveryManId
     * @param orderId
     * @param actionStep
     * @param orderAction
     */
    public int updateSendOrderActionStep(long deliveryManId,long orderId,int actionStep,OrderAction orderAction);

    /**
     * 更新帮送、帮取验证码验证订单操作
     * @param order
     * @param orderAction
     * @param deliveryMan
     * @param capitalLog
     */
    public int updateSendSmsCodeOrderAction(long deliveryManId,Order order, OrderAction orderAction, DeliveryMan deliveryMan, CapitalLog capitalLog);

    /**
     * 更新帮买订单操作轨迹
     * @param orderId
     * @param actionStep
     * @param orderAction
     */
    public int updateBuyOrderActionStep(long deliveryManId,long orderId,int actionStep,OrderAction orderAction);

    /**
     * 更新帮买验证码验证订单操作
     * @param order
     * @param orderAction
     * @param deliveryMan
     * @param capitalLog
     */
    public int updateBuySmsCodeOrderAction(long deliveryManId,Order order, OrderAction orderAction, DeliveryMan deliveryMan, CapitalLog capitalLog);

    /**
     * 更新代排队订单操作轨迹
     * @param orderId
     * @param actionStep
     * @param orderAction
     */
    public int updateLineupOrderActionStep(long deliveryManId,long orderId,int actionStep,OrderAction orderAction);

    /**
     * 更新代排队验证码验证订单操作
     * @param order
     * @param orderAction
     * @param deliveryMan
     * @param capitalLog
     */
    public int updateLineupSmsCodeOrderAction(Order order, OrderAction orderAction, DeliveryMan deliveryMan, CapitalLog capitalLog);

}
