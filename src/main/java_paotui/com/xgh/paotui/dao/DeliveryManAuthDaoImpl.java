package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.IDeliveryManAuthDaoR;
import com.xgh.paotui.dao.write.IDeliveryManAuthDaoW;
import com.xgh.paotui.entity.DeliveryManAuth;
import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 跑客认证表
 * 
 **/
@Service("deliveryManAuthDao")
public class DeliveryManAuthDaoImpl implements IDeliveryManAuthDao {


@Autowired
private IDeliveryManAuthDaoR deliveryManAuthDaoR;

@Autowired
private IDeliveryManAuthDaoW deliveryManAuthDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  DeliveryManAuth get(Long id){
		return  deliveryManAuthDaoR.get(id);
	}

	public  DeliveryManAuth getDeliveryManAuth(Long deliveryManId){
		return deliveryManAuthDaoR.getDeliveryManAuth(deliveryManId);
	}

	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<DeliveryManAuth> getList(Map<String, Object> map){
		return  deliveryManAuthDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<DeliveryManAuth> getListPage(Map<String, Object> map){
		return  deliveryManAuthDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  deliveryManAuthDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  deliveryManAuthDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliveryManAuth  deliveryManAuth){
		return  deliveryManAuthDaoW.add(deliveryManAuth);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliveryManAuth  deliveryManAuth){
		return  deliveryManAuthDaoW.update(deliveryManAuth);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  deliveryManAuthDaoW.deleteById(id);
	}

}
