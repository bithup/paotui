package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.DeliveryManState;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客接单状态表
 * 
 **/
public interface IDeliveryManStateDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliveryManState deliveryManState);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliveryManState deliveryManState);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
