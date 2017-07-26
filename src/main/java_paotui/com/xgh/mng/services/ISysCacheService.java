package com.xgh.mng.services;

import com.xgh.mng.entity.SysIndustry;
import com.xgh.mng.entity.SysUnits;
import com.xgh.mng.entity.SysUser;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysCacheService
{
  public abstract void updateUserCach(HttpServletRequest paramHttpServletRequest, SysUser paramSysUser);
  
  public abstract void clearUserCach(HttpServletRequest paramHttpServletRequest);
  
  public abstract boolean isUserLogin(HttpServletRequest paramHttpServletRequest);
  
  public abstract long getCurrentInstId(HttpServletRequest paramHttpServletRequest);
  
  public abstract long getCurrentUnitId(HttpServletRequest paramHttpServletRequest);
  
  public abstract long getCurrentUserId(HttpServletRequest paramHttpServletRequest);
  
  public abstract SysIndustry getCurrentIndustry(HttpServletRequest paramHttpServletRequest);
  
  public abstract SysUser getCurrentUser(HttpServletRequest paramHttpServletRequest);
  
  public abstract SysUnits getCurrentUnit(HttpServletRequest paramHttpServletRequest);
  
  public abstract long getCurrentSysRoleId(HttpServletRequest paramHttpServletRequest);
}
