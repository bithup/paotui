package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.services.IDeliveryManMarketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@Scope("prototype")
@RequestMapping(value="/paotui/deliverManMarket/")
public class DeliveryManMarketController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(DeliveryManMarketController.class);

    @Autowired
    protected IDeliveryManMarketService deliveryManMarketService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @RequestMapping(value = "init")
    public ModelAndView init() {
        return getModelAndView("paotui/deliverManMarket/init");
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        SysUser sysUser = this.getCurrentUser();
        String belongCityId="";
        if (sysUser.getOpenCityId()!=null  && sysUser.getOpenCityId()!=0){
            belongCityId=String.valueOf(sysUser.getOpenCityId());
        }
        outJson(deliveryManMarketService.getGridList(request,belongCityId));
    }
}
