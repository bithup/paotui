package com.xgh.paotui.services;

import com.xgh.paotui.entity.OrderCancel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IOrderCancelService {

    public int add(OrderCancel orderCancel);

    public OrderCancel get(Long id);

    public Map<String, Object> getGridList(Map<String, Object> map);


    public int save(OrderCancel orderCancel);


    /**
     * 分页查询
     * @param map
     * @return
     */
    public Map<String,Object> getPageList( Map<String, Object> map);


    /**
     * 后台审核订单取消的退款业务
     *
     * @param orderCancelId
     * @param checkState    审核状态
     * @param currentUserId 审核人id
     * @return  后台退款信息
     */
    public String updateOrderCancelCheck(long orderCancelId, int checkState, long currentUserId);

}
