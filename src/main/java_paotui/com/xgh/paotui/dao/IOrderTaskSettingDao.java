package com.xgh.paotui.dao;
import com.xgh.paotui.entity.OrderTaskSetting;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客抢单数设置表
 * 
 **/
public interface IOrderTaskSettingDao  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  OrderTaskSetting get(Long id);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<OrderTaskSetting> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<OrderTaskSetting> getListPage(Map<String, Object> map);



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
	public  int add(OrderTaskSetting orderTaskSetting);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderTaskSetting orderTaskSetting);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
