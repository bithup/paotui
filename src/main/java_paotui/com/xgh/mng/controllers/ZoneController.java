package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.Zone;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysLogService;
import com.xgh.mng.services.ISysValidationService;
import com.xgh.mng.services.IZoneService;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.OpResult;
import com.xgh.util.ConstantUtil.OpType;
import com.xgh.util.ConstantUtil.SelectsTableName;
import com.xgh.util.JSONUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping({"/sys/zone"})
public class ZoneController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(ZoneController.class);
  @Autowired
  protected IZoneService zoneService;
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/zone/init");
    
    SysUnits sysUnits = getCurrentUnits();
    
    List<Map<String, Object>> treeList = this.zoneService.getTreeList(0L, sysUnits.getZoneCode(), sysUnits.getId());
    
    view.addObject("treedata", JSONUtil.getJson(treeList));
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute Zone zone, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    if ((op.equals("modify")) || (op.equals("location")))
    {
      zone = this.zoneService.get(zone.getId());
      if ((op.equals("location")) && (!StringUtils.isNotBlank(zone.getLocation())))
      {
        String prefix = zone.getPreFix();
        if ((StringUtils.isNotBlank(prefix)) && (prefix.contains("_"))) {
          zone.setLocation(this.zoneService.getCurrentName(prefix));
        } else {
          zone.setLocation(zone.getName());
        }
      }
    }
    view.addObject("zone", zone);
    view.addObject("op", op);
    if (op.equals("location")) {
      view.setViewName("sys/zone/location");
    } else {
      view.setViewName("sys/zone/add");
    }
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("zone_validate"));
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam String op, @RequestParam long pid)
  {
    SysUnits sysUnits = getCurrentUnits();
    if (op.equals("grid")) {
      outJson(this.zoneService.getGridData(this.request, pid, sysUnits.getZoneCode(), sysUnits.getId()));
    } else if (op.equals("tree")) {
      outJson(this.zoneService.getTreeList(pid, sysUnits.getZoneCode(), sysUnits.getId()));
    }
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute Zone zone, @RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      this.zoneService.save(op, zone);
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
  public void delete(@ModelAttribute Zone zone)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "删除成功！");
      if (!this.zoneService.isHasChild(zone.getId() + ""))
      {
        this.zoneService.delete(zone.getId());
        this.sysLogService.addLog(this.request, getCurrentUser(), "区域维护", ConstantUtil.OpType.Delete.value(), ConstantUtil.OpResult.Success.value(), "删除成功！", zone.getId() + "", ConstantUtil.SelectsTableName.Zone.value());
      }
      else
      {
        map.put("code", "0");
        map.put("msg", "当前区域有子级不可删除！！");
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
  
  @RequestMapping({"/getAll"})
  public void getAll()
  {
    outJson(this.zoneService.getAll(this.request, getCurrentUnitId()));
  }
}
