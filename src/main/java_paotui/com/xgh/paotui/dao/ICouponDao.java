package com.xgh.paotui.dao;

import com.xgh.paotui.entity.Coupon;
import java.util.List;
import java.util.Map;

/**
 * 代金券
 **/
public interface ICouponDao  {

	public  Coupon get(Long id);

	public List<Map<String, Object>> getList(Map<String, Object> map);

	public List<Coupon> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public  int add(Coupon coupon);

	public  int update(Coupon coupon);

	public  int deleteById(long id);


	/**
	 * 接口
     */
	public List<Map<String,Object>> getCouponList(Map<String, Object> map);


}
