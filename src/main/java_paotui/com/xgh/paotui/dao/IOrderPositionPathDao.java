package com.xgh.paotui.dao;
import com.xgh.paotui.entity.OrderPositionPath;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 订单轨迹表
 * 
 **/
public interface IOrderPositionPathDao  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  OrderPositionPath get(Long id);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<OrderPositionPath> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<OrderPositionPath> getListPage(Map<String, Object> map);



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
	public  int add(OrderPositionPath orderPositionPath);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderPositionPath orderPositionPath);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
