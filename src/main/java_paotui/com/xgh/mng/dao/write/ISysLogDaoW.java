package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysLog;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysLogDaoW
{
  public abstract int add(SysLog paramSysLog);
  
  public abstract int update(SysLog paramSysLog);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
