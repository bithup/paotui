package com.xgh.paotui.controller;

import com.alibaba.fastjson.JSONObject;
import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.entity.OrderCancel;
import com.xgh.paotui.services.IOrderCancelService;
import com.xgh.util.DictUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tian on 2017/2/23.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "paotui/orderCancel/")
public class OrderCancelController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(OrderCancelController.class);

    @Autowired
    protected IOrderCancelService orderCancelService;

    @RequestMapping(value = "/init")
    public ModelAndView init() {
        logger.info("init");
        ModelAndView view = new ModelAndView();
        view.setViewName("paotui/orderCancel/init");
        view.addObject("toolbar", sysButtonService.getMenuButtons(request, getCurrentSysRoleId(), getCurrentUser()));
        view.addObject("gridComluns", sysGridColumnsService.getGridColumsByRequest(request));
        return view;
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        Map<String, Object> map = new HashMap<String, Object>();
        String page = request.getParameter("page");
        String pagesize = request.getParameter("pagesize");
        String orderType = request.getParameter("orderType");
        map.put("page", Integer.parseInt(page));
        map.put("pagesize", Integer.parseInt(pagesize));
        map.put("orderType",orderType);
        Map<String,Object> ret=orderCancelService.getGridList(map);
        outJson(ret);
    }

    @RequestMapping(value = "/add")
    public ModelAndView add(@ModelAttribute OrderCancel orderCancel, @RequestParam String op) {
        ModelAndView view = new ModelAndView();
        view.addObject("op", op);
        if ("modify".equals(op)) {
            orderCancel = orderCancelService.get(orderCancel.getId());
        }
        view.setViewName("paotui/orderCancel/add");
        view.addObject("orderCancel", orderCancel);
        view.addObject("orderCancel", orderCancel);
        String orderTypeName= DictUtils.getDictLabel(String.valueOf(orderCancel.getOrderType()),"paotui_order_type","");
        view.addObject("orderTypeName", orderTypeName);
        view.addObject("managementTypeRadioInit", DictUtils.getDictListForRadio("paotui_management_type"));
        return view;
    }

    @RequestMapping(value = "/save")
    public void save(@ModelAttribute OrderCancel orderCancel) {
        logger.info("save");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        SysUser sysUser = this.getCurrentUser();
        if (orderCancel != null) {
            String cancelFailInfo = orderCancelService.updateOrderCancelCheck(orderCancel.getId(),orderCancel.getCheckState(),sysUser.getId());
            JSONObject jsonCancelInfo= JSONObject.parseObject(cancelFailInfo);
            String msg="";
           if("success".equals(jsonCancelInfo.get("refundFreight"))){
               msg+="跑腿费退款成功；";
           }
            else if("fail".equals(jsonCancelInfo.get("refundFreight"))){
               msg+="跑腿费退款失败；";
           }
            if("success".equals(jsonCancelInfo.get("refundTips"))){
                msg+="小费退款成功；";
            }
            else if("fail".equals(jsonCancelInfo.get("refundTips"))){
                msg+="小费退款失败；";
            }
            if("success".equals(jsonCancelInfo.get("refundGoods"))){
                msg+="商品费退款成功；";
            }
            else if("fail".equals(jsonCancelInfo.get("refundGoods"))){
                msg+="商品费退款失败；";
            }
            if (StringUtils.isNotEmpty(msg)) {
                resultMap = getResultMap("1",msg);
            } else{
                resultMap = getResultMap("1", "退款失败!");
            }
        } else
            resultMap = getResultMap("0", "退款失败!");
        outJson(resultMap);
    }


}