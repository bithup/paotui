package com.xgh.mng.dao;

import com.xgh.mng.entity.SysQueryItem;
import java.util.List;
import java.util.Map;

public abstract interface ISysQueryItemDao
{
  public abstract int add(SysQueryItem paramSysQueryItem);
  
  public abstract int update(SysQueryItem paramSysQueryItem);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysQueryItem get(long paramLong);
  
  public abstract SysQueryItem getByNid(long paramLong);
  
  public abstract List<SysQueryItem> getList(Map<String, Object> paramMap);
  
  public abstract List<SysQueryItem> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysQueryItem> paramList);
  
  public abstract int deleteByJoinType(Map<String, Object> paramMap);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<SysQueryItem> getListByJoinType(Map<String, Object> paramMap);
}
