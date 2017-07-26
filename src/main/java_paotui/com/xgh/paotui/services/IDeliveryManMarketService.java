package com.xgh.paotui.services;

import com.xgh.paotui.entity.DeliverManMarket;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IDeliveryManMarketService {

    public DeliverManMarket get(Long id);

    public Map<String, Object> getGridList(HttpServletRequest request,String cityId);

    public  int add(DeliverManMarket deliverManMarket);

    public List<DeliverManMarket> getList(Map<String, Object> map);
}
