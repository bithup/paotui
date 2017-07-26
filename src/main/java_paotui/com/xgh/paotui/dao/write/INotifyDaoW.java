package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.Notify;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 通知表
 * 
 **/
public interface INotifyDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(Notify notify);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Notify notify);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
