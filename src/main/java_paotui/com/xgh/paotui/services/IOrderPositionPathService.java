package com.xgh.paotui.services;

import com.xgh.paotui.entity.OrderPositionPath;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IOrderPositionPathService {

    public int add(OrderPositionPath orderPositionPath);

    public int update(OrderPositionPath orderPositionPath);

    public int save(HttpServletRequest request, OrderPositionPath rderPositionPath);

    public int delete(long id);

    public OrderPositionPath get(long id);

    public List<OrderPositionPath> getList(Map<String, Object> map);

    public Map<String,Object> getGridList(HttpServletRequest request);


    public List<OrderPositionPath> getOrderState(Map<String,Object> map);
}
