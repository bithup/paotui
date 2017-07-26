package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysUser;
import com.xgh.mng.services.ISysDepartmentService;
import com.xgh.mng.services.ISysIndustryService;
import com.xgh.mng.services.ISysLogService;
import com.xgh.mng.services.ISysMenuService;
import com.xgh.mng.services.ISysPrivilegeListService;
import com.xgh.mng.services.ISysUserService;
import com.xgh.security.MD5Util;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.OpResult;
import com.xgh.util.ConstantUtil.OpType;
import com.xgh.util.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping({"/portal/login/"})
public class LoginController
  extends BaseController
{
  private static Logger logger = Logger.getLogger(LoginController.class);
  @Autowired
  protected ISysMenuService sysMenuService;
  @Autowired
  protected ISysDepartmentService sysDepartmentService;
  @Autowired
  protected ISysPrivilegeListService sysPrivilegeListService;
  
  @RequestMapping({"init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    
    view.addObject("industryList", this.sysIndustryService.getSysIndustry());
    view.setViewName("portal/login/login");
    
    return view;
  }
  
  @RequestMapping({"domainLogin"})
  public ModelAndView domainInit()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("portal/login/login_domain");
    

    String domain = this.request.getParameter("dm");
    view.addObject("domain", domain);
    
    return view;
  }
  
  @RequestMapping({"loginOut"})
  public ModelAndView loginOut()
  {
    ModelAndView view = new ModelAndView();
    

    this.sysLogService.addLog(this.request, getCurrentUser(), "登录", ConstantUtil.OpType.LoginOut.value(), ConstantUtil.OpResult.Success.value(), "退出！");
    
    clearCurrentSysUser();
    
    view.addObject("industryList", this.sysIndustryService.getSysIndustry());
    view.setViewName("portal/login/login");
    
    return view;
  }
  
  @RequestMapping({"desktop"})
  public ModelAndView desktop()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("portal/login/desktop");
    return view;
  }
  
  @RequestMapping({"check"})
  public void loginCheck()
  {
    String account = this.request.getParameter("account");
    String password = this.request.getParameter("password");
    

    String domain = this.request.getParameter("domain");
    if ((account != null) && (!"".equals(account)) && (!account.contains("@")) && (domain != null) && (!"".equals(domain))) {
      account = account + "@" + domain;
    }
    Map<String, Object> resultMap = new HashMap();
    SysUser sysUser = new SysUser();
    

    sysUser = new SysUser();
    sysUser.setAccount(account);
    




    sysUser = this.sysUserService.getLoginCheckUser(account, password);
    if (null == sysUser)
    {
      this.sysLogService.addLog(this.request, sysUser, "登录", ConstantUtil.OpType.Login.value(), ConstantUtil.OpResult.Fail.value(), "登陆失败,用户名错误！");
      resultMap = getResultMap(Integer.valueOf(0), "用户不存在,用户名错误！");
    }
    else if (sysUser.getPassword().equals(MD5Util.getMD5(password)))
    {
      sysUser.setPassword(null);
      
      updateCurrentSysUser(sysUser);
      
      this.sysLogService.addLog(this.request, sysUser, "登录", ConstantUtil.OpType.Login.value(), ConstantUtil.OpResult.Success.value(), "登录成功！");
      resultMap = getResultMap(Integer.valueOf(1), "登录成功");
    }
    else
    {
      this.sysLogService.addLog(this.request, sysUser, "登录", ConstantUtil.OpType.Login.value(), ConstantUtil.OpResult.Fail.value(), "登陆失败,密码错误！");
      resultMap = getResultMap(Integer.valueOf(0), "密码错误");
    }
    outJson(resultMap);
  }
  
  @RequestMapping({"index"})
  public ModelAndView index()
  {
    ModelAndView view = new ModelAndView();
    
    SysUser sysUser = getCurrentUser();
    view.addObject("sysUser", sysUser);
    view.addObject("sysUnits", getCurrentUnits());
    view.addObject("sysDepartment", this.sysDepartmentService.getDeptsByUserId(sysUser.getId()));
    
    List<Map<String, Object>> menuList = new ArrayList();
    if ((sysUser.getUnitId() == 1L) || ("admin".equals(sysUser.getAccount()))) {
      menuList = this.sysMenuService.getSysMenuTreeData(sysUser);
    } else {
      menuList = this.sysMenuService.getUserMenuTreeData(getCurrentSysRoleId(), sysUser);
    }
    view.addObject("menuData", JSONUtil.getJson(menuList));
    view.setViewName("portal/login/index");
    
    return view;
  }
}
