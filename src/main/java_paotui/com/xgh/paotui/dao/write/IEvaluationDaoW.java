package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.Evaluation;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 订单评价表
 * 
 **/
public interface IEvaluationDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(Evaluation evaluation);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Evaluation evaluation);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
