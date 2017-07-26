package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.ErrandsFee;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 跑腿费表
 * 
 **/
public interface IErrandsFeeDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(ErrandsFee errandsFee);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(ErrandsFee errandsFee);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
