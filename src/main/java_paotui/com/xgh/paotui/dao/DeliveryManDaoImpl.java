package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.IDeliveryManDaoR;
import com.xgh.paotui.dao.write.IDeliveryManDaoW;
import com.xgh.paotui.entity.DeliveryMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("deliveryManDao")
public class DeliveryManDaoImpl implements IDeliveryManDao {

@Autowired
private IDeliveryManDaoR deliveryManDaoR;

@Autowired
private IDeliveryManDaoW deliveryManDaoW;

	public DeliveryMan get(Long id){
		return  deliveryManDaoR.get(id);
	}

	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  deliveryManDaoR.getList(map);
	}

	public List<DeliveryMan> getListPage(Map<String, Object> map){
		return  deliveryManDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  deliveryManDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  deliveryManDaoR.getRows(map);
	}

	public  int add(DeliveryMan  deliveryMan){
		return  deliveryManDaoW.add(deliveryMan);
	}

	public  int update(DeliveryMan  deliveryMan){
		return  deliveryManDaoW.update(deliveryMan);
	}

	public  int deleteById(long id){
		return  deliveryManDaoW.deleteById(id);
	}

	//登录接口
	public DeliveryMan login(Map<String,Object> map){
		return deliveryManDaoR.login(map);
	}

}
