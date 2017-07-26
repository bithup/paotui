package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.OrderLineup;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 排队订单表
 * 
 **/
public interface IOrderLineupDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(OrderLineup orderLineup);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderLineup orderLineup);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
