package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.OrderGoods;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 订单-帮送、帮取、帮买表
 * 
 **/
public interface IOrderGoodsDaoW  {



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
