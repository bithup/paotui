package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.ICustomerDao;
import com.xgh.paotui.dao.ICustomerRechargeDao;
import com.xgh.paotui.dao.IOpenCityDao;
import com.xgh.paotui.entity.CustomerRecharge;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.entity.Order;
import com.xgh.util.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("customerRechargeService")
public class CustomerRechargeServiceImpl extends BaseService implements ICustomerRechargeService{

	@Autowired
	protected ICustomerRechargeDao customerRechargeDao;

	@Autowired
	protected ICustomerDao customerDao;

	@Autowired
	protected IOpenCityDao openCityDao;

	public CustomerRecharge get(Long id){
		return customerRechargeDao.get(id);
	}

	public Map<String,Object> getGridList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> gridMap = new HashMap<String, Object>();
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		String realName = request.getParameter("realName");
		String mobilePhone = request.getParameter("mobilePhone");
		String rechargeDate1 = request.getParameter("rechargeDate1");
		String rechargeDate2 = request.getParameter("rechargeDate2");
		map.put("page", Integer.parseInt(page));
		map.put("pagesize", Integer.parseInt(pagesize));
		map.put("realName",realName);
		map.put("mobilePhone",mobilePhone);
		map.put("rechargeDate1",rechargeDate1);
		map.put("rechargeDate2",rechargeDate2);
		//获取系统人员所属地区
		SysUser sysUser = getCurrentUser(request);
		if (sysUser.getOpenCityId()!=null&&sysUser.getOpenCityId()!=0){
			long belongCity = sysUser.getOpenCityId();
			OpenCity openCity = openCityDao.get(belongCity);
			if (openCity!=null){
				map.put("belongCity",openCity.getCityName());
			}
		}
		List<Map<String,Object>> dataList = customerRechargeDao.getListMapPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Map<String, Object>>();
		}
		for (Map<String,Object> dataList_:dataList){
			if (dataList_.get("rechargeFrom")!=null){
				String rechargeFrom = String.valueOf(dataList_.get("rechargeFrom"));
				dataList_.put("rechargeFrom", DictUtils.getDictLabel(rechargeFrom,"paotui_recharge_from",""));
			}
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", customerRechargeDao.getRows(map));
		return gridMap;
	}

}