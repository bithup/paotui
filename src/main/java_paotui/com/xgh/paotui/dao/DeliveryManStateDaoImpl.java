package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IDeliveryManStateDaoR;
import com.xgh.paotui.dao.write.IDeliveryManStateDaoW;
import com.xgh.paotui.entity.DeliveryManState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 跑客接单状态表
 * 
 **/
@Service("deliveryManStateDao")
public class DeliveryManStateDaoImpl implements IDeliveryManStateDao {


@Autowired
private IDeliveryManStateDaoR deliveryManStateDaoR;

@Autowired
private IDeliveryManStateDaoW deliveryManStateDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  DeliveryManState get(Long id){
		return  deliveryManStateDaoR.get(id);
	}


	/**
	 *
	 * 返回对象
	 *
	 **/
	public  DeliveryManState getByDeliverManId(Long deliverManId){
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("deliverManId",deliverManId);
		List<DeliveryManState> datas= deliveryManStateDaoR.getList(map);
		if(datas.size()>0){
			return  datas.get(0);
		}
		else{
			return null;
		}
	}


	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<DeliveryManState> getList(Map<String, Object> map){
		return  deliveryManStateDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<DeliveryManState> getListPage(Map<String, Object> map){
		return  deliveryManStateDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  deliveryManStateDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  deliveryManStateDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliveryManState  deliveryManState){
		return  deliveryManStateDaoW.add(deliveryManState);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliveryManState  deliveryManState){
		return  deliveryManStateDaoW.update(deliveryManState);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  deliveryManStateDaoW.deleteById(id);
	}

}
