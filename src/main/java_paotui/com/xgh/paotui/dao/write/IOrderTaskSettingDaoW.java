package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.OrderTaskSetting;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑客抢单数设置表
 * 
 **/
public interface IOrderTaskSettingDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(OrderTaskSetting orderTaskSetting);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderTaskSetting orderTaskSetting);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
