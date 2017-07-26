package com.xgh.mng.dao;

import com.xgh.mng.entity.SysGridColumns;
import java.util.List;
import java.util.Map;

public abstract interface ISysGridColumnsDao
{
  public abstract int add(SysGridColumns paramSysGridColumns);
  
  public abstract int update(SysGridColumns paramSysGridColumns);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract int deleteByIds(List<Long> paramList);
  
  public abstract SysGridColumns get(long paramLong);
  
  public abstract SysGridColumns getByNid(long paramLong);
  
  public abstract List<SysGridColumns> getList(Map<String, Object> paramMap);
  
  public abstract List<SysGridColumns> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract int deleteByJoinType(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysGridColumns> paramList);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<SysGridColumns> getListByJoinType(Map<String, Object> paramMap);
}
