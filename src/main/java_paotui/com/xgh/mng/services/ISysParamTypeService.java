package com.xgh.mng.services;

import com.xgh.mng.entity.SysParamType;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysParamTypeService
{
  public abstract int add(SysParamType paramSysParamType);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysParamType paramSysParamType);
  
  public abstract SysParamType get(long paramLong);
  
  public abstract Map<String, Object> getGirdData(HttpServletRequest paramHttpServletRequest);
  
  public abstract boolean isHasSameCode(String paramString, SysParamType paramSysParamType);
}
