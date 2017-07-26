package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.ICustomerRechargeDaoR;
import com.xgh.paotui.dao.write.ICustomerRechargeDaoW;
import com.xgh.paotui.entity.CustomerRecharge;
import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

/**
 * 
 * 客户充值表
 * 
 **/
@Service("customerRechargeDao")
public class CustomerRechargeDaoImpl implements ICustomerRechargeDao {


@Autowired
private ICustomerRechargeDaoR customerRechargeDaoR;

@Autowired
private ICustomerRechargeDaoW customerRechargeDaoW;


	/**
	 * 
	 * 返回对象
	 * 
	 **/
	public  CustomerRecharge get(Long id){
		return  customerRechargeDaoR.get(id);
	}



	/**
	 * 
	 * 返回列表
	 * 
	 **/
	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  customerRechargeDaoR.getList(map);
	}


	/**
	 * 
	 * 返回实体对象的分页记录
	 * 
	 **/
	public List<CustomerRecharge> getListPage(Map<String, Object> map){
		return  customerRechargeDaoR.getListPage(map);
	}


	/**
	 * 
	 * 返回Map对象的分页记录
	 * 
	 **/
	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  customerRechargeDaoR.getListMapPage(map);
	}


	/**
	 * 
	 * 返回记录数量
	 * 
	 **/
	public long getRows(Map<String, Object> map){
		return  customerRechargeDaoR.getRows(map);
	}


	/**
	 * 
	 * 新增
	 * 
	 **/
	public  int add(CustomerRecharge  customerRecharge){
		return  customerRechargeDaoW.add(customerRecharge);
	}


	/**
	 * 
	 * 更新
	 * 
	 **/
	public  int update(CustomerRecharge  customerRecharge){
		return  customerRechargeDaoW.update(customerRecharge);
	}


	/**
	 * 
	 * 逻辑删除
	 * 
	 **/
	public  int deleteById(long id){
		return  customerRechargeDaoW.deleteById(id);
	}

}
