package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysDictMain;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysDictMainService;
import com.xgh.mng.services.ISysGridColumnsService;
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
@RequestMapping({"/sys/sysdictmain"})
@Scope("prototype")
public class SysDictMainController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(SysDictMainController.class);
  @Autowired
  protected ISysDictMainService sysDictMainService;
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/sysdictmain/init");
    
    List<Map<String, Object>> treeList = this.sysDictMainService.getSysDictMainFirstTreeData();
    if (null == treeList) {
      treeList = new ArrayList();
    }
    Map<String, Object> nodeData = new HashMap();
    nodeData.put("id", Integer.valueOf(0));
    nodeData.put("text", "字典");
    treeList.add(nodeData);
    
    view.addObject("treedata", JSONUtil.getJson(treeList));
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysDictMain sysDictMain, @RequestParam String op)
  {
    ModelAndView view = new ModelAndView();
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("sysdictmain_validate"));
    if (op.equals("modify")) {
      sysDictMain = this.sysDictMainService.get(sysDictMain.getId());
    }
    view.addObject("sysDictMain", sysDictMain);
    view.addObject("op", op);
    view.setViewName("sys/sysdictmain/add");
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList()
  {
    outJson(this.sysDictMainService.getGridData(this.request));
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysDictMain sysDictMain, @RequestParam String op)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      if (this.sysDictMainService.isHasSameCode(sysDictMain, op))
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
          Date date = new Date();
          sysDictMain.setCreateDate(date);
          sysDictMain.setUpdateDate(date);
          sysDictMain.setStatus(1);
          
          this.sysDictMainService.add(sysDictMain);
          if (sysDictMain.getParentId() == 0L)
          {
            sysDictMain.setDictPrefix(sysDictMain.getId() + "");
          }
          else
          {
            SysDictMain parentDictMain = this.sysDictMainService.get(sysDictMain.getParentId());
            sysDictMain.setDictPrefix(parentDictMain.getId() + "_" + sysDictMain.getId());
          }
          this.sysDictMainService.update(sysDictMain);
        }
        else
        {
          SysDictMain sysDictMain2 = this.sysDictMainService.get(sysDictMain.getId());
          sysDictMain2.setDictCode(sysDictMain.getDictCode());
          sysDictMain2.setDictName(sysDictMain.getDictName());
          sysDictMain2.setOrd(sysDictMain.getOrd());
          sysDictMain2.setDictType(sysDictMain.getDictType());
          sysDictMain2.setDictValue(sysDictMain.getDictValue());
          sysDictMain2.setIsSys(sysDictMain.getIsSys());
          sysDictMain2.setIsUserConf(sysDictMain.getIsUserConf());
          sysDictMain2.setIsExtends(sysDictMain.getIsExtends());
          sysDictMain2.setUpdateDate(new Date());
          this.sysDictMainService.update(sysDictMain2);
        }
        this.sysDictMainService.loadDictDataToCache();
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
  public void delete(@ModelAttribute SysDictMain sysDictMain)
  {
    Map<String, Object> map = new HashMap();
    try
    {
      map.put("code", "1");
      map.put("msg", "删除成功！");
      if (this.sysDictMainService.getDictDetailRowsByMainId(sysDictMain.getId()) > 0L)
      {
        map.put("code", "0");
        map.put("msg", "当前字典主表有字典详细，不可删除！");
        outJson(map);
        return;
      }
      if (this.sysDictMainService.getSysDictMainChildRows(sysDictMain.getId()) > 0L)
      {
        map.put("code", "0");
        map.put("msg", "当前字典主表有子级不可删除！");
        outJson(map);
        return;
      }
      this.sysDictMainService.delete(sysDictMain.getId());
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
