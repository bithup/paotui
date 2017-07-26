package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysMenu;
import com.xgh.mng.services.ISysMenuService;
import com.xgh.util.JSONUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/sys/menu"})
@Scope("prototype")
public class SysMenuController
  extends BaseController
{
  private static Logger logger = Logger.getLogger(SysMenuController.class);
  @Autowired
  protected ISysMenuService sysMenuService;
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/menu/init");
    
    StringBuffer toolbar = new StringBuffer();
    toolbar.append(String.format("{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { "添加", "h2y_add", "add" }));
    toolbar.append(String.format(",{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { "修改", "h2y_modify", "modify" }));
    toolbar.append(String.format(",{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { "删除", "h2y_delete", "delete" }));
    toolbar.append(String.format(",{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { "刷新", "h2y_refresh", "refresh" }));
    
    view.addObject("toolbar", toolbar);
    
    view.addObject("treedata", JSONUtil.getJson(this.sysMenuService.getTreeData()));
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam String op)
  {
    if (op.equals("tree"))
    {
      outJson(this.sysMenuService.getTreeData());
    }
    else if (op.equals("grid"))
    {
      String pid = this.request.getParameter("pid");
      if (pid != null)
      {
        Map<String, Object> map = new HashMap();
        map.put("pid", Long.valueOf(pid));
        outJson(this.sysMenuService.getGridData(map));
      }
    }
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysMenu sysMenu, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    if (op.equals("modify"))
    {
      sysMenu = this.sysMenuService.get(sysMenu.getId());
      view.addObject("prentMenuList", this.sysMenuService.getParentMenus(0L));
    }
    view.addObject("sysMenu", sysMenu);
    view.addObject("op", op);
    view.setViewName("sys/menu/add");
    
    return view;
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysMenu sysMenu, String op)
  {
    Map<String, Object> resultMap = new HashMap();
    try
    {
      int flg = this.sysMenuService.save(this.request, sysMenu);
      if (flg == -2) {
        resultMap = getResultMap(Integer.valueOf(0), "url重复");
      } else if (flg == 1) {
        resultMap = getResultMap(Integer.valueOf(1), "保存成功");
      } else {
        resultMap = getResultMap(Integer.valueOf(0), "保存失败");
      }
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      resultMap = getResultMap(Integer.valueOf(0), "保存失败");
    }
    outJson(resultMap);
  }
  
  @RequestMapping({"/delete"})
  public void delete(@ModelAttribute SysMenu sysMenu)
  {
    String code = "1";
    String msg = "删除成功！";
    try
    {
      if (this.sysMenuService.isHasChildMenu(sysMenu.getId()))
      {
        code = "0";
        msg = "当前菜单下面有子级菜单，不可删除！";
      }
      else
      {
        this.sysMenuService.delete(sysMenu.getId());
      }
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
