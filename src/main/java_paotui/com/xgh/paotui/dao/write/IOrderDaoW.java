package com.xgh.paotui.dao.write;

import com.xgh.paotui.entity.Order;

/**
 * 
 * 订单表
 * 
 **/
public interface IOrderDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(Order order);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Order order);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
