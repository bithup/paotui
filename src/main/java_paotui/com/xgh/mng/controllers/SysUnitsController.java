package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysRole;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.Zone;
import com.xgh.mng.services.IFileDataService;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysQueryItemService;
import com.xgh.mng.services.ISysRoleService;
import com.xgh.mng.services.ISysUnitsService;
import com.xgh.mng.services.ISysValidationService;
import com.xgh.mng.services.IZoneService;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.FileUploadCode;
import com.xgh.util.JSONUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping({"/sys/units"})
public class SysUnitsController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(SysUnitsController.class);
  @Autowired
  protected ISysRoleService sysRoleService;
  @Autowired
  protected IZoneService zoneService;
  
  @InitBinder
  public void initBinder(WebDataBinder binder)
  {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
  }
  
  @InitBinder({"sysUnits"})
  public void initBinder2(WebDataBinder binder)
  {
    binder.setFieldDefaultPrefix("sysUnits.");
  }
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/units/init");
    
    view.addObject("kindType", Integer.valueOf(1));
    view.addObject("treedata", JSONUtil.getJson(this.sysUnitsService.getUnitTreeData(this.request)));
    view.addObject("parentId", Long.valueOf(getCurrentUnitId()));
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("conditionHtml", this.sysQueryItemService.getConditionHtmlByRequest(this.request, getCurrentUnitId()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    return view;
  }
  
  @RequestMapping({"/initorg"})
  public ModelAndView initorg()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/units/initorg");
    
    view.addObject("kindType", Integer.valueOf(2));
    
    view.addObject("parentId", Long.valueOf(getCurrentUnitId()));
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("conditionHtml", this.sysQueryItemService.getConditionHtmlByRequest(this.request, getCurrentUnitId()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    return view;
  }
  
  @RequestMapping({"/{methodName}"})
  public ModelAndView checklist(@PathVariable String methodName)
  {
    ModelAndView view = new ModelAndView();
    if (("checklist".equals(methodName)) || ("checkorglist".equals(methodName)))
    {
      view.setViewName("sys/units/checklist");
      if ("checklist".equals(methodName)) {
        view.addObject("kindType", Integer.valueOf(1));
      } else {
        view.addObject("kindType", Integer.valueOf(2));
      }
    }
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    return view;
  }
  
  @RequestMapping({"/add"})
  public ModelAndView add(@ModelAttribute SysUnits sysUnits, @RequestParam String op, @RequestParam int kindType)
  {
    ModelAndView view = new ModelAndView();
    if ("modify".equals(op))
    {
      sysUnits = this.sysUnitsService.get(sysUnits.getId());
      view.addObject("zonePrefix", this.sysUnitsService.getZonePrefixByCode(sysUnits.getZoneCode()));
      
      Map<String, Object> params = new HashMap();
      params.put("instId", Long.valueOf(getCurrentInstId()));
      params.put("dataId", Long.valueOf(sysUnits.getId()));
      params.put("dataCode", ConstantUtil.FileUploadCode.Unit.value());
      params.put("dataVersion", Integer.valueOf(sysUnits.getVersion()));
      params.put("server", ConstantUtil.SERVER_URL);
      
      view.addObject("fileListDataJson", JSONUtil.getJson(this.fileDataService.getFileDatas(params)));
    }
    else if (kindType == 2)
    {
      sysUnits.setUnitType(12);
    }
    else
    {
      sysUnits.setUnitType(3);
    }
    SysUnits sysUnitsParent = this.sysUnitsService.get(sysUnits.getParentId());
    Zone zone = this.zoneService.getZoneByCode(sysUnitsParent.getZoneCode());
    if ((zone != null) && (kindType == 1))
    {
      view.addObject("zoneName", zone.getName());
      view.addObject("parentZoneCode", zone.getCode());
      view.addObject("zoneList", this.sysUnitsService.getZoneListByPCode(zone.getCode()));
    }
    else
    {
      view.addObject("zoneList", this.sysUnitsService.getFirstZoneList());
    }
    sysUnits.setParentId(sysUnitsParent.getId());
    









    view.addObject("kindType", Integer.valueOf(kindType));
    view.addObject("unitType", Integer.valueOf(sysUnitsParent.getUnitType()));
    

    view.addObject("fileTypeList", this.sysUnitsService.getUnitFileTypeList());
    
    view.addObject("loginunitId", Long.valueOf(getCurrentUnitId()));
    view.addObject("op", op);
    if (kindType == 1)
    {
      view.addObject("prefixDomain", sysUnitsParent.getUnitDomain());
    }
    else
    {
      String prefixDomain = getCurrentIndustry().getCode();
      view.addObject("prefixDomain", prefixDomain);
      if ("modify".equals(op))
      {
        String unitDomain = sysUnits.getUnitDomain();
        if (unitDomain.indexOf(prefixDomain) > 0) {
          sysUnits.setUnitDomain(unitDomain.substring(unitDomain.indexOf(prefixDomain)));
        }
      }
    }
    view.addObject("sysUnits", sysUnits);
    
    view.addObject("validationRules", this.sysValidationService.getValidationByCode("sysunits_validate"));
    view.setViewName("sys/units/add");
    
    return view;
  }
  
  @RequestMapping({"/check"})
  public ModelAndView check(@ModelAttribute SysUnits sysUnits, @RequestParam int kindType)
  {
    ModelAndView view = new ModelAndView();
    sysUnits = this.sysUnitsService.get(sysUnits.getId());
    


    view.addObject("kindType", Integer.valueOf(kindType));
    
    view.addObject("unitType", Integer.valueOf(getCurrentUnits().getUnitType()));
    

    Map<String, Object> params = new HashMap();
    params.put("instId", Long.valueOf(getCurrentInstId()));
    params.put("dataId", Long.valueOf(sysUnits.getId()));
    params.put("dataCode", ConstantUtil.FileUploadCode.Unit.value());
    params.put("dataVersion", Integer.valueOf(sysUnits.getVersion()));
    params.put("server", ConstantUtil.SERVER_URL);
    view.addObject("fileListDataJson", JSONUtil.getJson(this.fileDataService.getFileDatas(params)));
    

    view.addObject("fileTypeList", this.sysUnitsService.getUnitFileTypeList());
    

    List<SysRole> roleList = new ArrayList();
    
    Map<String, Object> paramMap = new HashMap();
    paramMap.put("instId", Long.valueOf(getCurrentInstId()));
    

    paramMap.put("unitType", Integer.valueOf(sysUnits.getUnitType()));
    if ("1".equals(this.request.getParameter("kindType")))
    {
      paramMap.put("isSys", Integer.valueOf(2));
      paramMap.put("isPrivilege", Integer.valueOf(1));
      roleList = this.sysRoleService.getAgentOrgRoleList(paramMap);
    }
    else if ("2".equals(this.request.getParameter("kindType")))
    {
      paramMap.put("isSys", Integer.valueOf(3));
      paramMap.put("isPrivilege", Integer.valueOf(1));
      roleList = this.sysRoleService.getAgentOrgRoleList(paramMap);
    }
    view.addObject("roleList", roleList);
    view.addObject("loginUnitId", Long.valueOf(getCurrentUnitId()));
    view.addObject("sysUnits", sysUnits);
    view.setViewName("sys/units/check");
    


    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList()
  {
    outJson(this.sysUnitsService.getGridData(this.request));
  }
  
  @RequestMapping({"/getZone"})
  public void getZone(String op, String value)
  {
    if (op.equals("code")) {
      outJson(this.sysUnitsService.getChildZoneList(this.request, value));
    } else if (op.equals("id")) {
      outJson(this.sysUnitsService.getChildZoneListByPid(Long.parseLong(value)));
    }
  }
  
  @RequestMapping({"/save"})
  public void save(@ModelAttribute SysUnits sysUnits, @RequestParam String op)
  {
    outJson(this.sysUnitsService.save(this.request, sysUnits, op));
  }
  
  @RequestMapping({"/delete"})
  public void delete(@ModelAttribute SysUnits sysUnits)
  {
    Map<String, Object> resultMap = new HashMap();
    
    SysUnits sysUnits2 = this.sysUnitsService.get(sysUnits.getId());
    
    sysUnits2.setStatus(-1);
    sysUnits2.setUpdateDate(new Date());
    if (this.sysUnitsService.update(sysUnits2) > 0) {
      resultMap = getResultMap("1", "删除成功!");
    } else {
      resultMap = getResultMap("0", "删除失败!");
    }
    outJson(resultMap);
  }
}
