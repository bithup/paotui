package com.xgh.paotui.dao.read;

import com.xgh.paotui.entity.Customer;
import java.util.List;
import java.util.Map;

/**
 * 
 * 用户信息
 * 
 **/
public interface ICustomerDaoR  {

	public  Customer get(Long id);

	public List<Customer> getList(Map<String, Object> map);

	public List<Customer> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);


	public Customer login(Map<String,Object> map);

}
