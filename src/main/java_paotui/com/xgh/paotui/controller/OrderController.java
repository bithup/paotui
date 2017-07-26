package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.Order;
import com.xgh.paotui.entity.OrderAction;
import com.xgh.paotui.entity.OrderPositionPath;
import com.xgh.paotui.services.IOrderActionService;
import com.xgh.paotui.services.IOrderPositionPathService;
import com.xgh.paotui.services.IOrderService;
import com.xgh.util.ConstantUtil;
import com.xgh.util.DictUtils;
import com.xgh.util.StringUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
@RequestMapping(value = "paotui/order/")
public class OrderController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    protected IOrderActionService orderActionService;

    @Autowired
    protected IOrderService orderService;

    @Autowired
    protected IOrderPositionPathService orderPositionPathService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "/init")
    public ModelAndView init() {
        logger.info("init");
        return getModelAndView("paotui/order/init");
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        outJson(orderService.getGridList(request));
    }

    @RequestMapping(value = "/view")
    public ModelAndView view(@ModelAttribute Order order) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> map = orderService.getOrderInfo(order.getId());
        //订单类型
        String orderType=StringUtil.convertNullToEmpty(map.get("orderType"));
        map.put("orderType",DictUtils.getDictLabel(orderType,"paotui_order_type",""));
        //帮送：发货地；帮取：取货地；帮买：购买地
        String addressA = StringUtil.convertNullToEmpty(map.get("locationAddressA"));
        addressA+=StringUtil.convertNullToEmpty(map.get("locationAddressNameA"));
        addressA+=StringUtil.convertNullToEmpty(map.get("detailAddressA"));
        map.put("addressA",addressA);
        //收货地
        String addressB = StringUtil.convertNullToEmpty(map.get("locationAddressB"));
        addressB+=StringUtil.convertNullToEmpty(map.get("locationAddressNameB"));
        addressB+=StringUtil.convertNullToEmpty(map.get("detailAddressB"));
        map.put("addressB",addressB);
        //跑腿费
        String fee="跑腿费"+StringUtil.convertNullToEmpty(map.get("originalFreight"))+"元";
        String tips=StringUtil.convertNullToEmpty(map.get("tips"));
        if(StringUtil.isNotEmpty(tips)){
            fee+="+小费";
            fee+=tips;
            fee+="元";
        }
        map.put("fee",fee);
        if("1".equals(orderType)||"2".equals(orderType)){
            //发货时间
            String bookingType=StringUtil.convertNullToEmpty(map.get("bookingType"));
            if("1".equals(bookingType)){
                map.put("bookingTime","立即发货");
            } else{
                Date bookingTime= (Date)map.get("bookingTime");
                map.put("bookingTime","预约到"+bookingTime+"送货");
            }
            view.setViewName("paotui/order/view");
        } else if("3".equals(orderType)){
            //是否就近购买
            String isNearBuy = StringUtil.convertNullToEmpty(map.get("isNearBuy"));
            if ("1".equals(isNearBuy)){
                map.put("addressA","收货地附近购买");
            }else {
                map.put("addressA",addressA);
            }
            //商品价格
            Integer isKonwPrice=(Integer)map.get("isKonwPrice");
            if(isKonwPrice!=null && isKonwPrice==1){
                map.put("buyPrice",StringUtil.convertNullToEmpty(map.get("buyPrice"))+"元");
            } else{
                map.put("buyPrice","不知道价格");
            }
            //购买时间
            String bookingType=StringUtil.convertNullToEmpty(map.get("bookingType"));
            if("1".equals(bookingType)){
                map.put("bookingTime","立即购买");
            } else{
                Date bookingTime= (Date)map.get("bookingTime");
                map.put("bookingTime","预约到"+bookingTime+"购买");
            }
            view.setViewName("paotui/order/buy_view");
        } else if ("4".equals(orderType)){
            //排队时间
            String bookingType=StringUtil.convertNullToEmpty(map.get("bookingType"));
            if("1".equals(bookingType)){
                map.put("bookingTime","立即排队");
            } else{
                Date bookingTime= (Date)map.get("bookingTime");
                map.put("bookingTime","预约到"+bookingTime+"排队");
            }
            //排队地点
            String address = StringUtil.convertNullToEmpty(map.get("lineupLocationAddress"));
            address+=StringUtil.convertNullToEmpty(map.get("lineupLocationAddressName"));
            address+=StringUtil.convertNullToEmpty(map.get("lineupDetailAddress"));
            map.put("address",address);
            //总费用
            String lineupFee="排队费"+StringUtil.convertNullToEmpty(map.get("lineupRealMoney"))+"元";
            if(StringUtil.isNotEmpty(tips)){
                lineupFee+="+小费";
                lineupFee+=tips;
                lineupFee+="元";
            }
            map.put("lineupFee",lineupFee);
            view.setViewName("paotui/order/lineup_view");
        }
        view.addObject("orderMap", map);
        //状态轨迹图
        Map<String, Object> paraMap =new HashMap<String, Object>();
        paraMap.put("orderId",order.getId());
        List<OrderAction>  orderAction=orderActionService.getList(paraMap);
        List<Map<String, Object>>  orderActionMaps= new ArrayList<Map<String, Object>>();
        for(OrderAction orderAction_:orderAction){
            Map orderActionMap = new HashMap();
            Date actionTime= orderAction_.getActionTime();
            orderActionMap.put("actionTime",actionTime);
            orderActionMap.put("actionName",orderAction_.getActionName());
            //功能暂时按照一张开发
            String actionImage=orderAction_.getActionImage();
            if(StringUtil.isNotEmpty(actionImage)){
                orderActionMap.put("actionImageRealUrl", ConstantUtil.SERVER_URL+actionImage);
            }
            else{
                orderActionMap.put("actionImageRealUrl", "");
            }
            orderActionMaps.add(orderActionMap);
        }
        view.addObject("orderActionMaps", orderActionMaps);
        return view;
    }

    @RequestMapping(value = "/viewMap")
    public ModelAndView viewMap(@ModelAttribute Order order) {
        ModelAndView view = new ModelAndView();
        Map<String, Object> paraMap =new HashMap<String, Object>();
        paraMap.put("orderId",order.getId());
        List<OrderPositionPath> orderPositionPaths=orderPositionPathService.getList(paraMap);
        JSONArray ret= new JSONArray();
        for(OrderPositionPath data:orderPositionPaths){
            JSONObject json= new JSONObject();
            json.put("longitude",data.getLongitude());
            json.put("latitude",data.getLatitude());
            ret.add(json);
        }
        view.addObject("orderPositionPaths", ret.toJSONString());
        Map<String, Object> map = orderService.getOrderInfo(order.getId());
        String orderType = StringUtil.convertNullToEmpty(map.get("orderType"));
        if ("4".equals(orderType)){
            String longitude = StringUtil.convertNullToEmpty(map.get("lineupLongitude"));
            String latitude = StringUtil.convertNullToEmpty(map.get("lineupLatitude"));
            map.put("longitudeA",longitude);
            map.put("latitudeA",latitude);
        }
        //开通城市
        view.addObject("orderCityName", String.valueOf(map.get("orderCityName")));
        view.addObject("orderMap", map);
        view.setViewName("paotui/order/viewMap");
        return view;
    }

    @RequestMapping(value = "/save")
    public void save(@ModelAttribute Order order) {
        logger.info("save");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (order != null) {
            int flg = orderService.save(request, order);
            if (flg == 1) {
                resultMap = getResultMap("1", "取消订单成功!");
            } else{
                resultMap = getResultMap("1", "取消订单失败!");
            }
        } else{
            resultMap = getResultMap("0", "数据初始化失败!");
        }
        outJson(resultMap);
    }

    @RequestMapping(value = "/delete")
    public void delete(@ModelAttribute Order order) {
        logger.info("delete");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Order order2 = orderService.get(order.getId());
        order2.setStatus(2);
        int flg = orderService.update(order2);
        if (flg == 1) {
            resultMap = getResultMap("1", "删除成功!");
        } else
            resultMap = getResultMap("0", "删除失败!");
        outJson(resultMap);
    }

}