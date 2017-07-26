package com.xgh.paotui.dao;

import com.xgh.paotui.entity.OpenCity;
import java.util.List; 
import java.util.Map;

public interface IOpenCityDao  {

	public  OpenCity get(Long id);

	public List<OpenCity> getList(Map<String, Object> map);

	public List<OpenCity> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public  int add(OpenCity openCity);

	public  int update(OpenCity openCity);

	public  int deleteById(long id);


	/**
	 * 接口
     */
	public List<Map<String, Object>> getOpenCity(Map<String, Object> map);

}
