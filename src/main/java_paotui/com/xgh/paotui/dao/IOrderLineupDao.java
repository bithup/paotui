package com.xgh.paotui.dao;

import com.xgh.paotui.entity.OrderLineup;

import java.util.List;
import java.util.Map;

public interface IOrderLineupDao  {

	public  OrderLineup get(Long id);

	public List<Map<String, Object>> getList(Map<String, Object> map);

	public List<OrderLineup> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public  int add(OrderLineup orderLineup);

	public  int update(OrderLineup orderLineup);

	public  int deleteById(long id);

	public  OrderLineup getLineup(long orderId);


}
