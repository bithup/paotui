package com.xgh.paotui.services;

import com.xgh.paotui.entity.Coupon;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ICouponService {

    public Coupon get(Long id);

    public int save(HttpServletRequest request,Coupon coupon);

    public int update(Coupon coupon);

    public Map<String, Object> getGridList(HttpServletRequest request);

    /**
     *接口
     */
    public List<Map<String,Object>> getCoupon(Map<String,Object> map);

    public int updateGetCouponList(Coupon coupon);

}
