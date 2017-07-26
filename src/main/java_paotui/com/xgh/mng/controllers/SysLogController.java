package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysLogService;
import com.xgh.mng.services.ISysQueryItemService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({"/sys/log"})
@Scope("prototype")
public class SysLogController
  extends BaseController
{
  private static final Logger logger = Logger.getLogger(SysLogController.class);
  
  @RequestMapping({"/init"})
  public ModelAndView init()
  {
    ModelAndView view = new ModelAndView();
    view.setViewName("sys/log/init");
    
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    view.addObject("conditionHtml", this.sysQueryItemService.getConditionHtmlByRequest(this.request, getCurrentUnitId()));
    return view;
  }
  
  @RequestMapping({"/getList"})
  public void getList()
  {
    outJson(this.sysLogService.getGridData(this.request, getCurrentUnitId()));
  }
}
