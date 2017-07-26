package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.ICouponDao;
import com.xgh.paotui.dao.ICustomerDao;
import com.xgh.paotui.dao.IOrderDao;
import com.xgh.paotui.entity.Coupon;
import com.xgh.paotui.entity.Customer;
import com.xgh.paotui.entity.Order;
import com.xgh.util.DictUtils;
import com.xgh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("couponService")
public class CouponServiceImpl extends BaseService implements ICouponService{

	@Autowired
	protected ICouponDao couponDao;

	@Autowired
	protected ICustomerDao customerDao;

	@Autowired
	protected IOrderDao orderDao;

	public Coupon get(Long id){
		return couponDao.get(id);
	}

	public int save(HttpServletRequest request,Coupon coupon){
		Map<String, Object> map = new HashMap<String, Object>();
		String loginNameList = request.getParameter("loginNameList");
		coupon.setUseStatus(1);
		coupon.setStatus(1);
		if (loginNameList!=null&&loginNameList!=""){
			int falg = 0;
			String[] loginNameList_ = loginNameList.split("\r\n");
			for (int i = 0; i < loginNameList_.length; i++) {
				map.put("loginName",loginNameList_[i]);
				List<Customer> customerList = customerDao.getListPage(map);
				if (customerList!=null&&customerList.size()>0){
					coupon.setCustomerId(customerList.get(0).getId());
					couponDao.add(coupon);
					falg = 1 ;
				}
			}
			return falg;
		}else {
			return couponDao.add(coupon);
		}
	}

	public int update(Coupon coupon){
		Coupon coupon1 = couponDao.get(coupon.getId());
		coupon1.setStatus(2);
		return couponDao.update(coupon1);
	}

	public Map<String,Object> getGridList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> gridMap = new HashMap<String, Object>();
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		String openCityId = request.getParameter("openCityId");
		map.put("page", Integer.parseInt(page));
		map.put("pagesize", Integer.parseInt(pagesize));
		map.put("openCityId",openCityId);
		SysUser sysUser = getCurrentUser(request);
		if (sysUser.getOpenCityId()!=null&&sysUser.getOpenCityId()!=0){
			map.put("openCityId_",sysUser.getOpenCityId());
		}
		List<Map<String,Object>> dataList = couponDao.getListMapPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Map<String, Object>>();
		}
		for (Map<String,Object> dataList_:dataList){
			if (dataList_.get("startDate")!=null&&dataList_.get("endDate")!=null){
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String startDate = format.format(dataList_.get("startDate"));
				String endDate = format.format(dataList_.get("endDate"));
				String validDate = startDate+"~"+endDate;
				dataList_.put("validDate",validDate);
			}else {
				dataList_.put("validDate","");
			}
			String useStatus = StringUtil.convertNullToEmpty(dataList_.get("useStatus"));
			dataList_.put("useStatus", DictUtils.getDictLabel(useStatus,"paotui_use_status",""));
			long customerId = Long.parseLong(StringUtil.convertNullToEmpty(dataList_.get("customerId")));
			Customer customer = customerDao.get(customerId);
			if (customer!=null&&customer.getId()>0){
				dataList_.put("loginName",customer.getLoginName());
				dataList_.put("belongCity",customer.getBelongCity());
			}
			long orderId = Long.parseLong(StringUtil.convertNullToEmpty(dataList_.get("orderId")));
			Order order = orderDao.get(orderId);
			if (order!=null&&order.getId()>0){
				dataList_.put("orderNo",order.getOrderNo());
			}
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", couponDao.getRows(map));
		return gridMap;
	}


	public int updateGetCouponList(Coupon coupon){
		return couponDao.update(coupon);
	}

	public List<Map<String,Object>> getCoupon(Map<String,Object> map){
		return couponDao.getCouponList(map);
	}

}