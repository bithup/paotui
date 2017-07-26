package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.CustomerRecharge;
import java.util.List; 
import java.util.Map;

/**
 * 
 * 客户充值表
 * 
 **/
public interface ICustomerRechargeDaoR  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  CustomerRecharge get(Long id);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Map<String, Object>> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<CustomerRecharge> getListPage(Map<String, Object> map);



	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);



	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map);


}
