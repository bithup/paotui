package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.OrderLineup;

import java.util.List;
import java.util.Map;


/**
 * 
 * 排队订单表
 * 
 **/
public interface IOrderLineupDaoR  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  OrderLineup get(Long id);

	public  OrderLineup getLineup(Long orderId);


	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Map<String, Object>> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<OrderLineup> getListPage(Map<String, Object> map);



	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);



	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map);


}
