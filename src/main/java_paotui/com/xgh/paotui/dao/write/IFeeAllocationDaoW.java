package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.FeeAllocation;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 平台佣金分配表
 * 
 **/
public interface IFeeAllocationDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(FeeAllocation feeAllocation);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(FeeAllocation feeAllocation);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
