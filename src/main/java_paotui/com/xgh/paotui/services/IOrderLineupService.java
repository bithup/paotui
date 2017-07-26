package com.xgh.paotui.services;

import com.xgh.paotui.entity.OrderLineup;

public interface IOrderLineupService {

    public OrderLineup get(Long id);

    public int add(OrderLineup orderLineup);

    public int update(OrderLineup orderLineup);

    public OrderLineup getLineup(long orderId);
}
