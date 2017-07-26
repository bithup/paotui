package com.xgh.paotui.dao;
import com.xgh.paotui.dao.read.IOrderTaskSettingDaoR;
import com.xgh.paotui.dao.write.IOrderTaskSettingDaoW;
import com.xgh.paotui.entity.OrderTaskSetting;

import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;


/**
 * 
 * 跑客抢单数设置表
 * 
 **/
@Service("orderTaskSettingDao")
public class OrderTaskSettingDaoImpl implements IOrderTaskSettingDao {


@Autowired
private IOrderTaskSettingDaoR orderTaskSettingDaoR;

@Autowired
private IOrderTaskSettingDaoW orderTaskSettingDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  OrderTaskSetting get(Long id){
		return  orderTaskSettingDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<OrderTaskSetting> getList(Map<String, Object> map){
		return  orderTaskSettingDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<OrderTaskSetting> getListPage(Map<String, Object> map){
		return  orderTaskSettingDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  orderTaskSettingDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  orderTaskSettingDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(OrderTaskSetting  orderTaskSetting){
		return  orderTaskSettingDaoW.add(orderTaskSetting);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(OrderTaskSetting  orderTaskSetting){
		return  orderTaskSettingDaoW.update(orderTaskSetting);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  orderTaskSettingDaoW.deleteById(id);
	}

}
