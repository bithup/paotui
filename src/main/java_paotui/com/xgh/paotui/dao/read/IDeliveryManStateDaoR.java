package com.xgh.paotui.dao.read;
import com.xgh.paotui.entity.DeliveryManState;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客接单状态表
 * 
 **/
public interface IDeliveryManStateDaoR  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  DeliveryManState get(Long id);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<DeliveryManState> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<DeliveryManState> getListPage(Map<String, Object> map);



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
