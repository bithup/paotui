package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.Order;

import java.util.List;
import java.util.Map;

public interface IOrderDaoR  {

	public  Order get(Long id);

	public List<Order> getList(Map<String, Object> map);

	public List<Order> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public List<Map<String, Object>> getOrderInfoList(Map<String, Object> map);


	public List<Map<String, Object>> getMyOrder(Map<String, Object> map);

	public List<Map<String,Object>> getTodayAchievement(Map<String,Object> map);

}
