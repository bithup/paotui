package com.xgh.paotui.dao.read;
import com.xgh.paotui.entity.DeliveryManAuth;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客认证表
 * 
 **/
public interface IDeliveryManAuthDaoR  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  DeliveryManAuth get(Long id);

	public  DeliveryManAuth getDeliveryManAuth(Long deliveryManId);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<DeliveryManAuth> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<DeliveryManAuth> getListPage(Map<String, Object> map);



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
