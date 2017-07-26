package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.Coupon;
import com.xgh.paotui.services.ICouponService;
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
@RequestMapping(value="/paotui/coupon/")
public class CouponController extends BaseController {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(CouponController.class);

    @Autowired
    protected ICouponService couponService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(value = "init")
    public ModelAndView init() {
        return getModelAndView("paotui/coupon/init");
    }

    @RequestMapping(value = "add")
    public ModelAndView add(@ModelAttribute Coupon coupon, @RequestParam String op) {
        ModelAndView view = new ModelAndView();
        view.addObject("op", op);
        if ("modify".equals(op)) {
            coupon = couponService.get(coupon.getId());
        }
        view.setViewName("paotui/coupon/add");
        view.addObject("orderTaskSetting", coupon);
        return view;
    }

    @RequestMapping(value = "save")
    public void save(@ModelAttribute Coupon coupon){
        logger.info("save");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (coupon!=null){
            int falg = couponService.save(request,coupon);
            if (falg>0){
                resultMap = getResultMap("1","添加成功!");
            }else {
                resultMap = getResultMap("-1","添加失败!");
            }
        }else {
            resultMap = getResultMap("0","数据初始化失败!");
        }
        outJson(resultMap);
    }

    @RequestMapping(value = "delete")
    public void delete(@ModelAttribute Coupon coupon){
        logger.info("delete");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (coupon!=null){
            int falg = couponService.update(coupon);
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
        outJson(couponService.getGridList(request));
    }

}
