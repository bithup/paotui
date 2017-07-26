package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.IOrderDaoR;
import com.xgh.paotui.dao.write.IOrderDaoW;
import com.xgh.paotui.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("orderDao")
public class OrderDaoImpl implements IOrderDao {

	@Autowired
	private IOrderDaoR orderDaoR;

	@Autowired
	private IOrderDaoW orderDaoW;

	public  Order get(Long id){
		return  orderDaoR.get(id);
	}

	public List<Order> getList(Map<String, Object> map){
		return  orderDaoR.getList(map);
	}

	public List<Order> getListPage(Map<String, Object> map){
		return  orderDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  orderDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  orderDaoR.getRows(map);
	}

	public  int add(Order  order){
		return  orderDaoW.add(order);
	}

	public  int update(Order  order){
		return  orderDaoW.update(order);
	}

	public  int deleteById(long id){
		return  orderDaoW.deleteById(id);
	}

	public List<Map<String, Object>> getOrderInfoList(Map<String, Object> map){
		return  orderDaoR.getOrderInfoList(map);
	}

	public List<Map<String, Object>> getMyOrder(Map<String, Object> map){
		return  orderDaoR.getMyOrder(map);
	}

	public List<Map<String,Object>> getTodayAchievement(Map<String,Object> map){
		return orderDaoR.getTodayAchievement(map);
	}
}
