package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.OrderAction;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客操作步骤表
 * 
 **/
public interface IOrderActionDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(OrderAction orderAction);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderAction orderAction);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
