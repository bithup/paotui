package com.xgh.mng.services;

import com.xgh.mng.entity.SysValidation;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysValidationService
{
  public abstract int add(SysValidation paramSysValidation);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysValidation paramSysValidation);
  
  public abstract SysValidation get(long paramLong);
  
  public abstract Map<String, Object> getGridData(Map<String, Object> paramMap);
  
  public abstract void saveValidation(long paramLong, String paramString, List<Map<String, Object>> paramList);
  
  public abstract String getValidationByRequest(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getValidationByCode(String paramString);
}
