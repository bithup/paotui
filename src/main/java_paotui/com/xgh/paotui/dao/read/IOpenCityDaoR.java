package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.OpenCity;
import java.util.List; 
import java.util.Map;

public interface IOpenCityDaoR  {

	public  OpenCity get(Long id);

	public List<OpenCity> getList(Map<String, Object> map);

	public List<OpenCity> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);


	public List<Map<String, Object>> getOpenCity(Map<String, Object> map);
}
