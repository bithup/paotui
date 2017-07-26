package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.CapitalLog;

import java.util.List;
import java.util.Map;

/**
 * 
 * 资金日志表
 * 
 **/
public interface ICapitalLogDaoR  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  CapitalLog get(Long id);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<CapitalLog> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<CapitalLog> getListPage(Map<String, Object> map);



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
	 * 返回跑客收入排行榜
	 *
	 **/
	public List<Map<String, Object>> getRankingList(Map<String, Object> map);


}
