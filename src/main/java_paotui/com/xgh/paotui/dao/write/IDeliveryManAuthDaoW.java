package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.DeliveryManAuth;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客认证表
 * 
 **/
public interface IDeliveryManAuthDaoW  {



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
