package com.xgh.mng.services;

import com.xgh.mng.dao.ISysPrivilegeListDao;
import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import com.xgh.util.ConstantUtil;
import com.xgh.util.ConstantUtil.SessionKeys;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysCacheService")
public class SysCacheServiceImpl
  implements ISysCacheService
{
  private static final Logger logger = Logger.getLogger(SysCacheServiceImpl.class);
  @Autowired
  protected ISysUserService sysUserService;
  @Autowired
  protected ISysIndustryService sysIndustryService;
  @Autowired
  protected ISysUnitsService sysUnitsService;
  @Autowired
  protected ISysPrivilegeListDao sysPrivilegeListDao;
  
  protected HttpSession getSession(HttpServletRequest request)
  {
    HttpSession session = request.getSession();
    
    return session;
  }
  
  protected String getSessionId(HttpServletRequest request)
  {
    HttpSession session = getSession(request);
    if (session != null) {
      return session.getId();
    }
    return "0";
  }
  
  public void updateUserCach(HttpServletRequest request, SysUser sysUser)
  {
    long unitId = sysUser.getUnitId();
    long userId = sysUser.getId();
    
    SysUnits sysUnits = this.sysUnitsService.get(unitId);
    long sysRoleId = unitId == 1L ? -1L : this.sysPrivilegeListDao.getSysRoleIdByUnitId(unitId);
    
    SysIndustry sysIndustry = this.sysIndustryService.get(sysUnits.getInstId());
    if (sysIndustry == null)
    {
      sysIndustry = new SysIndustry();
      sysIndustry.setId(0L);
    }
    Map<String, Object> userMap = new HashMap();
    
    userMap.put(ConstantUtil.SessionKeys.IndustryKey.value(), sysIndustry);
    userMap.put(ConstantUtil.SessionKeys.UnitKey.value(), sysUnits);
    userMap.put(ConstantUtil.SessionKeys.UserKey.value(), sysUser);
    userMap.put(ConstantUtil.SessionKeys.SysRoleKey.value(), Long.valueOf(sysRoleId));
    

    getSession(request).setAttribute(getCurrentUserKey(request), userMap);
  }
  
  public void clearUserCach(HttpServletRequest request)
  {
    getSession(request).removeAttribute(getCurrentUserKey(request));
  }
  
  public boolean isUserLogin(HttpServletRequest request)
  {
    Map<String, Object> userMap = getUserMap(request);
    if ((userMap != null) && (!userMap.isEmpty()))
    {
      getSession(request).setAttribute(getCurrentUserKey(request), userMap);
      return true;
    }
    return false;
  }
  
  private Map<String, Object> getUserMap(HttpServletRequest request)
  {
    Object obj = getSession(request).getAttribute(getCurrentUserKey(request));
    Map<String, Object> userMap = null;
    if ((obj != null) && ((obj instanceof Map))) {
      userMap = (Map)obj;
    }
    return userMap;
  }
  
  public long getCurrentInstId(HttpServletRequest request)
  {
    SysIndustry sysIndustry = getCurrentIndustry(request);
    
    return sysIndustry != null ? sysIndustry.getId() : 0L;
  }
  
  public long getCurrentUnitId(HttpServletRequest request)
  {
    SysUnits sysUnits = getCurrentUnit(request);
    return sysUnits != null ? sysUnits.getId() : 0L;
  }
  
  public long getCurrentUserId(HttpServletRequest request)
  {
    SysUser sysUser = getCurrentUser(request);
    return sysUser != null ? sysUser.getId() : 0L;
  }
  
  public SysIndustry getCurrentIndustry(HttpServletRequest request)
  {
    Map<String, Object> userMap = getUserMap(request);
    SysIndustry sysIndustry = null;
    if ((userMap != null) && (!userMap.isEmpty()))
    {
      Object obj = userMap.get(ConstantUtil.SessionKeys.IndustryKey.value());
      if ((obj != null) && ((obj instanceof SysIndustry))) {
        sysIndustry = (SysIndustry)obj;
      }
    }
    return sysIndustry;
  }
  
  public SysUnits getCurrentUnit(HttpServletRequest request)
  {
    SysUnits sysUnits = null;
    Map<String, Object> userMap = getUserMap(request);
    if ((userMap != null) && (!userMap.isEmpty()))
    {
      Object obj = userMap.get(ConstantUtil.SessionKeys.UnitKey.value());
      if ((obj != null) && ((obj instanceof SysUnits))) {
        sysUnits = (SysUnits)obj;
      }
    }
    return sysUnits;
  }
  
  public SysUser getCurrentUser(HttpServletRequest request)
  {
    SysUser sysUser = null;
    Map<String, Object> userMap = getUserMap(request);
    if ((userMap != null) && (!userMap.isEmpty()))
    {
      Object obj = userMap.get(ConstantUtil.SessionKeys.UserKey.value());
      if ((obj != null) && ((obj instanceof SysUser))) {
        sysUser = (SysUser)obj;
      }
    }
    return sysUser;
  }
  
  public long getCurrentSysRoleId(HttpServletRequest request)
  {
    long sysRoleId = 0L;
    Map<String, Object> userMap = getUserMap(request);
    if ((userMap != null) && (!userMap.isEmpty()))
    {
      Object obj = userMap.get(ConstantUtil.SessionKeys.SysRoleKey.value());
      if (obj != null) {
        sysRoleId = Long.parseLong(obj + "");
      }
    }
    return sysRoleId;
  }
  
  private String getCurrentUserKey(HttpServletRequest request)
  {
    return getSessionId(request) + "_user";
  }
}
