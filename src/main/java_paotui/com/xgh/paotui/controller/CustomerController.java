package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.Customer;
import com.xgh.paotui.services.ICustomerService;
import com.xgh.paotui.services.IFileDataServiceNew;
import com.xgh.util.ConstantUtil;
import com.xgh.util.JSONUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping(value="/paotui/customer/")
public class CustomerController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CustomerController.class);

    @Autowired
    protected ICustomerService customerService;

    @Autowired
    protected IFileDataServiceNew fileDataServiceNew;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @RequestMapping(value = "init")
    public ModelAndView init() {
        return getModelAndView("paotui/customer/init");
    }

    @RequestMapping(value = "add")
    public ModelAndView add(@ModelAttribute Customer customer, @RequestParam String op) {
        ModelAndView view = new ModelAndView();
        view.addObject("op", op);
        if ("modify".equals(op)) {
            customer = customerService.get(customer.getId());
            if (customer != null) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("instId", getCurrentInstId());
                params.put("dataId", customer.getId());
                params.put("dataCode", ConstantUtil.FileUploadCode.Customer.value());
                params.put("dataVersion", 0);
                params.put("server", ConstantUtil.SERVER_URL);
                List<Map<String, Object>> fileDataList = fileDataServiceNew.getFileDatas(params);
                view.addObject("fileDataListJson", JSONUtil.getJson(fileDataList));
            }
        }
        view.setViewName("paotui/customer/add");
        view.addObject("customer", customer);
        return view;
    }

    @RequestMapping(value = "/getList")
        public void getList() {
        logger.info("getList");
        outJson(customerService.getGridList(request));
    }

}
