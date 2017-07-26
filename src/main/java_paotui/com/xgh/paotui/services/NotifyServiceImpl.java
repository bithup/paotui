package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.INotifyDao;
import com.xgh.paotui.dao.IOpenCityDao;
import com.xgh.paotui.entity.Notify;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.util.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("notifyService")
public class NotifyServiceImpl extends BaseService implements INotifyService{

	@Autowired
	protected INotifyDao notifyDao;

	@Autowired
	protected IOpenCityDao openCityDao;

	public Notify get(Long id){
		return notifyDao.get(id);
	}

	public int save(Notify notify){
		if (notify!=null&&notify.getId()>0){
			Notify notify1 = notifyDao.get(notify.getId());
			OpenCity openCity = openCityDao.get(notify.getOpenCityId());
			if (openCity!=null&&openCity.getId()>0){
				notify1.setCityName(openCity.getCityName());
			}
			notify1.setOpenCityId(notify.getOpenCityId());
			notify1.setTitle(notify.getTitle());
			notify1.setContent(notify.getContent());
			notify1.setNotifyRole(notify.getNotifyRole());
			notify1.setNotifyStatus(1);
			return notifyDao.update(notify1);
		}else {
			OpenCity openCity = openCityDao.get(notify.getOpenCityId());
			if (openCity!=null&&openCity.getId()>0){
				notify.setCityName(openCity.getCityName());
			}
			notify.setNotifyStatus(1);
			notify.setCreateDate(new Date());
			notify.setStatus(1);
			return notifyDao.add(notify);
		}
	}

	public int update(Notify notify){
		Notify notify1= notifyDao.get(notify.getId());
		notify1.setStatus(2);
		return notifyDao.update(notify1);
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
		List<Map<String,Object>> dataList = notifyDao.getListMapPage(map);
		if (dataList == null) {
			dataList = new ArrayList<Map<String, Object>>();
		}
		for (Map<String,Object> dataList_:dataList){
			if (dataList_.get("notifyRole")!=null){
				String notifyRole = dataList_.get("notifyRole").toString();
				dataList_.put("notifyRole", DictUtils.getDictLabel(notifyRole,"paotui_notify_role",""));
			}
			if (dataList_.get("notifyStatus")!=null){
				String notifyStatus = dataList_.get("notifyStatus").toString();
				dataList_.put("notifyStatus", DictUtils.getDictLabel(notifyStatus,"paotui_notify_status",""));
			}
		}
		gridMap.put("Rows", dataList);
		gridMap.put("Total", notifyDao.getRows(map));
		return gridMap;
	}


	/**
	 * 获取信息通知接口
	 * @param map
	 * @return
     */
	public List<Map<String,Object>> getNotify(Map<String,Object> map){
		return notifyDao.getNotify(map);
	}

}