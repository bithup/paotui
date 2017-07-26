package com.xgh.mng.dao;

import com.xgh.mng.entity.SysIndustry;
import java.util.List;
import java.util.Map;

public abstract interface ISysIndustryDao
{
  public abstract int add(SysIndustry paramSysIndustry);
  
  public abstract int update(SysIndustry paramSysIndustry);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
  
  public abstract SysIndustry get(long paramLong);
  
  public abstract SysIndustry getByNid(long paramLong);
  
  public abstract List<SysIndustry> getList(SysIndustry paramSysIndustry);
  
  public abstract List<SysIndustry> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<SysIndustry> getSysIndustry();
  
  public abstract List<Map<String, Object>> getIndustryTree(Map<String, Object> paramMap);
}
