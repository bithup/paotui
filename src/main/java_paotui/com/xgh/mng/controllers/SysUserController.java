package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysDeptUser;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysDepartmentService;
import com.xgh.mng.services.ISysDeptUserService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysLogService;
import com.xgh.mng.services.ISysQueryItemService;
import com.xgh.mng.services.ISysUnitsService;
import com.xgh.mng.services.ISysUserService;
import com.xgh.mng.services.ISysValidationService;
import com.xgh.paotui.entity.OpenCity;
import com.xgh.paotui.services.IOpenCityService;
import com.xgh.security.MD5Util;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.OpResult;
import com.xgh.util.ConstantUtil.OpType;
import com.xgh.util.ConstantUtil.SelectsTableName;
import com.xgh.util.JSONUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping({"/sys/user"})
public class SysUserController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(SysUserController.class);
  @Autowired
  protected ISysDepartmentService sysDepartmentService;
  @Autowired
  protected ISysUserService sysUserService;
  @Autowired
  protected ISysDeptUserService sysDeptUserService;

  @Autowired
  protected IOpenCityService openCityService;
  
  @InitBinder({"sysUser"})
  public void initBinder1(WebDataBinder binder)
  {
    binder.setFieldDefaultPrefix("sysUser.");
  }
  
  @InitBinder({"sysDeptUser"})
  public void initBinder2(WebDataBinder binder)
  {
    binder.setFieldDefaultPrefix("sysDeptUser.");
  }
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/user/init");
    

    long unitId = getCurrentUnitId();
    
    List<Map<String, Object>> treeList = this.sysDepartmentService.getChildTreeData(unitId, 0L);
    
    SysUnits sysUnits = this.sysUnitsService.get(unitId);
    Map<String, Object> rootMap = new HashMap();
    rootMap.put("id", Integer.valueOf(0));
    rootMap.put("code", Long.valueOf(sysUnits.getId()));
    rootMap.put("text", sysUnits.getUnitName());
    treeList.add(rootMap);
    view.addObject("treedata", JSONUtil.getJson(treeList));
    
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    view.addObject("conditionHtml", this.sysQueryItemService.getConditionHtmlByRequest(this.request, unitId));
    
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysUser sysUser, @ModelAttribute SysDeptUser sysDeptUser, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("sysuser_validate"));
    if (op.equals("modify"))
    {
      sysUser = this.sysUserService.get(sysUser.getId());
      sysDeptUser = this.sysDeptUserService.get(sysDeptUser.getId());
    }
    view.addObject("sysUser", sysUser);
    view.addObject("sysDeptUser", sysDeptUser);
    view.addObject("op", op);
    view.setViewName("sys/user/add");
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam long deptId)
  {
    outJson(this.sysUserService.getGridData(this.request, deptId));
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysUser sysUser, @ModelAttribute SysDeptUser sysDeptUser, @RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    
    sysUser.setInstId(getCurrentInstId());
    sysUser.setInstCode(getCurrentIndustry().getCode());
    sysUser.setUnitId(getCurrentUnitId());
    sysUser.setUnitCode(getCurrentUnits().getUnitCode());
   sysUser.setStatus(1);
    OpenCity openCity = openCityService.get(sysUser.getOpenCityId());
    sysUser.setOpenCityName(openCity.getCityName());
    try
    {
      if (this.sysUserService.isHasSameAcount(sysUser, op))
      {
        map.put("code", "0");
        map.put("msg", "账号重复，请用其他账号！");
      }
      else
      {
        map.put("code", "1");
        map.put("msg", "保存成功！");
        this.sysUserService.save(sysUser, sysDeptUser, op);
        
        String memo = op.equals("add") ? "用户注册" : "用户修改";
        this.sysLogService.addLog(this.request, getCurrentUser(), "用户维护", op, ConstantUtil.OpResult.Success.value(), memo, sysUser.getId() + "", ConstantUtil.SelectsTableName.SysUser.value());
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
  public void delete(@ModelAttribute SysUser sysUser, @ModelAttribute SysDeptUser sysDeptUser, @RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      if (op.equals("delete"))
      {
        SysUser sysUser2 = this.sysUserService.get(sysUser.getId());
        sysUser2.setStatus(1);
        this.sysUserService.update(sysUser2);
        
        this.sysDeptUserService.deleteByUserId(sysUser.getId());
        
        this.sysLogService.addLog(this.request, getCurrentUser(), "用户维护", ConstantUtil.OpType.Delete.value(), ConstantUtil.OpResult.Success.value(), "删除成功！", sysUser.getId() + "", ConstantUtil.SelectsTableName.SysUser.value());
        
        map.put("code", "1");
        map.put("msg", "删除成功！");
      }
      else if (op.equals("remove"))
      {
        if (this.sysUserService.isLastDeptForUser(sysUser.getId()))
        {
          map.put("code", "0");
          map.put("msg", "当前部门是用户的最后一个部门，不可移除！");
        }
        else
        {
          this.sysDeptUserService.delete(sysDeptUser.getId());
          map.put("code", "1");
          map.put("msg", "移除成功！");
        }
      }
      else
      {
        map.put("code", "0");
        map.put("msg", "无效标示符，op：" + op + "！");
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
  
  @RequestMapping({"/move"})
  public void move(@RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    String msgfix = op.equals("in") ? "移入" : "移除";
    try
    {
      String userId = this.request.getParameter("userId");
      if (op.equals("out"))
      {
        long rows = this.sysDeptUserService.getDeptRowsByUserId(Long.valueOf(userId).longValue());
        if (rows == 1L)
        {
          map.put("code", "0");
          map.put("msg", "当前部门为用户最后一个部门，不可移除！");
          outJson(map);
          return;
        }
      }
      this.sysUserService.move(this.request, op);
      map.put("code", "1");
      map.put("msg", msgfix + "成功！");
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      map.put("code", "0");
      map.put("msg", msgfix + "失败！");
    }
    outJson(map);
  }
  
  @RequestMapping({"/resetPwd"})
  public void resetPwd()
  {
    Map<String, Object> map = new HashMap();
    try
    {
      String pwd = String.valueOf((int)((Math.random() * 9.0D + 1.0D) * 100000.0D));
      
      String id = this.request.getParameter("id");
      SysUser sysUser = this.sysUserService.get(Long.valueOf(id).longValue());
      sysUser.setPassword(MD5Util.getMD5(pwd));
      this.sysUserService.update(sysUser);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      map.put("code", "0");
      map.put("msg", "密码重置失败！");
    }
    outJson(map);
  }
  
  @RequestMapping({"/changePwd"})
  public ModelAndView changePwd()
  {
    ModelAndView view = new ModelAndView();
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("changepwd_validate"));
    view.setViewName("sys/user/changePwd");
    return view;
  }
  
  @RequestMapping({"/savePwd"})
  public void savePwd()
  {
    Map<String, Object> map = new HashMap();
    try
    {
      SysUser sysUser = this.sysUserService.get(getCurrentUserId());
      
      String oldPassword = MD5Util.getMD5(this.request.getParameter("oldPassword"));
      
      String newPassword = MD5Util.getMD5(this.request.getParameter("newPassword"));
      if (oldPassword.equals(sysUser.getPassword()))
      {
        sysUser.setPassword(newPassword);
        this.sysUserService.update(sysUser);
        map.put("code", "1");
        map.put("msg", "密码修改成功！");
      }
      else
      {
        map.put("code", "0");
        map.put("msg", "密码错误！");
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      map.put("code", "0");
      map.put("msg", "密码修改失败！");
    }
    outJson(map);
  }
}
