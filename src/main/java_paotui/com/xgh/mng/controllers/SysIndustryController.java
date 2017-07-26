package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysIndustryService;
import com.xgh.mng.services.ISysLogService;
import com.xgh.mng.services.ISysQueryItemService;
import com.xgh.mng.services.ISysValidationService;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.OpResult;
import com.xgh.util.ConstantUtil.OpType;
import com.xgh.util.ConstantUtil.SelectsTableName;
import com.xgh.util.JSONUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping({"/sys/industry"})
public class SysIndustryController
  extends BaseController
{
  private static final long serialVersionUID = 1L;
  private static Logger logger = Logger.getLogger(SysIndustryController.class);
  
  @InitBinder
  public void initBinder(WebDataBinder binder)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  }
  
  @InitBinder({"sysIndustry"})
  public void initBinder2(WebDataBinder binder)
  {
    binder.setFieldDefaultPrefix("sysIndustry.");
  }
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/industry/init");
    
    logger.info("industry init");
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    view.addObject("conditionHtml", this.sysQueryItemService.getConditionHtmlByRequest(this.request, getCurrentUnitId()));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysIndustry sysIndustry, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    if (op.equals("modify")) {
      sysIndustry = this.sysIndustryService.get(sysIndustry.getId());
    }
    view.setViewName("sys/industry/add");
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("industry_add"));
    view.addObject("sysIndustry", sysIndustry);
    view.addObject("op", op);
    
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList()
  {
    Map<String, Object> params = new HashMap();
    params.put("configQuery", this.request.getParameter("configQuery"));
    params.put("page", this.request.getParameter("page"));
    params.put("pagesize", this.request.getParameter("pagesize"));
    
    outJson(this.sysIndustryService.getListPage(params));
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysIndustry sysIndustry, @RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    
    sysIndustry.setStatus(1);
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      this.sysIndustryService.save(sysIndustry, op);
      
      String remark = op.equals("add") ? "行业添加" : "行业修改";
      this.sysLogService.addLog(this.request, getCurrentUser(), "行业维护", op, ConstantUtil.OpResult.Success.value(), remark, sysIndustry.getId() + "", ConstantUtil.SelectsTableName.SysIndustry.value());
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      map.put("code", "0");
      map.put("msg", "保存失败！");
    }
    outJson(map);
  }
  
  @RequestMapping({"/checkInit"})
  public ModelAndView checkInit(@ModelAttribute SysIndustry sysIndustry)
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/industry/check");
    
    SysIndustry sysIndustry2 = this.sysIndustryService.get(sysIndustry.getId());
    
    view.addObject("sysIndustry", sysIndustry2);
    
    return view;
  }
  
  @RequestMapping({"/check"})
  public void check(@ModelAttribute SysIndustry sysIndustry)
  {
    Map<String, Object> resultMap = new HashMap();
    try
    {
      int flg = this.sysIndustryService.check(sysIndustry);
      if (flg == 1) {
        resultMap = getResultMap("1", "审核通过");
      } else {
        resultMap = getResultMap("0", "审核不通过");
      }
      String msg = flg == 1 ? "审核通过" : "审核失败";
      this.sysLogService.addLog(this.request, getCurrentUser(), "行业审核", msg, ConstantUtil.OpResult.Success.value(), JSONUtil.getJson(sysIndustry), sysIndustry.getId() + "", ConstantUtil.SelectsTableName.SysIndustry.value());
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      resultMap = getResultMap("0", "审核失败");
    }
    outJson(resultMap);
  }
  
  @RequestMapping({"/delete"})
  public void delete(@ModelAttribute SysIndustry sysIndustry)
  {
    Map<String, Object> resultMap = new HashMap();
    try
    {
      resultMap = getResultMap("1", "删除成功!");
      
      SysIndustry sysIndustry2 = this.sysIndustryService.get(sysIndustry.getId());
      sysIndustry2.setStatus(0);
      this.sysIndustryService.update(sysIndustry2);
      this.sysLogService.addLog(this.request, getCurrentUser(), "行业维护", ConstantUtil.OpType.Delete.toString(), ConstantUtil.OpResult.Success.value(), "行业删除", sysIndustry.getId() + "", ConstantUtil.SelectsTableName.SysDepartment.value());
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      resultMap = getResultMap("0", "删除失败!");
    }
    outJson(resultMap);
  }
}
