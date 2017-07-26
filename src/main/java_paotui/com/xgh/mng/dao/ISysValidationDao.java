package com.xgh.mng.dao;

import com.xgh.mng.entity.SysValidation;
import java.util.List;
import java.util.Map;

public abstract interface ISysValidationDao
{
  public abstract int add(SysValidation paramSysValidation);
  
  public abstract int update(SysValidation paramSysValidation);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysValidation get(long paramLong);
  
  public abstract SysValidation getByNid(long paramLong);
  
  public abstract List<SysValidation> getList(Map<String, Object> paramMap);
  
  public abstract List<SysValidation> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract int deleteByJoinType(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysValidation> paramList);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract List<SysValidation> getListByJoinType(Map<String, Object> paramMap);
}
