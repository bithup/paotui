package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.Zone;
import com.xgh.mng.services.ISysDepartmentService;
import com.xgh.mng.services.ISysDialogService;
import com.xgh.mng.services.ISysUnitsService;
import com.xgh.mng.services.IZoneService;
import com.xgh.util.JSONUtil;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/sys/dialog"})
@Scope("prototype")
public class SysDialogController
  extends BaseController
{
  @Autowired
  protected ISysDepartmentService sysDepartmentService;
  @Autowired
  protected ISysDialogService sysDialogService;
  @Autowired
  protected IZoneService zoneService;
  
  @RequestMapping({"/init"})
  public ModelAndView init(String op)
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/dialog/" + op);
    

    long unitId = getCurrentUnitId();
    
    List<Map<String, Object>> treeList = this.sysDepartmentService.getChildTreeData(unitId, 0L);
    
    SysUnits sysUnits = this.sysUnitsService.get(unitId);
    Map<String, Object> rootMap = new HashMap();
    rootMap.put("id", Integer.valueOf(0));
    rootMap.put("code", Long.valueOf(sysUnits.getId()));
    rootMap.put("text", sysUnits.getUnitName());
    treeList.add(rootMap);
    view.addObject("treedata", JSONUtil.getJson(treeList));
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam String op)
  {
    outJson(this.sysDialogService.getList(this.request, getCurrentUnitId(), op));
  }
  
  @RequestMapping({"/pageinit"})
  public ModelAndView pageinit(String op)
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/dialog/" + op);
    
    Enumeration<String> names = this.request.getParameterNames();
    while (names.hasMoreElements())
    {
      String name = (String)names.nextElement();
      view.addObject(name, this.request.getParameter(name));
    }
    SysUnits sysUnits = getCurrentUnits();
    Zone zone = this.zoneService.getZoneByCode(sysUnits.getUnitCode());
    if (zone == null) {
      view.addObject("zoneName", "郑州");
    } else {
      view.addObject("zoneName", zone.getName());
    }
    return view;
  }
}
