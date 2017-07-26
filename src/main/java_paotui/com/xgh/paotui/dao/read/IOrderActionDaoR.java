package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.OrderAction;

import java.util.List;
import java.util.Map;

public interface IOrderActionDaoR  {

	public  OrderAction get(Long id);

	public List<OrderAction> getList(Map<String, Object> map);

	public List<OrderAction> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

}
