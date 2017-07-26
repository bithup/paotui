package com.xgh.paotui.services;

import com.xgh.paotui.entity.Order;
import com.xgh.paotui.entity.OrderGoods;
import com.xgh.paotui.entity.OrderLineup;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOrderService {
    public int update(Order order);

    public int save(HttpServletRequest request, Order order);

    public int delete(long id);

    public Order get(long id);

    public List<Order> getList(Map<String, Object> map);

    /**
     * 返回订单信息列表,包含帮送、帮买、帮取和代排队
     **/
    public Map<String,Object> getGridList(HttpServletRequest request);

    /**
     * 根据订单id取出订单信息，包含帮送、帮买、帮取和代排队
     * @param id
     * @return
     */
    public Map<String, Object> getOrderInfo(long id);

    /**
     * 根据订单编号获取一条订单信息，包含帮送、帮买、帮取和代排队
     * @param orderNo
     * @return
     */
    public Map<String, Object> getOrderInfoByOrderNo(String orderNo);



    /**
     * 接口
     */
    public Map<String,Object> getMyOrder(Map<String, Object> map);

    public List<Map<String,Object>> getOrderDetail(Map<String, Object> map);

    public List<Map<String,Object>> getDeliveryMan(Map<String, Object> map);

    /**
     * 获取当前城市的下一个订单流水号，并更新流水号所在的表
     * @param openCityId
     * @return
     */
    public String updateAndGetOrderNoOfOpenCity(long openCityId);


    public Map<String,Object> addSendOrder(Order order, OrderGoods orderGoods);

    public Map<String,Object> addBuyOrder(Order order, OrderGoods orderGoods);

    public Map<String,Object> addLineupOrder(Order order, OrderLineup orderLineup);

    /**
     * 获取订单详细信息列表
     * @param map
     * @return
     */
    public List<Map<String, Object>> getOrderInfoList(Map<String, Object> map);


    public List<Map<String,Object>> getTodayAchievement(Map<String,Object> map);


}
