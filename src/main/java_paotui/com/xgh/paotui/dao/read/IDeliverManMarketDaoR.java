package com.xgh.paotui.dao.read;
import com.xgh.paotui.entity.DeliverManMarket;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客推广表
 * 
 **/
public interface IDeliverManMarketDaoR  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  DeliverManMarket get(Long id);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<DeliverManMarket> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<DeliverManMarket> getListPage(Map<String, Object> map);



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
