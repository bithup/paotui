package com.xgh.paotui.services;

import com.xgh.paotui.entity.DeliveryManWithdrawals;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IDeliveryManWithdrawalsService {

    public DeliveryManWithdrawals get(Long id);

    public Map<String, Object> getGridList(HttpServletRequest request);

    /**
     * @param deliveryManWithdrawals
     * @return 1：审核通过，提现成功；2：审核不通过成功；3：审核通过，提现失败；0：失败
     */
    public int save(DeliveryManWithdrawals deliveryManWithdrawals);

}
