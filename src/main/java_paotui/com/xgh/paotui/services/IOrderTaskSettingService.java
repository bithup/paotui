package com.xgh.paotui.services;

import com.xgh.paotui.entity.OrderTaskSetting;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IOrderTaskSettingService {

    public OrderTaskSetting get(Long id);

    public int save(OrderTaskSetting orderTaskSetting);

    public int update(OrderTaskSetting orderTaskSetting);

    public Map<String, Object> getGridList(HttpServletRequest request);

}
