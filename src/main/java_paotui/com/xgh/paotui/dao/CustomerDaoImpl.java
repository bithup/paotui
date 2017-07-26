package com.xgh.paotui.dao;

import com.xgh.paotui.dao.read.ICustomerDaoR;
import com.xgh.paotui.dao.write.ICustomerDaoW;
import com.xgh.paotui.entity.Customer;
import java.util.List; 
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

/**
 * 
 * 用户信息
 * 
 **/
@Service("customerDao")
public class CustomerDaoImpl implements ICustomerDao {

@Autowired
private ICustomerDaoR customerDaoR;

@Autowired
private ICustomerDaoW customerDaoW;

	public  Customer get(Long id){
		return  customerDaoR.get(id);
	}

	public List<Customer> getList(Map<String, Object> map){
		return  customerDaoR.getList(map);
	}

	public List<Customer> getListPage(Map<String, Object> map){
		return  customerDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  customerDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  customerDaoR.getRows(map);
	}

	public  int add(Customer  customer){
		return  customerDaoW.add(customer);
	}

	public  int update(Customer  customer){
		return  customerDaoW.update(customer);
	}

	public  int deleteById(long id){
		return  customerDaoW.deleteById(id);
	}


	public Customer login(Map<String,Object> map){
		return customerDaoR.login(map);
	}

}
