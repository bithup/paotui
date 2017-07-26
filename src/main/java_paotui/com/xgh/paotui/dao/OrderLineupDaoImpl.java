package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.IOrderLineupDaoR;
import com.xgh.paotui.dao.write.IOrderLineupDaoW;
import com.xgh.paotui.entity.OrderLineup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("orderLineupDao")
public class OrderLineupDaoImpl implements IOrderLineupDao {

@Autowired
private IOrderLineupDaoR orderLineupDaoR;

@Autowired
private IOrderLineupDaoW orderLineupDaoW;

	public  OrderLineup get(Long id){
		return  orderLineupDaoR.get(id);
	}


	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  orderLineupDaoR.getList(map);
	}

	public List<OrderLineup> getListPage(Map<String, Object> map){
		return  orderLineupDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  orderLineupDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  orderLineupDaoR.getRows(map);
	}

	public  int add(OrderLineup  orderLineup){
		return  orderLineupDaoW.add(orderLineup);
	}

	public  int update(OrderLineup  orderLineup){
		return  orderLineupDaoW.update(orderLineup);
	}

	public  OrderLineup getLineup(long orderId){
		return  orderLineupDaoR.getLineup(orderId);
	}

	public  int deleteById(long id){
		return  orderLineupDaoW.deleteById(id);
	}


}
