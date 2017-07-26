package com.xgh.mng.services;

import com.xgh.mng.entity.SysType;
import java.util.List;
import java.util.Map;

public abstract interface ISysTypeService
{
  public abstract int add(SysType paramSysType);
  
  public abstract int delete(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int update(SysType paramSysType);
  
  public abstract SysType get(long paramLong);
  
  public abstract List<SysType> getList(Map<String, Object> paramMap);
  
  public abstract List<SysType> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getChildTreeData(String paramString, Long paramLong);
  
  public abstract List<Map<String, Object>> getTreeData(String paramString);
  
  public abstract Map<String, Object> getGridData(String paramString, long paramLong);
  
  public abstract void save(String paramString, long paramLong);
  
  public abstract boolean isHasSameCode(String paramString, SysType paramSysType);
  
  public abstract boolean isHasChild(SysType paramSysType);
}
