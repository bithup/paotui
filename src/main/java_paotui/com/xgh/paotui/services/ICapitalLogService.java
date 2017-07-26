package com.xgh.paotui.services;

import com.xgh.paotui.entity.CapitalLog;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ICapitalLogService {

    public int add(CapitalLog capitalLog);

    public CapitalLog get(Long id);

    public Map<String, Object> getGridList(HttpServletRequest request);


    public int save(CapitalLog capitalLog);


    /**
     * 分页查询
     * @param map
     * @return
     */
    public Map<String,Object> getPageList( Map<String, Object> map);


    /**
     *
     * 返回跑客收入排行榜
     *
     **/
    public List<Map<String, Object>> getRankingList(Map<String, Object> map);
}
