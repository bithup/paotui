package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysParamType;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysParamTypeDaoW
{
  public abstract int add(SysParamType paramSysParamType);
  
  public abstract int update(SysParamType paramSysParamType);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
