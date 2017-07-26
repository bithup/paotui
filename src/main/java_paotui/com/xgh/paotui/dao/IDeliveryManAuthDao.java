package com.xgh.paotui.dao;

import com.xgh.paotui.entity.DeliveryManAuth;
import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客认证表
 * 
 **/
public interface IDeliveryManAuthDao  {



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



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliveryManAuth deliveryManAuth);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliveryManAuth deliveryManAuth);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
