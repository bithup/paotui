package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysParamType;
import com.xgh.mng.entity.SysUser;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysParamTypeService;
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
@RequestMapping({"/sys/paramtype"})
@Scope("prototype")
public class SysParamTypeController
  extends BaseController
{
  private static Logger logger = Logger.getLogger(SysParamTypeController.class);
  @Autowired
  protected ISysParamTypeService sysParamTypeService;
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/paramtype/init");
    
    SysUser sysUser = getCurrentUser();
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), sysUser));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    view.addObject("conditionHtml", this.sysQueryItemService.getConditionHtmlByRequest(this.request, sysUser.getUnitId()));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysParamType sysParamType, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("sysparamtype_validate"));
    if (op.equals("modify")) {
      sysParamType = this.sysParamTypeService.get(sysParamType.getId());
    }
    view.addObject("sysParamType", sysParamType);
    view.addObject("op", op);
    view.setViewName("sys/paramtype/add");
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam String op)
  {
    outJson(this.sysParamTypeService.getGirdData(this.request));
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysParamType sysParamType, String op)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      if (this.sysParamTypeService.isHasSameCode(op, sysParamType))
      {
        map.put("code", "0");
        map.put("msg", "当前编码已使用，请使用其他编码！");
      }
      else if (op.equals("add"))
      {
        this.sysParamTypeService.add(sysParamType);
      }
      else
      {
        this.sysParamTypeService.update(sysParamType);
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
  public void delete(@ModelAttribute SysParamType sysParamType)
  {
    String code = "1";
    String msg = "删除成功！";
    try
    {
      this.sysParamTypeService.delete(sysParamType.getId());
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
