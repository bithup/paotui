package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.IErrandsFeeDao;
import com.xgh.paotui.dao.IOpenCityDao;
import com.xgh.paotui.entity.ErrandsFee;
import com.xgh.paotui.entity.OpenCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("errandsFeeService")
public class ErrandsFeeServiceImpl extends BaseService implements IErrandsFeeService{

	@Autowired
	protected IErrandsFeeDao errandsFeeDao;

	@Autowired
	protected IOpenCityDao openCityDao;

	public ErrandsFee get(Long id){
		return errandsFeeDao.get(id);
	}

	public int save(HttpServletRequest request,ErrandsFee errandsFee){
		Map<String, Object> map = new HashMap<String, Object>();
		if (errandsFee!=null&&errandsFee.getId()>0){
			ErrandsFee errandsFee1 = errandsFeeDao.get(errandsFee.getId());
			errandsFee1.setOpenCityId(errandsFee.getOpenCityId());
			errandsFee1.setStartMileage(errandsFee.getStartMileage());
			errandsFee1.setStartFee(errandsFee.getStartFee());
			errandsFee1.setExceedMileage(errandsFee.getExceedMileage());
			errandsFee1.setExceedFee(errandsFee.getExceedFee());
			errandsFee1.setNightStartTime(errandsFee.getNightStartTime());
			errandsFee1.setNightEndTime(errandsFee.getNightEndTime());
			errandsFee1.setNightAddFee(errandsFee.getNightAddFee());
			errandsFee1.setQueueStartTime(errandsFee.getQueueStartTime());
			errandsFee1.setQueueStartFee(errandsFee.getQueueStartFee());
			errandsFee1.setQueueExceedTime(errandsFee.getQueueExceedTime());
			errandsFee1.setQueueExceedFee(errandsFee.getQueueExceedFee());
			errandsFee1.setUpdateDate(new Date());
			return errandsFeeDao.update(errandsFee1);
		}else {
			String openCityId = request.getParameter("openCityId");
			map.put("openCityId",openCityId);
			long list = errandsFeeDao.getRows(map);
			if (list>0){
				return 2;
			}else {
				Date date = new Date();
				errandsFee.setCreateDate(date);
				errandsFee.setUpdateDate(date);
				errandsFee.setStatus(1);
				return errandsFeeDao.add(errandsFee);
			}
		}
	}

	public int update(ErrandsFee errandsFee){
		ErrandsFee errandsFee1 = errandsFeeDao.get(errandsFee.getId());
		errandsFee1.setStatus(2);
		errandsFee1.setUpdateDate(new Date());
		return errandsFeeDao.update(errandsFee1);
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
		List<Map<String,Object>> dataList = errandsFeeDao.getListMapPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Map<String, Object>>();
		}
		for (Map<String,Object> dataList_:dataList){
			//里程收费规则
			String mileageChargeRule = "";
			mileageChargeRule+="起步";
			mileageChargeRule+=dataList_.get("startMileage");
			mileageChargeRule+="公里";
			mileageChargeRule+=dataList_.get("startFee");
			mileageChargeRule+="元，";
			mileageChargeRule+=dataList_.get("exceedMileage");
			mileageChargeRule+="公里加收";
			mileageChargeRule+=dataList_.get("exceedFee");
			mileageChargeRule+="元。夜间";
			mileageChargeRule+=dataList_.get("nightStartTime");
			mileageChargeRule+="点到凌晨";
			mileageChargeRule+=dataList_.get("nightEndTime");
			mileageChargeRule+="点，每单加收";
			mileageChargeRule+=dataList_.get("nightAddFee");
			mileageChargeRule+="元。";
			dataList_.put("mileageChargeRule",mileageChargeRule);
			//排队收费规则
			String queueChargeRule = "";
			queueChargeRule+="起步";
			queueChargeRule+=dataList_.get("queueStartTime");
			queueChargeRule+="分钟";
			queueChargeRule+=dataList_.get("queueStartFee");
			queueChargeRule+="元。超出后每";
			queueChargeRule+=dataList_.get("queueExceedTime");
			queueChargeRule+="分钟";
			queueChargeRule+=dataList_.get("queueExceedFee");
			queueChargeRule+="元。";
			dataList_.put("queueChargeRule",queueChargeRule);
			if (dataList_.get("openCityId")!=null){
				openCityId = dataList_.get("openCityId").toString();
				OpenCity openCity = openCityDao.get(Long.parseLong(openCityId));
				dataList_.put("cityName",openCity.getCityName());
			}
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", errandsFeeDao.getRows(map));
		return gridMap;
	}

	public List<ErrandsFee> getList(Map<String, Object> map){
		return errandsFeeDao.getList(map);
	}
}