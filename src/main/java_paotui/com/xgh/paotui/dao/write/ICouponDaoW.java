package com.xgh.paotui.dao.write;


import com.xgh.paotui.entity.Coupon;

/**
 * 
 * 代金券表
 * 
 **/
public interface ICouponDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(Coupon coupon);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Coupon coupon);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
