package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.dao.IOpenCityDao;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.util.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service("openCityService")
public class OpenCityServiceImpl  extends BaseService implements IOpenCityService {

    @Autowired
    protected IOpenCityDao openCityDao;

    public int update(OpenCity openCity) {
        return openCityDao.update(openCity);
    }

    /**
     * 保存主题
     * @param request
     * @param openCity
     * @return
     */
    public int save(HttpServletRequest request, OpenCity openCity) {
        //此处认为不为空，进行操作，是否为空的判断在controller中进行
        if (openCity != null && openCity.getId() < 1) {
            //该处认为是添加操作
            Date date = new Date();
            openCity.setCreateDate(date);
            openCity.setStatus(1);
            int ret=openCityDao.add(openCity);
            return ret;
        } else {
            OpenCity openCity2 = get(openCity.getId());
            openCity2.setCityName(openCity.getCityName());
            openCity2.setManagementType(openCity.getManagementType());
            openCity2.setResponsibleName(openCity.getResponsibleName());
            openCity2.setResponsiblePhone(openCity.getResponsiblePhone());
            openCity2.setOfficeAddress(openCity.getOfficeAddress());
            openCity2.setOrderPrefix(openCity.getOrderPrefix());
            openCity2.setUpdateDate(new Date());
            return update(openCity2);
        }
    }

    public int delete(long id) {
        return openCityDao.deleteById(id);
    }

    public OpenCity get(long id) {
        OpenCity openCity = openCityDao.get(id);
        return openCity;
    }

    public List<OpenCity> getList(Map<String, Object> map) {
        return openCityDao.getList(map);
    }

    public Map<String, Object> getGridList(HttpServletRequest request) {
        String page = request.getParameter("page");
        String pagesize = request.getParameter("pagesize");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("instId", getCurrentInstId(request));
        map.put("unitId", getCurrentUnitId(request));
        map.put("type", request.getParameter("type"));
        map.put("page", Integer.parseInt(page));
        map.put("pagesize", Integer.parseInt(pagesize));
        Map<String, Object> gridMap = new HashMap<String, Object>();
        List<Map<String, Object>> dataList = openCityDao.getListMapPage(map);
        if (dataList == null) {
            dataList = new ArrayList<Map<String, Object>>();
        }
        for(Map<String, Object> data:dataList){
            if (data.get("managementType")!=null){
                String managementType=String.valueOf(data.get("managementType"));
                data.put("managementTypeName", DictUtils.getDictLabel(managementType,"paotui_management_type",""));
            }
        }
        gridMap.put("Rows", dataList);
        gridMap.put("Total", openCityDao.getRows(map));
        return gridMap;
    }



    /**
     * 获取开通城市列表接口
     * @return
     */
    public List<Map<String,Object>> getOpenCity(Map<String,Object> map){
        return openCityDao.getOpenCity(map);
    }

    /**
     * 判断是否为开通城市接口
     * @param request
     * @return
     */
    public Map<String,Object> isOpenCity(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String cityName = request.getParameter("cityName");
        map.put("cityName",cityName);
        List<Map<String,Object>> openCityList = openCityDao.getOpenCity(map);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (openCityList!=null&&openCityList.size()>0){
            for (Map<String,Object> openCityList_:openCityList){
                if (openCityList_.get("managementType")!=null){
                    String managementType=String.valueOf(openCityList_.get("managementType"));
                    openCityList_.put("managementTypeName", DictUtils.getDictLabel(managementType,"paotui_management_type",""));
                }
                mapList.add(openCityList_);
            }
            resultMap = getResultMap("1","该城市是开通城市",mapList);
        }else {
            resultMap = getResultMap("0","该城市不是开通城市");
        }
        return resultMap;
    }

}