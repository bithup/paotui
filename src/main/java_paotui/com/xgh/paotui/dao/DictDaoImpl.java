package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IDictDaoR;
import com.xgh.paotui.dao.write.IDictDaoW;
import com.xgh.paotui.entity.Dict;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 
 * 
 **/
@Service("dictDao")
public class DictDaoImpl implements IDictDao {


@Autowired
private IDictDaoR dictDaoR;

@Autowired
private IDictDaoW dictDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  Dict get(Long id){
		return  dictDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Dict> getList(Map<String, Object> map){
		return  dictDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<Dict> getListPage(Map<String, Object> map){
		return  dictDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  dictDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  dictDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(Dict  dict){
		return  dictDaoW.add(dict);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(Dict  dict){
		return  dictDaoW.update(dict);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  dictDaoW.deleteById(id);
	}

}
