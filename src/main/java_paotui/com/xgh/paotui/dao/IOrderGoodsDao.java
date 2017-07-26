package com.xgh.paotui.dao;
import com.xgh.paotui.entity.OrderGoods;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 订单-帮送、帮取、帮买表
 * 
 **/
public interface IOrderGoodsDao  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  OrderGoods get(Long id);


	public  OrderGoods getOrderGoods(Long orderId);


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
	public List<OrderGoods> getListPage(Map<String, Object> map);



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
	public  int add(OrderGoods orderGoods);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderGoods orderGoods);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
