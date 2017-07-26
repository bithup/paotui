package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.dao.IDeliveryManAuthDao;
import com.xgh.paotui.entity.DeliveryManAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("deliveryManAuthService")
public class DeliveryManAuthServiceImpl extends BaseService implements IDeliveryManAuthService{

	@Autowired
	protected IDeliveryManAuthDao deliveryManAuthDao;


	public DeliveryManAuth get(Long id){
		return deliveryManAuthDao.get(id);
	}

	public int update(DeliveryManAuth deliveryManAuth){
		return deliveryManAuthDao.update(deliveryManAuth);
	}

	public  DeliveryManAuth getDeliveryManAuth(Long deliveryManId){
		return deliveryManAuthDao.getDeliveryManAuth(deliveryManId);
	}

}