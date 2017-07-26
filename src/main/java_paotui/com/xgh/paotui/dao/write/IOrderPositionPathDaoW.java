package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.OrderPositionPath;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 订单轨迹表
 * 
 **/
public interface IOrderPositionPathDaoW  {



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
