package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.paotui.entity.FeeAllocation;
import com.xgh.paotui.services.IFeeAllocationService;
import com.xgh.util.DictUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tian on 2017/2/23.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "paotui/feeAllocation/")
public class FeeAllocationController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(FeeAllocationController.class);

    @Autowired
    protected IFeeAllocationService feeAllocationService;

    @RequestMapping(value = "/init")
    public ModelAndView init() {
        logger.info("init");
        ModelAndView view = new ModelAndView();
        view.setViewName("paotui/feeAllocation/init");

        view.addObject("toolbar", sysButtonService.getMenuButtons(request, getCurrentSysRoleId(), getCurrentUser()));
        view.addObject("gridComluns", sysGridColumnsService.getGridColumsByRequest(request));


        return view;
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        Map<String,Object> ret=feeAllocationService.getGridList(request);

        outJson(ret);
    }

    @RequestMapping(value = "/add")
    public ModelAndView add(@ModelAttribute FeeAllocation feeAllocation, @RequestParam String op) {
        ModelAndView view = new ModelAndView();
        view.addObject("op", op);
        if ("modify".equals(op)) {
            feeAllocation = feeAllocationService.get(feeAllocation.getId());
        }
        view.setViewName("paotui/feeAllocation/add");
        view.addObject("feeAllocation", feeAllocation);

        view.addObject("allocationTypeRadioInit", DictUtils.getDictListForRadio("paotui_allocation_type"));
        return view;
    }

    @RequestMapping(value = "/save")
    public void save(@ModelAttribute FeeAllocation feeAllocation) {
        logger.info("save");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        if (feeAllocation != null) {
            //判断添加修改,aom, 0 添加,1 修改
            Long a=feeAllocation.getId();
            int aom = feeAllocation.getId() > 0 ? 1 : 0;
            int flg = feeAllocationService.save(request, feeAllocation);

            if (aom == 1) {
                if (flg == 1) {
                    resultMap = getResultMap("1", "修改成功!");
                } else
                    resultMap = getResultMap("0", "修改失败!");
            } else {
                if (flg == 1) {
                    resultMap = getResultMap("1", "添加成功!");
                } else
                    resultMap = getResultMap("0", "添加失败!");
            }
        } else
            resultMap = getResultMap("-1", "数据初始化失败!");

        outJson(resultMap);
    }

    @RequestMapping(value = "/delete")
    public void delete(@ModelAttribute FeeAllocation feeAllocation) {
        logger.info("delete");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        FeeAllocation feeAllocation2 = feeAllocationService.get(feeAllocation.getId());
        feeAllocation2.setUpdateDate(new Date());
        feeAllocation2.setStatus(2);

        int flg = feeAllocationService.update(feeAllocation2);
        if (flg == 1) {
            resultMap = getResultMap("1", "删除成功!");
        } else
            resultMap = getResultMap("0", "删除失败!");

        outJson(resultMap);
    }
}