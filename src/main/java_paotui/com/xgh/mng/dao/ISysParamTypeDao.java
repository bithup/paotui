package com.xgh.mng.dao;

import com.xgh.mng.entity.SysParamType;
import java.util.List;
import java.util.Map;

public abstract interface ISysParamTypeDao
{
  public abstract int add(SysParamType paramSysParamType);
  
  public abstract int update(SysParamType paramSysParamType);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysParamType get(long paramLong);
  
  public abstract SysParamType getByNid(long paramLong);
  
  public abstract List<SysParamType> getList(SysParamType paramSysParamType);
  
  public abstract List<SysParamType> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract long getRowsByCode(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getTreeList();
  
  public abstract SysParamType getParamTypeByCode(String paramString);
}
