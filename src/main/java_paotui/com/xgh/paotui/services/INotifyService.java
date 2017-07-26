package com.xgh.paotui.services;

import com.xgh.paotui.entity.Notify;
import com.xgh.paotui.entity.OpenCity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface INotifyService {

    public Notify get(Long id);

    public int save(Notify notify);

    public int update(Notify notify);

    public Map<String, Object> getGridList(HttpServletRequest request);



    /**
     * 接口
     */
    public List<Map<String,Object>> getNotify(Map<String,Object> map);

}
