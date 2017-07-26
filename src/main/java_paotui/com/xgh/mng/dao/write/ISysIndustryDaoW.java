package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysIndustry;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysIndustryDaoW
{
  public abstract int add(SysIndustry paramSysIndustry);
  
  public abstract int update(SysIndustry paramSysIndustry);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
