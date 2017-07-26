package com.xgh.paotui.dao;

import com.xgh.paotui.entity.Customer;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 **/
public interface ICustomerDao  {

	public  Customer get(Long id);

	public List<Customer> getList(Map<String, Object> map);

	public List<Customer> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public  int add(Customer customer);

	public  int update(Customer customer);

	public  int deleteById(long id);

	/**
	 * 接口
     */
	public Customer login(Map<String,Object> map);
}
