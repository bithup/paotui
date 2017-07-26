package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.Customer;
import com.xgh.paotui.entity.Order;
import com.xgh.paotui.entity.OrderTaskSetting;
import com.xgh.paotui.services.ICustomerService;
import com.xgh.paotui.services.IFileDataServiceNew;
import com.xgh.paotui.services.IOrderTaskSettingService;
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
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping(value="/paotui/orderTaskSetting/")
public class OrderTaskSettingController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(OrderTaskSettingController.class);

    @Autowired
    protected IOrderTaskSettingService orderTaskSettingService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @RequestMapping(value = "init")
    public ModelAndView init() {
        return getModelAndView("paotui/orderTaskSetting/init");
    }

    @RequestMapping(value = "add")
    public ModelAndView add(@ModelAttribute OrderTaskSetting orderTaskSetting, @RequestParam String op) {
        ModelAndView view = new ModelAndView();
        view.addObject("op", op);
        if ("modify".equals(op)) {
            orderTaskSetting = orderTaskSettingService.get(orderTaskSetting.getId());
        }
        view.setViewName("paotui/orderTaskSetting/add");
        view.addObject("orderTaskSetting", orderTaskSetting);
        return view;
    }

    @RequestMapping(value = "save")
    public void save(@ModelAttribute OrderTaskSetting orderTaskSetting){
        logger.info("save");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (orderTaskSetting!=null){
            int aom = orderTaskSetting.getId()>0 ? 1:0;
            int falg = orderTaskSettingService.save(orderTaskSetting);
            if (falg>0){
                if (aom==0){
                    resultMap = getResultMap("1","添加成功!");
                }else {
                    resultMap = getResultMap("1","修改成功!");
                }
            }else {
                if (aom==0){
                    resultMap = getResultMap("0","添加失败!");
                }else {
                    resultMap = getResultMap("0","修改失败!");
                }
            }
        }else {
            resultMap = getResultMap("0","数据初始化失败!");
        }
        outJson(resultMap);
    }

    @RequestMapping(value = "delete")
    public void delete(@ModelAttribute OrderTaskSetting orderTaskSetting){
        logger.info("delete");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (orderTaskSetting!=null){
            int falg = orderTaskSettingService.update(orderTaskSetting);
            if (falg>0){
                resultMap = getResultMap("1","移除成功!");
            }else {
                resultMap = getResultMap("0","移除失败!");
            }
        }else {
            resultMap = getResultMap("0","数据初始化失败!");
        }
        outJson(resultMap);
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        outJson(orderTaskSettingService.getGridList(request));
    }

}
