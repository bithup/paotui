package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.*;
import com.xgh.paotui.entity.CapitalLog;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.util.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("capitalLogService")
public class CapitalLogServiceImpl extends BaseService implements ICapitalLogService{

	@Autowired
	protected ICapitalLogDao capitalLogDao;

	@Autowired
	protected ICustomerDao customerDao;

	@Autowired
	protected IDeliveryManDao deliveryManDao;

	@Autowired
	protected IOrderDao orderDao;

	@Autowired
	protected IOpenCityDao openCityDao;

	public int add(CapitalLog capitalLog){
		return capitalLogDao.add(capitalLog);
	}

	public int save(CapitalLog capitalLog){
		return capitalLogDao.add(capitalLog);
	}

	public CapitalLog get(Long id){
		return capitalLogDao.get(id);
	}

	public Map<String,Object> getGridList(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> gridMap = new HashMap<String, Object>();
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		String loginName = request.getParameter("loginName");
		String type = request.getParameter("type");
		String createDate1 = request.getParameter("createDate1");
		String createDate2 = request.getParameter("createDate2");
		//获取指定跑客所有资金日志的条件
		String deliveryManId = request.getParameter("deliveryManId");
		String accountType = request.getParameter("accountType");
		map.put("page", Integer.parseInt(page));
		map.put("pagesize", Integer.parseInt(pagesize));
		map.put("loginName",loginName);
		map.put("type",type);
		map.put("createDate1",createDate1);
		map.put("createDate2",createDate2);
		map.put("deliveryManId",deliveryManId);
		map.put("accountType",accountType);
		//获取系统人员所属地区
		SysUser sysUser = getCurrentUser(request);
		if (sysUser.getOpenCityId()!=null&&sysUser.getOpenCityId()!=0){
			long openCityId = sysUser.getOpenCityId();
			OpenCity openCity = openCityDao.get(openCityId);
			if (openCity!=null){
				//跑客
				map.put("belongCityId",sysUser.getOpenCityId());
				//客户
				map.put("belongCity",openCity.getCityName());
			}
		}
		List<Map<String,Object>> dataList = capitalLogDao.getListMapPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Map<String, Object>>();
		}
		for (Map<String,Object> dataList_:dataList){
			if (dataList_.get("accountType")!=null){
				accountType = String.valueOf(dataList_.get("accountType"));
				dataList_.put("accountType", DictUtils.getDictLabel(accountType,"paotui_account_type",""));
			}
			if (dataList_.get("type")!=null){
				type = String.valueOf(dataList_.get("type"));
				dataList_.put("type", DictUtils.getDictLabel(type,"paotui_type",""));
			}
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", capitalLogDao.getRows(map));
		return gridMap;
	}


	/**
	 * 分页查询
	 * @param map
	 * @return
     */
	public Map<String,Object> getPageList( Map<String, Object> map){

		Map<String, Object> gridMap = new HashMap<String, Object>();

		List<Map<String,Object>> dataList = capitalLogDao.getListMapPage(map);
		gridMap.put("Rows", dataList);
		gridMap.put("Total", capitalLogDao.getRows(map));
		return gridMap;
	}


	/**
	 *
	 * 返回跑客收入排行榜
	 *
	 **/
	public List<Map<String, Object>> getRankingList(Map<String, Object> map){
		return capitalLogDao.getRankingList(map);
	}

}