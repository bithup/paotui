package com.xgh.paotui.dao;

import com.mysql.fabric.xmlrpc.base.Data;
import com.xgh.paotui.dao.read.ICouponDaoR;
import com.xgh.paotui.dao.write.ICouponDaoW;
import java.util.List; 
import java.util.Map;
import com.xgh.paotui.entity.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("couponDao")
public class CouponDaoImpl implements ICouponDao {

	@Autowired
	private ICouponDaoR couponDaoR;

	@Autowired
	private ICouponDaoW couponDaoW;

	public Coupon get(Long id){
		return  couponDaoR.get(id);
	}

	public List<Map<String, Object>> getList(Map<String, Object> map){
		return  couponDaoR.getList(map);
	}

	public List<Coupon> getListPage(Map<String, Object> map){
		return  couponDaoR.getListPage(map);
	}

	public List<Map<String, Object>> getListMapPage(Map<String, Object> map){
		return  couponDaoR.getListMapPage(map);
	}

	public long getRows(Map<String, Object> map){
		return  couponDaoR.getRows(map);
	}

	public  int add(Coupon  coupon){
		return  couponDaoW.add(coupon);
	}

	public  int update(Coupon  coupon){
		return  couponDaoW.update(coupon);
	}

	public  int deleteById(long id){
		return  couponDaoW.deleteById(id);
	}


	public List<Map<String,Object>> getCouponList(Map<String,Object> map){
		return couponDaoR.getCouponList(map);
	}

}
