package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.OpenCity;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 开通城市表
 * 
 **/
public interface IOpenCityDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(OpenCity openCity);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OpenCity openCity);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
