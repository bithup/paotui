package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysParam;
import com.xgh.mng.entity.SysUser;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysParamService;
import com.xgh.mng.services.ISysQueryItemService;
import com.xgh.mng.services.ISysValidationService;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/sys/param"})
@Scope("prototype")
public class SysParamController
  extends BaseController
{
  private static Logger logger = Logger.getLogger(SysParamController.class);
  @Autowired
  protected ISysParamService sysParamService;
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/param/init");
    
    SysUser sysUser = getCurrentUser();
    
    view.addObject("unitId", Long.valueOf(sysUser.getUnitId()));
    view.addObject("treedata", this.sysParamService.getParamTypeTreeData());
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), sysUser));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    view.addObject("conditionHtml", this.sysQueryItemService.getConditionHtmlByRequest(this.request, sysUser.getUnitId()));
    
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysParam sysParam, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("sysparam_validate"));
    if (op.equals("modify")) {
      sysParam = this.sysParamService.get(sysParam.getId());
    }
    view.addObject("unitId", Long.valueOf(getCurrentUnitId()));
    view.addObject("sysParam", sysParam);
    view.addObject("op", op);
    view.setViewName("sys/param/add");
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam String op)
  {
    outJson(this.sysParamService.getGirdData(this.request, getCurrentUnitId()));
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysParam sysParam, String op)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      long unitId = getCurrentUnitId();
      
      sysParam.setUnitId(unitId);
      if (this.sysParamService.isHasSameCode(op, sysParam))
      {
        map.put("code", "0");
        map.put("msg", "当前编码已使用，请使用其他编码！");
      }
      else
      {
        this.sysParamService.save(op, unitId, sysParam);
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      map.put("code", "0");
      map.put("msg", "保存失败！");
    }
    outJson(map);
  }
  
  @RequestMapping({"/delete"})
  public void delete(@ModelAttribute SysParam sysParam)
  {
    String code = "1";
    String msg = "删除成功！";
    try
    {
      this.sysParamService.delete(sysParam.getId());
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      code = "0";
      msg = "删除失败！";
    }
    outJson(getResultMap(code, msg));
  }
}
