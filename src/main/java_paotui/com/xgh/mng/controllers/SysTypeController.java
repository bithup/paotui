package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysType;
import com.xgh.mng.services.ISysTypeService;
import com.xgh.util.JSONUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping({"/sys/type"})
public class SysTypeController
  extends BaseController
{
  private static Logger logger = Logger.getLogger(SysTypeController.class);
  @Autowired
  protected ISysTypeService sysTypeService;
  
  @RequestMapping({"/init"})
  public ModelAndView init(@RequestParam String type)
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/type/init");
    view.addObject("type", type);
    
    List<Map<String, Object>> treeList = this.sysTypeService.getChildTreeData(type, Long.valueOf(0L));
    
    Map<String, Object> rootMap = new HashMap();
    rootMap.put("id", Integer.valueOf(0));
    rootMap.put("text", "类型");
    treeList.add(rootMap);
    
    view.addObject("treedata", JSONUtil.getJson(treeList));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView init(@ModelAttribute SysType sysType, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    if (op.equals("modify")) {
      sysType = this.sysTypeService.get(sysType.getId());
    }
    view.addObject("sysType", sysType);
    view.addObject("op", op);
    view.setViewName("sys/type/add");
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList(@RequestParam String type, @RequestParam long pid)
  {
    outJson(this.sysTypeService.getGridData(type, pid));
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysType sysType, @RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "保存成功！");
      if (this.sysTypeService.isHasSameCode(op, sysType))
      {
        map.put("code", "0");
        map.put("msg", "编码重复，请用其他编码！");
      }
      else if (op.equals("add"))
      {
        Date date = new Date();
        sysType.setCreateDate(date);
        sysType.setUpdateDate(date);
        sysType.setStatus(1);
        this.sysTypeService.add(sysType);
      }
      else
      {
        this.sysTypeService.update(sysType);
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
  public void save(@ModelAttribute SysType sysType)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "删除成功！");
      if (this.sysTypeService.isHasChild(sysType))
      {
        map.put("code", "0");
        map.put("msg", "当前类型有子级，不可删除！");
      }
      else
      {
        this.sysTypeService.delete(sysType.getId());
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
