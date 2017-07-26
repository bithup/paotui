package com.xgh.paotui.dao.write;

import com.xgh.paotui.entity.CustomerRecharge;

/**
 * 
 * 客户充值表
 * 
 **/
public interface ICustomerRechargeDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(CustomerRecharge customerRecharge);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(CustomerRecharge customerRecharge);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
