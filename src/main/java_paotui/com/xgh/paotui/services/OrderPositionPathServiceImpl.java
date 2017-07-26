package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.paotui.dao.IOrderPositionPathDao;
import com.xgh.paotui.entity.OrderPositionPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderPositionPathService")
public class OrderPositionPathServiceImpl  extends BaseService implements IOrderPositionPathService {

    @Autowired
    protected IOrderPositionPathDao orderPositionPathDao;

    public int add(OrderPositionPath orderPositionPath){
        return orderPositionPathDao.add(orderPositionPath);
    }

    public int update(OrderPositionPath orderPositionPath) {
        return orderPositionPathDao.update(orderPositionPath);
    }

    public int save(HttpServletRequest request, OrderPositionPath orderPositionPath) {
        if (orderPositionPath != null && orderPositionPath.getId() < 1) {
            Date date = new Date();
            orderPositionPath.setStatus(1);
            int ret=orderPositionPathDao.add(orderPositionPath);
            return ret;
        } else {
            OrderPositionPath orderPositionPath2 = get(orderPositionPath.getId());
            return update(orderPositionPath2);
        }
    }

    public int delete(long id) {
        return orderPositionPathDao.deleteById(id);
    }

    public OrderPositionPath get(long id) {
        OrderPositionPath orderPositionPath = orderPositionPathDao.get(id);
        return orderPositionPath;
    }

    public List<OrderPositionPath> getList(Map<String, Object> map) {
        return orderPositionPathDao.getList(map);
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
        List<Map<String, Object>> dataList = orderPositionPathDao.getListMapPage(map);
        gridMap.put("Rows", dataList);
        gridMap.put("Total", orderPositionPathDao.getRows(map));
        return gridMap;
    }


    public List<OrderPositionPath> getOrderState(Map<String,Object> map){
        return orderPositionPathDao.getListPage(map);
    }

}