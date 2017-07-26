package com.xgh.paotui.services;

import com.xgh.mng.basic.BaseService;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.dao.*;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.entity.Order;
import com.xgh.paotui.entity.OrderGoods;
import com.xgh.paotui.entity.OrderLineup;
import com.xgh.util.DictUtils;
import com.xgh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("orderService")
public class OrderServiceImpl  extends BaseService implements IOrderService {

    @Autowired
    protected IOrderDao orderDao;

    @Autowired
    protected IOrderGoodsDao orderGoodsDao;

    @Autowired
    protected IOrderLineupDao orderLineupDao;

    @Autowired
    protected ICustomerDao customerDao;

    @Autowired
    protected IOpenCityDao openCityDao;

    public int update(Order order) {
        return orderDao.update(order);
    }

    public int save(HttpServletRequest request, Order order) {
        if (order!=null && order.getId()>0) {
            Order order1 = orderDao.get(order.getId());
            order1.setOrderStatus(9);
            return orderDao.update(order1);
        } else {
            return 0;
        }
    }

    public int delete(long id) {
        return orderDao.deleteById(id);
    }

    public Order get(long id) {
        Order order = orderDao.get(id);
        return order;
    }

    public List<Order> getList(Map<String, Object> map) {
        return orderDao.getList(map);
    }

    /**
     *
     * 返回订单信息列表,包含帮送、帮买、帮取和代排队
     *
     **/
    public Map<String, Object> getGridList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> gridMap = new HashMap<String, Object>();
        String page = request.getParameter("page");
        String pagesize = request.getParameter("pagesize");
        String orderType = request.getParameter("orderType");
        String orderStatus = request.getParameter("orderStatus");
        String customerName = request.getParameter("customerName");
        String deliveryManName = request.getParameter("deliveryManName");
        map.put("page", Integer.parseInt(page));
        map.put("pagesize", Integer.parseInt(pagesize));
        map.put("orderType",orderType);
        map.put("orderStatus",orderStatus);
        map.put("customerName",customerName);
        map.put("deliveryManName",deliveryManName);
        //获取系统人员所属地区
        SysUser sysUser = getCurrentUser(request);
        if (sysUser.getOpenCityId()!=null&&sysUser.getOpenCityId()!=0){
            map.put("orderCity",sysUser.getOpenCityId());
        }
        List<Map<String, Object>> dataList = orderDao.getOrderInfoList(map);
        if (dataList==null){
            dataList=new ArrayList<Map<String, Object>>();
        }
        for(Map<String, Object> dataList_:dataList){
            orderStatus=StringUtil.convertNullToEmpty(dataList_.get("orderStatus"));
            dataList_.put("orderStatus",DictUtils.getDictLabel(orderStatus,"paotui_order_state",""));
            orderType = StringUtil.convertNullToEmpty(dataList_.get("orderType"));
            dataList_.put("orderType",DictUtils.getDictLabel(orderType,"paotui_order_type",""));
            //帮送、帮取、帮买
            if("1".equals(orderType)||"2".equals(orderType)||"3".equals(orderType)) {
                //物品所在地址
                String addressA = "";
                addressA += StringUtil.convertNullToEmpty(dataList_.get("locationAddressA"));
                addressA += StringUtil.convertNullToEmpty(dataList_.get("locationAddressNameA"));
                addressA += StringUtil.convertNullToEmpty(dataList_.get("detailAddressA"));
                dataList_.put("addressA",addressA);
                //发货人电话
                dataList_.put("mobilePhoneA",dataList_.get("mobilePhoneA"));
                //送达地址
                String addressB = "";
                addressB += StringUtil.convertNullToEmpty(dataList_.get("locationAddressB"));
                addressB += StringUtil.convertNullToEmpty(dataList_.get("locationAddressNameB"));
                addressB += StringUtil.convertNullToEmpty(dataList_.get("detailAddressB"));
                dataList_.put("addressB",addressB);
                //收货人电话
                dataList_.put("mobilePhoneB",StringUtil.convertNullToEmpty(dataList_.get("mobilePhoneB")));
            }
            //带排队
            else if ("4".equals(orderType)){
                String addressA = "";
                addressA += StringUtil.convertNullToEmpty(dataList_.get("lineupLocationAddress"));
                addressA += StringUtil.convertNullToEmpty(dataList_.get("lineupLocationAddressName"));
                addressA += StringUtil.convertNullToEmpty(dataList_.get("lineupDetailAddress"));
                dataList_.put("addressA",addressA);
                dataList_.put("addressB","无");
                dataList_.put("mobilePhoneA","无");
                dataList_.put("mobilePhoneB",StringUtil.convertNullToEmpty(dataList_.get("lineupPhone")));
            }
        }
        gridMap.put("Rows", dataList);
        gridMap.put("Total", orderDao.getRows(map));
        return gridMap;
    }

    /**
     * 根据订单id获取一条订单信息，包含帮送、帮买、帮取和代排队
     * @param id
     * @return
     */
    public Map<String, Object> getOrderInfo(long id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id",id);
        List<Map<String, Object>> dataList = orderDao.getOrderInfoList(map);
        if(dataList!= null && dataList.size()>0){
            return  dataList.get(0);
        } else{
            return null;
        }
    }

    /**
     * 根据订单编号获取一条订单信息，包含帮送、帮买、帮取和代排队
     * @param orderNo
     * @return
     */
    public Map<String, Object> getOrderInfoByOrderNo(String orderNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo",orderNo);
        List<Map<String, Object>> dataList = orderDao.getOrderInfoList(map);
        if(dataList!= null && dataList.size()>0){
            return  dataList.get(0);
        } else{
            return null;
        }
    }

    /**
     * 我的订单接口
     * @param map
     * @return
     */
    public Map<String, Object> getMyOrder(Map<String,Object> map){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String,Object>> orderList = orderDao.getMyOrder(map);
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        if (orderList!=null&&orderList.size()>0){
            for(Map<String, Object> orderList_:orderList){
                Map<String, Object> map1 = new HashMap<String, Object>();
                //订单状态
                String orderStatus=StringUtil.convertNullToEmpty(orderList_.get("orderStatus"));
                map1.put("orderStatus",DictUtils.getDictLabel(orderStatus,"paotui_order_state",""));
                //下单时间
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String createTime=sdf.format(orderList_.get("createTime"));
                map1.put("orderTime",createTime);
                //订单类型
                String orderType = StringUtil.convertNullToEmpty(orderList_.get("orderType"));
                map1.put("orderType",DictUtils.getDictLabel(orderType,"paotui_order_type",""));
                /*帮送、帮取*/
                if ("1".equals(orderType) || "2".equals(orderType)) {
                    String addressA = "";
                    addressA += StringUtil.convertNullToEmpty(orderList_.get("locationAddressA"));
                    addressA += StringUtil.convertNullToEmpty(orderList_.get("locationAddressNameA"));
                    addressA += StringUtil.convertNullToEmpty(orderList_.get("detailAddressA"));
                    map1.put("addressA", addressA);
                    map1.put("linkmanNameA", StringUtil.convertNullToEmpty(orderList_.get("linkmanNameA")));
                    map1.put("mobilePhoneA", StringUtil.convertNullToEmpty(orderList_.get("mobilePhoneA")));
                    String addressB = "";
                    addressB += StringUtil.convertNullToEmpty(orderList_.get("locationAddressB"));
                    addressB += StringUtil.convertNullToEmpty(orderList_.get("locationAddressNameB"));
                    addressB += StringUtil.convertNullToEmpty(orderList_.get("detailAddressB"));
                    map1.put("addressB", addressB);
                    map1.put("linkmanNameB", StringUtil.convertNullToEmpty(orderList_.get("linkmanNameB")));
                    map1.put("mobilePhoneB", StringUtil.convertNullToEmpty(orderList_.get("mobilePhoneB")));
                }
                /*帮买*/
                else if ("3".equals(orderType)) {
                    String isNearBuy = StringUtil.convertNullToEmpty(orderList_.get("isNearBuy"));
                    if ("2".equals(isNearBuy)) {
                        String addressA = "";
                        addressA += StringUtil.convertNullToEmpty(orderList_.get("locationAddressA"));
                        addressA += StringUtil.convertNullToEmpty(orderList_.get("locationAddressNameA"));
                        addressA += StringUtil.convertNullToEmpty(orderList_.get("detailAddressA"));
                        map1.put("addressA", addressA);
                    } else {
                        map1.put("addressA", DictUtils.getDictLabel(isNearBuy, "paotui_is_near_buy", ""));
                    }
                    String addressB = "";
                    addressB += StringUtil.convertNullToEmpty(orderList_.get("locationAddressB"));
                    addressB += StringUtil.convertNullToEmpty(orderList_.get("locationAddressNameB"));
                    addressB += StringUtil.convertNullToEmpty(orderList_.get("detailAddressB"));
                    map1.put("addressB", addressB);
                    map1.put("linkmanNameB", StringUtil.convertNullToEmpty(orderList_.get("linkmanNameB")));
                    map1.put("mobilePhoneB", StringUtil.convertNullToEmpty(orderList_.get("mobilePhoneB")));
                    //购买要求
                    map1.put("buyRequire", StringUtil.convertNullToEmpty(orderList_.get("buyRequire")));
                    //商是否知道价格
                    String isKonwPrice = StringUtil.convertNullToEmpty(orderList_.get("isKonwPrice"));
                    if ("1".equals(isKonwPrice)) {
                        //商品价格
                        map1.put("buyPrice", StringUtil.convertNullToEmpty(orderList_.get("buyPrice")));
                    } else {
                        map1.put("isKonwPrice", DictUtils.getDictLabel(isKonwPrice, "paotui_is_konw_price", ""));
                    }
                }
                /*带排队*/
                else if ("4".equals(orderType)) {
                    String address = "";
                    address += StringUtil.convertNullToEmpty(orderList_.get("lineupLocationAddress"));
                    address += StringUtil.convertNullToEmpty(orderList_.get("lineupLocationAddressName"));
                    address += StringUtil.convertNullToEmpty(orderList_.get("lineupDetailAddress"));
                    map1.put("address", address);
                    //联系电话
                    map1.put("lineupPhone", StringUtil.convertNullToEmpty(orderList_.get("lineupPhone")));
                    // 排队要求
                    map1.put("lineupRequire", StringUtil.convertNullToEmpty(orderList_.get("lineupRequire")));
                    // 排队时长
                    map1.put("lineupDuration", StringUtil.convertNullToEmpty(orderList_.get("lineupDuration")));
                }
                mapList.add(map1);
            }
            Map<String,Object> map2 = new HashMap<String, Object>();
            map2.put("row",orderList.size());
            mapList.add(map2);
            resultMap = getResultMap("1","获取我的订单成功",mapList);
        }else {
            resultMap = getResultMap("0","获取我的订单失败");
        }
        return resultMap;
    }

    /**
     * 获取订单详情
     * @param map
     * @return
     */


    public List<Map<String,Object>> getOrderDetail(Map<String,Object> map){
        return orderDao.getMyOrder(map);
    }

    /**
     * 获取接单跑客信息
     * @param map
     * @return
     */
    public List<Map<String,Object>> getDeliveryMan(Map<String,Object> map){
        return orderDao.getMyOrder(map);
    }

    /**
     * 获取当前城市的下一个订单流水号，并更新流水号所在的表
     * @param openCityId
     * @return -1:开通城市不存在
     */
    public String updateAndGetOrderNoOfOpenCity(long openCityId){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
        OpenCity openCity =openCityDao.get(openCityId);
        String orderNo="";
        if(openCity==null){
            orderNo="-1";
        }
        else{
            Date nowDate=new Date();
            orderNo=openCity.getOrderPrefix();
            orderNo+=dateFormat.format(nowDate);
            if(openCity.getOrderSerialDate()!=null){
                if(dateFormat.format(nowDate).equals(dateFormat.format(openCity.getOrderSerialDate()))){
                    int serialNo=openCity.getOrderSerialNo()+1;
                    openCity.setOrderSerialNo(serialNo);
                    String orderSerialNo=String.valueOf(serialNo);
                    //更新数据库
                    orderSerialNo="000000".substring(0, 6-orderSerialNo.length())+orderSerialNo;
                    orderNo+=orderSerialNo;
                }
                else{
                    openCity.setOrderSerialDate(nowDate);
                    openCity.setOrderSerialNo(1);
                    orderNo+="000001";
                }
            }
            else{
                openCity.setOrderSerialDate(nowDate);
                openCity.setOrderSerialNo(1);
                orderNo+="000001";
            }
            openCityDao.update(openCity);
        }

        return orderNo;
    }

    public Map<String, Object> addSendOrder(Order order, OrderGoods orderGoods){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int flag = orderDao.add(order);
        if (flag>0){
            orderGoods.setOrderId(order.getId());
            orderGoodsDao.add(orderGoods);
            resultMap = getResultMap("1","提交帮送/帮取订单成功");
        }else {
            resultMap = getResultMap("0","提交帮送/帮取订单失败");
        }
        return resultMap;
    }
    public Map<String, Object> addBuyOrder(Order order, OrderGoods orderGoods){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int flag = orderDao.add(order);
        if (flag>0){
            orderGoods.setOrderId(order.getId());
            orderGoodsDao.add(orderGoods);
            resultMap = getResultMap("1","提交帮买订单成功");
        }else {
            resultMap = getResultMap("0","提交帮买订单失败");
        }
        return resultMap;
    }

    public Map<String, Object>addLineupOrder(Order order, OrderLineup orderLineup){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int flag = orderDao.add(order);
        if (flag>0){
            orderLineup.setOrderId(order.getId());
            orderLineupDao.add(orderLineup);
            resultMap = getResultMap("1","提交代排队订单信息成功");
        }else {
            resultMap = getResultMap("0","提交代排队订单信息失败");
        }
        return resultMap;
    }

    /**
     * 获取订单详细信息列表
     * @param map
     * @return
     */
    public List<Map<String, Object>> getOrderInfoList(Map<String, Object> map){
        return orderDao.getOrderInfoList(map);
    }

    public List<Map<String,Object>> getTodayAchievement(Map<String,Object> map){
        return orderDao.getTodayAchievement(map);
    }



}