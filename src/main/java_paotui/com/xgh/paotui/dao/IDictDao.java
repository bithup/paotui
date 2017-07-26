package com.xgh.paotui.dao;
import com.xgh.paotui.entity.Dict;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 
 * 
 **/
public interface IDictDao  {



	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  Dict get(Long id);



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Dict> getList(Map<String, Object> map);



	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<Dict> getListPage(Map<String, Object> map);



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
	public  int add(Dict dict);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Dict dict);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
