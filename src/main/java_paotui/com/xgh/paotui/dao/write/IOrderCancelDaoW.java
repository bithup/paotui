package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.OrderCancel;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 
 * 
 **/
public interface IOrderCancelDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(OrderCancel orderCancel);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderCancel orderCancel);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
