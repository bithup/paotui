package com.xgh.paotui.dao.write;

import com.xgh.paotui.entity.DeliveryMan;

public interface IDeliveryManDaoW  {

	public  int add(DeliveryMan deliveryMan);

	public  int update(DeliveryMan deliveryMan);

	public  int deleteById(long id);


}
