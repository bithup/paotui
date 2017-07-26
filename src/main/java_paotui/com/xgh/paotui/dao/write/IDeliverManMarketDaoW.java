package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.DeliverManMarket;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客推广表
 * 
 **/
public interface IDeliverManMarketDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliverManMarket deliverManMarket);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliverManMarket deliverManMarket);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
