package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.services.ISysMenuService;
import com.xgh.mng.services.ISysQueryItemService;
import com.xgh.mng.services.ISysTypeService;
import com.xgh.util.JSONUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/sys/queryitem"})
@Scope("prototype")
public class SysQueryItemController
  extends BaseController
{
  private static Logger logger = Logger.getLogger(SysQueryItemController.class);
  @Autowired
  protected ISysMenuService sysMenuService;
  @Autowired
  protected ISysTypeService sysTypeService;
  
  @RequestMapping({"/init"})
  public ModelAndView init(@RequestParam String joinType)
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/queryitem/init");
    view.addObject("joinType", joinType);
    if (joinType.equals("menu")) {
      view.addObject("treedata", JSONUtil.getJson(this.sysMenuService.getQueryMenuTreeData()));
    } else if (joinType.equals("query")) {
      view.addObject("treedata", JSONUtil.getJson(this.sysTypeService.getTreeData(joinType)));
    }
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam long joinId, @RequestParam String joinType)
  {
    Map<String, Object> map = new HashMap();
    map.put("joinId", Long.valueOf(joinId));
    map.put("joinType", joinType);
    
    outJson(this.sysQueryItemService.getGirdData(map));
  }
  
  @RequestMapping({"/save"})
  public void save(@RequestParam long joinId, @RequestParam String joinType)
  {
    Map<String, Object> resultMap = new HashMap();
    try
    {
      resultMap = getResultMap("1", "保存成功!");
      String queryItemData = this.request.getParameter("queryItemData");
      List<Map<String, Object>> dataList = JSONUtil.jsonToListMap(queryItemData);
      this.sysQueryItemService.saveQueryItem(joinId, joinType, dataList);
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
      resultMap = getResultMap("0", "保存失败!");
    }
    outJson(resultMap);
  }
}
