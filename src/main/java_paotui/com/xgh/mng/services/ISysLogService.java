package com.xgh.mng.services;

import com.xgh.mng.entity.SysLog;
import com.xgh.mng.entity.SysUser;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysLogService
{
  public abstract int add(SysLog paramSysLog);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysLog paramSysLog);
  
  public abstract SysLog get(long paramLong);
  
  public abstract int addLog(HttpServletRequest paramHttpServletRequest, SysUser paramSysUser, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract int addLog(HttpServletRequest paramHttpServletRequest, SysUser paramSysUser, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6);
  
  public abstract Map<String, Object> getGridData(HttpServletRequest paramHttpServletRequest, long paramLong);
}
