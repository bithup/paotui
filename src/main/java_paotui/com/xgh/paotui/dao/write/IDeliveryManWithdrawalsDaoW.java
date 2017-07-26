package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.DeliveryManWithdrawals;

/**
 * 
 * 跑客提现表
 * 
 **/
public interface IDeliveryManWithdrawalsDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(DeliveryManWithdrawals deliveryManWithdrawals);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(DeliveryManWithdrawals deliveryManWithdrawals);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
