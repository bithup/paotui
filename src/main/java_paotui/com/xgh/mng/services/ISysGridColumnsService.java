package com.xgh.mng.services;

import com.xgh.mng.entity.SysGridColumns;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface ISysGridColumnsService
{
  public abstract int add(SysGridColumns paramSysGridColumns);
  
  public abstract int delete(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int update(SysGridColumns paramSysGridColumns);
  
  public abstract SysGridColumns get(long paramLong);
  
  public abstract List<SysGridColumns> getList(Map<String, Object> paramMap);
  
  public abstract List<SysGridColumns> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract Map<String, Object> getGridData(Map<String, Object> paramMap);
  
  public abstract void saveGridColumns(long paramLong, String paramString, List<Map<String, Object>> paramList);
  
  public abstract String getGridColumsByRequest(HttpServletRequest paramHttpServletRequest);
  
  public abstract String getGridColumsByCode(String paramString);
}
