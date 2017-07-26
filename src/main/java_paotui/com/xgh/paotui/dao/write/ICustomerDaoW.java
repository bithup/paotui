package com.xgh.paotui.dao.write;

import com.xgh.paotui.entity.Customer;

/**
 * 
 * 用户信息表
 * 
 **/
public interface ICustomerDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(Customer customer);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Customer customer);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
