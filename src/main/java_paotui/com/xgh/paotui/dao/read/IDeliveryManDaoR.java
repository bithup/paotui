package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.DeliveryMan;

import java.util.List;
import java.util.Map;

public interface IDeliveryManDaoR  {

	public DeliveryMan get(Long id);

	public List<Map<String, Object>> getList(Map<String, Object> map);

	public List<DeliveryMan> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public DeliveryMan login(Map<String,Object> map);

}
