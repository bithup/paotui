package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysDepartment;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysDepartmentService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysLogService;
import com.xgh.mng.services.ISysUnitsService;
import com.xgh.mng.services.ISysValidationService;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.OpResult;
import com.xgh.util.ConstantUtil.OpType;
import com.xgh.util.ConstantUtil.SelectsTableName;
import com.xgh.util.JSONUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@Controller
@RequestMapping({"/sys/department"})
@Scope("prototype")
public class SysDepartmentController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(SysDepartmentController.class);
  @Autowired
  protected ISysDepartmentService sysDepartmentService;
  
  @InitBinder
  public void initBinder(WebDataBinder binder)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  }
  
  @InitBinder({"sysDepartment"})
  public void initBinder1(WebDataBinder binder)
  {
    binder.setFieldDefaultPrefix("sysDepartment.");
  }
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/department/init");
    

    long unitId = getCurrentUnitId();
    
    List<Map<String, Object>> treeList = this.sysDepartmentService.getChildTreeData(unitId, 0L);
    
    SysUnits sysUnits = this.sysUnitsService.get(unitId);
    Map<String, Object> rootMap = new HashMap();
    rootMap.put("id", Integer.valueOf(0));
    rootMap.put("text", sysUnits.getUnitName());
    treeList.add(rootMap);
    view.addObject("treedata", JSONUtil.getJson(treeList));
    
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysDepartment sysDepartment, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    if (op.equals("modify")) {
      sysDepartment = this.sysDepartmentService.get(sysDepartment.getId());
    }
    view.setViewName("sys/department/add");
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("sysdepartment_validate"));
    
    view.addObject("sysDepartment", sysDepartment);
    view.addObject("op", op);
    view.addObject("unitType", Integer.valueOf(getCurrentUnits().getUnitType()));
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam String op, @RequestParam long pid)
  {
    if (op.equals("grid")) {
      outJson(this.sysDepartmentService.getGridData(this.request, getCurrentUnitId(), pid));
    } else if (op.equals("tree")) {
      outJson(this.sysDepartmentService.getChildTreeData(getCurrentUnitId(), pid));
    }
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysDepartment sysDepartment, @RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    
    long unitId = getCurrentUnitId();
    
    sysDepartment.setUnitId(unitId);
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      this.sysDepartmentService.save(sysDepartment, op);
      
      String remark = op.equals("add") ? "部门添加" : "部门修改";
      this.sysLogService.addLog(this.request, getCurrentUser(), "部门维护", op, ConstantUtil.OpResult.Success.value(), remark, sysDepartment.getId() + "", ConstantUtil.SelectsTableName.SysDepartment.value());
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
  public void delete(@ModelAttribute SysDepartment sysDepartment)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "删除成功！");
      if (this.sysDepartmentService.isHasChild(sysDepartment))
      {
        map.put("code", "0");
        map.put("msg", "当前部门有子级，不可删除！");
      }
      else if (this.sysDepartmentService.getUserRowsByDeptId(sysDepartment.getId()) > 0L)
      {
        map.put("code", "0");
        map.put("msg", "当前部门有用户，不可删除！");
      }
      else
      {
        this.sysDepartmentService.deleteDepartment(sysDepartment);
        this.sysLogService.addLog(this.request, getCurrentUser(), "部门维护", ConstantUtil.OpType.Delete.toString(), ConstantUtil.OpResult.Success.value(), "部门删除", sysDepartment.getId() + "", ConstantUtil.SelectsTableName.SysDepartment.value());
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      map.put("code", "0");
      map.put("msg", "删除失败！");
    }
    outJson(map);
  }
}
