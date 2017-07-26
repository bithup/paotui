package com.xgh.paotui.services;

import com.xgh.paotui.entity.DeliveryManAuth;

public interface IDeliveryManAuthService {

    public DeliveryManAuth get(Long id);

    public int update(DeliveryManAuth deliveryManAuth);

    public  DeliveryManAuth getDeliveryManAuth(Long deliveryManId);

}
