package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysRole;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysRoleDaoW
{
  public abstract int add(SysRole paramSysRole);
  
  public abstract int update(SysRole paramSysRole);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
