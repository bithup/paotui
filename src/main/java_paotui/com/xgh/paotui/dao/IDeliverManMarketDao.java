package com.xgh.paotui.dao;

import com.xgh.paotui.entity.DeliverManMarket;

import java.util.List;
import java.util.Map;


/**
 * 
 * 跑客推广表
 * 
 **/
public interface IDeliverManMarketDao  {



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



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliverManMarket deliverManMarket);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliverManMarket deliverManMarket);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
