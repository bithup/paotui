package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysRole;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysLogService;
import com.xgh.mng.services.ISysRoleService;
import com.xgh.mng.services.ISysValidationService;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.OpResult;
import com.xgh.util.ConstantUtil.OpType;
import com.xgh.util.ConstantUtil.SelectsTableName;
import com.xgh.util.JSONUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping({"/sys/role"})
public class SysRoleController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(SysRoleController.class);
  @Autowired
  protected ISysRoleService sysRoleService;
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    










    String type = this.request.getParameter("type");
    
    view.addObject("type", type);
    view.setViewName("sys/role/init");
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysRole sysRole, @RequestParam String type, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    if (op.equals("modify"))
    {
      sysRole = this.sysRoleService.get(sysRole.getId());
    }
    else if ("inst".equals(type))
    {
      sysRole.setIsSys(1L);
      sysRole.setIsPrivilege(1L);
    }
    else if ("agent".equals(type))
    {
      sysRole.setIsSys(2L);
      sysRole.setIsPrivilege(1L);
    }
    else if ("agents".equals(type))
    {
      sysRole.setIsSys(2L);
      sysRole.setIsPrivilege(0L);
    }
    else if ("org".equals(type))
    {
      sysRole.setIsSys(3L);
      sysRole.setIsPrivilege(1L);
    }
    else if ("orgs".equals(type))
    {
      sysRole.setIsSys(3L);
      sysRole.setIsPrivilege(0L);
    }
    else
    {
      sysRole.setIsSys(0L);
      sysRole.setIsPrivilege(0L);
    }
    view.addObject("type", type);
    view.addObject("unitId", Long.valueOf(getCurrentUnitId()));
    view.addObject("userId", Long.valueOf(getCurrentUserId()));
    view.addObject("sysRole", sysRole);
    view.addObject("op", op);
    view.setViewName("sys/role/add");
    
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("sysrole_validate"));
    return view;
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysRole sysRole, @RequestParam String type, @RequestParam String op)
  {
    Map<String, Object> resultMap = new HashMap();
    
    sysRole.setUnitId(getCurrentUnitId());
    sysRole.setStatus(1);
    try
    {
      resultMap = getResultMap("1", "保存成功");
      String remark = op.equals("add") ? "添加角色" : "修改角色";
      
      String opResult = ConstantUtil.OpResult.Fail.value();
      if (this.sysRoleService.save(sysRole, op) == 1) {
        opResult = ConstantUtil.OpResult.Success.value();
      }
      this.sysLogService.addLog(this.request, getCurrentUser(), "角色", ConstantUtil.OpType.Add.value(), opResult, remark, sysRole.getId() + "", ConstantUtil.SelectsTableName.SysRole.value());
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      resultMap = getResultMap("0", "保存失败!");
    }
    outJson(resultMap);
  }
  
  @RequestMapping({"/getList"})
  public void getList()
  {
    outJson(this.sysRoleService.getGridData(this.request, getCurrentUser(), getCurrentSysRoleId()));
  }
  
  @RequestMapping({"/rps/{methodName}"})
  public ModelAndView industryRole(@PathVariable("methodName") String methodName)
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/role/" + methodName);
    
    view.addObject("type", this.request.getParameter("type"));
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    view.addObject("treedata", JSONUtil.getJson(this.sysRoleService.getRoleTreeData(methodName, this.request)));
    return view;
  }
  
  @RequestMapping({"/delete"})
  public void delete(@ModelAttribute SysRole sysRole)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "删除成功！");
      SysRole sysRole2 = this.sysRoleService.get(sysRole.getId());
      sysRole2.setStatus(0);
      this.sysRoleService.update(sysRole2);
      
      this.sysLogService.addLog(this.request, getCurrentUser(), "角色", ConstantUtil.OpType.Delete.value(), ConstantUtil.OpResult.Success.value(), "角色删除", sysRole.getId() + "", ConstantUtil.SelectsTableName.SysRole.value());
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      map.put("code", "0");
      map.put("msg", "删除失败！");
    }
    outJson(map);
  }
  
  @RequestMapping({"/savePrivilege"})
  public void savePrivilege(long roleId)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      
      this.sysRoleService.savePrivilege(this.request, getCurrentUnitId(), roleId);
      this.sysLogService.addLog(this.request, getCurrentUser(), "权限保存", "savePrivilege", ConstantUtil.OpResult.Success.value(), "角色权限保存，数据" + this.request.getParameter("checkedData"), roleId + "", ConstantUtil.SelectsTableName.SysPrivilegeList.value());
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      this.sysLogService.addLog(this.request, getCurrentUser(), "权限保存", "savePrivilege", ConstantUtil.OpResult.Fail.value(), "角色权限保存", roleId + "", ConstantUtil.SelectsTableName.SysPrivilegeList.value());
      
      map.put("code", "0");
      map.put("msg", "保存失败！");
    }
    outJson(map);
  }
  
  @RequestMapping({"/saveRoleUser"})
  public void saveRoleUser(long roleId, String op)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      if (op.equals("add"))
      {
        this.sysRoleService.saveRoleUser(this.request, getCurrentUnitId(), roleId);
        this.sysLogService.addLog(this.request, getCurrentUser(), "角色用户关联", op, ConstantUtil.OpResult.Success.value(), "角色(" + roleId + ")，添加用户Id为(" + this.request.getParameter("userData") + ")", roleId + "", ConstantUtil.SelectsTableName.SysPrivilegeList.value());
      }
      else if (op.equals("remove"))
      {
        map.put("msg", "移除成功！");
        this.sysRoleService.removeRoleUser(this.request);
        this.sysLogService.addLog(this.request, getCurrentUser(), "角色用户关联", op, ConstantUtil.OpResult.Success.value(), "角色(" + roleId + ")，移除权限Id为(" + this.request.getParameter("privilegeIds") + ")", roleId + "", ConstantUtil.SelectsTableName.SysPrivilegeList.value());
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      map.put("code", "0");
      map.put("msg", "保存失败！");
      this.sysLogService.addLog(this.request, getCurrentUser(), "角色用户关联", op, ConstantUtil.OpResult.Success.value(), "保存失败", roleId + "", ConstantUtil.SelectsTableName.SysPrivilegeList.value());
    }
    outJson(map);
  }
}
