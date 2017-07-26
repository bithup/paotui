package com.xgh.mng.dao.write;

import com.xgh.mng.entity.SysUser;
import org.springframework.stereotype.Component;

@Component
public abstract interface ISysUserDaoW
{
  public abstract int add(SysUser paramSysUser);
  
  public abstract int update(SysUser paramSysUser);
  
  public abstract int deleteById(long paramLong);
  
  public abstract int deleteByNid(long paramLong);
}
