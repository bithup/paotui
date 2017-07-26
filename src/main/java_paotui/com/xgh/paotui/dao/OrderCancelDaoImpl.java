package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IOrderCancelDaoR;
import com.xgh.paotui.dao.write.IOrderCancelDaoW;
import com.xgh.paotui.entity.OrderCancel;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 
 * 
 **/
@Service("orderCancelDao")
public class OrderCancelDaoImpl implements IOrderCancelDao {


@Autowired
private IOrderCancelDaoR orderCancelDaoR;

@Autowired
private IOrderCancelDaoW orderCancelDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  OrderCancel get(Long id){
		return  orderCancelDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  orderCancelDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<OrderCancel> getListPage(Map<String, Object> map){
		return  orderCancelDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  orderCancelDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  orderCancelDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(OrderCancel  orderCancel){
		return  orderCancelDaoW.add(orderCancel);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderCancel  orderCancel){
		return  orderCancelDaoW.update(orderCancel);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  orderCancelDaoW.deleteById(id);
	}

}
