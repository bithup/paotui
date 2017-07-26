package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.dao.IOrderLineupDao;
import com.xgh.paotui.entity.OrderLineup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderLineupService")
public class OrderLineupServiceImpl extends BaseService implements IOrderLineupService{

	@Autowired
	protected IOrderLineupDao orderLineupDao;

	public OrderLineup get(Long id){
		return orderLineupDao.get(id);
	}

	public int add(OrderLineup orderLineup){
		return orderLineupDao.add(orderLineup);
	}

	public int update(OrderLineup orderLineup){
		return orderLineupDao.update(orderLineup);
	}

	public OrderLineup getLineup(long orderId){
		return orderLineupDao.getLineup(orderId);
	}


}