package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.services.ICustomerRechargeService;
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
@RequestMapping(value="/paotui/customerRecharge/")
public class CustomerRechargeController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CustomerRechargeController.class);

    @Autowired
    protected ICustomerRechargeService customerRechargeService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @RequestMapping(value = "init")
    public ModelAndView init() {
        return getModelAndView("paotui/customerRecharge/init");
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        outJson(customerRechargeService.getGridList(request));
    }

}
