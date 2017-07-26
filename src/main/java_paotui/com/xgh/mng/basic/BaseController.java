package com.xgh.mng.basic;

import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import com.xgh.mng.services.IFileDataService;
import com.xgh.mng.services.IOsNoteService;
import com.xgh.mng.services.ISysButtonService;
import com.xgh.mng.services.ISysCacheService;
import com.xgh.mng.services.ISysGridColumnsService;
import com.xgh.mng.services.ISysIndustryService;
import com.xgh.mng.services.ISysLogService;
import com.xgh.mng.services.ISysQueryItemService;
import com.xgh.mng.services.ISysUnitsService;
import com.xgh.mng.services.ISysUserService;
import com.xgh.mng.services.ISysValidationService;
import com.xgh.paotui.entity.AppToken;
import com.xgh.paotui.entity.Customer;
import com.xgh.util.ConstantUtil;
import com.xgh.util.JSONUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xgh.util.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController
{
  @Autowired
  protected ISysButtonService sysButtonService;
  @Autowired
  protected ISysGridColumnsService sysGridColumnsService;
  @Autowired
  protected ISysValidationService sysValidationService;
  @Autowired
  protected ISysQueryItemService sysQueryItemService;
  @Autowired
  protected ISysCacheService sysCacheService;
  @Autowired
  protected IOsNoteService osNoteService;
  @Autowired
  protected ISysIndustryService sysIndustryService;
  @Autowired
  protected ISysUnitsService sysUnitsService;
  @Autowired
  protected ISysUserService sysUserService;
  @Autowired
  protected IFileDataService fileDataService;
  @Autowired
  protected ISysLogService sysLogService;
  protected DecimalFormat format = new DecimalFormat("0.00");
  protected HttpServletResponse response;
  protected HttpServletRequest request;
  protected HttpSession session;
  
  @ModelAttribute
  public void setReqAndResp(HttpServletResponse response, HttpServletRequest request)
  {
    this.response = response;
    this.request = request;
    this.session = request.getSession();
  }
  
  protected void outJson(Object obj)
  {
    out(JSONUtil.getJson(obj));
  }
  
  protected void out(String value)
  {
    PrintWriter out = null;
    try
    {
      this.response.setContentType("text/html;charset=UTF-8");
      out = this.response.getWriter();
      out.println(value);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (out != null)
      {
        out.flush();
        out.close();
      }
    }
  }
  
  protected Map<String, Object> getResultMap(Object code, Object msg)
  {
    Map<String, Object> resultMap = new HashMap();
    resultMap.put("code", code);
    resultMap.put("msg", msg);
    
    return resultMap;
  }

  protected Map<String, Object> getResultMap(Object flg, Object msg, Object data) {
    Map<String, Object> resultMap = getResultMap(flg, msg);

    resultMap.put(ConstantUtil.ResultKey.resultData.value(), data);

    return resultMap;
  }
  
  protected int getOsNO()
  {
    return 101000;
  }
  
  protected String getSessionId()
  {
    return this.session.getId();
  }
  
  protected SysIndustry getCurrentIndustry()
  {
    return this.sysCacheService.getCurrentIndustry(this.request);
  }
  
  protected long getCurrentInstId()
  {
    return this.sysCacheService.getCurrentInstId(this.request);
  }
  
  protected SysUnits getCurrentUnits()
  {
    return this.sysCacheService.getCurrentUnit(this.request);
  }
  
  protected long getCurrentUnitId()
  {
    return this.sysCacheService.getCurrentUnitId(this.request);
  }
  
  protected long getCurrentSysRoleId()
  {
    return this.sysCacheService.getCurrentSysRoleId(this.request);
  }
  
  protected long getCurrentUserId()
  {
    return this.sysCacheService.getCurrentUserId(this.request);
  }
  
  protected SysUser getCurrentUser()
  {
    return this.sysCacheService.getCurrentUser(this.request);
  }
  
  protected void updateCurrentSysUser(SysUser sysUser)
  {
    this.sysCacheService.updateUserCach(this.request, sysUser);
  }
  
  protected void clearCurrentSysUser()
  {
    this.sysCacheService.clearUserCach(this.request);
  }
  
  protected ModelAndView getModelAndView(String viewName)
  {
    ModelAndView view = new ModelAndView();
    view.setViewName(viewName);
    
    view.addObject("toolbar", this.sysButtonService.getMenuButtons(this.request, getCurrentSysRoleId(), getCurrentUser()));
    view.addObject("gridComluns", this.sysGridColumnsService.getGridColumsByRequest(this.request));
    
    return view;
  }

  /**
   * 通过Token字符串返回客户Id。
   * @param token
   * @return  -1:认证失败
     */
  protected long getCustomerIdByAppToken(String token){
    AppToken appToken= JWT.unsign(token,AppToken.class);
    if(appToken!=null   ){
      if("customer".equals(appToken.getAppType()) || "weixin".equals(appToken.getAppType()) ){
         return appToken.getId();
      }
      else{
        return -1;
      }

    }
    else{
      return -1;
    }

  }


  /**
   * 通过Token字符串返回客户Id。
   * @param token
   * @return  -1:认证失败
   */
  protected long getCustomerIdByToken(String token){
    AppToken appToken= JWT.unsign(token,AppToken.class);
    if(appToken!=null   ){
      if("customer".equals(appToken.getAppType()) || "weixin".equals(appToken.getAppType()) ){
        return appToken.getId();
      }
      else{
        return -1;
      }

    }
    else{
      return -1;
    }

  }






  /**
   * 通过Token字符串返回跑客Id。
   * @param token
   * @return  -1:认证失败
   */
  protected long getDeliveryManIdByAppToken(String token){
    AppToken appToken= JWT.unsign(token,AppToken.class);
    if(appToken!=null  && "deliveryMan".equals(appToken.getAppType())){
      return  appToken.getId();
    }
    else{
      return -1;
    }

  }
}
