package com.xgh.paotui.dao;

import com.xgh.paotui.entity.Notify;
import java.util.List; 
import java.util.Map;

public interface INotifyDao  {

	public  Notify get(Long id);

	public List<Map<String, Object>> getList(Map<String, Object> map);

	public List<Notify> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public  int add(Notify notify);

	public  int update(Notify notify);

	public  int deleteById(long id);


	public List<Map<String, Object>> getNotify(Map<String, Object> map);

}
