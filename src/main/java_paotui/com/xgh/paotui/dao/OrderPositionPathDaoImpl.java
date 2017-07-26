package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IOrderPositionPathDaoR;
import com.xgh.paotui.dao.write.IOrderPositionPathDaoW;
import com.xgh.paotui.entity.OrderPositionPath;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 订单轨迹表
 * 
 **/
@Service("orderPositionPathDao")
public class OrderPositionPathDaoImpl implements IOrderPositionPathDao {


@Autowired
private IOrderPositionPathDaoR orderPositionPathDaoR;

@Autowired
private IOrderPositionPathDaoW orderPositionPathDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  OrderPositionPath get(Long id){
		return  orderPositionPathDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<OrderPositionPath> getList(Map<String, Object> map){
		return  orderPositionPathDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<OrderPositionPath> getListPage(Map<String, Object> map){
		return  orderPositionPathDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  orderPositionPathDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  orderPositionPathDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(OrderPositionPath  orderPositionPath){
		return  orderPositionPathDaoW.add(orderPositionPath);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderPositionPath  orderPositionPath){
		return  orderPositionPathDaoW.update(orderPositionPath);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  orderPositionPathDaoW.deleteById(id);
	}

}
