package com.xgh.paotui.dao.read;

import com.mysql.fabric.xmlrpc.base.Data;
import com.xgh.paotui.entity.Coupon;
import java.util.List;
import java.util.Map;

public interface ICouponDaoR  {

	public Coupon get(Long id);

	public List<Map<String, Object>> getList(Map<String, Object> map);

	public List<Coupon> getListPage(Map<String, Object> map);

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map);

	public long getRows(Map<String, Object> map);

	public List<Map<String,Object>> getCouponList(Map<String,Object> map);


}
