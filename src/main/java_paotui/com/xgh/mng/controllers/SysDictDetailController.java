package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysDictDetail;
import com.xgh.mng.entity.SysDictMain;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysDictDetailService;
import com.xgh.mng.services.ISysDictMainService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysRoleService;
import com.xgh.mng.services.ISysValidationService;
import com.xgh.util.JSONUtil;
import java.util.ArrayList;
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
@RequestMapping({"/sys/sysdictdetail"})
@Scope("prototype")
public class SysDictDetailController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(SysDictDetailController.class);
  @Autowired
  protected ISysRoleService sysRoleService;
  @Autowired
  protected ISysDictMainService sysDictMainService;
  @Autowired
  protected ISysDictDetailService sysDictDetailService;
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/sysdictdetail/init");
    
    long unitId = getCurrentUnitId();
    
    Map<String, Object> map = new HashMap();
    map.put("unitId", Long.valueOf(unitId));
    List<Map<String, Object>> treeList = this.sysDictMainService.getDictMainTreeList(map);
    if (treeList == null) {
      treeList = new ArrayList();
    }
    Map<String, Object> nodeData = new HashMap();
    nodeData.put("id", Integer.valueOf(0));
    nodeData.put("text", "字典");
    treeList.add(nodeData);
    
    view.addObject("treedata", JSONUtil.getJson(treeList));
    view.addObject("unitId", Long.valueOf(unitId));
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    return view;
  }
  
  @RequestMapping({"/menuInit"})
  public ModelAndView menuInit(String mcode)
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/sysdictdetail/menuInit");
    
    long unitId = getCurrentUnitId();
    
    Map<String, Object> map = new HashMap();
    map.put("unitId", Long.valueOf(unitId));
    
    SysDictMain sysDictMain = this.sysDictMainService.getSysDictMainByCode(mcode);
    view.addObject("sysDictMain", sysDictMain);
    view.addObject("unitId", Long.valueOf(unitId));
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByCode("dictdetail_grid"));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysDictDetail sysDictDetail, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    if (op.equals("modify")) {
      sysDictDetail = this.sysDictDetailService.get(sysDictDetail.getId());
    }
    SysDictMain sysDictMain = this.sysDictMainService.get(sysDictDetail.getDictMainId());
    String validationRules = this.sysValidationService.getValidationByCode("Dict_" + sysDictMain.getDictCode());
    if ((null == validationRules) || ("".equals(validationRules))) {
      validationRules = this.sysValidationService.getValidationByCode("sysdictdetail_validate");
    }
    view.addObject("validationRules", validationRules);
    
    view.addObject("sysDictMain", this.sysDictMainService.get(sysDictDetail.getDictMainId()));
    view.addObject("unitId", Long.valueOf(getCurrentUnitId()));
    view.addObject("sysDictDetail", sysDictDetail);
    view.addObject("op", op);
    view.setViewName("sys/sysdictdetail/add");
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList()
  {
    outJson(this.sysDictDetailService.getGridData(this.request, getCurrentUnitId()));
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysDictDetail sysDictDetail, @RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    long unitId = getCurrentUnitId();
    sysDictDetail.setUnitId(unitId);
    try
    {
      SysDictMain sysDictMain = this.sysDictMainService.get(sysDictDetail.getDictMainId());
      if (sysDictMain.getIsExtends() == 1)
      {
        map.put("code", "1");
        map.put("msg", "保存成功！");
        if (op.equals("add"))
        {
          if (this.sysDictDetailService.isHasSameCode(sysDictDetail, op))
          {
            map.put("code", "0");
            map.put("msg", "字典编码重复，请使用其他编码！");
          }
          else if ((unitId != 1L) && (!sysDictMain.getDictCode().equals("topSearch")))
          {
            map.put("code", "0");
            map.put("msg", "非平台单位不可添加可继承的字典数据！");
          }
          else
          {
            Date date = new Date();
            sysDictDetail.setCreateDate(date);
            sysDictDetail.setUpdateDate(date);
            sysDictDetail.setStatus(1);
            this.sysDictDetailService.add(sysDictDetail);
          }
        }
        else
        {
          SysDictDetail sysDictDetail2 = this.sysDictDetailService.get(sysDictDetail.getId());
          if (unitId != 1L) {
            sysDictDetail.setCode(sysDictDetail2.getCode());
          }
          if (sysDictDetail2.getUnitId() != unitId)
          {
            this.sysDictDetailService.add(sysDictDetail);
          }
          else
          {
            sysDictDetail.setUpdateDate(new Date());
            this.sysDictDetailService.update(sysDictDetail);
          }
        }
      }
      else if (this.sysDictDetailService.isHasSameCode(sysDictDetail, op))
      {
        map.put("code", "0");
        map.put("msg", "字典编码重复，请使用其他编码！");
      }
      else
      {
        map.put("code", "1");
        map.put("msg", "保存成功！");
        if (op.equals("add"))
        {
          this.sysDictDetailService.add(sysDictDetail);
        }
        else
        {
          sysDictDetail.setUpdateDate(new Date());
          this.sysDictDetailService.update(sysDictDetail);
        }
      }
      this.sysDictMainService.loadDictDataToCache();
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
  public void delete(@ModelAttribute SysDictDetail sysDictDetail)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "删除成功！");
      
      this.sysDictDetailService.delete(sysDictDetail.getId());
      this.sysDictMainService.loadDictDataToCache();
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
