package com.xgh.paotui.services;

import com.xgh.paotui.dao.IDeliverManMarketDao;
import com.xgh.paotui.entity.DeliverManMarket;
import com.xgh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
@Service("deliveryManMarketService")
public class DeliverManMarketServiceImpl implements IDeliveryManMarketService  {

    @Autowired
    protected IDeliverManMarketDao deliverManMarketDao;


    public DeliverManMarket get(Long id){
        return deliverManMarketDao.get(id);
    }

    public Map<String,Object> getGridList(HttpServletRequest request,String cityId){
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> gridMap = new HashMap<String, Object>();
        String page = request.getParameter("page");
        String pagesize = request.getParameter("pagesize");
        String deliveryManLoginName = request.getParameter("deliveryManLoginName");
        String marketTime1 = request.getParameter("marketTime1");
        String marketTime2 = request.getParameter("marketTime2");
        map.put("page", Integer.parseInt(page));
        map.put("pagesize", Integer.parseInt(pagesize));
        map.put("deliveryManLoginName",deliveryManLoginName);
        map.put("marketTime1",marketTime1);
        map.put("marketTime2",marketTime2);
        if(StringUtil.isNotEmpty(cityId)){
            map.put("belongCityId",cityId);
        }
        List<Map<String, Object>> dataList = deliverManMarketDao.getListMapPage(map);
        if (dataList == null) {
            dataList = new ArrayList<Map<String, Object>>();
        }
        gridMap.put("Rows", dataList);
        gridMap.put("Total", deliverManMarketDao.getRows(map));
        return gridMap;
    }

    public  int add(DeliverManMarket deliverManMarket){
        return deliverManMarketDao.add(deliverManMarket);
    }

    public List<DeliverManMarket> getList(Map<String, Object> map){
        return deliverManMarketDao.getList(map);
    }
}
