package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysUnits;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysUnitsDaoW
{
  public abstract int add(SysUnits paramSysUnits);
  
  public abstract int update(SysUnits paramSysUnits);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
