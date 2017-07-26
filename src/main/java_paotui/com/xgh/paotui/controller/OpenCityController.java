package com.xgh.paotui.controller;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysUser;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.services.IOpenCityService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by Tian on 2017/2/23.
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "paotui/openCity/")
public class OpenCityController extends BaseController {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(OpenCityController.class);

    @Autowired
    protected IOpenCityService openCityService;

    @RequestMapping(value = "/init")
    public ModelAndView init() {
        logger.info("init");
        ModelAndView view = new ModelAndView();
        view.setViewName("paotui/openCity/init");

        view.addObject("toolbar", sysButtonService.getMenuButtons(request, getCurrentSysRoleId(), getCurrentUser()));
        view.addObject("gridComluns", sysGridColumnsService.getGridColumsByRequest(request));


        return view;
    }

    @RequestMapping(value = "/getList")
    public void getList() {
        logger.info("getList");
        Map<String,Object> ret=openCityService.getGridList(request);
        outJson(ret);
    }

    @RequestMapping(value = "/add")
    public ModelAndView add(@ModelAttribute OpenCity openCity, @RequestParam String op) {
        ModelAndView view = new ModelAndView();
        view.addObject("op", op);
        if ("modify".equals(op)) {
            openCity = openCityService.get(openCity.getId());
        }
        view.setViewName("paotui/openCity/add");
        view.addObject("openCity", openCity);

        view.addObject("managementTypeRadioInit", DictUtils.getDictListForRadio("paotui_management_type"));
        return view;
    }

    @RequestMapping(value = "/save")
    public void save(@ModelAttribute OpenCity openCity) {
        logger.info("save");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        if (openCity != null) {
            //判断添加修改,aom, 0 添加,1 修改
            Long a=openCity.getId();
            int aom = openCity.getId() > 0 ? 1 : 0;
            int flg = openCityService.save(request, openCity);

            if (aom == 1) {
                if (flg == 1) {
                    resultMap = getResultMap("1", "修改成功!");
                } else
                    resultMap = getResultMap("1", "修改失败!");
            } else {
                if (flg == 1) {
                    resultMap = getResultMap("1", "添加成功!");
                } else
                    resultMap = getResultMap("0", "添加失败!");
            }
        } else
            resultMap = getResultMap("0", "数据初始化失败!");

        outJson(resultMap);
    }

    @RequestMapping(value = "/delete")
    public void delete(@ModelAttribute OpenCity openCity) {
        logger.info("delete");
        Map<String, Object> resultMap = new HashMap<String, Object>();

        OpenCity openCity2 = openCityService.get(openCity.getId());
        openCity2.setUpdateDate(new Date());
        openCity2.setStatus(2);

        int flg = openCityService.update(openCity2);
        if (flg == 1) {
            resultMap = getResultMap("1", "删除成功!");
        } else
            resultMap = getResultMap("0", "删除失败!");
        outJson(resultMap);
    }
    /**
     * 获取所有开通的城市
     */
    @RequestMapping(value = "/getAllOpenCitys")
    public void getAllOpenCitys() {
        logger.info("getAllOpenCitys");
        Map<String, Object> map = new HashMap<String, Object>();
        SysUser sysUser = this.getCurrentUser();
        if(sysUser.getOpenCityId()!=null){
            map.put("openCityId",sysUser.getOpenCityId());
        }
        List<OpenCity> resultMap = openCityService.getList(map);
        outJson(resultMap);
    }

    @RequestMapping(value = "/getOpenCity")
    public void getOpenCity(){
        Map<String, Object> map = new HashMap<String, Object>();
        SysUser sysUser = this.getCurrentUser();
        if(sysUser.getOpenCityId()!=null){
            map.put("openCityId",sysUser.getOpenCityId());
        }
        List list = openCityService.getList(map);
        outJson(list);
    }
}