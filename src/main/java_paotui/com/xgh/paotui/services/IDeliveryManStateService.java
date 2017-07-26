package com.xgh.paotui.services;

import com.xgh.paotui.entity.DeliveryManState;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IDeliveryManStateService {

    public DeliveryManState get(Long id);

    public int save(HttpServletRequest request,DeliveryManState deliveryManState);

    public int update(DeliveryManState deliveryManState);

    public List<DeliveryManState> getList(Map<String, Object> map);

    /**
     * 实时上传跑客位置
     * @param deliveryManState
     * @return
     */
    public int addDeliveryManState(DeliveryManState deliveryManState);

    /**
     * 返回跑客是否可以接新单
     * @param deliveryManId
     * @return 1：可以接单；2：有任务不能接单
     */
    public int getGainNewOrder(Long deliveryManId);

    /**
     * 跑客抢单
     * @param deliveryManId:跑客id
     * @param orderId:订单id
     *
     * @return 1：抢单成功；2：抢单失败  3：订单被别人抢了 4:订单被自己抢过了
     */
    public int updateOfGainNewOrder(long deliveryManId, long orderId);

}
