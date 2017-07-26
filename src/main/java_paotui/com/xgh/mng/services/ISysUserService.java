package com.xgh.mng.services;

import com.xgh.mng.entity.SysDeptUser;
import com.xgh.mng.entity.SysUser;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysUserService
{
  public abstract int add(SysUser paramSysUser);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysUser paramSysUser);
  
  public abstract SysUser get(long paramLong);
  
  public abstract Map<String, Object> getGridData(HttpServletRequest paramHttpServletRequest, long paramLong);
  
  public abstract void save(SysUser paramSysUser, SysDeptUser paramSysDeptUser, String paramString);
  
  public abstract boolean isHasSameAcount(SysUser paramSysUser, String paramString);
  
  public abstract boolean isLastDeptForUser(long paramLong);
  
  public abstract SysUser getLoginCheckUser(String paramString1, String paramString2);
  
  public abstract void move(HttpServletRequest paramHttpServletRequest, String paramString);
  
  public abstract List<SysUser> getListByUnitId(long paramLong);
  
  public abstract SysUser getAdmin(long paramLong);
}
