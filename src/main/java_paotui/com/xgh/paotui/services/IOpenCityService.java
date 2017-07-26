package com.xgh.paotui.services;

import com.xgh.paotui.entity.OpenCity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by Tian on 2017/2/23.
 */
public interface IOpenCityService {
    public int update(OpenCity openCity);

    public int save(HttpServletRequest request, OpenCity openCity);

    public int delete(long id);

    public OpenCity get(long id);

    public List<OpenCity> getList(Map<String, Object> map);

    public Map<String,Object> getGridList(HttpServletRequest request);


    /**
     * 接口
     */
    public List<Map<String,Object>> getOpenCity(Map<String,Object> map);

    public Map<String,Object> isOpenCity(HttpServletRequest request);

}
