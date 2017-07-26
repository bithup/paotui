package com.xgh.paotui.dao;

import com.xgh.paotui.entity.OrderAction;

import java.util.List;
import java.util.Map;

public interface IOrderActionDao  {

	public  OrderAction get(Long id);

	public List<OrderAction> getList(Map<String, Object> map);

	public List<OrderAction> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public  int add(OrderAction orderAction);

	public  int update(OrderAction orderAction);

	public  int deleteById(long id);

}
