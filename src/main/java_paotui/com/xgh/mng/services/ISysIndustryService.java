package com.xgh.mng.services;

import com.xgh.mng.entity.SysIndustry;
import java.util.List;
import java.util.Map;

public abstract interface ISysIndustryService
{
  public abstract int add(SysIndustry paramSysIndustry);
  
  public abstract int delete(long paramLong);
  
  public abstract int update(SysIndustry paramSysIndustry);
  
  public abstract SysIndustry get(long paramLong);
  
  public abstract List<SysIndustry> getList(SysIndustry paramSysIndustry);
  
  public abstract Map<String, Object> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<SysIndustry> getSysIndustry();
  
  public abstract long save(SysIndustry paramSysIndustry, String paramString);
  
  public abstract int check(SysIndustry paramSysIndustry);
  
  public abstract List<Map<String, Object>> getIndustryTree(Map<String, Object> paramMap);
}
