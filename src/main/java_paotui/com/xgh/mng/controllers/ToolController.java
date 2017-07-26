package com.xgh.mng.controllers;

import com.xgh.mng.basic.BaseController;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysQueryItemService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
@RequestMapping({"/sys/tool"})
public class ToolController
  extends BaseController
{
  @RequestMapping({"toolbar"})
  public void getToolBar()
  {
    outJson(this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
  }
  
  @RequestMapping({"gridComluns"})
  public void getGridComluns()
  {
    outJson(this.sysGridColumnsService.getGridColumsByRequest(this.request));
  }
  
  @RequestMapping({"queryHtml"})
  public void getQueryHtml()
  {
    outJson(this.sysQueryItemService.getConditionHtmlByRequest(this.request, getCurrentUnitId()));
  }
}
