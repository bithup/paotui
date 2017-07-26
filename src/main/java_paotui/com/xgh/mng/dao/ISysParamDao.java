package com.xgh.mng.dao;

import com.xgh.mng.entity.SysParam;
import java.util.List;
import java.util.Map;

public abstract interface ISysParamDao
{
  public abstract int add(SysParam paramSysParam);
  
  public abstract int update(SysParam paramSysParam);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysParam get(long paramLong);
  
  public abstract SysParam getByNid(long paramLong);
  
  public abstract List<SysParam> getList(SysParam paramSysParam);
  
  public abstract List<SysParam> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract int addBatch(List<SysParam> paramList);
  
  public abstract List<Map<String, Object>> getListMap(Map<String, Object> paramMap);
  
  public abstract long getListRows(Map<String, Object> paramMap);
  
  public abstract long getRowsByTypeIdAndCode(Map<String, Object> paramMap);
  
  public abstract SysParam getByTypeIdAndCode(Map<String, Object> paramMap);
  
  public abstract List<SysParam> getListByTypeId(Map<String, Object> paramMap);
}
