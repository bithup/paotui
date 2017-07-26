package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.ICapitalLogDaoR;
import com.xgh.paotui.dao.write.ICapitalLogDaoW;
import java.util.List;
import java.util.Map;

import com.xgh.paotui.entity.CapitalLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 资金日志表
 * 
 **/
@Service("capitalLogDao")
public class CapitalLogDaoImpl implements ICapitalLogDao {


@Autowired
private ICapitalLogDaoR capitalLogDaoR;

@Autowired
private ICapitalLogDaoW capitalLogDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public CapitalLog get(Long id){
		return  capitalLogDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<CapitalLog> getList(Map<String, Object> map){
		return  capitalLogDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<CapitalLog> getListPage(Map<String, Object> map){
		return  capitalLogDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  capitalLogDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  capitalLogDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(CapitalLog  capitalLog){
		return  capitalLogDaoW.add(capitalLog);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(CapitalLog  capitalLog){
		return  capitalLogDaoW.update(capitalLog);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  capitalLogDaoW.deleteById(id);
	}


	/**
	 *
	 * 返回跑客收入排行榜
	 *
	 **/
	public List<Map<String, Object>> getRankingList(Map<String, Object> map){
		return  capitalLogDaoR.getRankingList(map);
	}

}
