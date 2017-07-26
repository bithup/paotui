package com.xgh.mng.services;

import com.xgh.mng.entity.SysQueryItem;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysQueryItemService
{
  public abstract int add(SysQueryItem paramSysQueryItem);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysQueryItem paramSysQueryItem);
  
  public abstract SysQueryItem get(long paramLong);
  
  public abstract Map<String, Object> getGirdData(Map<String, Object> paramMap);
  
  public abstract void saveQueryItem(long paramLong, String paramString, List<Map<String, Object>> paramList);
  
  public abstract String getConditionHtmlByRequest(HttpServletRequest paramHttpServletRequest, long paramLong);
  
  public abstract String getConditionHtmlByCode(String paramString, long paramLong);
}
