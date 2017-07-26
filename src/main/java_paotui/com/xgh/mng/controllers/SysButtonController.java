package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysButton;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysMenuService;
import com.xgh.util.JSONUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/sys/button"})
public class SysButtonController
  extends BaseController
{
  private static Logger logger = Logger.getLogger(SysButtonController.class);
  @Autowired
  protected ISysMenuService sysMenuService;
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/button/init");
    
    StringBuffer toolbar = new StringBuffer();
    toolbar.append(String.format("{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { "添加", "h2y_add", "add" }));
    toolbar.append(String.format(",{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { "删除", "h2y_delete", "delete" }));
    toolbar.append(String.format(",{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { "保存", "h2y_save", "save" }));
    toolbar.append(String.format(",{ line:true },{ text: '%s' , click: %s , icon:'%s' }", new Object[] { "刷新", "h2y_refresh", "refresh" }));
    
    view.addObject("toolbar", toolbar);
    view.addObject("treedata", JSONUtil.getJson(this.sysMenuService.getButtonMenuTreeData()));
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam String menuId)
  {
    outJson(this.sysButtonService.getGridData(Long.valueOf(menuId).longValue()));
  }
  
  @RequestMapping({"/save"})
  public void save(String menuId)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      String buttonData = this.request.getParameter("buttonData");
      List<Map<String, Object>> dataList = JSONUtil.jsonToListMap(buttonData);
      this.sysButtonService.saveButton(Long.valueOf(menuId).longValue(), dataList);
      logger.debug("按钮数据：" + buttonData);
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
  public void delete(@ModelAttribute SysButton sysButton)
  {
    String code = "1";
    String msg = "删除成功！";
    try
    {
      this.sysButtonService.delete(sysButton.getId());
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
