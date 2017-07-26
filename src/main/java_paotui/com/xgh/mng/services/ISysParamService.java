package com.xgh.mng.services;

import com.xgh.mng.entity.SysParam;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysParamService
{
  public abstract int add(SysParam paramSysParam);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysParam paramSysParam);
  
  public abstract SysParam get(long paramLong);
  
  public abstract Map<String, Object> getGirdData(HttpServletRequest paramHttpServletRequest, long paramLong);
  
  public abstract String getParamTypeTreeData();
  
  public abstract boolean isHasSameCode(String paramString, SysParam paramSysParam);
  
  public abstract void save(String paramString, long paramLong, SysParam paramSysParam);
  
  public abstract SysParam getSysParamByCode(long paramLong, String paramString1, String paramString2);
  
  public abstract List<SysParam> getSysParamListByTypeCode(long paramLong, String paramString);
}
