package com.xgh.paotui.dao.write;

import com.xgh.paotui.entity.CapitalLog;

/**
 * 
 * 资金日志表
 * 
 **/
public interface ICapitalLogDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(CapitalLog capitalLog);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(CapitalLog capitalLog);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
