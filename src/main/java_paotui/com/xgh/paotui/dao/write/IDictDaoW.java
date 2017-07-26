package com.xgh.paotui.dao.write;
import com.xgh.paotui.entity.Dict;

import java.util.List; 
import java.util.Map;


/**
 * 
 * 
 * 
 **/
public interface IDictDaoW  {



	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(Dict dict);



	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Dict dict);



	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id);


}
