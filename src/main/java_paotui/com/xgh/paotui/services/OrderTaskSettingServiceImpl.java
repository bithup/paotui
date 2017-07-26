package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.IOpenCityDao;
import com.xgh.paotui.dao.IOrderTaskSettingDao;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.entity.Order;
import com.xgh.paotui.entity.OrderTaskSetting;
import com.xgh.util.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("orderTaskSettingService")
public class OrderTaskSettingServiceImpl extends BaseService implements IOrderTaskSettingService{

	@Autowired
	protected IOrderTaskSettingDao orderTaskSettingDao;

	@Autowired
	protected IOpenCityDao openCityDao;

	public OrderTaskSetting get(Long id){
		return orderTaskSettingDao.get(id);
	}

	public int save(OrderTaskSetting orderTaskSetting){
		if (orderTaskSetting!=null&&orderTaskSetting.getId()>0){
			OrderTaskSetting orderTaskSetting1 = orderTaskSettingDao.get(orderTaskSetting.getId());
			orderTaskSetting1.setOpenCityId(orderTaskSetting.getOpenCityId());
			orderTaskSetting1.setLevel(orderTaskSetting.getLevel());
			orderTaskSetting1.setStaffOrderNum(orderTaskSetting.getStaffOrderNum());
			orderTaskSetting1.setCrowdsourcingOrderNum(orderTaskSetting.getCrowdsourcingOrderNum());
			orderTaskSetting1.setUpdateDate(new Date());
			return orderTaskSettingDao.update(orderTaskSetting1);
		}else {
			Date date = new Date();
			orderTaskSetting.setCreateDate(date);
			orderTaskSetting.setUpdateDate(date);
			orderTaskSetting.setStatus(1);
			return orderTaskSettingDao.add(orderTaskSetting);
		}
	}

	public int update(OrderTaskSetting orderTaskSetting){
		OrderTaskSetting orderTaskSetting1 = orderTaskSettingDao.get(orderTaskSetting.getId());
		orderTaskSetting1.setUpdateDate(new Date());
		orderTaskSetting1.setStatus(2);
		return orderTaskSettingDao.update(orderTaskSetting1);
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
		List<Map<String,Object>> dataList = orderTaskSettingDao.getListMapPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Map<String, Object>>();
		}
		for (Map<String,Object> dataList_:dataList){
			if (dataList_.get("openCityId")!=null){
				openCityId = dataList_.get("openCityId").toString();
				OpenCity openCity = openCityDao.get(Long.parseLong(openCityId));
				dataList_.put("cityName",openCity.getCityName());
			}
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", orderTaskSettingDao.getRows(map));
		return gridMap;
	}

}