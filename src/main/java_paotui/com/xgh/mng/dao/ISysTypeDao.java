package com.xgh.mng.dao;

import com.xgh.mng.entity.SysType;
import java.util.List;
import java.util.Map;

public abstract interface ISysTypeDao
{
  public abstract int add(SysType paramSysType);
  
  public abstract int update(SysType paramSysType);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysType get(long paramLong);
  
  public abstract SysType getByNid(long paramLong);
  
  public abstract List<SysType> getList(Map<String, Object> paramMap);
  
  public abstract List<SysType> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysType> paramList);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getTreeData(Map<String, Object> paramMap);
  
  public abstract long getChildTypeRows(long paramLong);
  
  public abstract long getSameCodeRows(Map<String, Object> paramMap);
  
  public abstract SysType getSysTypeByCode(Map<String, Object> paramMap);
}
