package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.IDeliveryManWithdrawalsDaoR;
import com.xgh.paotui.dao.write.IDeliveryManWithdrawalsDaoW;
import com.xgh.paotui.entity.DeliveryManWithdrawals;
import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

/**
 * 
 * 跑客提现表
 * 
 **/
@Service("deliveryManWithdrawalsDao")
public class DeliveryManWithdrawalsDaoImpl implements IDeliveryManWithdrawalsDao {


@Autowired
private IDeliveryManWithdrawalsDaoR deliveryManWithdrawalsDaoR;

@Autowired
private IDeliveryManWithdrawalsDaoW deliveryManWithdrawalsDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  DeliveryManWithdrawals get(Long id){
		return  deliveryManWithdrawalsDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  deliveryManWithdrawalsDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<DeliveryManWithdrawals> getListPage(Map<String, Object> map){
		return  deliveryManWithdrawalsDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  deliveryManWithdrawalsDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  deliveryManWithdrawalsDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliveryManWithdrawals  deliveryManWithdrawals){
		return  deliveryManWithdrawalsDaoW.add(deliveryManWithdrawals);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliveryManWithdrawals  deliveryManWithdrawals){
		return  deliveryManWithdrawalsDaoW.update(deliveryManWithdrawals);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  deliveryManWithdrawalsDaoW.deleteById(id);
	}

}
