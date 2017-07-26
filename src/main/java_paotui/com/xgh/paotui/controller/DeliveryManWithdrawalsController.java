package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.entity.DeliveryMan;
import com.xgh.paotui.entity.DeliveryManWithdrawals;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.services.*;
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
@RequestMapping(value="/paotui/deliveryManWithdrawals/")
public class DeliveryManWithdrawalsController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(DeliveryManWithdrawalsController.class);

    @Autowired
    protected IDeliveryManWithdrawalsService deliveryManWithdrawalsService;

    @Autowired
    protected IDeliveryManService deliveryManService;

    @Autowired
    protected IOpenCityService openCityService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
    }

    @RequestMapping(value = "init")
    public ModelAndView init() {
        return getModelAndView("paotui/deliveryManWithdrawals/init");
    }

    @RequestMapping(value = "add")
    public ModelAndView add(@ModelAttribute DeliveryManWithdrawals deliveryManWithdrawals, @RequestParam String op) {
        ModelAndView view = new ModelAndView();
        view.addObject("op", op);
        if ("modify".equals(op)) {
            deliveryManWithdrawals = deliveryManWithdrawalsService.get(deliveryManWithdrawals.getId());
            DeliveryMan deliveryMan = deliveryManService.get(deliveryManWithdrawals.getDeliverManId());
            if (deliveryMan!=null){
                OpenCity openCity = openCityService.get(deliveryMan.getBelongCityId());
                view.addObject("openCity",openCity);
            }
            view.addObject("deliveryMan",deliveryMan);
        }
        view.setViewName("paotui/deliveryManWithdrawals/add");
        view.addObject("deliveryManWithdrawals", deliveryManWithdrawals);
        return view;
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        outJson(deliveryManWithdrawalsService.getGridList(request));
    }

    @RequestMapping(value = "/save")
    public void save(@ModelAttribute DeliveryManWithdrawals deliveryManWithdrawals) {
        logger.info("save");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (deliveryManWithdrawals != null) {
            SysUser sysUser=getCurrentUser();
            deliveryManWithdrawals.setCheckUserId(sysUser.getId());
            int flg = deliveryManWithdrawalsService.save(deliveryManWithdrawals);
            logger.info(sysUser.getAccount()+"审核提现Id:"+String.valueOf(deliveryManWithdrawals.getId())+"，返回值："+String.valueOf(flg));
            if (flg == 1) {
                resultMap = getResultMap("1", "审核通过，提现成功!");
            } else if(flg == 2) {
                resultMap = getResultMap("1", "审核不通过!");
            }
            else if(flg == 3){
                resultMap = getResultMap("0", "审核通过，提现失败!");
            }
            else {
                resultMap = getResultMap("0", "审核失败!");
            }
        } else{
            resultMap = getResultMap("0", "审核失败!");
        }
        outJson(resultMap);
    }

}
