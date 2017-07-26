package com.xgh.mng.dao.read;

import com.xgh.mng.entity.SysIndustry;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysIndustryDaoR
{
  public abstract SysIndustry get(long paramLong);
  
  public abstract SysIndustry getByNid(long paramLong);
  
  public abstract List<SysIndustry> getList(SysIndustry paramSysIndustry);
  
  public abstract List<SysIndustry> getListPage(Map<String, Object> paramMap);
  
  public abstract long getRows(Map<String, Object> paramMap);
  
  public abstract List<SysIndustry> getSysIndustry();
  
  public abstract List<Map<String, Object>> getIndustryTree(Map<String, Object> paramMap);
}
