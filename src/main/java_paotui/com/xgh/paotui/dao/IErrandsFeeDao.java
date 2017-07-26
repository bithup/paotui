package com.xgh.paotui.dao;
import com.xgh.paotui.entity.ErrandsFee;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑腿费表
 * 
 **/
public interface IErrandsFeeDao  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  ErrandsFee get(Long id);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<ErrandsFee> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<ErrandsFee> getListPage(Map<String, Object> map);



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



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(ErrandsFee errandsFee);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(ErrandsFee errandsFee);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
