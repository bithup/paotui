package com.xgh.paotui.dao.read;
import com.xgh.paotui.entity.FeeAllocation;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 平台佣金分配表
 * 
 **/
public interface IFeeAllocationDaoR  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  FeeAllocation get(Long id);



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
	public List<FeeAllocation> getListPage(Map<String, Object> map);



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
