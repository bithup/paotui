package com.xgh.paotui.dao;

import com.xgh.paotui.entity.DeliveryMan;

import java.util.List;
import java.util.Map;

public interface IDeliveryManDao  {

	public DeliveryMan get(Long id);

	public List<Map<String, Object>> getList(Map<String, Object> map);

	public List<DeliveryMan> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public  int add(DeliveryMan deliveryMan);

	public  int update(DeliveryMan deliveryMan);

	public  int deleteById(long id);



	public DeliveryMan login(Map<String,Object> map);

}
